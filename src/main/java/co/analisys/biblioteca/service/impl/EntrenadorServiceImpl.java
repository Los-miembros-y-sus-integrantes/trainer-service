package co.analisys.biblioteca.service.impl;

import co.analisys.biblioteca.model.Entrenador;
import co.analisys.biblioteca.repository.EntrenadorRepository;
import co.analisys.biblioteca.service.interfaces.EntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// RabbitMQ imports
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import co.analisys.biblioteca.messaging.EntrenadorCreadoEvent;
import static co.analisys.biblioteca.messaging.RabbitConfig.EXCHANGE;
import static co.analisys.biblioteca.messaging.RabbitConfig.ROUTING_KEY_ENTRENADOR_CREADO;

@Service
public class EntrenadorServiceImpl implements EntrenadorService {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Entrenador agregarEntrenador(Entrenador entrenador) {
        Entrenador guardado = entrenadorRepository.save(entrenador);
        // Publicar evento
    EntrenadorCreadoEvent event = new EntrenadorCreadoEvent(guardado.getId(), guardado.getNombre(), guardado.getEspecialidad());
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_ENTRENADOR_CREADO, event);
        return guardado;
    }

    @Override
    public List<Entrenador> obtenerTodosEntrenadores() {
        return entrenadorRepository.findAll();
    }
}
