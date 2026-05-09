package co.edu.uniquindio.poo.proyectofinal.model;

public class Incidencia {

    // Atributos Propios de la Clase (Los de tu compañero)
    private String idIncidencia;
    private String tipo;
    private String descripcion;
    private String fecha;
    private EntidadAfectada entidadAfectada;

    // creado Para saber si el problema ya se solucionó
    private EstadoIncidencia estado;

    // Constructor
    public Incidencia(String idIncidencia, String tipo, String descripcion, String fecha, EntidadAfectada entidadAfectada){
        this.idIncidencia = idIncidencia;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.entidadAfectada = entidadAfectada;

        // Toda incidencia se crea ABIERTA por defecto
        this.estado = EstadoIncidencia.ABIERTA;
    }

    //  Metodo para que el administrador cierre el caso

    public void resolverIncidencia() {
        if (this.estado == EstadoIncidencia.RESUELTA) {
            System.out.println("Esta incidencia ya estaba resuelta.");
            return;
        }
        this.estado = EstadoIncidencia.RESUELTA;
        System.out.println("La incidencia " + idIncidencia + " ha sido marcada como RESUELTA.");
    }

    // Getters y Setters

    public EstadoIncidencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }

    public EntidadAfectada getEntidadAfectada() {
        return entidadAfectada;
    }

    public void setEntidadAfectada(EntidadAfectada entidadAfectada) {
        this.entidadAfectada = entidadAfectada;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(String idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    // Metodo toString actualizado
    @Override
    public String toString() {
        return "Incidencia{" +
                "idIncidencia='" + idIncidencia + '\'' +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", entidadAfectada=" + entidadAfectada +
                ", estado=" + estado +
                '}';
    }
}