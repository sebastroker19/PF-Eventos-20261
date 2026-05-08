module co.edu.uniquindio.poo.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens co.edu.uniquindio.poo.proyectofinal.views to javafx.fxml;
    opens co.edu.uniquindio.poo.proyectofinal.css to javafx.graphics;


}