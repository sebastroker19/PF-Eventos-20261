package co.edu.uniquindio.poo.proyectofinal.model;

public class Entrada {

    private String idEntrada;
    private String precioFinal;
    private EstadoEntrada estadoEntrada;
    private Asiento asiento;


    public Entrada(String idEntrada, String precioFinal, EstadoEntrada estadoEntrada, Asiento asiento){

        this.idEntrada = idEntrada;
        this.precioFinal = precioFinal;
        this.estadoEntrada = estadoEntrada;
        this.asiento = asiento;
    }

    public String getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(String idEntrada) {
        this.idEntrada = idEntrada;
    }

    public String getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(String precioFinal) {
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
}
