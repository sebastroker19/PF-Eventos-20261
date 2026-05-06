package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Compra {

    //Atributos Propios de la Clase

    private String idCompra;
    private String fechaCreacion;
    private double total;
    private Usuario usuario;
    private Evento evento;
    private EstadoCompra estadoCompra;
    private List<Entrada> listEntradas;
    private List<Incidencia> listIncidencias;


    //Constructor de la Clase

    public Compra(String idCompra, String fechaCreacion, double total, Usuario usuario, Evento evento, EstadoCompra estadoCompra) {
        this.idCompra = idCompra;
        this.fechaCreacion = fechaCreacion;
        this.usuario = usuario;
        this.evento = evento;
        this.estadoCompra = estadoCompra;
        this.total = 0;
        this.estadoCompra = EstadoCompra.CREADA;
        this.listEntradas = new ArrayList<>();
        this.listIncidencias = new ArrayList<>();
    }

    // Metodo para agregar una entrada a la compra y recalcula el total

    public boolean agregarEntrada(Entrada entrada) {
        if (!puedeModificarse()) {
            System.out.println("La compra no puede modificarse en estado: " + estadoCompra);
            return false;
        }
        listEntradas.add(entrada);
        calcularTotal();
        return true;
    }

    // Metodo para sumar el precio de todas las entradas y actualiza el campo total

    public void calcularTotal() {
        double suma = 0;
        for (Entrada e : listEntradas) {
            suma += e.getPrecioFinal();
        }
        this.total = suma;
    }

    // Metodo para marcar la compra como PAGADA y marca cada asiento como VENDIDO

    public boolean confirmarPago() {
        if (estadoCompra != EstadoCompra.CREADA) {
            System.out.println("Solo se puede pagar una compra en estado CREADA.");
            return false;
        }
        if (listEntradas.isEmpty()) {
            System.out.println("La compra no tiene entradas.");
            return false;
        }
        estadoCompra = EstadoCompra.PAGADA;
        for (Entrada e : listEntradas) {
            if (e.getAsiento() != null) {
                e.getAsiento().setEstadoAsiento(EstadoAsiento.VENDIDO);
            }
        }
        return true;
    }

    // Metodo para cancelar la compra y libera los asientos reservados

    public boolean cancelar() {
        if (estadoCompra == EstadoCompra.CANCELADA || estadoCompra == EstadoCompra.REEMBOLSADA) {
            System.out.println("La compra ya esta cancelada o reembolsada.");
            return false;
        }
        estadoCompra = EstadoCompra.CANCELADA;
        for (Entrada e : listEntradas) {
            if (e.getAsiento() != null) {
                e.getAsiento().liberar();
            }
        }
        return true;
    }

    // Metodo para verificar si la compra esta creada

    public boolean puedeModificarse() {
        return estadoCompra == EstadoCompra.CREADA;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public EstadoCompra getEstadoCompra() {
        return estadoCompra;
    }

    public void setEstadoCompra(EstadoCompra estadoCompra) {
        this.estadoCompra = estadoCompra;
    }

    public List<Entrada> getListEntradas() {
        return listEntradas;
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
                ", estado=" + estadoCompra +
                ", entradas=" + listEntradas.size() +
                '}';
    }
}