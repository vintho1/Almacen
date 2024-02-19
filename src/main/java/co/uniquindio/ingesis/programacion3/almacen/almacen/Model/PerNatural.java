package co.uniquindio.ingesis.programacion3.almacen.almacen.Model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Data @SuperBuilder
public class PerNatural extends Clientee {

    private String mail ;
    private LocalDate fechaNacimiento;
}
