package co.edu.uniquindio.poo.proyectofinal.model;

public interface IEntrada {
    double getPrecio();
    String getDescripcion();
    Asiento getAsiento(); // Necesario para que la Compra pueda marcar el asiento como vendido
}