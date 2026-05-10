module co.edu.uniquindio.poo.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // App principal necesita ser abierta para que JavaFX la cargue
    opens co.edu.uniquindio.poo.proyectofinal to javafx.graphics, javafx.fxml;

    // FXMLLoader necesita acceso reflectivo a los controllers
    opens co.edu.uniquindio.poo.proyectofinal.viewController to javafx.fxml;

    // El modelo es accesible desde cualquier clase del módulo
    exports co.edu.uniquindio.poo.proyectofinal.model;
    exports co.edu.uniquindio.poo.proyectofinal.viewController;
}