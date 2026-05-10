package co.edu.uniquindio.poo.proyectofinal.controller;

import co.edu.uniquindio.poo.proyectofinal.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Label lblTotalEventos;
    @FXML private Label lblTendenciaEventos;
    @FXML private Label lblTotalUsuarios;
    @FXML private Label lblTotalCompras;
    @FXML private Label lblComprasPagadas;
    @FXML private Label lblTotalIncidencias;

    @FXML private TableView<Evento>      tablaEventosRecientes;
    @FXML private TableColumn<Evento, String> colEventoNombre;
    @FXML private TableColumn<Evento, String> colEventoCiudad;
    @FXML private TableColumn<Evento, String> colEventoFecha;
    @FXML private TableColumn<Evento, String> colEventoEstado;

    @FXML private TableView<Compra>      tablaComprasRecientes;
    @FXML private TableColumn<Compra, String> colCompraId;
    @FXML private TableColumn<Compra, String> colCompraUsuario;
    @FXML private TableColumn<Compra, String> colCompraTotal;
    @FXML private TableColumn<Compra, String> colCompraEstado;

    private final Empresa empresa = Empresa.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnasEventos();
        configurarColumnasCompras();
        cargarMetricas();
        cargarTablas();
    }

    private void configurarColumnasEventos() {
        colEventoNombre.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getNombre()));

        colEventoCiudad.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getCiudad()));

        colEventoFecha.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getFecha()));

        colEventoEstado.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getEstadoEvento().name()));
    }

    private void configurarColumnasCompras() {
        colCompraId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getIdCompra()));

        colCompraUsuario.setCellValueFactory(data -> {
            Usuario u = data.getValue().getUsuario();
            String nombre = (u != null && u.getNombreCompleto() != null)
                    ? u.getNombreCompleto() : "—";
            return new javafx.beans.property.SimpleStringProperty(nombre);
        });

        colCompraTotal.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        String.format("$%,.0f", data.getValue().getTotal())));

        colCompraEstado.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getEstadoCompra().name()));
    }

    private void cargarMetricas() {
        List<Evento> eventos   = empresa.getListEventos();
        List<Usuario> usuarios = empresa.getListUsuarios();

        // Conteo de eventos activos (publicados)
        long eventosActivos = eventos.stream()
                .filter(e -> e.getEstadoEvento() == EstadoEvento.PUBLICADO)
                .count();

        lblTotalEventos.setText(String.valueOf(eventos.size()));
        lblTendenciaEventos.setText("↑ " + eventosActivos + " activos");

        lblTotalUsuarios.setText(String.valueOf(usuarios.size()));

        // Compras: se recorren todas las compras de todos los usuarios
        long totalCompras = 0;
        long comprasPagadas = 0;
        long totalIncidencias = 0;

        for (Usuario u : usuarios) {
            List<Compra> compras = u.getListCompras();
            if (compras == null) continue;
            totalCompras += compras.size();
            for (Compra c : compras) {
                if (c.getEstadoCompra() == EstadoCompra.PAGADA
                        || c.getEstadoCompra() == EstadoCompra.CONFIRMADA) {
                    comprasPagadas++;
                }
                totalIncidencias += c.getListIncidencias().size();
            }
        }

        lblTotalCompras.setText(String.valueOf(totalCompras));
        lblComprasPagadas.setText(comprasPagadas + " pagadas");
        lblTotalIncidencias.setText(String.valueOf(totalIncidencias));
    }

    private void cargarTablas() {
        // Eventos: mostramos los últimos 5 registrados
        List<Evento> eventos = empresa.getListEventos();
        int desdeEvento = Math.max(0, eventos.size() - 5);
        ObservableList<Evento> eventosRecientes =
                FXCollections.observableArrayList(
                        eventos.subList(desdeEvento, eventos.size()));
        tablaEventosRecientes.setItems(eventosRecientes);

        // Compras: recorremos todos los usuarios y tomamos las últimas 5
        ObservableList<Compra> comprasRecientes = FXCollections.observableArrayList();
        for (Usuario u : empresa.getListUsuarios()) {
            if (u.getListCompras() != null) {
                comprasRecientes.addAll(u.getListCompras());
            }
        }
        int desdeCompra = Math.max(0, comprasRecientes.size() - 5);
        tablaComprasRecientes.setItems(
                FXCollections.observableArrayList(
                        comprasRecientes.subList(desdeCompra, comprasRecientes.size())));
    }

    @FXML
    private void irAEventos() {
        // Recarga la vista de eventos a través del MainController
        tablaEventosRecientes.getScene().lookup("#btnGestionEventos");
        // Forma limpia: disparamos el botón de la sidebar si existe, de lo
        // contrario dejamos al usuario que navegue manualmente.
        // (La navegación completa se implementa en MainController)
    }

    @FXML
    private void irACompras() {
    }
}