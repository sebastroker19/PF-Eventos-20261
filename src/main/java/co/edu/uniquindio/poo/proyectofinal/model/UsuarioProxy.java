package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.List;

public class UsuarioProxy implements OperacionesUsuario{
    private Usuario usuarioReal;
    private List<Compra> CacheCompras;

    public UsuarioProxy(Usuario usuarioReal){
        this.usuarioReal = usuarioReal;
    }
    //sobreescrito de los metodos de OperacionUsuario
    @Override
    public boolean iniciarSesion(String correo, String clave) {
        //asegurar que quien inicia sesion es el usuario verdadero
        if(correo == null || !correo.contains("@") || clave == null || clave.isEmpty() ){
            System.out.println("[Alerta]: Formato de correo o clave inválido. Acceso denegado.");
            return false;
        }
        System.out.println("[Info]: Datos correctos. Pasando al sistema real...");
        return usuarioReal.iniciarSesion(correo, clave);
    }
    @Override
    public void actualizarPerfil(String nombre, String correo, String telefono){
        //validar que el telefono no tenga letras
        if(!telefono.matches("[0-9]+")){
            System.out.println("[Error]: El teléfono solo debe contener números. Operación rechazada.");
            return;
        }
        usuarioReal.actualizarPerfil(nombre, correo, telefono);
    }

    @Override
    public void agregarMetodoPago(EstrategiaPago metodo){
        usuarioReal.agregarMetodoPago(metodo);
        System.out.println("Método de pago enviado al usuario real.");
    }

    @Override
    public List<Compra> consultarHistorialCompra() {
        if (CacheCompras == null){
            System.out.println("Cargando el historial por primera vez puede tardar un poco...");
            CacheCompras = usuarioReal.consultarHistorialCompra();
        }else{
            System.out.println("Entregando historial guardado en memoria");
        }
        return CacheCompras;

    }

}
