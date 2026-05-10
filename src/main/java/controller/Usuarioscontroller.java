package co.edu.uniquindio.poo.proyectofinal.controller;

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

public class UsuariosController implements Initializable {

    @FXML private TextField txtBuscarUsuario;
    @FXML private TableView<Usuario>              tablaUsuarios;
    @FXML private TableColumn<Usuario, String>    colUserId;
    @FXML private TableColumn<Usuario, String>    colUserNombre;
    @FXML private TableColumn<Usuario, String>    colUserCorreo;
    @FXML private TableColumn<Usuario, String>    colUserTelefono;
    @FXML private TableColumn<Usuario, String>    colUserCompras;

    @FXML private Button btnEditarUsuario;
    @FXML private Button btnEliminarUsuario;
    @FXML private Button btnVerCompras;

    @FXML private TextField txtUserId;
    @FXML private TextField txtUserNombre;
    @FXML private TextField txtUserCorreo;
    @FXML private TextField txtUserTelefono;
    @FXML private Label     lblMensajeUsuario;

    private final Empresa       empresa = Empresa.getInstance();
    private final Administrador admin   = new Administrador(
            "ADMIN-01", "Administrador Sistema", "admin@eventOS.com", "N/A");

    private ObservableList<Usuario> todosLosUsuarios;

    /** Indica si el formulario está en modo edición o creación. */
    private boolean modoEdicion = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarUsuarios();
        configurarSeleccion();
    }

    private void configurarColumnas() {
        colUserId.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getIdUsuario()));

        colUserNombre.setCellValueFactory(d -> {
            String nombre = d.getValue().getNombreCompleto();
            return new SimpleStringProperty(nombre != null ? nombre : "—");
        });

        colUserCorreo.setCellValueFactory(d -> {
            String correo = d.getValue().getCorreo();
            return new SimpleStringProperty(correo != null ? correo : "—");
        });

        colUserTelefono.setCellValueFactory(d -> {
            String tel = d.getValue().getNumTelefono();
            return new SimpleStringProperty(tel != null ? tel : "—");
        });

        colUserCompras.setCellValueFactory(d -> {
            List<Compra> compras = d.getValue().getListCompras();
            int cantidad = compras != null ? compras.size() : 0;
            return new SimpleStringProperty(String.valueOf(cantidad));
        });
    }

    private void cargarUsuarios() {
        todosLosUsuarios = FXCollections.observableArrayList(empresa.getListUsuarios());
        tablaUsuarios.setItems(todosLosUsuarios);
    }

    private void configurarSeleccion() {
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, seleccionado) -> {
                    boolean haySeleccion = seleccionado != null;
                    btnEditarUsuario.setDisable(!haySeleccion);
                    btnEliminarUsuario.setDisable(!haySeleccion);
                    btnVerCompras.setDisable(!haySeleccion);
                });
    }


    /** Filtra la tabla por nombre o correo. */
    @FXML
    private void buscarUsuario() {
        String texto = txtBuscarUsuario.getText().toLowerCase().trim();

        if (texto.isEmpty()) {
            tablaUsuarios.setItems(todosLosUsuarios);
            return;
        }

        List<Usuario> filtrados = empresa.getListUsuarios().stream()
                .filter(u ->
                        (u.getNombreCompleto() != null
                                && u.getNombreCompleto().toLowerCase().contains(texto))
                                || (u.getCorreo() != null
                                && u.getCorreo().toLowerCase().contains(texto)))
                .collect(Collectors.toList());

        tablaUsuarios.setItems(FXCollections.observableArrayList(filtrados));
    }

    /**
     * Carga los datos del usuario seleccionado en el formulario lateral para su edición.
     */
    @FXML
    private void editarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        modoEdicion = true;
        txtUserId.setText(seleccionado.getIdUsuario());
        txtUserId.setDisable(true);  // El ID no se puede cambiar
        txtUserNombre.setText(seleccionado.getNombreCompleto());
        txtUserCorreo.setText(seleccionado.getCorreo());
        txtUserTelefono.setText(seleccionado.getNumTelefono());
        ocultarMensaje();
    }

    /**
     * Elimina el usuario seleccionado.
     */
    @FXML
    private void eliminarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Eliminar usuario");
        confirm.setHeaderText("¿Eliminar a \"" + seleccionado.getNombreCompleto() + "\"?");
        confirm.setContentText("Esta acción no se puede deshacer.");

        confirm.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                boolean eliminado = admin.eliminarUsuario(
                        seleccionado.getIdUsuario(),
                        empresa.getListUsuarios());

                if (eliminado) {
                    todosLosUsuarios.remove(seleccionado);
                    limpiarFormUsuario();
                    mostrarMensajeExito("Usuario eliminado correctamente.");
                } else {
                    mostrarMensajeError("No se encontró el usuario en el sistema.");
                }
            }
        });
    }

    /** Muestra el historial de compras del usuario seleccionado. */
    @FXML
    private void verComprasUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        List<Compra> compras = seleccionado.getListCompras();
        StringBuilder sb = new StringBuilder();

        if (compras == null || compras.isEmpty()) {
            sb.append("Este usuario no tiene compras registradas.");
        } else {
            for (Compra c : compras) {
                sb.append("• ").append(c.getIdCompra())
                        .append(" | Evento: ").append(
                                c.getEvento() != null ? c.getEvento().getNombre() : "—")
                        .append(" | Total: $").append(String.format("%,.0f", c.getTotal()))
                        .append(" | Estado: ").append(c.getEstadoCompra().name())
                        .append("\n");
            }
        }

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Compras de " + seleccionado.getNombreCompleto());
        info.setHeaderText("Historial de compras");
        info.setContentText(sb.toString());
        info.showAndWait();
    }

    @FXML
    private void guardarUsuario() {
        String id       = txtUserId.getText().trim();
        String nombre   = txtUserNombre.getText().trim();
        String correo   = txtUserCorreo.getText().trim();
        String telefono = txtUserTelefono.getText().trim();

        // Validación mínima en el controllero
        if (id.isEmpty() || nombre.isEmpty() || correo.isEmpty()) {
            mostrarMensajeError("ID, nombre y correo son obligatorios.");
            return;
        }

        if (modoEdicion) {
            // El Modelo actualiza los atributos del usuario existente
            Usuario seleccionado = empresa.buscarUsuario(id);
            if (seleccionado != null) {
                seleccionado.actualizarPerfil(nombre, correo, telefono);
                tablaUsuarios.refresh();
                mostrarMensajeExito("Usuario actualizado correctamente.");
            }
        } else {
            // Construcción con el Builder del Modelo
            Usuario nuevoUsuario = new Usuario.Builder()
                    .idUsuario(id)
                    .nombreCompleto(nombre)
                    .correo(correo)
                    .numTelefono(telefono)
                    .build();

            boolean registrado = empresa.registrarUsuario(nuevoUsuario);
            if (registrado) {
                todosLosUsuarios.add(nuevoUsuario);
                tablaUsuarios.refresh();
                mostrarMensajeExito("Usuario registrado correctamente.");
                limpiarFormUsuario();
            } else {
                mostrarMensajeError("Ya existe un usuario con ese correo.");
            }
        }
    }

    /** Limpia el formulario lateral y resetea el modo a creación. */
    @FXML
    private void limpiarFormUsuario() {
        modoEdicion = false;
        txtUserId.clear();
        txtUserId.setDisable(false);
        txtUserNombre.clear();
        txtUserCorreo.clear();
        txtUserTelefono.clear();
        ocultarMensaje();
    }

    @FXML
    private void abrirFormularioUsuario() {
        limpiarFormUsuario();
    }

    private void mostrarMensajeExito(String texto) {
        lblMensajeUsuario.setText(texto);
        lblMensajeUsuario.setStyle("-fx-text-fill: #059669;");
        lblMensajeUsuario.setVisible(true);
        lblMensajeUsuario.setManaged(true);
    }

    private void mostrarMensajeError(String texto) {
        lblMensajeUsuario.setText(texto);
        lblMensajeUsuario.setStyle("-fx-text-fill: #e11d48;");
        lblMensajeUsuario.setVisible(true);
        lblMensajeUsuario.setManaged(true);
    }

    private void ocultarMensaje() {
        lblMensajeUsuario.setVisible(false);
        lblMensajeUsuario.setManaged(false);
    }
}