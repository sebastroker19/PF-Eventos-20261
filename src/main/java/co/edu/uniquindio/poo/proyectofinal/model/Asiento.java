package co.edu.uniquindio.poo.proyectofinal.model;

public class Asiento {


    //Atributos Propios de la clase

    private String idAsiento;
    private String fila;
    private int numero;
    private EstadoAsiento estadoAsiento;
    private Entrada entrada;


    //Constructor de la clase

    public Asiento(String idAsiento, String fila, int numero, EstadoAsiento estadoAsiento,Entrada entrada){

        this.idAsiento = idAsiento;
        this.fila = fila;
        this.numero = numero;
        this.estadoAsiento = estadoAsiento;
        this.entrada = entrada;

    }


    // Getters y Setters


    public String getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(String idAsiento) {
        this.idAsiento = idAsiento;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public EstadoAsiento getEstadoAsiento() {
        return estadoAsiento;
    }

    public void setEstadoAsiento(EstadoAsiento estadoAsiento) {
        this.estadoAsiento = estadoAsiento;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }
}
