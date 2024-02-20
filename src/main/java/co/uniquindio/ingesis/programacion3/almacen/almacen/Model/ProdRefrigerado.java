package co.uniquindio.ingesis.programacion3.almacen.almacen.Model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data @SuperBuilder
public class ProdRefrigerado extends Producto{

    private String codigoAprovado;

    private String temperatura;

}
