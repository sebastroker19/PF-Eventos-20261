module co.edu.uniquindio.poo.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // Abrimos los paquetes para que JavaFX pueda trabajar
    opens co.edu.uniquindio.poo.proyectofinal.controller to javafx.fxml;
    opens co.edu.uniquindio.poo.proyectofinal.views to javafx.fxml;

    // ESTO ES LO QUE HACE QUE EL CSS FUNCIONE
    opens co.edu.uniquindio.poo.proyectofinal.css to javafx.graphics;

    // Exportamos el paquete principal (donde está tu clase App o Main)
    exports co.edu.uniquindio.poo.proyectofinal;
}