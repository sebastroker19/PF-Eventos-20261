package co.edu.uniquindio.poo.proyectofinal.model;

//Clase Decoradora de Descuento

public class Descuento extends EntradaDecorator{

    public double porcentaje;

    public Descuento(IEntrada entrada,double porcentaje){
        super(entrada);
        this.porcentaje = porcentaje;
    }


    //Metodos sobreescritos de la Interface

    @Override
    public double getPrecio(){
        return entrada.getPrecio() * (1 - porcentaje);
    }



    @Override
    public String getDescripcion(){
        return entrada.getDescripcion() + "+ Descuento";
    }




}
