package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.List;

    public interface OperacionesUsuario {

        //interfaz que define las acciones que usa el usuario para implementar el patron proxy
        // asi pueda contar intentos fallidos

        boolean iniciarSesion(String correo, String clave);

        // verifica que la informacion tenga parametros acorde por ejemplo telefono tiene que ser numeros

        void actualizarPerfil(String nombre, String correo, String telefono);

        //permite asociar una nueva forma de pago al usuario

        void agregarMetodoPago(EstrategiaPago metodo );

        List<Compra> consultarHistorialCompra();


    }
