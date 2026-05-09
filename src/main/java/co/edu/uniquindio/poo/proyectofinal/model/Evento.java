package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Evento {

    // Atributos originales de Evento

    private Evento instance;
    private String idEvento;
    private String nombre;
    private String descripcion;
    private String ciudad;
    private String fecha;
    private String hora;

    private List<Usuario> listUsuarios;
    private List<Compra> listCompras;
    private List<Administrador> listAdministradores;
    private List<Recinto> listRecintos;
    private List<Entrada> listEntradas;

    private CategoriaEvento categoriaEvento;
    private EstadoEvento estadoEvento;
    private PoliticaEvento politicaEvento;
    //agregamos la lista de los suscriptores a la clase evento
    private List<IObservadorUsuario> suscriptores;

    // Constructor de la clase evento

    private Evento(String idEvento, String nombre, String descripcion, String ciudad, String fecha, String hora, CategoriaEvento categoriaEvento, EstadoEvento estadoEvento, PoliticaEvento politicaEvento){
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.hora = hora;
        this.listUsuarios = new ArrayList<>();
        this.listCompras = new ArrayList<>();
        this.listAdministradores = new ArrayList<>();
        this.listRecintos = new ArrayList<>();
        this.listEntradas = new ArrayList<>();
        this.categoriaEvento = categoriaEvento;
        this.estadoEvento = estadoEvento;
        this.politicaEvento = politicaEvento;
        //inicializamos la lista vacia de suscriptores
        this.suscriptores = new ArrayList<>();
    }

    /**
     * Agrega una compra al evento solo si este se encuentra PUBLICADO.
     * Si el evento está en BORRADOR, PAUSADO o CANCELADO, la compra no se procesa.
     */

    public boolean agregarCompra(Compra compra) {
        if (compra == null) {
            System.out.println("Error: La compra no puede ser nula.");
            return false;
        }

        if (this.estadoEvento != EstadoEvento.PUBLICADO) {
            System.out.println("Error: No se pueden agregar compras a un evento en estado " + estadoEvento);
            return false;
        }

        listCompras.add(compra);
        System.out.println("Compra agregada exitosamente al evento: " + nombre);
        return true;
    }

    //Metodo para crear evento

    public static Evento crear(String idEvento, String nombre, String descripcion,
                               String ciudad, String fecha, String hora,
                               CategoriaEvento categoriaEvento, PoliticaEvento politicaEvento) {
        if (idEvento == null || idEvento.isBlank() || nombre == null || nombre.isBlank()) {
            return null;
        }
        return new Evento(idEvento, nombre, descripcion, ciudad, fecha, hora,
                categoriaEvento, EstadoEvento.BORRADOR, politicaEvento);
    }

    //Metodo para publicar evento

    public boolean publicar() {
        if (estadoEvento != EstadoEvento.BORRADOR || listRecintos.isEmpty()) {
            return false;
        }
        estadoEvento = EstadoEvento.PUBLICADO;
        return true;
    }

    //Metodo para pausar evento

    public boolean pausar() {
        if (estadoEvento != EstadoEvento.PUBLICADO) {
            return false;
        }
        estadoEvento = EstadoEvento.PAUSADO;
        return true;
    }


    //Metodo para ver disponiblidad por zonas

    public List<String> getDisponibilidadZonas() {
        List<String> disponibilidad = new ArrayList<>();
        for (Recinto recinto : listRecintos) {
            for (Zona zona : recinto.getListZonas()) {
                int libres = 0;
                for (Asiento a : zona.getListAsientos()) {
                    if (a.estaDisponible()) libres++;
                }
                disponibilidad.add(zona.getNombre() + ": " + libres);
            }
        }
        return disponibilidad;
    }
    // metodo del patron observer
    //aqui es como tener el boton que da permisos Para recibir notificaciones

    public void agregarSuscriptor(IObservadorUsuario usuario) {
        if (!suscriptores.contains(usuario))
            suscriptores.add(usuario);
    }

    // accion de anular la suscripcion
    public void eliminarSuscriptor(IObservadorUsuario usuario){
        suscriptores.remove(usuario);
    }
    // este es el sistema automatico para enviar las notificaciones automaticamente

    public void notificarSuscriptores(String mensajeExtra) {
        String mensajeCompleto = "El evento: " + this.nombre + " ha cambiado su estado a: " + this.estadoEvento + ". " + mensajeExtra;
        for (IObservadorUsuario suscriptor : suscriptores) {
            suscriptor.recibirNotificacion(mensajeCompleto);
        }
    }
    // notificaciones de la logica de negocio por si algo sale mal se cancela evento o se aplaza

    public void cancelarEvento(String motivo){
        // CORREGIDO: Aquí reutilizamos tu lógica de cancelación en cascada que ya habías hecho
        if (this.estadoEvento == EstadoEvento.CANCELADO || this.estadoEvento == EstadoEvento.FINALIZADO) {
            System.out.println("El evento ya estaba cancelado o finalizado.");
            return;
        }

        this.estadoEvento = EstadoEvento.CANCELADO;

        for (Compra c : listCompras) {
            c.cancelar(); // Lógica de cancelación en cascada
        }

        System.out.println("ATENCIÓN: El evento " + this.nombre + " ha sido cancelado.");
        notificarSuscriptores("Motivo: " + motivo + ". Se iniciará el proceso de reembolso.");
    }
    public void aplazarEvento(String nuevaFecha) {
        this.estadoEvento = EstadoEvento.PAUSADO;
        this.fecha = nuevaFecha;
        System.out.println("⚠️ ATENCIÓN: El evento " + this.nombre + " cambió de fecha.");

        notificarSuscriptores("La nueva fecha del evento será: " + nuevaFecha + ". Tus entradas siguen siendo válidas.");
    }


    //Setters y getters

    public Evento getInstance() {
        return instance;
    }

    public void setInstance(Evento instance) {
        this.instance = instance;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public List<Usuario> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<Usuario> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public List<Compra> getListCompras() {
        return listCompras;
    }

    public void setListCompras(List<Compra> listCompras) {
        this.listCompras = listCompras;
    }

    public List<Administrador> getListAdministradores() {
        return listAdministradores;
    }

    public void setListAdministradores(List<Administrador> listAdministradores) {
        this.listAdministradores = listAdministradores;
    }

    public List<Recinto> getListRecintos() {
        return listRecintos;
    }

    public void setListRecintos(List<Recinto> listRecintos) {
        this.listRecintos = listRecintos;
    }

    public List<Entrada> getListEntradas() {
        return listEntradas;
    }

    public void setListEntradas(List<Entrada> listEntradas) {
        this.listEntradas = listEntradas;
    }

    public CategoriaEvento getCategoriaEvento() {
        return categoriaEvento;
    }

    public void setCategoriaEvento(CategoriaEvento categoriaEvento) {
        this.categoriaEvento = categoriaEvento;
    }

    public EstadoEvento getEstadoEvento() {
        return estadoEvento;
    }

    public void setEstadoEvento(EstadoEvento estadoEvento) {
        this.estadoEvento = estadoEvento;
    }

    public PoliticaEvento getPoliticaEvento() {
        return politicaEvento;
    }

    public void setPoliticaEvento(PoliticaEvento politicaEvento) {
        this.politicaEvento = politicaEvento;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "instance=" + instance +
                ", idEvento='" + idEvento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", listUsuarios=" + listUsuarios +
                ", listCompras=" + listCompras +
                ", listAdministradores=" + listAdministradores +
                ", listRecintos=" + listRecintos +
                ", listEntradas=" + listEntradas +
                ", categoriaEvento=" + categoriaEvento +
                ", estadoEvento=" + estadoEvento +
                ", politicaEvento=" + politicaEvento +
                '}';
    }
}