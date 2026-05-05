package co.edu.uniquindio.poo.proyectofinal.model;


//Clase Decoradora de EntradaVIP

public class EntradaVIP extends EntradaDecorator{

    public EntradaVIP(IEntrada entrada){
        super(entrada);
    }

    //Metodos sobreescritos de la Interface

    @Override
    public double getPrecio(){
        return entrada.getPrecio() + 50000;
    }

    @Override
    public String getDescripcion(){
        return entrada.getDescripcion() + " + VIP ";
    }
}
