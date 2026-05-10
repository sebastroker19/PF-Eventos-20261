module co.edu.uniquindio.poo.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens co.edu.uniquindio.poo.proyectofinal to javafx.graphics, javafx.fxml;

    opens co.edu.uniquindio.poo.proyectofinal.viewController to javafx.fxml;

    exports co.edu.uniquindio.poo.proyectofinal.model;
    exports co.edu.uniquindio.poo.proyectofinal.viewController;
}