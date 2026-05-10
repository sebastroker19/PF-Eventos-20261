package co.edu.uniquindio.poo.proyectofinal.controller;

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

public class IncidenciasController implements Initializable {

    @FXML private TextField            txtFiltroTipo;
    @FXML private ComboBox<String>     cmbFiltroEntidad;
    @FXML private TextField            txtFechaDesde;
    @FXML private TextField            txtFechaHasta;
    @FXML private Label                lblConteoIncidencias;


    @FXML private TableView<Incidencia>              tablaIncidencias;
    @FXML private TableColumn<Incidencia, String>    colIncId;
    @FXML private TableColumn<Incidencia, String>    colIncTipo;
    @FXML private TableColumn<Incidencia, String>    colIncDescripcion;
    @FXML private TableColumn<Incidencia, String>    colIncFecha;
    @FXML private TableColumn<Incidencia, String>    colIncEntidad;

    @FXML private TextField        txtIncId;
    @FXML private ComboBox<String> cmbTipoInc;
    @FXML private ComboBox<String> cmbEntidadInc;
    @FXML private TextField        txtFechaInc;
    @FXML private TextArea         txtDescripcionInc;
    @FXML private TextField        txtCompraAsociada;
    @FXML private Label            lblMensajeInc;

    private final Empresa       empresa = Empresa.getInstance();
    private final Administrador admin   = new Administrador(
            "ADMIN-01", "Administrador Sistema", "admin@eventOS.com", "N/A");

    /** Lista maestra con todas las incidencias del sistema. */
    private ObservableList<Incidencia> todasLasIncidencias;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        poblarFiltros();
        cargarTodasLasIncidencias();
    }

    private void configurarColumnas() {
        colIncId.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getIdIncidencia()));

        colIncTipo.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getTipo()));

        colIncDescripcion.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getDescripcion()));

        colIncFecha.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getFecha()));

        colIncEntidad.setCellValueFactory(d ->
                new SimpleStringProperty(
                        d.getValue().getEntidadAfectada() != null
                                ? d.getValue().getEntidadAfectada().name() : "—"));
    }

    private void poblarFiltros() {
        cmbFiltroEntidad.getItems().add("Todas");
        for (EntidadAfectada e : EntidadAfectada.values()) {
            cmbFiltroEntidad.getItems().add(e.name());
        }
        cmbFiltroEntidad.setValue("Todas");

        cmbTipoInc.getItems().addAll(
                "Error de pago",
                "Doble reserva de asiento",
                "Cancelación masiva",
                "Error de sistema",
                "Fraude detectado",
                "Otro");
        cmbTipoInc.setValue("Error de pago");

        for (EntidadAfectada e : EntidadAfectada.values()) {
            cmbEntidadInc.getItems().add(e.name());
        }
        cmbEntidadInc.setValue(EntidadAfectada.values()[0].name());

        txtFechaInc.setText(java.time.LocalDate.now().toString());
    }


    /**
     * Recorre todas las compras de todos los usuarios del Modelo y agrega sus incidencias a la lista maestra.
     */
    private void cargarTodasLasIncidencias() {
        List<Incidencia> lista = new ArrayList<>();
        for (Usuario u : empresa.getListUsuarios()) {
            if (u.getListCompras() == null) continue;
            for (Compra c : u.getListCompras()) {
                lista.addAll(c.getListIncidencias());
            }
        }
        todasLasIncidencias = FXCollections.observableArrayList(lista);
        tablaIncidencias.setItems(todasLasIncidencias);
        actualizarConteo(todasLasIncidencias.size());
    }

    @FXML
    private void filtrarIncidencias() {
        String tipo          = txtFiltroTipo.getText().toLowerCase().trim();
        String entidadFiltro = cmbFiltroEntidad.getValue();
        String fechaDesde    = txtFechaDesde.getText().trim();
        String fechaHasta    = txtFechaHasta.getText().trim();

        List<Incidencia> filtradas = todasLasIncidencias.stream()
                .filter(inc -> {
                    boolean coincideTipo = tipo.isEmpty()
                            || inc.getTipo().toLowerCase().contains(tipo);

                    boolean coincideEntidad =
                            "Todas".equals(entidadFiltro)
                                    || (inc.getEntidadAfectada() != null
                                    && inc.getEntidadAfectada().name().equals(entidadFiltro));

                    boolean dentroDeRango = true;
                    String fechaInc = inc.getFecha();
                    if (fechaInc != null && !fechaDesde.isEmpty()) {
                        dentroDeRango = fechaInc.compareTo(fechaDesde) >= 0;
                    }
                    if (dentroDeRango && fechaInc != null && !fechaHasta.isEmpty()) {
                        dentroDeRango = fechaInc.compareTo(fechaHasta) <= 0;
                    }

                    return coincideTipo && coincideEntidad && dentroDeRango;
                })
                .collect(Collectors.toList());

        tablaIncidencias.setItems(FXCollections.observableArrayList(filtradas));
        actualizarConteo(filtradas.size());
    }

    @FXML
    private void limpiarFiltros() {
        txtFiltroTipo.clear();
        cmbFiltroEntidad.setValue("Todas");
        txtFechaDesde.clear();
        txtFechaHasta.clear();
        tablaIncidencias.setItems(todasLasIncidencias);
        actualizarConteo(todasLasIncidencias.size());
    }


    /** Muestra el formulario lateral */
    @FXML
    private void abrirFormularioIncidencia() {
        limpiarFormulario();
    }

    /**
     * Registra una nueva incidencia y la asocia a la compra indicada.
     */
    @FXML
    private void registrarIncidencia() {
        String id          = txtIncId.getText().trim();
        String tipo        = cmbTipoInc.getValue();
        String entidad     = cmbEntidadInc.getValue();
        String fecha       = txtFechaInc.getText().trim();
        String descripcion = txtDescripcionInc.getText().trim();
        String idCompra    = txtCompraAsociada.getText().trim();

        // Validación de campos obligatorios
        if (id.isEmpty() || tipo == null || fecha.isEmpty() || descripcion.isEmpty()) {
            mostrarMensajeError("ID, tipo, fecha y descripción son obligatorios.");
            return;
        }

        EntidadAfectada entidadAfectada;
        try {
            entidadAfectada = EntidadAfectada.valueOf(entidad);
        } catch (IllegalArgumentException e) {
            mostrarMensajeError("Entidad afectada inválida.");
            return;
        }

        Incidencia nuevaIncidencia = new Incidencia(
                id, tipo, descripcion, fecha, entidadAfectada);

        if (!idCompra.isEmpty()) {
            Compra compraEncontrada = buscarCompraPorId(idCompra);
            if (compraEncontrada != null) {
                admin.registrarIncidenciaEnCompra(compraEncontrada, nuevaIncidencia);
                mostrarMensajeExito("Incidencia registrada y asociada a la compra " + idCompra + ".");
            } else {
                mostrarMensajeError("No se encontró la compra con ID: " + idCompra
                        + ". La incidencia no fue guardada.");
                return;
            }
        } else {
            mostrarMensajeExito("Incidencia registrada (sin compra asociada).");
        }

        todasLasIncidencias.add(nuevaIncidencia);
        tablaIncidencias.setItems(todasLasIncidencias);
        actualizarConteo(todasLasIncidencias.size());
        limpiarFormulario();
    }

    @FXML
    private void limpiarFormulario() {
        txtIncId.clear();
        cmbTipoInc.setValue("Error de pago");
        cmbEntidadInc.setValue(EntidadAfectada.values()[0].name());
        txtFechaInc.setText(java.time.LocalDate.now().toString());
        txtDescripcionInc.clear();
        txtCompraAsociada.clear();
        ocultarMensaje();
    }

    /**
     * Busca una compra por ID recorriendo los usuarios del Modelo.
     */
    private Compra buscarCompraPorId(String idCompra) {
        for (Usuario u : empresa.getListUsuarios()) {
            if (u.getListCompras() == null) continue;
            for (Compra c : u.getListCompras()) {
                if (c.getIdCompra().equals(idCompra)) return c;
            }
        }
        return null;
    }

    private void actualizarConteo(int total) {
        lblConteoIncidencias.setText(total + " incidencia" + (total != 1 ? "s" : ""));
    }

    private void mostrarMensajeExito(String texto) {
        lblMensajeInc.setText(texto);
        lblMensajeInc.setStyle("-fx-text-fill: #059669;");
        lblMensajeInc.setVisible(true);
        lblMensajeInc.setManaged(true);
    }

    private void mostrarMensajeError(String texto) {
        lblMensajeInc.setText(texto);
        lblMensajeInc.setStyle("-fx-text-fill: #e11d48;");
        lblMensajeInc.setVisible(true);
        lblMensajeInc.setManaged(true);
    }

    private void ocultarMensaje() {
        lblMensajeInc.setVisible(false);
        lblMensajeInc.setManaged(false);
    }
}