package co.analisys.biblioteca.controller.impl;

import co.analisys.biblioteca.model.DatosEntrenamiento;
import co.analisys.biblioteca.service.interfaces.DatosEntrenamientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final DatosEntrenamientoService datosEntrenamientoService;

    /**
     * Test endpoint to create sample training data
     * Example usage: POST /test/crear-entrenamiento-ejemplo
     */
    @PostMapping("/crear-entrenamiento-ejemplo")
    public DatosEntrenamiento crearEjemploEntrenamiento() {
        // Create sample training data
        // Assuming member ID 1 and trainer ID 1 exist
        return datosEntrenamientoService.crearDatosEntrenamiento(
                1L,  // miembroId
                1L,  // entrenadorId
                "Cardio",  // tipoEntrenamiento
                45  // duration in minutes
        );
    }

    /**
     * Test endpoint to validate if a member exists
     * Example usage: GET /test/validar-miembro/1
     */
    @GetMapping("/validar-miembro/{id}")
    public String validarMiembro(@PathVariable Long id) {
        boolean exists = datosEntrenamientoService.validarMiembroExiste(id);
        return "Miembro con ID " + id + " " + (exists ? "existe" : "no existe");
    }
}