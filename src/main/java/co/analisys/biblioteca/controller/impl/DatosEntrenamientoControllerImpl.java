package co.analisys.biblioteca.controller.impl;

import co.analisys.biblioteca.controller.interfaces.DatosEntrenamientoController;
import co.analisys.biblioteca.model.DatosEntrenamiento;
import co.analisys.biblioteca.service.interfaces.DatosEntrenamientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class DatosEntrenamientoControllerImpl implements DatosEntrenamientoController {

    private final DatosEntrenamientoService datosEntrenamientoService;

    @Override
    public DatosEntrenamiento crearDatosEntrenamiento(Long miembroId, Long entrenadorId, String tipoEntrenamiento, int duration) {
        try {
            return datosEntrenamientoService.crearDatosEntrenamiento(miembroId, entrenadorId, tipoEntrenamiento, duration);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("no existe")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public boolean validarMiembro(Long miembroId) {
        return datosEntrenamientoService.validarMiembroExiste(miembroId);
    }
}