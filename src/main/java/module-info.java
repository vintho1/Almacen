module co.uniquindio.ingesis.programacion3.almacen.almacen {
    requires  javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.logging;
    requires java.desktop;

    opens co.uniquindio.ingesis.programacion3.almacen.almacen to javafx.fxml;
    opens co.uniquindio.ingesis.programacion3.almacen.almacen.Controllers to javafx.fxml;
    opens co.uniquindio.ingesis.programacion3.almacen.almacen.Model to javafx.fxml;
    exports co.uniquindio.ingesis.programacion3.almacen.almacen;

    //opens co.uniquindio.ingesis.programacion3.almacen.almacen to javafx.fxml;
    // exports co.uniquindio.ingesis.programacion3.almacen.almacen;
    exports co.uniquindio.ingesis.programacion3.almacen.almacen.Controllers;
    exports co.uniquindio.ingesis.programacion3.almacen.almacen.Model;
    //opens co.uniquindio.ingesis.programacion3.almacen.almacen  to javafx.fxml;
   // exports  co.uniquindio.ingesis.programacion3.almacen.almacen;


}