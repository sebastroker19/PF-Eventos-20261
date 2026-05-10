package co.edu.uniquindio.poo.proyectofinal.model;

public class PagoTarjeta implements EstrategiaPago {
    private String numeroTarjeta;
    private String titular;
    private String fechaVencimiento;
    private String cvv;

    // Constructor
    public PagoTarjeta(String numeroTarjeta, String titular, String fechaVencimiento, String cvv) {
        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
        this.fechaVencimiento = fechaVencimiento;
        this.cvv = cvv;
    }

    @Override
    public boolean procesarPago(double monto) {
        // Aquí iría la validación real con un banco.
        // Por ahora, simulamos que el pago se aprueba y ocultamos parte de la tarjeta por seguridad.
        String ultimosDigitos = numeroTarjeta.substring(numeroTarjeta.length() - 4);

        System.out.println("💳 Procesando pago de $" + monto + " con Tarjeta terminada en " + ultimosDigitos);
        System.out.println("✅ Pago aprobado para el titular: " + titular);

        return true; // Retorna true porque el pago fue exitoso
    }

}

