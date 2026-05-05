package co.edu.uniquindio.poo.proyectofinal.model;

public class EntradaBase implements IEntrada {

     public double precioBase;

     //Constructor de la clase

     public EntradaBase(double precioBase){
         this.precioBase = precioBase;
     }

     // Metodos sobreescritos de la Interface IEntrada

     @Override
    public double getPrecio(){
         return precioBase;
    }


    @Override
    public String getDescripcion(){
         return "Entrada Basica";
    }


}
