package co.analisys.biblioteca.service.impl;

import co.analisys.biblioteca.model.Entrenador;
import co.analisys.biblioteca.repository.EntrenadorRepository;
import co.analisys.biblioteca.service.interfaces.EntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrenadorServiceImpl implements EntrenadorService {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @Override
    public Entrenador agregarEntrenador(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    @Override
    public List<Entrenador> obtenerTodosEntrenadores() {
        return entrenadorRepository.findAll();
    }
}
