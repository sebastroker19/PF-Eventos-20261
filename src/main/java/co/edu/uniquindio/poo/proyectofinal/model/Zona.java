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
