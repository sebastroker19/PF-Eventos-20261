package co.edu.uniquindio.poo.proyectofinal.viewController;

import co.edu.uniquindio.poo.proyectofinal.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EventosController implements Initializable {

    @FXML private TextField  txtBuscar;
    @FXML private ComboBox<String> cmbCategoria;
    @FXML private ComboBox<String> cmbEstado;
    @FXML private Label      lblConteoEventos;

    @FXML private TableView<Evento>           tablaEventos;
    @FXML private TableColumn<Evento, String> colId;
    @FXML private TableColumn<Evento, String> colNombre;
    @FXML private TableColumn<Evento, String> colCategoria;
    @FXML private TableColumn<Evento, String> colCiudad;
    @FXML private TableColumn<Evento, String> colFecha;
    @FXML private TableColumn<Evento, String> colEstado;
    @FXML private TableColumn<Evento, String> colAcciones;

    @FXML private Button btnPublicar;
    @FXML private Button btnPausar;
    @FXML private Button btnCancelar;
    @FXML private Button btnVerAsientos;
    @FXML private Label  lblMensaje;

    private final Empresa empresa = Empresa.getInstance();

    private ObservableList<Evento> todosLosEventos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        poblarFiltros();
        cargarEventos();
        configurarSeleccion();
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getIdEvento()));

        colNombre.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getNombre()));

        colCategoria.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getCategoriaEvento() != null
                        ? d.getValue().getCategoriaEvento().name() : "—"));

        colCiudad.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getCiudad()));

        colFecha.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getFecha()));

        colEstado.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEstadoEvento().name()));

        // Columna acciones: muestra texto descriptivo del estado actual
        colAcciones.setCellValueFactory(d -> {
            EstadoEvento estado = d.getValue().getEstadoEvento();
            String texto = switch (estado) {
                case BORRADOR   -> "Listo para publicar";
                case PUBLICADO  -> "Activo";
                case PAUSADO    -> "En pausa";
                case CANCELADO  -> "Cancelado";
                case FINALIZADO -> "Finalizado";
            };
            return new SimpleStringProperty(texto);
        });
    }

    private void poblarFiltros() {
        cmbCategoria.getItems().add("Todas");
        for (CategoriaEvento cat : CategoriaEvento.values()) {
            cmbCategoria.getItems().add(cat.name());
        }
        cmbCategoria.setValue("Todas");

        cmbEstado.getItems().add("Todos");
        for (EstadoEvento est : EstadoEvento.values()) {
            cmbEstado.getItems().add(est.name());
        }
        cmbEstado.setValue("Todos");
    }

    private void cargarEventos() {
        todosLosEventos = FXCollections.observableArrayList(empresa.getListEventos());
        tablaEventos.setItems(todosLosEventos);
        actualizarConteo(todosLosEventos.size());
    }


    /**
     * Habilita / deshabilita los botones de acción según el estado del evento seleccionado.
     */
    private void configurarSeleccion() {
        tablaEventos.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, seleccionado) -> {
                    boolean haySeleccion = seleccionado != null;

                    if (!haySeleccion) {
                        btnPublicar.setDisable(true);
                        btnPausar.setDisable(true);
                        btnCancelar.setDisable(true);
                        btnVerAsientos.setDisable(true);
                        return;
                    }

                    EstadoEvento estado = seleccionado.getEstadoEvento();
                    btnPublicar.setDisable(estado != EstadoEvento.BORRADOR);
                    btnPausar.setDisable(estado != EstadoEvento.PUBLICADO);
                    btnCancelar.setDisable(
                            estado == EstadoEvento.CANCELADO
                                    || estado == EstadoEvento.FINALIZADO);
                    btnVerAsientos.setDisable(!haySeleccion);
                });
    }

    /** Filtra la tabla según los criterios de búsqueda seleccionados. */
    @FXML
    private void filtrarEventos() {
        String textoBuscar  = txtBuscar.getText().toLowerCase().trim();
        String categoriaFiltro = cmbCategoria.getValue();
        String estadoFiltro    = cmbEstado.getValue();

        List<Evento> filtrados = empresa.getListEventos().stream()
                .filter(e -> {
                    boolean coincideTexto = textoBuscar.isEmpty()
                            || e.getNombre().toLowerCase().contains(textoBuscar)
                            || e.getCiudad().toLowerCase().contains(textoBuscar);

                    boolean coincideCategoria =
                            "Todas".equals(categoriaFiltro)
                                    || (e.getCategoriaEvento() != null
                                    && e.getCategoriaEvento().name().equals(categoriaFiltro));

                    boolean coincideEstado =
                            "Todos".equals(estadoFiltro)
                                    || e.getEstadoEvento().name().equals(estadoFiltro);

                    return coincideTexto && coincideCategoria && coincideEstado;
                })
                .collect(Collectors.toList());

        tablaEventos.setItems(FXCollections.observableArrayList(filtrados));
        actualizarConteo(filtrados.size());
    }

    @FXML
    private void limpiarFiltros() {
        txtBuscar.clear();
        cmbCategoria.setValue("Todas");
        cmbEstado.setValue("Todos");
        tablaEventos.setItems(todosLosEventos);
        actualizarConteo(todosLosEventos.size());
        ocultarMensaje();
    }

    @FXML
    private void publicarEvento() {
        Evento evento = tablaEventos.getSelectionModel().getSelectedItem();
        if (evento == null) return;

        boolean exito = evento.publicar();

        if (exito) {
            mostrarMensajeExito("Evento \"" + evento.getNombre() + "\" publicado correctamente.");
        } else {
            mostrarMensajeError("No se pudo publicar. Verifica que el evento tenga un recinto asignado.");
        }

        tablaEventos.refresh();
        configurarBotonesParaSeleccion(evento);
    }

    @FXML
    private void pausarEvento() {
        Evento evento = tablaEventos.getSelectionModel().getSelectedItem();
        if (evento == null) return;

        boolean exito = evento.pausar();

        if (exito) {
            mostrarMensajeExito("Evento \"" + evento.getNombre() + "\" pausado.");
        } else {
            mostrarMensajeError("Solo se puede pausar un evento PUBLICADO.");
        }

        tablaEventos.refresh();
        configurarBotonesParaSeleccion(evento);
    }

    @FXML
    private void cancelarEvento() {
        Evento evento = tablaEventos.getSelectionModel().getSelectedItem();
        if (evento == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Cancelar evento");
        confirm.setHeaderText("¿Cancelar \"" + evento.getNombre() + "\"?");
        confirm.setContentText("Se cancelarán todas las compras asociadas y se notificará a los suscriptores.");

        confirm.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                evento.cancelarEvento("Cancelado desde la administración.");
                mostrarMensajeExito("Evento cancelado. Se notificó a los suscriptores.");
                tablaEventos.refresh();
                configurarBotonesParaSeleccion(evento);
            }
        });
    }

    @FXML
    private void abrirFormularioEvento() {
        Dialog<Evento> dialog = new Dialog<>();
        dialog.setTitle("Nuevo Evento");
        dialog.setHeaderText("Ingresa los datos del evento");

        ButtonType guardarBtn = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarBtn, ButtonType.CANCEL);

        // Campos del formulario
        TextField fId          = new TextField();  fId.setPromptText("EVT-001");
        TextField fNombre      = new TextField();  fNombre.setPromptText("Nombre del evento");
        TextField fDescripcion = new TextField();  fDescripcion.setPromptText("Descripción");
        TextField fCiudad      = new TextField();  fCiudad.setPromptText("Ciudad");
        TextField fFecha       = new TextField();  fFecha.setPromptText("YYYY-MM-DD");
        TextField fHora        = new TextField();  fHora.setPromptText("HH:MM");

        ComboBox<String> cbCategoria = new ComboBox<>();
        for (CategoriaEvento c : CategoriaEvento.values()) cbCategoria.getItems().add(c.name());
        cbCategoria.setValue(CategoriaEvento.values()[0].name());

        ComboBox<String> cbPolitica = new ComboBox<>();
        for (PoliticaEvento p : PoliticaEvento.values()) cbPolitica.getItems().add(p.name());
        cbPolitica.setValue(PoliticaEvento.values()[0].name());

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10); grid.setVgap(8);
        grid.addRow(0, new Label("ID:"),          fId);
        grid.addRow(1, new Label("Nombre:"),      fNombre);
        grid.addRow(2, new Label("Descripción:"), fDescripcion);
        grid.addRow(3, new Label("Ciudad:"),      fCiudad);
        grid.addRow(4, new Label("Fecha:"),       fFecha);
        grid.addRow(5, new Label("Hora:"),        fHora);
        grid.addRow(6, new Label("Categoría:"),   cbCategoria);
        grid.addRow(7, new Label("Política:"),    cbPolitica);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == guardarBtn) {
                CategoriaEvento cat = CategoriaEvento.valueOf(cbCategoria.getValue());
                PoliticaEvento pol  = PoliticaEvento.valueOf(cbPolitica.getValue());
                // Delega la construcción al Factory Method del Modelo
                return Evento.crear(
                        fId.getText(), fNombre.getText(), fDescripcion.getText(),
                        fCiudad.getText(), fFecha.getText(), fHora.getText(),
                        cat, pol);
            }
            return null;
        });

        dialog.showAndWait().ifPresent(nuevoEvento -> {
            if (nuevoEvento == null) {
                mostrarMensajeError("ID o nombre inválidos. El evento no fue creado.");
                return;
            }
            boolean registrado = empresa.registrarEvento(nuevoEvento);
            if (registrado) {
                todosLosEventos.add(nuevoEvento);
                tablaEventos.refresh();
                actualizarConteo(todosLosEventos.size());
                mostrarMensajeExito("Evento \"" + nuevoEvento.getNombre() + "\" creado en estado BORRADOR.");
            } else {
                mostrarMensajeError("Ya existe un evento con ese ID.");
            }
        });
    }

    /** Navega a la vista de asientos pasando el evento seleccionado. */
    @FXML
    private void verAsientos() {
        mostrarMensajeExito("Usa la sección 'Gestión Asientos' del menú lateral.");
    }

    private void actualizarConteo(int total) {
        lblConteoEventos.setText(total + " evento" + (total != 1 ? "s" : ""));
    }

    private void configurarBotonesParaSeleccion(Evento evento) {
        EstadoEvento estado = evento.getEstadoEvento();
        btnPublicar.setDisable(estado != EstadoEvento.BORRADOR);
        btnPausar.setDisable(estado != EstadoEvento.PUBLICADO);
        btnCancelar.setDisable(
                estado == EstadoEvento.CANCELADO || estado == EstadoEvento.FINALIZADO);
    }

    private void mostrarMensajeExito(String texto) {
        lblMensaje.setText(texto);
        lblMensaje.setStyle("-fx-text-fill: #059669;");
        lblMensaje.setVisible(true);
        lblMensaje.setManaged(true);
    }

    private void mostrarMensajeError(String texto) {
        lblMensaje.setText(texto);
        lblMensaje.setStyle("-fx-text-fill: #e11d48;");
        lblMensaje.setVisible(true);
        lblMensaje.setManaged(true);
    }

    private void ocultarMensaje() {
        lblMensaje.setVisible(false);
        lblMensaje.setManaged(false);
    }
}