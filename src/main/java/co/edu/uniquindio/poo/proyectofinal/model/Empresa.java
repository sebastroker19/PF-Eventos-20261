package co.edu.uniquindio.poo.proyectofinal.model;

public final class Empresa {

    //Instancia de la Clase

    private static Empresa instance;

    //Atributos Propios de la Clase

    private String nombre;
    private String nit;


    //Constructor

    private Empresa(String nombre, String nit){
        this.nombre = nombre;
        this.nit = nit;

    }

    //Metodo getInstance Propio del patron Singleton

    public static Empresa getInstance(){

        if(instance == null){
            instance = new Empresa("SoluEmpresa","jdk42");
        }
        return instance;
    }


    //Getters y Setters

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "nombre='" + nombre + '\'' +
                ", nit='" + nit + '\'' +
                '}';
    }
}
