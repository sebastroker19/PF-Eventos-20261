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


    //metodo para anular asientos

    public boolean anular() {
        if (estadoEntrada != EstadoEntrada.ACTIVA) {
            System.out.println("Solo se puede anular una entrada ACTIVA.");
            return false;
        }
        estadoEntrada = EstadoEntrada.ANULADA;
        if (asiento != null) {
            asiento.liberar();
        }
        return true;
    }

    //metodo para marcar entradas como usadas

    public boolean marcarUsada() {
        if (estadoEntrada != EstadoEntrada.ACTIVA) {
            System.out.println("Solo se puede usar una entrada ACTIVA.");
            return false;
        }
        estadoEntrada = EstadoEntrada.USADA;
        return true;
    }

    //metodo para calcular el precio final

    public void calcularPrecioFinal(double precioBase, double adicional) {
        this.precioFinal = precioBase + adicional;
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