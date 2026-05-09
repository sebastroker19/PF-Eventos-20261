package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.List;

// PATRÓN TEMPLATE METHOD (Comportamiento)
public abstract class ReporteComprasTemplate {

    // 1. EL METODO PLANTILLA: Se usa 'final' para que nadie pueda cambiar este orden lógico
    public final String generarReporte(Usuario usuario) {
        StringBuilder reporte = new StringBuilder();

        // se Escribio la cabecera
        reporte.append(generarCabecera(usuario)).append("\n");

        // se escribio el cuerpo (Las compras)
        List<Compra> historial = usuario.consultarHistorialCompra();
        if (historial == null || historial.isEmpty()) {
            reporte.append(generarCuerpoVacio()).append("\n");
        } else {
            for (Compra compra : historial) {
                reporte.append(generarDetalleCompra(compra)).append("\n");
            }
        }

        // Se escribio el pie de página (Totales)
        reporte.append(generarPieDePagina(historial)).append("\n");

        return reporte.toString();
    }

    // 2. LOS "HUECOS" QUE LAS SUBCLASES DEBEN LLENAR
    protected abstract String generarCabecera(Usuario usuario);
    protected abstract String generarCuerpoVacio();
    protected abstract String generarDetalleCompra(Compra compra);
    protected abstract String generarPieDePagina(List<Compra> compras);
}