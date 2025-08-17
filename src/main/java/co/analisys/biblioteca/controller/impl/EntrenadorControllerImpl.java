package co.analisys.biblioteca.controller.impl;

import co.analisys.biblioteca.controller.interfaces.EntrenadorController;
import co.analisys.biblioteca.model.Entrenador;
import co.analisys.biblioteca.service.interfaces.EntrenadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EntrenadorControllerImpl implements EntrenadorController {

    private final EntrenadorService entrenadorService;

    @Override
    public Entrenador agregarEntrenador(Entrenador entrenador) {
        return entrenadorService.agregarEntrenador(entrenador);
    }

    @Override
    public List<Entrenador> listarEntrenadores() {
        return entrenadorService.obtenerTodosEntrenadores();
    }
}
