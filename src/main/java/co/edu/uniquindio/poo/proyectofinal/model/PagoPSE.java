package co.edu.uniquindio.poo.proyectofinal.model;

public class PagoPSE implements EstrategiaPago {
    private String banco;
    private String correoPSE;

    // Constructor
    public PagoPSE(String banco, String correoPSE) {
        this.banco = banco;
        this.correoPSE = correoPSE;
    }

    @Override
    public boolean procesarPago(double monto) {
        // Simulamos la redirección a la sucursal virtual del banco
        System.out.println("Redirigiendo a la pasarela de " + banco + "...");
        System.out.println("Procesando pago de $" + monto + " desde la cuenta PSE: " + correoPSE);
        System.out.println("Transferencia PSE exitosa.");

        return true;
    }

}
