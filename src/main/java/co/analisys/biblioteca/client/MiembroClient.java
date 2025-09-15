package co.analisys.biblioteca.client;

import co.analisys.biblioteca.config.FeignConfig;
import co.analisys.biblioteca.model.Miembro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "member-service", url = "http://localhost:8083", configuration = FeignConfig.class)
public interface MiembroClient {

    @GetMapping("/miembros")
    List<Miembro> obtenerTodos();

    @GetMapping("/miembros/{id}")
    Miembro obtenerPorId(@PathVariable("id") Long id);
}