package co.edu.uniquindio.poo.proyectofinal.viewController;

import co.edu.uniquindio.poo.proyectofinal.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AsientosController implements Initializable {

    @FXML private ComboBox<String> cmbEventoAsientos;
    @FXML private ListView<String> listaZonas;

    @FXML private Label lblCapacidadZona;
    @FXML private Label lblDisponiblesZona;
    @FXML private Label lblOcupacionZona;
    @FXML private Label lblZonaNombre;

    @FXML private FlowPane mapaAsientos;

    @FXML private Label  lblAsientoSeleccionado;
    @FXML private Button btnBloquearAsiento;
    @FXML private Button btnLiberarAsiento;
    @FXML private Label  lblMensajeAsiento;

    private final Empresa       empresa = Empresa.getInstance();
    private final Administrador admin   = new Administrador(
            "ADMIN-01", "Administrador Sistema", "admin@eventOS.com", "N/A");

    /** Evento y zona actualmente seleccionados. */
    private Evento eventoActual;
    private Zona   zonaActual;

    /** Asiento seleccionado en el mapa. */
    private Asiento asientoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        poblarComboEventos();
        configurarSeleccionEvento();
        configurarSeleccionZona();
    }

    private void poblarComboEventos() {
        for (Evento e : empresa.getListEventos()) {
            cmbEventoAsientos.getItems().add(e.getIdEvento() + " — " + e.getNombre());
        }
    }

    private void configurarSeleccionEvento() {
        cmbEventoAsientos.setOnAction(ev -> {
            int indice = cmbEventoAsientos.getSelectionModel().getSelectedIndex();
            if (indice < 0 || indice >= empresa.getListEventos().size()) return;

            eventoActual = empresa.getListEventos().get(indice);
            cargarZonasDelEvento();
        });
    }

    private void configurarSeleccionZona() {
        listaZonas.setOnMouseClicked(ev -> seleccionarZona());
    }

    /** Llena el ListView de zonas con las zonas del primer recinto del evento. */
    private void cargarZonasDelEvento() {
        listaZonas.getItems().clear();
        mapaAsientos.getChildren().clear();
        zonaActual = null;

        if (eventoActual == null || eventoActual.getListRecintos().isEmpty()) return;

        Recinto recinto = eventoActual.getListRecintos().get(0);
        for (Zona z : recinto.getListZonas()) {
            listaZonas.getItems().add(z.getSectorZona().name() + " — " + z.getNombre());
        }
    }

    /**
     * Actualiza las estadísticas y regenera el mapa de asientos.
     */
    @FXML
    public void seleccionarZona() {
        int indice = listaZonas.getSelectionModel().getSelectedIndex();
        if (eventoActual == null || eventoActual.getListRecintos().isEmpty()) return;

        Recinto recinto = eventoActual.getListRecintos().get(0);
        List<Zona> zonas = recinto.getListZonas();
        if (indice < 0 || indice >= zonas.size()) return;

        zonaActual = zonas.get(indice);
        actualizarEstadisticasZona();
        renderizarMapaAsientos();
    }

    /** Actualiza la capacidad, disponibles y ocupación. */
    private void actualizarEstadisticasZona() {
        if (zonaActual == null) return;
        lblZonaNombre.setText(zonaActual.getNombre());
        lblCapacidadZona.setText(String.valueOf(zonaActual.getCantidad()));
        lblDisponiblesZona.setText(String.valueOf(
                zonaActual.getAsientosDisponibles().size()));
        lblOcupacionZona.setText(String.format("%.0f%%",
                zonaActual.getPorcentajeOcupacion()));
    }

    /**
     * Genera un botón por cada asiento de la zona el color refleja el estado del asiento
     *
     *   Verde   → DISPONIBLE
     *   Amarillo → RESERVADO
     *   Rojo    → VENDIDO
     *   Gris    → BLOQUEADO
     */
    private void renderizarMapaAsientos() {
        mapaAsientos.getChildren().clear();
        asientoSeleccionado = null;
        deseleccionarAsiento();

        if (zonaActual == null) return;

        for (Asiento asiento : zonaActual.getListAsientos()) {
            Button btnAsiento = crearBotonAsiento(asiento);
            mapaAsientos.getChildren().add(btnAsiento);
        }
    }

    /** Crea el botón visual que representa un asiento en el mapa. */
    private Button crearBotonAsiento(Asiento asiento) {
        Button btn = new Button(asiento.getFila() + asiento.getNumero());
        btn.setPrefSize(48, 40);
        btn.setStyle(obtenerEstiloAsiento(asiento.getEstadoAsiento()));

        btn.setOnAction(ev -> {
            asientoSeleccionado = asiento;
            actualizarPanelAsientoSeleccionado(asiento);
            renderizarMapaAsientos();
            btn.setStyle(btn.getStyle() + "-fx-border-color: #0f172a; -fx-border-width: 2;");
        });

        return btn;
    }

    private String obtenerEstiloAsiento(EstadoAsiento estado) {
        String base = "-fx-font-size:11px; -fx-font-weight:bold;"
                + "-fx-background-radius:6; -fx-cursor:hand; -fx-border-radius:6;";
        return switch (estado) {
            case DISPONIBLE -> base + "-fx-background-color:#d1fae5; -fx-text-fill:#065f46;";
            case RESERVADO  -> base + "-fx-background-color:#fef3c7; -fx-text-fill:#92400e;";
            case VENDIDO    -> base + "-fx-background-color:#fee2e2; -fx-text-fill:#991b1b;";
            case BLOQUEADO  -> base + "-fx-background-color:#e2e8f0; -fx-text-fill:#64748b;";
        };
    }

    /** Actualiza el panel inferior con el asiento seleccionado y habilita los botones. */
    private void actualizarPanelAsientoSeleccionado(Asiento asiento) {
        lblAsientoSeleccionado.setText(
                "Asiento: Fila " + asiento.getFila()
                        + " — N° " + asiento.getNumero()
                        + " | Estado: " + asiento.getEstadoAsiento().name());

        // Solo se puede bloquear si NO está vendido
        btnBloquearAsiento.setDisable(
                asiento.getEstadoAsiento() == EstadoAsiento.VENDIDO
                        || asiento.getEstadoAsiento() == EstadoAsiento.BLOQUEADO);

        // Solo se puede liberar si está bloqueado
        btnLiberarAsiento.setDisable(
                asiento.getEstadoAsiento() != EstadoAsiento.BLOQUEADO);

        ocultarMensaje();
    }

    private void deseleccionarAsiento() {
        lblAsientoSeleccionado.setText("Ningún asiento seleccionado");
        btnBloquearAsiento.setDisable(true);
        btnLiberarAsiento.setDisable(true);
    }

    @FXML
    private void bloquearAsiento() {
        if (asientoSeleccionado == null) return;

        boolean exito = admin.bloquearAsiento(asientoSeleccionado);

        if (exito) {
            mostrarMensajeExito("Asiento " + asientoSeleccionado.getFila()
                    + asientoSeleccionado.getNumero() + " bloqueado.");
            renderizarMapaAsientos();
            actualizarEstadisticasZona();
        } else {
            mostrarMensajeError("No se puede bloquear un asiento VENDIDO.");
        }
        deseleccionarAsiento();
        asientoSeleccionado = null;
    }

    @FXML
    private void liberarAsiento() {
        if (asientoSeleccionado == null) return;

        boolean exito = admin.liberarAsientoBloqueado(asientoSeleccionado);

        if (exito) {
            mostrarMensajeExito("Asiento " + asientoSeleccionado.getFila()
                    + asientoSeleccionado.getNumero() + " liberado.");
            renderizarMapaAsientos();
            actualizarEstadisticasZona();
        } else {
            mostrarMensajeError("Solo se puede liberar un asiento BLOQUEADO.");
        }
        deseleccionarAsiento();
        asientoSeleccionado = null;
    }

    private void mostrarMensajeExito(String texto) {
        lblMensajeAsiento.setText(texto);
        lblMensajeAsiento.setStyle("-fx-text-fill: #059669;");
        lblMensajeAsiento.setVisible(true);
        lblMensajeAsiento.setManaged(true);
    }

    private void mostrarMensajeError(String texto) {
        lblMensajeAsiento.setText(texto);
        lblMensajeAsiento.setStyle("-fx-text-fill: #e11d48;");
        lblMensajeAsiento.setVisible(true);
        lblMensajeAsiento.setManaged(true);
    }

    private void ocultarMensaje() {
        lblMensajeAsiento.setVisible(false);
        lblMensajeAsiento.setManaged(false);
    }
}