package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    // Atributos Propios de la Clase

    private String idUsuario;
    private String nombreCompleto;
    private String correo;
    private String numTelefono;
    private int edad;
    private String direccion;

    // Relacion con la Clase Compra

    private List<Compra> listCompras;


    //Constructor Builder

    public Usuario(Builder builder){

        this.idUsuario = builder.idUsuario;
        this.nombreCompleto = builder.nombreCompleto;
        this.correo = builder.correo;
        this.numTelefono = builder.numTelefono;
        this.edad = builder.edad;
        this.direccion = builder.direccion;
        this.listCompras = builder.listCompras;
    }


    //Getters y Setters


    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Compra> getListCompras() {
        return listCompras;
    }

    public void setListCompras(List<Compra> listCompras) {
        this.listCompras = listCompras;
    }

    //Clase Builder para implementar el patron

    public static class Builder{

        private String idUsuario;
        private String nombreCompleto;
        private String correo;
        private String numTelefono;
        private int edad;
        private String direccion;
        private List<Compra> listCompras = new ArrayList<>();


        // Metodos tipo Setter


        public Builder idUsuario(String idUsuario){
            this.idUsuario = idUsuario;
            return this;
        }

        public Builder nombreCompleto(String nombreCompleto){
            this.nombreCompleto = nombreCompleto;
            return this;
        }

        public Builder correo(String correo){
            this.correo = correo;
            return this;
        }

        public Builder numTelefono(String numTelefono){
            this.numTelefono = numTelefono;
            return this;
        }

        public Builder edad (int edad){
            this.edad = edad;
            return this;
        }

        public Builder direccion(String direccion){
            this.direccion = direccion;
            return this;
        }

        public Builder listCompras(Compra compra){
            this.listCompras.add(compra);
            return this;
        }


        // Metodo build


        public Usuario build(){
            return new Usuario(this);
        }
    }


    //Metodo toString


    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", correo='" + correo + '\'' +
                ", numTelefono='" + numTelefono + '\'' +
                ", edad=" + edad +
                ", direccion='" + direccion + '\'' +
                ", listCompras=" + listCompras +
                '}';
    }
}















