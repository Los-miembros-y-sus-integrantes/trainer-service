package co.analisys.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DatosEntrenamientoDTO {

    private String idUsuario;
    private String fecha;
    private String hora;
    private String duracion;
    private String tipoEjercicio;
}