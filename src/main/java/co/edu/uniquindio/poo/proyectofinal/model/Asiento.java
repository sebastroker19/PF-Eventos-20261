package co.edu.uniquindio.poo.proyectofinal.model;

public class Asiento {

    //Atributos Propios de la clase

    private String idAsiento;
    private String fila;
    private int numero;
    private EstadoAsiento estadoAsiento;
    private Entrada entrada;


    //Constructor de la clase

    public Asiento(String idAsiento, String fila, int numero, EstadoAsiento estadoAsiento, Entrada entrada) {
        this.idAsiento = idAsiento;
        this.fila = fila;
        this.numero = numero;
        this.estadoAsiento = estadoAsiento;
        this.entrada = entrada;
    }

    // Marca el asiento como RESERVADO, solo si esta DISPONIBLE

    public boolean reservar() {
        if (estadoAsiento != EstadoAsiento.DISPONIBLE) {
            System.out.println("El asiento " + fila + numero + " no esta disponible.");
            return false;
        }
        estadoAsiento = EstadoAsiento.RESERVADO;
        return true;
    }

    // Libera el asiento volviendo a DISPONIBLE (al cancelar una compra)

    public boolean liberar() {
        if (estadoAsiento == EstadoAsiento.VENDIDO) {
            System.out.println("El asiento " + fila + numero + " ya fue vendido, no se puede liberar.");
            return false;
        }
        estadoAsiento = EstadoAsiento.DISPONIBLE;
        this.entrada = null;
        return true;
    }

    // Retorna true si el asiento esta DISPONIBLE

    public boolean estaDisponible() {
        return estadoAsiento == EstadoAsiento.DISPONIBLE;
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


    //Metodo toString

    @Override
    public String toString() {
        return "Asiento{" +
                "idAsiento='" + idAsiento + '\'' +
                ", fila='" + fila + '\'' +
                ", numero=" + numero +
                ", estado=" + estadoAsiento +
                '}';
    }
}