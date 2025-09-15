package co.analisys.biblioteca.controller.interfaces;

import co.analisys.biblioteca.model.DatosEntrenamiento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/datos-entrenamiento")
@Tag(name = "Datos Entrenamiento", description = "Operaciones relacionadas con los datos de entrenamiento")
public interface DatosEntrenamientoController {

    @PostMapping
    @Operation(summary = "Crear nuevos datos de entrenamiento")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Datos de entrenamiento creados exitosamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Miembro o entrenador no encontrado")
    })
    DatosEntrenamiento crearDatosEntrenamiento(
            @RequestParam Long miembroId,
            @RequestParam Long entrenadorId,
            @RequestParam String tipoEntrenamiento,
            @RequestParam int duration
    );

    @GetMapping("/validar-miembro/{miembroId}")
    @Operation(summary = "Validar si un miembro existe")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Validación completada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error en la validación")
    })
    boolean validarMiembro(@PathVariable Long miembroId);
}