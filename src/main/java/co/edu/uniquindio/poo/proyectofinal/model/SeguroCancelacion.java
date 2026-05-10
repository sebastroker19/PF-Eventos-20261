package co.edu.uniquindio.poo.proyectofinal.model;

//Clase Decoradora de SeguroCancelacion

public class SeguroCancelacion extends EntradaDecorator{

    public SeguroCancelacion(IEntrada entrada){
        super(entrada);
    }


    //Metodos sobreescritos de la Interface

    @Override
    public double getPrecio(){
        return entrada.getPrecio() + 10000;
    }

    @Override
    public String getDescripcion(){
        return entrada.getDescripcion() + "+ Seguro de Cancelacion";
    }
}
