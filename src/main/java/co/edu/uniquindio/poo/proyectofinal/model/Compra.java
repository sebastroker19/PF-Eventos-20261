package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Compra {

    //Atributos Propios de la Clase

    private String idCompra;
    private String fechaCreacion;
    private double total;
    private Usuario usuario;
    private EstadoCompra estadoCompra;
    private List<Incidencia> listIncidencias;


    //Constructor de la Clase

    public Compra(String idCompra, String fechaCreacion, double total, Usuario usuario, EstadoCompra estadoCompra){

        this.idCompra = idCompra;
        this.fechaCreacion = fechaCreacion;
        this.total = total;
        this.usuario = usuario;
        this.estadoCompra = estadoCompra;
        this.listIncidencias = new ArrayList<>();


    }


    //Getters y Setters

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EstadoCompra getEstadoCompra() {
        return estadoCompra;
    }

    public void setEstadoCompra(EstadoCompra estadoCompra) {
        this.estadoCompra = estadoCompra;
    }

    public List<Incidencia> getListIncidencias() {
        return listIncidencias;
    }

    public void setListIncidencias(List<Incidencia> listIncidencias) {
        this.listIncidencias = listIncidencias;
    }


    //Metodo toString

    @Override
    public String toString() {
        return "Compra{" +
                "idCompra='" + idCompra + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", total=" + total +
                ", usuario=" + usuario +
                ", estadoCompra=" + estadoCompra +
                ", listIncidencias=" + listIncidencias +
                '}';
    }
}


