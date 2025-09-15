package co.analisys.biblioteca.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonSerde;

import co.analisys.biblioteca.model.DatosEntrenamiento;
import co.analisys.biblioteca.model.ResumenEntrenamiento;

@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:29092}")
    private String bootstrapServers;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "trainer-streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public KStream<String, DatosEntrenamiento> kStream(StreamsBuilder streamsBuilder) {
        JsonSerde<DatosEntrenamiento> datosEntrenamientoSerde = new JsonSerde<>(DatosEntrenamiento.class);
        JsonSerde<ResumenEntrenamiento> resumenSerde = new JsonSerde<>(ResumenEntrenamiento.class);
        
        KStream<String, DatosEntrenamiento> stream = streamsBuilder.stream("datos-entrenamiento", 
            org.apache.kafka.streams.kstream.Consumed.with(Serdes.String(), datosEntrenamientoSerde));
        
        // Add logging to see what's being processed
        stream.peek((key, value) -> {
            System.out.println("Processing training data: key=" + key + ", member=" + 
                (value.getMiembro() != null ? value.getMiembro().getId() : "null") + 
                ", duration=" + value.getDuration());
        });
        
        stream.groupByKey()
        .windowedBy(TimeWindows.of(Duration.ofDays(7)))
        .aggregate(
            () -> new ResumenEntrenamiento(),
            (key, value, aggregate) -> {
                ResumenEntrenamiento updated = aggregate.actualizar(value);
                System.out.println("Aggregating for key: " + key + ", total duration: " + 
                    updated.getTotalDuration() + ", session count: " + updated.getSessionCount());
                return updated;
            },
            Materialized.<String, ResumenEntrenamiento, org.apache.kafka.streams.state.WindowStore<org.apache.kafka.common.utils.Bytes, byte[]>>as("resumen-entrenamiento-store")
                .withKeySerde(Serdes.String())
                .withValueSerde(resumenSerde)
        )
        .toStream()
        .map((windowedKey, value) -> {
            // Convert windowed key to simple string key
            String simpleKey = windowedKey.key() + "-" + windowedKey.window().start() + "-" + windowedKey.window().end();
            System.out.println("Sending to output topic: key=" + simpleKey + ", value=" + value);
            return new org.apache.kafka.streams.KeyValue<>(simpleKey, value);
        })
        .to("resumen-entrenamiento", org.apache.kafka.streams.kstream.Produced.with(
            Serdes.String(),
            resumenSerde));
        
        return stream;
    }
}