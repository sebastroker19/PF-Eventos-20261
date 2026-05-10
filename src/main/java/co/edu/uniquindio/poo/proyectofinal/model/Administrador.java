package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.List;

public class Administrador  {

    //Atributos Propios de la clase

    private String idAdministrador;
    private String nombreCompleto;
    private String correo;
    private String numTelefono;


    //Constructor de la clase

    public Administrador(String idAdministrador, String nombreCompleto, String correo, String numTelefono){
        this.idAdministrador = idAdministrador;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.numTelefono = numTelefono;

    }



    // Metodo para buscar usuarios

    public Usuario buscarUsuario(String idUsuario, List<Usuario> listUsuarios) {
        for (Usuario u : listUsuarios) {
            if (u.getIdUsuario().equals(idUsuario)) {
                return u;
            }
        }
        System.out.println("No se encontro un usuario con id: " + idUsuario);
        return null;
    }

    // Metodo para eliminar usuarios

    public boolean eliminarUsuario(String idUsuario, List<Usuario> listUsuarios) {
        Usuario encontrado = buscarUsuario(idUsuario, listUsuarios);
        if (encontrado == null) {
            return false;
        }
        listUsuarios.remove(encontrado);
        return true;
    }

    // Metodo para reasigna el asiento de una compra

    public boolean reasignarAsiento(Compra compra, Asiento asientoNuevo) {

        if (compra.getEstadoCompra() != EstadoCompra.PAGADA &&
                compra.getEstadoCompra() != EstadoCompra.CONFIRMADA) {

            System.out.println("Solo se puede reasignar en compras PAGADAS o CONFIRMADAS.");
            return false;
        }

        if (!asientoNuevo.estaDisponible()) {
            System.out.println("El asiento nuevo no esta disponible.");
            return false;
        }

        // Busca la entrada que tiene el asiento a reemplazar y lo libera

        for (IEntrada e : compra.getListEntradas()) {

            if (e.getAsiento() != null &&
                    !e.getAsiento().estaDisponible()) {

                e.getAsiento().liberar();

                asientoNuevo.reservar();

                e.getAsiento().setEstadoAsiento(EstadoAsiento.DISPONIBLE);

                return true;
            }
        }

        System.out.println("No se encontro una entrada con asiento ocupado en la compra.");
        return false;
    }

    // Metodo para registra una incidencia

    public void registrarIncidenciaEnCompra(Compra compra, Incidencia incidencia) {
        compra.getListIncidencias().add(incidencia);
    }

    // Metodo para cambia el estado de un asiento a BLOQUEADO manualmente.


    public boolean bloquearAsiento(Asiento asiento) {
        if (asiento.getEstadoAsiento() == EstadoAsiento.VENDIDO) {
            System.out.println("No se puede bloquear un asiento VENDIDO.");
            return false;
        }
        asiento.setEstadoAsiento(EstadoAsiento.BLOQUEADO);
        return true;
    }

    // Metodo para libera un asiento BLOQUEADO

    public boolean liberarAsientoBloqueado(Asiento asiento) {
        if (asiento.getEstadoAsiento() != EstadoAsiento.BLOQUEADO) {
            System.out.println("Solo se puede liberar un asiento que este BLOQUEADO.");
            return false;
        }
        asiento.setEstadoAsiento(EstadoAsiento.DISPONIBLE);
        return true;
    }

    // Getters y Setters

    public String getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(String idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    //Metodo toString

    @Override
    public String toString() {
        return "Administrador{" +
                "idAdministrador='" + idAdministrador + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", correo='" + correo + '\'' +
                ", numTelefono='" + numTelefono + '\'' +
                '}';
    }
}