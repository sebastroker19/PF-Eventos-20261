package co.edu.uniquindio.poo.proyectofinal.viewController;

import co.edu.uniquindio.poo.proyectofinal.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ComprasController implements Initializable {

    @FXML private ComboBox<String>               cmbFiltroEvento;
    @FXML private Label                          lblConteoCompras;

    @FXML private TableView<Compra>              tablaCompras;
    @FXML private TableColumn<Compra, String>    colCompraId;
    @FXML private TableColumn<Compra, String>    colCompraEvento;
    @FXML private TableColumn<Compra, String>    colCompraFecha;
    @FXML private TableColumn<Compra, String>    colCompraEntradas;
    @FXML private TableColumn<Compra, String>    colCompraTotal;
    @FXML private TableColumn<Compra, String>    colCompraEstado;

    @FXML private Button btnPagar;
    @FXML private Button btnCancelarCompra;

    @FXML private Label         lblDetalleId;
    @FXML private Label         lblDetalleEvento;
    @FXML private Label         lblDetalleFecha;
    @FXML private Label         lblDetalleEstado;
    @FXML private Label         lblDetalleTotal;
    @FXML private ListView<String> listaEntradas;
    @FXML private Label         lblMensajeCompra;

    private final Empresa empresa = Empresa.getInstance();

    /** Lista maestra de todas las compras del sistema (todos los usuarios). */
    private ObservableList<Compra> todasLasCompras;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarTodasLasCompras();
        poblarFiltroEvento();
        configurarSeleccion();
    }

    private void configurarColumnas() {
        colCompraId.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getIdCompra()));

        colCompraEvento.setCellValueFactory(d -> {
            Evento e = d.getValue().getEvento();
            return new SimpleStringProperty(e != null ? e.getNombre() : "—");
        });

        colCompraFecha.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getFechaCreacion()));

        colCompraEntradas.setCellValueFactory(d ->
                new SimpleStringProperty(
                        String.valueOf(d.getValue().getListEntradas().size())));

        colCompraTotal.setCellValueFactory(d ->
                new SimpleStringProperty(
                        String.format("$%,.0f", d.getValue().getTotal())));

        colCompraEstado.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEstadoCompra().name()));
    }

    /**
     * Recorre todos los usuarios del Modelo y agrega sus compras
     */
    private void cargarTodasLasCompras() {
        List<Compra> lista = new ArrayList<>();
        for (Usuario u : empresa.getListUsuarios()) {
            if (u.getListCompras() != null) {
                lista.addAll(u.getListCompras());
            }
        }
        todasLasCompras = FXCollections.observableArrayList(lista);
        tablaCompras.setItems(todasLasCompras);
        actualizarConteo(todasLasCompras.size());
    }

    private void poblarFiltroEvento() {
        cmbFiltroEvento.getItems().add("Todos");
        for (Evento e : empresa.getListEventos()) {
            cmbFiltroEvento.getItems().add(e.getNombre());
        }
        cmbFiltroEvento.setValue("Todos");
        cmbFiltroEvento.setOnAction(ev -> filtrarPorEvento());
    }

    private void configurarSeleccion() {
        tablaCompras.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, seleccionada) -> {
                    if (seleccionada == null) {
                        limpiarDetalle();
                        btnPagar.setDisable(true);
                        btnCancelarCompra.setDisable(true);
                        return;
                    }
                    mostrarDetalle(seleccionada);
                    EstadoCompra estado = seleccionada.getEstadoCompra();
                    btnPagar.setDisable(estado != EstadoCompra.CREADA);
                    btnCancelarCompra.setDisable(
                            estado == EstadoCompra.CANCELADA
                                    || estado == EstadoCompra.REEMBOLSADA);
                });
    }

    /** Rellena el panel derecho con los datos de la compra seleccionada. */
    private void mostrarDetalle(Compra compra) {
        lblDetalleId.setText(compra.getIdCompra());
        lblDetalleEvento.setText(
                compra.getEvento() != null ? compra.getEvento().getNombre() : "—");
        lblDetalleFecha.setText(compra.getFechaCreacion());
        lblDetalleEstado.setText(compra.getEstadoCompra().name());
        lblDetalleTotal.setText(String.format("$%,.0f", compra.getTotal()));

        listaEntradas.getItems().clear();
        for (IEntrada entrada : compra.getListEntradas()) {
            String texto = entrada.getDescripcion()
                    + " — $" + String.format("%,.0f", entrada.getPrecio());
            if (entrada.getAsiento() != null) {
                texto += " (Fila " + entrada.getAsiento().getFila()
                        + " #" + entrada.getAsiento().getNumero() + ")";
            }
            listaEntradas.getItems().add(texto);
        }
    }

    private void limpiarDetalle() {
        lblDetalleId.setText("—");
        lblDetalleEvento.setText("—");
        lblDetalleFecha.setText("—");
        lblDetalleEstado.setText("—");
        lblDetalleTotal.setText("$0");
        listaEntradas.getItems().clear();
    }

    @FXML
    private void pagarCompra() {
        Compra seleccionada = tablaCompras.getSelectionModel().getSelectedItem();
        if (seleccionada == null) return;

        // Diálogo de selección de método de pago (Strategy pattern del Modelo)
        ChoiceDialog<String> dialog = new ChoiceDialog<>(
                "Tarjeta de crédito",
                "Tarjeta de crédito", "PSE");
        dialog.setTitle("Método de pago");
        dialog.setHeaderText("Total a pagar: $"
                + String.format("%,.0f", seleccionada.getTotal()));
        dialog.setContentText("Selecciona el método de pago:");

        dialog.showAndWait().ifPresent(metodo -> {
            EstrategiaPago estrategia;

            // El controller solo decide QUÉ estrategia instanciar;
            // la lógica de procesamiento es del Modelo.
            if ("Tarjeta de crédito".equals(metodo)) {
                estrategia = new PagoTarjeta(
                        "4000000000000000", "Usuario", "12/28", "000");
            } else {
                estrategia = new PagoPSE("Bancolombia", "pago@eventOS.com");
            }

            // Delega al Modelo
            boolean exito = seleccionada.confirmarPago(estrategia);

            if (exito) {
                mostrarMensajeExito("Pago procesado correctamente. Estado: PAGADA.");
                tablaCompras.refresh();
                mostrarDetalle(seleccionada);
                btnPagar.setDisable(true);
            } else {
                mostrarMensajeError("El pago fue rechazado. Verifica que la compra tenga entradas.");
            }
        });
    }

    /**
     * Delega la cancelación al Modelo.
     * Compra.cancelar() libera los asientos asociados automáticamente.
     */
    @FXML
    private void cancelarCompra() {
        Compra seleccionada = tablaCompras.getSelectionModel().getSelectedItem();
        if (seleccionada == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Cancelar compra");
        confirm.setHeaderText("¿Cancelar la compra " + seleccionada.getIdCompra() + "?");
        confirm.setContentText("Los asientos reservados serán liberados.");

        confirm.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                boolean exito = seleccionada.cancelar();  // Lógica en el Modelo
                if (exito) {
                    mostrarMensajeExito("Compra cancelada. Asientos liberados.");
                    tablaCompras.refresh();
                    mostrarDetalle(seleccionada);
                    btnCancelarCompra.setDisable(true);
                } else {
                    mostrarMensajeError("Esta compra ya estaba cancelada o reembolsada.");
                }
            }
        });
    }

    /** Crea una nueva compra vacía para el primer usuario disponible. */
    @FXML
    private void nuevaCompra() {
        if (empresa.getListUsuarios().isEmpty() || empresa.getListEventos().isEmpty()) {
            mostrarMensajeError("Necesitas al menos un usuario y un evento registrados.");
            return;
        }

        List<String> opciones = empresa.getListEventos().stream()
                .filter(e -> e.getEstadoEvento() == EstadoEvento.PUBLICADO)
                .map(e -> e.getIdEvento() + " — " + e.getNombre())
                .collect(Collectors.toList());

        if (opciones.isEmpty()) {
            mostrarMensajeError("No hay eventos publicados disponibles.");
            return;
        }

        ChoiceDialog<String> dialogEvento = new ChoiceDialog<>(
                opciones.get(0), opciones);
        dialogEvento.setTitle("Nueva Compra");
        dialogEvento.setHeaderText("Selecciona el evento");
        dialogEvento.setContentText("Evento:");

        dialogEvento.showAndWait().ifPresent(opcion -> {
            String idEvento = opcion.split(" — ")[0];
            Evento evento   = empresa.buscarEvento(idEvento);
            Usuario usuario = empresa.getListUsuarios().get(0);

            String idCompra = "CMP-" + (todasLasCompras.size() + 1);
            Compra nuevaCompra = new Compra(
                    idCompra,
                    java.time.LocalDate.now().toString(),
                    0,
                    usuario,
                    evento,
                    EstadoCompra.CREADA);

            if (usuario.getListCompras() != null) {
                usuario.getListCompras().add(nuevaCompra);
            }

            todasLasCompras.add(nuevaCompra);
            actualizarConteo(todasLasCompras.size());
            mostrarMensajeExito("Compra " + idCompra + " creada. Agrega entradas y procede a pagar.");
        });
    }

    @FXML private void filtrarTodas()       { aplicarFiltroEstado(null); }
    @FXML private void filtrarCreadas()     { aplicarFiltroEstado(EstadoCompra.CREADA); }
    @FXML private void filtrarPagadas()     { aplicarFiltroEstado(EstadoCompra.PAGADA); }
    @FXML private void filtrarConfirmadas() { aplicarFiltroEstado(EstadoCompra.CONFIRMADA); }
    @FXML private void filtrarCanceladas()  { aplicarFiltroEstado(EstadoCompra.CANCELADA); }

    private void aplicarFiltroEstado(EstadoCompra estadoFiltro) {
        if (estadoFiltro == null) {
            tablaCompras.setItems(todasLasCompras);
            actualizarConteo(todasLasCompras.size());
            return;
        }
        List<Compra> filtradas = todasLasCompras.stream()
                .filter(c -> c.getEstadoCompra() == estadoFiltro)
                .collect(Collectors.toList());
        tablaCompras.setItems(FXCollections.observableArrayList(filtradas));
        actualizarConteo(filtradas.size());
    }

    private void filtrarPorEvento() {
        String eventoFiltro = cmbFiltroEvento.getValue();
        if ("Todos".equals(eventoFiltro) || eventoFiltro == null) {
            tablaCompras.setItems(todasLasCompras);
            actualizarConteo(todasLasCompras.size());
            return;
        }
        List<Compra> filtradas = todasLasCompras.stream()
                .filter(c -> c.getEvento() != null
                        && c.getEvento().getNombre().equals(eventoFiltro))
                .collect(Collectors.toList());
        tablaCompras.setItems(FXCollections.observableArrayList(filtradas));
        actualizarConteo(filtradas.size());
    }

    private void actualizarConteo(int total) {
        lblConteoCompras.setText(total + " compra" + (total != 1 ? "s" : ""));
    }

    private void mostrarMensajeExito(String texto) {
        lblMensajeCompra.setText(texto);
        lblMensajeCompra.setStyle("-fx-text-fill: #059669;");
        lblMensajeCompra.setVisible(true);
        lblMensajeCompra.setManaged(true);
    }

    private void mostrarMensajeError(String texto) {
        lblMensajeCompra.setText(texto);
        lblMensajeCompra.setStyle("-fx-text-fill: #e11d48;");
        lblMensajeCompra.setVisible(true);
        lblMensajeCompra.setManaged(true);
    }
}