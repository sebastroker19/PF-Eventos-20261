package co.edu.uniquindio.poo.proyectofinal.model;

public abstract class EntradaDecorator implements IEntrada {

    protected IEntrada entrada;

    //Constructor de la clase

    private EntradaDecorator(IEntrada entrada){
        this.entrada = entrada;
    }

    //Metodos sobreescritos de la clase Entrada

    @Override
    public double getPrecio(){
        return entrada.getPrecio();
    }


    @Override
    public String getDescripcion(){
        return entrada.getDescripcion();
    }
}
