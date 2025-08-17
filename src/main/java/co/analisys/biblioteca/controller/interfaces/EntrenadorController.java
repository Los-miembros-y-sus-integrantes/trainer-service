package co.analisys.biblioteca.controller.interfaces;

import co.analisys.biblioteca.model.Entrenador;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/entrenadores")
public interface EntrenadorController {

    @PostMapping
    public Entrenador agregarEntrenador(@RequestBody Entrenador entrenador);
    @GetMapping
    public List<Entrenador> listarEntrenadores();
}
