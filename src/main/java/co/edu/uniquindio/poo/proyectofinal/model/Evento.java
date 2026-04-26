package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public  class Evento {

    //Atributos Propios de la Clase

    private Evento instance;
    private String idEvento;
    private String nombre;
    private String descripcion;
    private String ciudad;
    private String fecha;
    private String hora;

    // Relaciones con las Clases

    private List<Usuario> listUsuarios;
    private List<Compra> listCompras;
    private List<Administrador> listAdministradores;
    private List<Recinto> listRecintos;
    private List<Entrada> listEntradas;

    // Relaciones con los Enums

    private CategoriaEvento categoriaEvento;
    private EstadoEvento estadoEvento;
    private PoliticaEvento politicaEvento;


    // Constructor

    private Evento(String idEvento, String nombre, String descripcion, String ciudad, String fecha, String hora, CategoriaEvento categoriaEvento, EstadoEvento estadoEvento, PoliticaEvento politicaEvento){
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.hora = hora;
        this.listUsuarios = new ArrayList<>();
        this.listCompras = new ArrayList<>();
        this.listAdministradores = new ArrayList<>();
        this.listRecintos = new ArrayList<>();
        this.listEntradas = new ArrayList<>();
        this.categoriaEvento = categoriaEvento;
        this.estadoEvento = estadoEvento;
        this.politicaEvento = politicaEvento;
    }


    // Getters y Setters


    public Evento getInstance() {
        return instance;
    }

    public void setInstance(Evento instance) {
        this.instance = instance;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public List<Usuario> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<Usuario> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public List<Compra> getListCompras() {
        return listCompras;
    }

    public void setListCompras(List<Compra> listCompras) {
        this.listCompras = listCompras;
    }

    public List<Administrador> getListAdministradores() {
        return listAdministradores;
    }

    public void setListAdministradores(List<Administrador> listAdministradores) {
        this.listAdministradores = listAdministradores;
    }

    public List<Recinto> getListRecintos() {
        return listRecintos;
    }

    public void setListRecintos(List<Recinto> listRecintos) {
        this.listRecintos = listRecintos;
    }

    public List<Entrada> getListEntradas() {
        return listEntradas;
    }

    public void setListEntradas(List<Entrada> listEntradas) {
        this.listEntradas = listEntradas;
    }

    public CategoriaEvento getCategoriaEvento() {
        return categoriaEvento;
    }

    public void setCategoriaEvento(CategoriaEvento categoriaEvento) {
        this.categoriaEvento = categoriaEvento;
    }

    public EstadoEvento getEstadoEvento() {
        return estadoEvento;
    }

    public void setEstadoEvento(EstadoEvento estadoEvento) {
        this.estadoEvento = estadoEvento;
    }

    public PoliticaEvento getPoliticaEvento() {
        return politicaEvento;
    }

    public void setPoliticaEvento(PoliticaEvento politicaEvento) {
        this.politicaEvento = politicaEvento;
    }


    // Metodo toString

    @Override
    public String toString() {
        return "Evento{" +
                "instance=" + instance +
                ", idEvento='" + idEvento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", listUsuarios=" + listUsuarios +
                ", listCompras=" + listCompras +
                ", listAdministradores=" + listAdministradores +
                ", listRecintos=" + listRecintos +
                ", listEntradas=" + listEntradas +
                ", categoriaEvento=" + categoriaEvento +
                ", estadoEvento=" + estadoEvento +
                ", politicaEvento=" + politicaEvento +
                '}';
    }
}
