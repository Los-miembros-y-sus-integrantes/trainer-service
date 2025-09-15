package co.analisys.biblioteca.service.interfaces;

import co.analisys.biblioteca.model.DatosEntrenamiento;

public interface DatosEntrenamientoService {
    
    /**
     * Creates training data for a member and trainer
     * @param miembroId ID of the member
     * @param entrenadorId ID of the trainer
     * @param tipoEntrenamiento Type of training (e.g., "Cardio", "Fuerza", "Yoga")
     * @param duration Duration in minutes
     * @return Created training data
     */
    DatosEntrenamiento crearDatosEntrenamiento(Long miembroId, Long entrenadorId, String tipoEntrenamiento, int duration);
    
    /**
     * Validates if a member exists by querying the member service
     * @param miembroId ID of the member to validate
     * @return true if member exists, false otherwise
     */
    boolean validarMiembroExiste(Long miembroId);
}