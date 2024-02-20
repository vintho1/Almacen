package co.uniquindio.ingesis.programacion3.almacen.almacen.Controllers;
import co.uniquindio.ingesis.programacion3.almacen.almacen.Model.*;
import co.uniquindio.ingesis.programacion3.almacen.almacen.Model.Almacen.*;
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
//-----------------cliente---------------------------------
    @FXML
    private TextField nombreTxt;
    @FXML
    private TextField apellidoTxt;
    @FXML
    private TextField identificacionTxt;
    @FXML
    private TextField direccionTxt;
    @FXML
    private TextField telefonoTxt;
    @FXML
    private TextField mailTxt;
    @FXML
    private TextField nitTxt;
    @FXML
    private DatePicker fechaDP;

//----------productos-----------------------------------------
    @FXML
    private TextField codigoTxt;
    @FXML
    private TextField nombreProductoTxt;
    @FXML
    private TextField DescripcionTxt;
    @FXML
    private TextField valorUnitarioTxt;
    @FXML
    private TextField cantidadExistentext;
    @FXML
    private DatePicker dPfechaenvasado;
    @FXML
    private TextField pesoEnvaseTxt;
    @FXML
    private DatePicker dPfechavencimiento;
    @FXML
    private TextField codigoAprobadoTxt;
    @FXML
    private TextField temperaturaTxt;
//------------Buttons---------------------------------

    @FXML
    private Button clienteButton;
    @FXML
    private Button productoButton;
    @FXML
    private Button guardarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private Button guardarProductoButton;
    @FXML
    private Button cancelarProductoButton;

    //----------------Pane-------------------------------------------------
    @FXML
    private AnchorPane clienteAnchorPane;
    @FXML
    private AnchorPane productoEnvasadoPane;
    @FXML
    private AnchorPane productoPerecederoPane;
    @FXML
    private AnchorPane productoRefrigeradoPane;
    @FXML
    private AnchorPane productoAnchorPane;
    @FXML
    private AnchorPane perJuridicaAp;
    @FXML
    private AnchorPane pernaturalAp;
    //---------------ComboBox------------------------
    @FXML
    private ComboBox<String> tipoProductoCb;
    @FXML
    private ComboBox<String> tipoperComboBox;
    //---------------TableView------------------------
    @FXML
    private TableView<Clientee> clienteTBL;
    @FXML
    private TableView<Producto> tablaProductosTv;
    @FXML
    private ImageView cerrarVentana1;
    private ContextMenu cmOpciones;
    private ContextMenu cmOpciones2;
    private Clientee clienteselect;
    private Producto productoSelect;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String[] opcione = {"natural","juridica"};
        ObservableList<String> items = FXCollections.observableArrayList(opcione);
        tipoperComboBox.setItems(items);
        tipoperComboBox.setValue("seleccione");

        String[] opciones2 = {"prodEnvasado","prodPerecederos","prodRefrigerados"};
        ObservableList<String> items2 = FXCollections.observableArrayList(opciones2);
        tipoProductoCb.setItems(items2);
        tipoProductoCb.setValue("seleccione");

        cargarClientes();
        cargarProductos();

        cmOpciones = new ContextMenu();
        MenuItem miEditar  = new MenuItem("Editar");
        MenuItem miEliminar = new MenuItem("Eliminar");
        cmOpciones.getItems().addAll(miEditar,miEliminar);

        cmOpciones2 = new ContextMenu();
        MenuItem miEditarProducto = new MenuItem("Editar");
        MenuItem miEliminarProducto = new MenuItem("Eliminar");
        cmOpciones2.getItems().addAll(miEditarProducto,miEliminarProducto);

        miEditarProducto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int index = tablaProductosTv.getSelectionModel().getSelectedIndex();
                productoSelect = tablaProductosTv.getItems().get(index);
                System.out.println(productoSelect);


                codigoTxt.setText(productoSelect.getCodigo());
                nombreProductoTxt.setText(productoSelect.getNombre());
                DescripcionTxt.setText(productoSelect.getDescripcion());
                valorUnitarioTxt.setText(productoSelect.getValorUnitario());
                cantidadExistentext.setText(productoSelect.getCantidadExistencia());

                if(productoSelect instanceof ProdEvasado) {
                    visivilitiesProductPane(true,false,false);
                    dPfechaenvasado.setValue(((ProdEvasado) productoSelect).getFechaEnvasado());
                    pesoEnvaseTxt.setText(((ProdEvasado) productoSelect).getPesoEnvase());
                } else if (productoSelect instanceof  ProdPerecederos) {
                    visivilitiesProductPane(false,true,false);
                    dPfechavencimiento.setValue(((ProdPerecederos) productoSelect).getVencimiento());
                } else if (productoSelect instanceof  ProdRefrigerado) {
                    visivilitiesProductPane(false,false,true);
                    codigoAprobadoTxt.setText(((ProdRefrigerado) productoSelect).getCodigoAprovado());
                    temperaturaTxt.setText(((ProdRefrigerado) productoSelect).getTemperatura());
                }
                cancelarProductoButton.setDisable(false);


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
        miEliminarProducto.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int index = tablaProductosTv.getSelectionModel().getSelectedIndex();
                Producto productoEliminar = tablaProductosTv.getItems().get(index);
                almacen.eliminarProducto(productoEliminar.getCodigo());

                cargarProductos();

            }
        });
        miEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = clienteTBL.getSelectionModel().getSelectedIndex();
                Clientee clienteEliminar = clienteTBL.getItems().get(index);
                almacen.eliminatCliente(clienteEliminar.getCedula());

                cargarClientes();
            }
        });

        clienteTBL.setContextMenu(cmOpciones);
        tablaProductosTv.setContextMenu(cmOpciones2);

    }




    public void onExitButtonClick( ) {
        Stage stage = (Stage) cerrarVentana1.getScene().getWindow();
        stage.close();
    }

    public void visivlitiesPanes(boolean pane1,boolean pane2){
        clienteAnchorPane.setVisible(pane1);
        productoAnchorPane.setVisible(pane2);
    }

    public void visivlitiesPanesMnini(boolean pane1,boolean pane2) {
        perJuridicaAp.setVisible(pane1);
        pernaturalAp.setVisible(pane2);

    }

    public void visivilitiesProductPane(boolean pane1,boolean pane2,boolean pane3){
        productoEnvasadoPane.setVisible(pane1);
        productoPerecederoPane.setVisible(pane2);
        productoRefrigeradoPane.setVisible(pane3);

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

    public void onAbreteCesamo2(ActionEvent event) {

        String value = tipoProductoCb.getValue();

        if(value.equals("prodEnvasado")){
            visivilitiesProductPane(true,false,false);
        } else if (value.equals("prodPerecederos")) {
            visivilitiesProductPane(false,true,false);
        } else if (value.equals("prodRefrigerados")) {
            visivilitiesProductPane(false,false,true);
        }
    }

    public void onClientesButtonClick(ActionEvent e) {
        if(e.getTarget()== clienteButton ){visivlitiesPanes(true,false);}
        if(e.getTarget()== productoButton){visivlitiesPanes(false,true);}
    }

    public void onGuardarProducto( ) {


        if(productoSelect == null) {
            try {
                almacen.registrarProducto(codigoTxt.getText(), nombreProductoTxt.getText(), DescripcionTxt.getText(), valorUnitarioTxt.getText(), cantidadExistentext.getText(), codigoAprobadoTxt.getText(), temperaturaTxt.getText(), dPfechavencimiento.getValue(), dPfechaenvasado.getValue(), pesoEnvaseTxt.getText(), tipoProductoCb.getValue());
            } catch (InformacionRepetidaException e) {
                throw new RuntimeException(e);
            }

            limpiarcamposProducto();
            cargarProductos();

        }else{

            productoSelect.setCodigo(codigoTxt.getText());
            productoSelect.setNombre(nombreProductoTxt.getText());
            productoSelect.setDescripcion(DescripcionTxt.getText());
            productoSelect.setValorUnitario(valorUnitarioTxt.getText());
            productoSelect.setCantidadExistencia(cantidadExistentext.getText());

            if(productoSelect instanceof ProdEvasado) {
                visivilitiesProductPane(true,false,false);
                ((ProdEvasado) productoSelect).setFechaEnvasado(dPfechaenvasado.getValue());
                ((ProdEvasado) productoSelect).setPesoEnvase(pesoEnvaseTxt.getText());
            } else if (productoSelect instanceof  ProdPerecederos) {
                visivilitiesProductPane(false,true,false);
                ((ProdPerecederos) productoSelect).setVencimiento(dPfechavencimiento.getValue());
            } else if (productoSelect instanceof  ProdRefrigerado) {
                visivilitiesProductPane(false,false,true);
                ((ProdRefrigerado) productoSelect).setCodigoAprovado(codigoAprobadoTxt.getText());
                ((ProdRefrigerado) productoSelect).setTemperatura(temperaturaTxt.getText());
            }
            limpiarcamposProducto();
            cargarProductos();
            productoSelect = null;
            cancelarProductoButton.setDisable(true);

        }

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

    private void limpiarcamposProducto(){
        codigoTxt.setText("");
        nombreProductoTxt.setText("");
        DescripcionTxt.setText("");
        valorUnitarioTxt.setText("");
        cantidadExistentext.setText("");
        codigoAprobadoTxt.setText("");
        temperaturaTxt.setText("");
        dPfechavencimiento.setValue(null);
        dPfechaenvasado.setValue(null);
        pesoEnvaseTxt.setText("");
        tipoProductoCb.getSelectionModel().select("seleccione");

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


    public void cargarProductos(){
        tablaProductosTv.getItems().clear();
        tablaProductosTv.getItems().clear();

        ObservableList<Producto> observableList = tablaProductosTv.getItems();
        observableList.addAll(almacen.getListaProductors());

        TableColumn codigocol = new TableColumn("Codigo");
        TableColumn nombrecol = new TableColumn("Nombre");
        TableColumn descripcioncol = new TableColumn("Descripcion");
        TableColumn valorUnitariocol = new TableColumn("Valor Unitario");
        TableColumn cantidadexistenciacol = new TableColumn("CantidadExistencia");
        TableColumn codigoaprobadocol = new TableColumn("Codigo Aprovado");
        TableColumn temperaturacol = new TableColumn("Temperatura");
        TableColumn fechaVencimientocol = new TableColumn("Fecha Vencimiento");
        TableColumn fechaenvasecol = new TableColumn("Fecha Envase");
        TableColumn pesoEnvasecol = new TableColumn("Peso Envase");

        codigocol.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        nombrecol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descripcioncol.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        valorUnitariocol.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        cantidadexistenciacol.setCellValueFactory(new PropertyValueFactory<>("cantidadExistencia"));
        codigoaprobadocol.setCellValueFactory(new PropertyValueFactory<>("codigoAprovado"));
        temperaturacol.setCellValueFactory(new PropertyValueFactory<>("temperatura"));
        fechaVencimientocol.setCellValueFactory(new PropertyValueFactory<>("vencimiento"));
        fechaenvasecol.setCellValueFactory(new PropertyValueFactory<>("fechaEnvasado"));
        pesoEnvasecol.setCellValueFactory(new PropertyValueFactory<>("pesoEnvase"));


        tablaProductosTv.setItems(observableList);
        tablaProductosTv.getColumns().addAll(codigocol,nombrecol,descripcioncol,valorUnitariocol,cantidadexistenciacol,codigoaprobadocol,temperaturacol,fechaVencimientocol,fechaenvasecol,pesoEnvasecol);
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