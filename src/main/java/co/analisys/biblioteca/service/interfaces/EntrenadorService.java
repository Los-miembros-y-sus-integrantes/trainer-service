package co.analisys.biblioteca.service.interfaces;

import co.analisys.biblioteca.model.Entrenador;

import java.util.List;

public interface EntrenadorService {

    public Entrenador agregarEntrenador(Entrenador entrenador);
    public List<Entrenador> obtenerTodosEntrenadores();
}
