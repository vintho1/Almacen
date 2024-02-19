package co.uniquindio.ingesis.programacion3.almacen.almacen.Controllers;
import co.uniquindio.ingesis.programacion3.almacen.almacen.Model.Almacen;
import co.uniquindio.ingesis.programacion3.almacen.almacen.Model.Clientee;
import co.uniquindio.ingesis.programacion3.almacen.almacen.Model.Almacen.*;
import co.uniquindio.ingesis.programacion3.almacen.almacen.Model.PerJuridica;
import co.uniquindio.ingesis.programacion3.almacen.almacen.Model.PerNatural;
import co.uniquindio.ingesis.programacion3.almacen.almacen.exceptions.AtributoVacioException;
import co.uniquindio.ingesis.programacion3.almacen.almacen.exceptions.InformacionRepetidaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.java.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log

public class VentanaPrincipalController implements Initializable {

    private final Almacen almacen = Almacen.getInstance();

    @FXML
    private TextField apellidoTxt;

    @FXML
    private ImageView cerrarVentana1;

    @FXML
    private AnchorPane clienteAnchorPane;

    @FXML
    private Button clienteButton;

    @FXML
    private TextField direccionTxt;

    @FXML
    private TextField identificacionTxt;

    @FXML
    private DatePicker fechaDP;

    @FXML
    private Button cancelarButton;

    @FXML
    private Button guardarButton;

    @FXML
    private TextField nombreTxt;

    @FXML
    private TextField mailTxt;

    @FXML
    private TextField nitTxt;

    @FXML
    private AnchorPane productoAnchorPane;

    @FXML
    private AnchorPane perJuridicaAp;

    @FXML
    private AnchorPane pernaturalAp;


    @FXML
    private Button productoButton;

    @FXML
    private TextField telefonoTxt;

    @FXML
    private ComboBox<String> tipoperComboBox;

    @FXML
    private TableView<Clientee> clienteTBL;

    private ContextMenu cmOpciones;

    private Clientee clienteselect;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String[] opcione = {"natural","juridica"};
        ObservableList<String> items = FXCollections.observableArrayList(opcione);
        tipoperComboBox.setItems(items);
        tipoperComboBox.setValue("seleccione");

        cargarClientes();

        cmOpciones = new ContextMenu();
        MenuItem miEditar  = new MenuItem("Editar");
        MenuItem miEliminar = new MenuItem("Eliminar");
        cmOpciones.getItems().addAll(miEditar,miEliminar);

        miEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = clienteTBL.getSelectionModel().getSelectedIndex();
                Clientee clienteEliminar = clienteTBL.getItems().get(index);
                almacen.eliminatCliente(clienteEliminar.getCedula());

                cargarClientes();
            }
        });
        miEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                int index = clienteTBL.getSelectionModel().getSelectedIndex();
                clienteselect = clienteTBL.getItems().get(index);
                System.out.println(clienteselect);


                nombreTxt.setText(clienteselect.getNombre());
                apellidoTxt.setText(clienteselect.getApellido());
                identificacionTxt.setText(clienteselect.getCedula());
                direccionTxt.setText(clienteselect.getDirecciion());
                telefonoTxt.setText(clienteselect.getTelefono());


                if(clienteselect instanceof PerNatural){
                    visivlitiesPanesMnini(false,true);
                    mailTxt.setText(((PerNatural) clienteselect).getMail());
                    fechaDP.setValue(((PerNatural) clienteselect).getFechaNacimiento());

                }else if(clienteselect instanceof PerJuridica){
                    visivlitiesPanesMnini(true,false);
                    nitTxt.setText(((PerJuridica) clienteselect).getNit());

                }

                cancelarButton.setDisable(false);

            }
        });

        clienteTBL.setContextMenu(cmOpciones);

    }




    public void onExitButtonClick( ) {
        Stage stage = (Stage) cerrarVentana1.getScene().getWindow();
        stage.close();
    }

    public void visivlitiesPanes(boolean pane1,boolean pane2){
        clienteAnchorPane.setVisible(pane1);
        productoAnchorPane.setVisible(pane2);
    }

    public void visivlitiesPanesMnini(boolean pane1,boolean pane2){
        perJuridicaAp.setVisible(pane1);
        pernaturalAp.setVisible(pane2);
        log.info("pane1"+pane1);
        log.info("pane2"+pane2);
    }

    public void onClientesButtonClick(ActionEvent e) {
        if(e.getTarget()== clienteButton ){visivlitiesPanes(true,false);}
        if(e.getTarget()== productoButton){visivlitiesPanes(false,true);}
    }

    public void onGuardarClientButtonClick(){


        if(clienteselect == null){
            try {
                almacen.registrarCliente(nombreTxt.getText(), apellidoTxt.getText(), identificacionTxt.getText(), direccionTxt.getText(), telefonoTxt.getText(), mailTxt.getText(), fechaDP.getValue(), nitTxt.getText(), tipoperComboBox.getValue());
            } catch (AtributoVacioException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InformacionRepetidaException e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("jujujui");
            alert.setHeaderText(null);
            alert.setContentText("se registro correctamente la tarea");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();

            limpiarcamposCliente();
            cargarClientes();
        }else{

            clienteselect.setNombre(nombreTxt.getText());
            clienteselect.setApellido(apellidoTxt.getText());
            clienteselect.setCedula(identificacionTxt.getText());
            clienteselect.setDirecciion(direccionTxt.getText());
            clienteselect.setTelefono(telefonoTxt.getText());


            if(clienteselect instanceof PerNatural){
                visivlitiesPanesMnini(false,true);
                ((PerNatural) clienteselect).setMail(mailTxt.getText());
                ((PerNatural) clienteselect).setFechaNacimiento(fechaDP.getValue());
            }else if(clienteselect instanceof PerJuridica){
                visivlitiesPanesMnini(true,false);
                ((PerJuridica) clienteselect).setNit(nitTxt.getText());
            }
            limpiarcamposCliente();
            cargarClientes();
            clienteselect = null;
            cancelarButton.setDisable(true);



        }


    }

    private void limpiarcamposCliente(){
        nombreTxt.setText("");
        apellidoTxt.setText("");
        identificacionTxt.setText("");
        direccionTxt.setText("");
        telefonoTxt.setText("");
        mailTxt.setText("");
        nitTxt.setText("");
        fechaDP.setValue(null);
        tipoperComboBox.getSelectionModel().select("seleccione");
    }


    public void onabretecesamoClick(ActionEvent e) {

        String value = tipoperComboBox.getValue();

        if(value.equals("natural")){
            visivlitiesPanesMnini(false,true);

        }
        else if(value.equals("juridica")){
            visivlitiesPanesMnini(true,false);
        }

    }

    public void cargarClientes(){
        clienteTBL.getItems().clear();
        clienteTBL.getColumns().clear();


        ObservableList<Clientee> observableList = clienteTBL.getItems();
        observableList.addAll(almacen.getListaClientees());

        TableColumn nombrecol = new TableColumn("Mombre");
        TableColumn apellidocol = new TableColumn("Apellido");
        TableColumn cedulacol = new TableColumn("Id");
        TableColumn direccioncol = new TableColumn("Direccion");
        TableColumn telefonocol = new TableColumn("Telefono");
        TableColumn nitcol = new TableColumn("Nit");
        TableColumn mailcol = new TableColumn("Mail");
        TableColumn fechacol = new TableColumn("Fecha Nacimiento");

        nombrecol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidocol.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        cedulacol.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        direccioncol.setCellValueFactory(new PropertyValueFactory<>("direcciion"));
        telefonocol.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        nitcol.setCellValueFactory(new PropertyValueFactory<>("nit"));
        mailcol.setCellValueFactory(new PropertyValueFactory<>("mail"));
        fechacol.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        clienteTBL.setItems(observableList);
        clienteTBL.getColumns().addAll(nombrecol,apellidocol,cedulacol,direccioncol,telefonocol,nitcol,mailcol,fechacol);



    }

    public void btnCancelaronCtion(ActionEvent event) {
        clienteselect = null;
        limpiarcamposCliente();
        cancelarButton.setDisable(true);


    }
}