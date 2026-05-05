package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Incidencia {

    //Atributos Propios de la Clase

    private String idIncidencia;
    private String tipo;
    private String descripcion;
    private String fecha;

    // Relacion con el Enum

    private EntidadAfectada entidadAfectada;

    //Constructor

    public Incidencia(String idIncidencia, String tipo, String descripcion, String fecha, EntidadAfectada entidadAfectada){

        this.idIncidencia = idIncidencia;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.entidadAfectada = entidadAfectada;

    }

    // metodo para verificar que la incidencia tenga los datos minimos
    // id, tipo y fecha

    public boolean esValida() {
        if (idIncidencia == null || idIncidencia.isBlank()) {
            System.out.println("El id de la incidencia es obligatorio.");
            return false;
        }
        if (tipo == null || tipo.isBlank()) {
            System.out.println("El tipo de la incidencia es obligatorio.");
            return false;
        }
        if (fecha == null || fecha.isBlank()) {
            System.out.println("La fecha de la incidencia es obligatoria.");
            return false;
        }
        return true;
    }

    // metodo para retornar las incidencias del mismo tipo

    public static List<Incidencia> filtrarPorTipo(List<Incidencia> lista, String tipo) {
        List<Incidencia> resultado = new ArrayList<>();
        for (Incidencia i : lista) {
            if (i.getTipo().equalsIgnoreCase(tipo)) {
                resultado.add(i);
            }
        }
        return resultado;
    }

    // metodo para retornar las incidencias de la misma entidad

    public static List<Incidencia> filtrarPorEntidad(List<Incidencia> lista, EntidadAfectada entidad) {
        List<Incidencia> resultado = new ArrayList<>();
        for (Incidencia i : lista) {
            if (i.getEntidadAfectada() == entidad) {
                resultado.add(i);
            }
        }
        return resultado;
    }

    // metodo para retornar las incidencias dentro del rango de fecha
    // Las fechas deben estar en formato "YYYY-MM-DD"

    public static List<Incidencia> filtrarPorFecha(List<Incidencia> lista, String desde, String hasta) {
        List<Incidencia> resultado = new ArrayList<>();
        for (Incidencia i : lista) {
            if (i.getFecha().compareTo(desde) >= 0 && i.getFecha().compareTo(hasta) <= 0) {
                resultado.add(i);
            }
        }
        return resultado;
    }

    //Getters y Setters

    public String getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(String idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public EntidadAfectada getEntidadAfectada() {
        return entidadAfectada;
    }

    public void setEntidadAfectada(EntidadAfectada entidadAfectada) {
        this.entidadAfectada = entidadAfectada;
    }

    //Metodo toString

    @Override
    public String toString() {
        return "Incidencia{" +
                "idIncidencia='" + idIncidencia + '\'' +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", entidadAfectada=" + entidadAfectada +
                '}';
    }
}