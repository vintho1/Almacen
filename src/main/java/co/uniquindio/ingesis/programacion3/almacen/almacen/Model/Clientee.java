package co.uniquindio.ingesis.programacion3.almacen.almacen.Model;

import lombok.*;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode @Data
@SuperBuilder
public class Clientee {

    private String nombre;
    private String apellido;
    private String cedula;
    private String direcciion;
    private String telefono;

}
