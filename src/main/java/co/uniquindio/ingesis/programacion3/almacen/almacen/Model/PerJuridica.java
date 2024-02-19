package co.uniquindio.ingesis.programacion3.almacen.almacen.Model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data @SuperBuilder
public class PerJuridica extends Clientee {

    private String nit;
}
