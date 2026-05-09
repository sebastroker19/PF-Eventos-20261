package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.List;

public class ReporteComprasTexto extends ReporteComprasTemplate {

    @Override
    protected String generarCabecera(Usuario usuario) {
        return "==========================================\n" +
                "       REPORTE DE COMPRAS DE USUARIO      \n" +
                "==========================================\n" +
                "Cliente: " + usuario.getNombreCompleto() + "\n" +
                "Correo: " + usuario.getCorreo() + "\n" +
                "------------------------------------------";
    }

    @Override
    protected String generarCuerpoVacio() {
        return "El usuario aún no tiene compras registradas.";
    }

    @Override
    protected String generarDetalleCompra(Compra compra) {
        return " -> Compra ID: " + compra.getIdCompra() + " | Fecha: " + compra.getFechaCreacion() +
                " | Estado: " + compra.getEstadoCompra() + " | Total: $" + compra.getTotal();
    }

    @Override
    protected String generarPieDePagina(List<Compra> compras) {
        double totalGastado = 0;
        if (compras != null) {
            for (Compra c : compras) {
                if(c.getEstadoCompra() == EstadoCompra.PAGADA) {
                    totalGastado += c.getTotal();
                }
            }
        }
        return "------------------------------------------\n" +
                "Total invertido en compras exitosas: $" + totalGastado + "\n" +
                "==========================================";
    }
}