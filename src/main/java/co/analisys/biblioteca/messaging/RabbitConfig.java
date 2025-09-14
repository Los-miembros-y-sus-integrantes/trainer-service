package co.analisys.biblioteca.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // Unified exchange (updated from gimnasio.entrenador.exchange)
    public static final String EXCHANGE = "gimnasio.exchange";
    public static final String ROUTING_KEY_ENTRENADOR_CREADO = "entrenador.creado";
    public static final String QUEUE_DUMMY = "trainer.entrenador.creado.dummy"; // opcional para debug si quisieras

    @Bean
    public TopicExchange entrenadorExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    // Cola opcional local (no necesaria para publicar, pero útil si quieres ver internamente)
    @Bean
    public Queue dummyQueue() {
        return new Queue(QUEUE_DUMMY, false);
    }

    @Bean
    public Binding dummyBinding(Queue dummyQueue, TopicExchange entrenadorExchange) {
        return BindingBuilder.bind(dummyQueue).to(entrenadorExchange).with(ROUTING_KEY_ENTRENADOR_CREADO);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}
