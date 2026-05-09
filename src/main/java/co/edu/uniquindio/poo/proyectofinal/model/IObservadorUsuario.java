package co.edu.uniquindio.poo.proyectofinal.model;

//clase interface para que cualquiera que quiera recibir notificaciones deba tener un buzon de mensajes

public interface IObservadorUsuario {

    // buzon donde los eventos dejaran los mensajes

    void recibirNotificacion(String mensaje);
}
