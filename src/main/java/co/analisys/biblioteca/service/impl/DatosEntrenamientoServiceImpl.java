package co.analisys.biblioteca.service.impl;

import co.analisys.biblioteca.client.MiembroClient;
import co.analisys.biblioteca.model.DatosEntrenamiento;
import co.analisys.biblioteca.model.Entrenador;
import co.analisys.biblioteca.model.Miembro;
import co.analisys.biblioteca.repository.EntrenadorRepository;
import co.analisys.biblioteca.service.interfaces.DatosEntrenamientoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatosEntrenamientoServiceImpl implements DatosEntrenamientoService {

    private final MiembroClient miembroClient;
    private final EntrenadorRepository entrenadorRepository;
    private final KafkaTemplate<String, DatosEntrenamiento> kafkaTemplate;
    
    private static final String TOPIC_DATOS_ENTRENAMIENTO = "datos-entrenamiento";

    @Override
    public DatosEntrenamiento crearDatosEntrenamiento(Long miembroId, Long entrenadorId, String tipoEntrenamiento, int duration) {
        // Validate member exists
        if (!validarMiembroExiste(miembroId)) {
            throw new RuntimeException("El miembro con ID " + miembroId + " no existe");
        }
        
        // Validate trainer exists
        Entrenador entrenador = entrenadorRepository.findById(entrenadorId)
                .orElseThrow(() -> new RuntimeException("El entrenador con ID " + entrenadorId + " no existe"));
        
        // Get member data
        Miembro miembro;
        try {
            miembro = miembroClient.obtenerPorId(miembroId);
            if (miembro == null) {
                throw new RuntimeException("No se pudo obtener la información del miembro con ID " + miembroId);
            }
        } catch (Exception e) {
            log.error("Error al obtener miembro con ID {}: {}", miembroId, e.getMessage());
            throw new RuntimeException("Error al comunicarse con el servicio de miembros", e);
        }
        
        // Create training data
        DatosEntrenamiento datosEntrenamiento = new DatosEntrenamiento(
                null, // ID will be auto-generated if needed
                miembro,
                entrenador,
                tipoEntrenamiento,
                duration
        );
        
        // Publish to Kafka topic
        try {
            String key = "member-" + miembroId + "-trainer-" + entrenadorId;
            kafkaTemplate.send(TOPIC_DATOS_ENTRENAMIENTO, key, datosEntrenamiento);
            log.info("Datos de entrenamiento enviados al topic '{}' con key '{}'", TOPIC_DATOS_ENTRENAMIENTO, key);
        } catch (Exception e) {
            log.error("Error al enviar datos de entrenamiento a Kafka: {}", e.getMessage());
            throw new RuntimeException("Error al publicar datos de entrenamiento", e);
        }
        
        return datosEntrenamiento;
    }

    @Override
    public boolean validarMiembroExiste(Long miembroId) {
        try {
            Miembro miembro = miembroClient.obtenerPorId(miembroId);
            return miembro != null;
        } catch (Exception e) {
            log.error("Error al validar existencia del miembro con ID {}: {}", miembroId, e.getMessage());
            return false;
        }
    }
}