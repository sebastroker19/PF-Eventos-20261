package co.edu.uniquindio.poo.proyectofinal.model;

public class Administrador implements Cloneable {

    //Atributos Propios de la clase

    private String idAdministrador;
    private String nombreCompleto;
    private String correo;
    private String numTelefono;


    //Constructor de la clase

    public Administrador(String idAdministrador, String nombreCompleto, String correo, String numTelefono){
        this.idAdministrador = idAdministrador;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.numTelefono = numTelefono;

    }

    // Metodo para clonar de el Patron Prototype

    public Administrador clone() throws CloneNotSupportedException {
        return (Administrador) super.clone();
    }


    // Getters y Setters


    public String getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(String idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }


    //Metodo toString

    @Override
    public String toString() {
        return "Administrador{" +
                "idAdministrador='" + idAdministrador + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", correo='" + correo + '\'' +
                ", numTelefono='" + numTelefono + '\'' +
                '}';
    }
}
