package co.uniquindio.ingesis.programacion3.almacen.almacen.Model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;

@Data @SuperBuilder
public class ProdEvasado extends Producto {

    private LocalDate fechaEnvasado;
    private double pesoEnvase;
}
