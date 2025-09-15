package co.analisys.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatosEntrenamiento {

    private Long id;
    private Miembro miembro;
    private Entrenador entrenador;
    private String tipoEntrenamiento;
    private int duration;


    
}
