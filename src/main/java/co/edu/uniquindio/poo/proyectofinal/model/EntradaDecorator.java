package co.edu.uniquindio.poo.proyectofinal.model;

public abstract class EntradaDecorator implements IEntrada {

    protected IEntrada entrada;

    public EntradaDecorator(IEntrada entrada){
        this.entrada = entrada;
    }

    @Override
    public double getPrecio(){
        return entrada.getPrecio();
    }

    @Override
    public String getDescripcion(){
        return entrada.getDescripcion();
    }

    // ¡ESTE METODO ES CLAVE AQUI TAMBIEN!
    @Override
    public Asiento getAsiento() {
        return entrada.getAsiento();
    }
}