package co.uniquindio.ingesis.programacion3.almacen.almacen.Model;
import co.uniquindio.ingesis.programacion3.almacen.almacen.exceptions.AtributoVacioException;
import co.uniquindio.ingesis.programacion3.almacen.almacen.exceptions.InformacionRepetidaException;
import javafx.scene.control.Alert;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

@Log
@Getter
public class Almacen {

         List<Producto> listaProductors;
         List<Clientee> listaClientees;
    private static Almacen almacen;

    private Almacen(){

        listaClientees = new ArrayList<>();

        try {

            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter( new SimpleFormatter());
            log.addHandler(fh);

        }catch (IOException e){
            log.severe(e.getMessage());
        }

    }



    public static Almacen getInstance(){
        if(almacen == null){
            almacen = new Almacen();
        }

        log.info("se intancia el singleton");

        return almacen;
    }



    public void registrarCliente(String nombre, String apellido, String cedula, String direccion, String telefono, String mail, LocalDate fechaNacimiento, String nit, String option) throws AtributoVacioException, InformacionRepetidaException, IOException {

        if (nombre == null || nombre.isBlank() || apellido == null ||apellido.isBlank() || cedula == null || cedula.isBlank()){
            crearAlertaError(("textoTituloAlertaErrorAtributoVacio"), ("textoContenidoAlertaErrorAtributoVacio"));
            log.info("Se ha hecho un intento de registro de cliente con campos vacios.");
            throw new AtributoVacioException(("textoAtributoVacioException"));
        }

        if (listaClientees.stream().anyMatch(cliente -> cliente.getCedula().equals(cedula))){
            crearAlertaError(("textoTituloAlertaErrorInformacionRepetida"), ("textoContenidoAlertaErrorInformacionRepetida"));
            log.info("Se ha hecho un intento de registro de cliente con datos repetidos.");
            throw new InformacionRepetidaException(("textoInformacionRepetidaException"));
        }


        if(option.equals("natural")){

            PerNatural perNatural = PerNatural.builder()
                    .nombre(nombre)
                    .apellido(apellido)
                    .cedula(cedula)
                    .direcciion(direccion)
                    .telefono(telefono)
                    .mail(mail)
                    .fechaNacimiento(fechaNacimiento)
                    .build();


            listaClientees.add(perNatural);

            log.info("Se ha registrado un cliente natural " + cedula);


        } else if (option.equals("juridica")){



            PerJuridica perJuridica = PerJuridica.builder()
                    .nombre(nombre)
                    .apellido(apellido)
                    .cedula(cedula)
                    .direcciion(direccion)
                    .telefono(telefono)
                    .nit(nit)
                    .build();

            listaClientees.add(perJuridica);

            log.info("Se ha registrado un cliente juridico  " + cedula);

        }
    }

    public void eliminatCliente(String cedula){
        listaClientees = listaClientees.stream().filter(clientee -> !clientee.getCedula().equals(cedula)).collect(Collectors.toList());

    }
    public void registrarProducto(String codigo,String nombre,String descripcion,double valorUnitario, int cantidadExistencia,String codigoAprovado,double temperatura,LocalDate vencimiento, LocalDate fechaEnvasado,double pesoEnvase){

    }











//    public void registratCLiente (String nombre,String apellido,String cedula,String direccion,String telefono) throws InformacionRepetidaException {
//        for (Clientee clientee : listaClientees){
//            if(clientee.getCedula().equals(cedula)){
//                throw new InformacionRepetidaException("ya esta");
//            }
//        }
//
//        Clientee nuevoClientee = new Clientee(nombre,apellido,cedula,direccion,telefono);
//        listaClientees.add(nuevoClientee);
//        System.out.println("cliente registrado exitosamente."+ cedula);
//    }







    public void crearAlertaError(String tituloError, String contenidoError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(tituloError);
        alert.setContentText(contenidoError);
        alert.show();
    }



}
