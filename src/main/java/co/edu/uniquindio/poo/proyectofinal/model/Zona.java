package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Zona {

    // Atributos Propios de la Clase

    private String idZona;
    private String nombre;
    private int cantidad;
    private double precioBase;
    private SectorZona sectorZona;
    private List<Asiento> listAsientos;

    // Constructor

    public Zona(String idZona, String nombre, int cantidad, double precioBase, SectorZona sectorZona){

        this.idZona = idZona;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioBase = precioBase;
        this.sectorZona = sectorZona;
        this.listAsientos = new ArrayList<>();
    }

    // Metodo para agrega un asiento a la zona

    public boolean agregarAsiento(Asiento asiento) {
        if (listAsientos.size() >= cantidad) {
            System.out.println("La zona " + nombre + " ya alcanzo su capacidad maxima.");
            return false;
        }
        listAsientos.add(asiento);
        return true;
    }

    // Metodo para retorna una lista solo con los asientos en estado DISPONIBLE.

    public List<Asiento> getAsientosDisponibles() {
        List<Asiento> disponibles = new ArrayList<>();
        for (Asiento a : listAsientos) {
            if (a.estaDisponible()) {
                disponibles.add(a);
            }
        }
        return disponibles;
    }

    // Metodo para retornar el porcentaje de asientos ocupados.

    public double getPorcentajeOcupacion() {
        if (cantidad == 0) {
            return 0;
        }
        int ocupados = 0;
        for (Asiento a : listAsientos) {
            if (!a.estaDisponible()) {
                ocupados++;
            }
        }
        return (ocupados * 100.0) / cantidad;
    }

    // Metodo para verificar si hay asientos disponibles

    public boolean tieneCapacidad() {
        return !getAsientosDisponibles().isEmpty();
    }

    // Getters y Setters

    public String getIdZona() {
        return idZona;
    }

    public void setIdZona(String idZona) {
        this.idZona = idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public SectorZona getSectorZona() {
        return sectorZona;
    }

    public void setSectorZona(SectorZona sectorZona) {
        this.sectorZona = sectorZona;
    }

    public List<Asiento> getListAsientos() {
        return listAsientos;
    }

    public void setListAsientos(List<Asiento> listAsientos) {
        this.listAsientos = listAsientos;
    }

    //Metodo toString

    @Override
    public String toString() {
        return "Zona{" +
                "idZona='" + idZona + '\'' +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", precioBase=" + precioBase +
                ", sectorZona=" + sectorZona +
                ", listAsientos=" + listAsientos +
                '}';
    }


}