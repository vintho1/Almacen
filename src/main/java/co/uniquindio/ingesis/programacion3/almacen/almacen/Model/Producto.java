package co.uniquindio.ingesis.programacion3.almacen.almacen.Model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data @SuperBuilder
public class Producto {

    private String codigo, nombre, descripcion;

    private String  valorUnitario;

    private String cantidadExistencia;

}
