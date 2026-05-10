package co.edu.uniquindio.poo.proyectofinal.viewController;

import co.edu.uniquindio.poo.proyectofinal.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private StackPane contentArea;
    @FXML private Label topbarTitle;
    @FXML private Label topbarUser;

    private final Empresa empresa = Empresa.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        topbarUser.setText("Admin — " + empresa.getNombre());
        mostrarDashboard();
    }

    @FXML
    public void mostrarDashboard() {
        cargarVista("dashboard-view.fxml", "Dashboard");
    }

    @FXML
    public void mostrarGestionEventos() {
        cargarVista("eventos-view.fxml", "Gestión de Eventos");
    }

    @FXML
    public void mostrarGestionUsuarios() {
        cargarVista("usuarios-view.fxml", "Gestión de Usuarios");
    }

    @FXML
    public void mostrarGestionAsientos() {
        cargarVista("asientos-view.fxml", "Mapa de Asientos");
    }

    @FXML
    public void mostrarMisCompras() {
        cargarVista("compras-view.fxml", "Mis Compras");
    }

    @FXML
    public void mostrarIncidencias() {
        cargarVista("incidencias-view.fxml", "Incidencias");
    }

    @FXML public void mostrarExplorarEventos() { mostrarGestionEventos(); }
    @FXML public void mostrarMiPerfil()        { mostrarGestionUsuarios(); }

    @FXML
    public void cerrarSesion() {
        System.exit(0);
    }

    private void cargarVista(String fxmlName, String titulo) {
        try {
            topbarTitle.setText(titulo);
            // Referencia corregida para buscar en la carpeta de vistas desde el nuevo paquete
            URL fxmlUrl = getClass().getResource("/co/edu/uniquindio/poo/proyectofinal/views/" + fxmlName);

            if (fxmlUrl == null) {
                System.err.println("Error: No se encontró el archivo FXML: " + fxmlName);
                return;
            }

            Parent vista = FXMLLoader.load(fxmlUrl);
            contentArea.getChildren().setAll(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}