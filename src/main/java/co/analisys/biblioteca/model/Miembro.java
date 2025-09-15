package co.analisys.biblioteca.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Miembro {
    private Long id;
    private String nombre;
    private String email;
    private LocalDate fechaInscripcion;
}
