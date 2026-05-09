package co.edu.uniquindio.poo.proyectofinal.model;

public class EntradaBase implements IEntrada {

    // 1. Atributos completos (La fusión)
    private String idEntrada;
    private double precioBase;
    private EstadoEntrada estadoEntrada;
    private Asiento asiento;

    // 2. Constructor completo
    public EntradaBase(String idEntrada, double precioBase, EstadoEntrada estadoEntrada, Asiento asiento) {
        this.idEntrada = idEntrada;
        this.precioBase = precioBase;
        this.estadoEntrada = estadoEntrada;
        this.asiento = asiento;
    }

    // 3. CUMPLIMOS EL CONTRATO DE IENTRADA
    @Override
    public double getPrecio() {
        return precioBase;
    }

    @Override
    public String getDescripcion() {
        return "Entrada Básica (ID: " + idEntrada + ")";
    }

    @Override
    public Asiento getAsiento() { // ¡El método que faltaba!
        return asiento;
    }

    // 4. Lógica de negocio (Anular y Usar)
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

    public boolean marcarUsada() {
        if (estadoEntrada != EstadoEntrada.ACTIVA) {
            System.out.println("Solo se puede usar una entrada ACTIVA.");
            return false;
        }
        estadoEntrada = EstadoEntrada.USADA;
        return true;
    }

    // Getters y Setters
    public String getIdEntrada() { return idEntrada; }
    public void setIdEntrada(String idEntrada) { this.idEntrada = idEntrada; }
    public EstadoEntrada getEstadoEntrada() { return estadoEntrada; }
    public void setEstadoEntrada(EstadoEntrada estadoEntrada) { this.estadoEntrada = estadoEntrada; }
}