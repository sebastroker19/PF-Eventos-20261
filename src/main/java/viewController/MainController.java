package co.edu.uniquindio.poo.proyectofinal.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import java.io.IOException;

public class MainController {
    @FXML private StackPane contentArea;
    @FXML private Label topbarTitle;

    // Método genérico para cambiar de vista
    private void cargarVista(String fxml, String titulo) {
        try {
            topbarTitle.setText(titulo);
            Parent view = FXMLLoader.load(getClass().getResource("/co/edu/uniquindio/poo/proyectofinal/views/" + fxml));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void mostrarDashboard() { cargarVista("dashboard-view.fxml", "Dashboard"); }
    @FXML void mostrarGestionEventos() { cargarVista("eventos-view.fxml", "Gestión de Eventos"); }
    @FXML void mostrarGestionUsuarios() { cargarVista("usuarios-view.fxml", "Gestión de Usuarios"); }
    @FXML void mostrarGestionAsientos() { cargarVista("asientos-view.fxml", "Mapa de Asientos"); }
    @FXML void mostrarMisCompras() { cargarVista("compras-view.fxml", "Mis Compras"); }
    @FXML void mostrarIncidencias() { cargarVista("incidencias-view.fxml", "Incidencias"); }

    @FXML void cerrarSesion() { System.exit(0); }
}