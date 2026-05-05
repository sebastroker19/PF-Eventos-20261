package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public final class Empresa {

    private static Empresa instance;

    // Atributos Propios de la Clase

    private String nombre;
    private String nit;

    // Listas maestras del sistema

    private List<Usuario> listUsuarios;
    private List<Evento> listEventos;


    // Constructor de la clase empresa

    private Empresa(String nombre, String nit) {
        this.nombre = nombre;
        this.nit = nit;
        this.listUsuarios = new ArrayList<>();
        this.listEventos = new ArrayList<>();
    }

    // Metodo getInstance Propio del patron Singleton

    public static Empresa getInstance() {
        if (instance == null) {
            instance = new Empresa("SoluEmpresa", "jdk42");
        }
        return instance;
    }

    // Métodos para registrar usuarios

    public boolean registrarUsuario(Usuario usuario) {
        for (Usuario u : listUsuarios) {
            if (u.getCorreo().equals(usuario.getCorreo())) {
                return false;
            }
        }
        listUsuarios.add(usuario);
        return true;
    }

    // Métodos para registrar eventos

    public boolean registrarEvento(Evento evento) {
        for (Evento e : listEventos) {
            if (e.getIdEvento().equals(evento.getIdEvento())) {
                return false;
            }
        }
        listEventos.add(evento);
        return true;
    }

    // Métodos para bsuacr usuarios

    public Usuario buscarUsuario(String idUsuario) {
        for (Usuario u : listUsuarios) {
            if (u.getIdUsuario().equals(idUsuario)) return u;
        }
        return null;
    }

    // Métodos para  buscar eventos

    public Evento buscarEvento(String idEvento) {
        for (Evento e : listEventos) {
            if (e.getIdEvento().equals(idEvento)) return e;
        }
        return null;
    }

    // Métodos getter y setters

    public static void setInstance(Empresa instance) {
        Empresa.instance = instance;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Usuario> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<Usuario> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public List<Evento> getListEventos() {
        return listEventos;
    }

    public void setListEventos(List<Evento> listEventos) {
        this.listEventos = listEventos;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "nombre='" + nombre + '\'' +
                ", nit='" + nit + '\'' +
                ", listUsuarios=" + listUsuarios +
                ", listEventos=" + listEventos +
                '}';
    }
}