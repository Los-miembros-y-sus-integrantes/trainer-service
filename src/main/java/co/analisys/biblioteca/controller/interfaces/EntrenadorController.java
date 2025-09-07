package co.analisys.biblioteca.controller.interfaces;

import co.analisys.biblioteca.model.Entrenador;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RequestMapping("/entrenadores")
@Tag(name = "Entrenador", description = "Operaciones relacionadas con los entrenadores")
public interface EntrenadorController {

    @PostMapping
    @Operation(summary = "Agregar un nuevo entrenador")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Entrenador agregado exitosamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public Entrenador agregarEntrenador(@RequestBody Entrenador entrenador);
    
    @GetMapping
    @Operation(summary = "Listar todos los entrenadores")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Entrenadores listados exitosamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "No se encontraron entrenadores")
    })
    public List<Entrenador> listarEntrenadores();
}
