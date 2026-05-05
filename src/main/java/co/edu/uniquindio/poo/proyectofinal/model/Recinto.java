package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Recinto {

    //Atributos Propios de la Clase

    private String idRecinto;
    private String nombre;
    private String direccion;
    private String ciudad;

    //Relacion con la Clase Zona

    private List<Zona> listZonas;


    // Constructor

    public Recinto (String idRecinto, String nombre, String direccion, String ciudad){

        this.idRecinto = idRecinto;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.listZonas = new ArrayList<>();
    }


    // Metodo para agregar zonas

    public boolean agregarZona(Zona zona) {
        for (Zona z : listZonas) {
            if (z.getIdZona().equals(zona.getIdZona())) {
                System.out.println("Ya existe una zona con ese id en el recinto.");
                return false;
            }
        }
        listZonas.add(zona);
        return true;
    }

    // Metodo para llamar las zonas de un sector

    public Zona getZonaPorSector(SectorZona sector) {
        for (Zona z : listZonas) {
            if (z.getSectorZona() == sector) {
                return z;
            }
        }
        return null;
    }

    // Metodo para sumar la capacidad de todas las zonas del recinto.

    public int getCapacidadTotal() {
        int total = 0;
        for (Zona z : listZonas) {
            total += z.getCantidad();
        }
        return total;
    }

    // Getters y Setters

    public String getIdRecinto() {
        return idRecinto;
    }

    public void setIdRecinto(String idRecinto) {
        this.idRecinto = idRecinto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<Zona> getListZonas() {
        return listZonas;
    }

    public void setListZonas(List<Zona> listZonas) {
        this.listZonas = listZonas;
    }

    //Metodo toString

    @Override
    public String toString() {
        return "Recinto{" +
                "idRecinto='" + idRecinto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", listZonas=" + listZonas +
                '}';
    }
}