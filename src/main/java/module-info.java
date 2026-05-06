module co.edu.uniquindio.poo.pfeventos20261 {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.graphics;

    opens co.edu.uniquindio.poo.proyectofinal.views to javafx.fxml;
    opens co.edu.uniquindio.poo.proyectofinal.css to javafx.graphics;

    opens co.edu.uniquindio.poo.pfeventos20261 to javafx.fxml;
    exports co.edu.uniquindio.poo.pfeventos20261;
}