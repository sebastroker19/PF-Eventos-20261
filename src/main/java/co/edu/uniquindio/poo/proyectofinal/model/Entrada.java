package co.edu.uniquindio.poo.proyectofinal.model;

public class Entrada {

    //Atributos Propios de la Clase

    private String idEntrada;
    private double precioFinal;
    private EstadoEntrada estadoEntrada;
    private Asiento asiento;


    //Constructor de la Clase

    public Entrada(String idEntrada, double precioFinal, EstadoEntrada estadoEntrada, Asiento asiento) {
        this.idEntrada = idEntrada;
        this.precioFinal = precioFinal;
        this.estadoEntrada = estadoEntrada;
        this.asiento = asiento;
    }


    //Getters y Setters

    public String getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(String idEntrada) {
        this.idEntrada = idEntrada;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public EstadoEntrada getEstadoEntrada() {
        return estadoEntrada;
    }

    public void setEstadoEntrada(EstadoEntrada estadoEntrada) {
        this.estadoEntrada = estadoEntrada;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }


    //Metodo toString

    @Override
    public String toString() {
        return "Entrada{" +
                "idEntrada='" + idEntrada + '\'' +
                ", precioFinal=" + precioFinal +
                ", estadoEntrada=" + estadoEntrada +
                ", asiento=" + asiento +
                '}';
    }
}