package co.edu.uniquindio.poo.proyectofinal;

import co.edu.uniquindio.poo.proyectofinal.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // ── 1. Inicializar datos de prueba en el Modelo ──────────────────────
        inicializarDatosDePrueba();

        // ── 2. Cargar la vista principal ─────────────────────────────────────
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/co/edu/uniquindio/poo/proyectofinal/views/main-view.fxml"));

        Scene scene = new Scene(loader.load(), 1200, 750);
        stage.setTitle("EventOS — Plataforma de gestión de eventos");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    private void inicializarDatosDePrueba() {
        Empresa empresa = Empresa.getInstance();

        // ── Recintos ─────────────────────────────────────────────────────────
        Recinto estadio = new Recinto("REC-01", "Estadio El Campín",
                "Cra 30 # 57-60", "Bogotá");
        Recinto teatro  = new Recinto("REC-02", "Teatro Mayor Julio Mario",
                "Cll 170 # 67-51", "Bogotá");

        // ── Zonas con asientos ───────────────────────────────────────────────
        Zona zonaVip   = new Zona("ZON-01", "VIP", 24, 350000, SectorZona.VIP);
        Zona zonaPref  = new Zona("ZON-02", "Preferencial", 48, 180000, SectorZona.PREFERENCIAL);
        Zona zonaGral  = new Zona("ZON-03", "General", 72, 90000, SectorZona.GENERAL);

        // Asientos zona VIP (2 filas × 12)
        for (int fila = 1; fila <= 2; fila++) {
            for (int num = 1; num <= 12; num++) {
                String id = "VIP-" + fila + "-" + num;
                Asiento a = new Asiento(id, "V" + fila, num, EstadoAsiento.DISPONIBLE, null);
                zonaVip.getListAsientos().add(a);
            }
        }
        // Asiento bloqueado de ejemplo
        if (!zonaVip.getListAsientos().isEmpty()) {
            zonaVip.getListAsientos().get(0).setEstadoAsiento(EstadoAsiento.BLOQUEADO);
        }

        // Asientos zona Preferencial (4 filas × 12)
        for (int fila = 1; fila <= 4; fila++) {
            for (int num = 1; num <= 12; num++) {
                String id = "PREF-" + fila + "-" + num;
                Asiento a = new Asiento(id, "P" + fila, num, EstadoAsiento.DISPONIBLE, null);
                zonaPref.getListAsientos().add(a);
            }
        }

        // Asientos zona General (6 filas × 12)
        for (int fila = 1; fila <= 6; fila++) {
            for (int num = 1; num <= 12; num++) {
                String id = "GEN-" + fila + "-" + num;
                Asiento a = new Asiento(id, "G" + fila, num, EstadoAsiento.DISPONIBLE, null);
                zonaGral.getListAsientos().add(a);
            }
        }

        estadio.agregarZona(zonaVip);
        estadio.agregarZona(zonaPref);
        estadio.agregarZona(zonaGral);

        // Zona teatro (sin asientos numerados - capacidad libre)
        Zona zonaTeatro = new Zona("ZON-04", "Platea", 200, 120000, SectorZona.GENERAL);
        for (int num = 1; num <= 20; num++) {
            Asiento a = new Asiento("PLT-" + num, "P1", num, EstadoAsiento.DISPONIBLE, null);
            zonaTeatro.getListAsientos().add(a);
        }
        teatro.agregarZona(zonaTeatro);

        // ── Usuarios de prueba ───────────────────────────────────────────────
        Usuario u1 = new Usuario.Builder()
                .idUsuario("USR-01")
                .nombreCompleto("Carlos Pérez García")
                .correo("carlos.perez@email.com")
                .numTelefono("+57 310 123 4567")
                .build();

        Usuario u2 = new Usuario.Builder()
                .idUsuario("USR-02")
                .nombreCompleto("María Rodríguez López")
                .correo("maria.rodriguez@email.com")
                .numTelefono("+57 315 987 6543")
                .build();

        Usuario u3 = new Usuario.Builder()
                .idUsuario("USR-03")
                .nombreCompleto("Andrés Martínez Soto")
                .correo("andres.martinez@email.com")
                .numTelefono("+57 300 456 7890")
                .build();

        empresa.registrarUsuario(u1);
        empresa.registrarUsuario(u2);
        empresa.registrarUsuario(u3);

        // ── Eventos de prueba ────────────────────────────────────────────────
        Evento concierto = Evento.crear(
                "EVT-01", "Concierto Juanes", "Gran concierto en vivo",
                "Bogotá", "2026-08-15", "20:00",
                CategoriaEvento.CONCIERTO, PoliticaEvento.REEMBOLSO);

            Evento obra = Evento.crear(
                    "EVT-02", "Hamlet — Teatro Mayor", "Obra clásica de Shakespeare",
                    "Bogotá", "2026-09-10", "19:30",
                    CategoriaEvento.TEATRO, PoliticaEvento.REEMBOLSO);

            Evento conferencia = Evento.crear(
                    "EVT-03", "TechTalk Colombia 2026", "Conferencia de tecnología e innovación",
                    "Medellín", "2026-07-20", "09:00",
                    CategoriaEvento.CONFERENCIA, PoliticaEvento.CANCELACION);

        // Asignar recintos a los eventos
        if (concierto != null) concierto.getListRecintos().add(estadio);
        if (obra != null)      obra.getListRecintos().add(teatro);
        if (conferencia != null) conferencia.getListRecintos().add(estadio);

        // Registrar y publicar
        if (concierto != null) {
            empresa.registrarEvento(concierto);
            concierto.publicar();           // Estado: PUBLICADO
        }
        if (obra != null) {
            empresa.registrarEvento(obra);
            obra.publicar();                // Estado: PUBLICADO
        }
        if (conferencia != null) {
            empresa.registrarEvento(conferencia);
            // Se queda en BORRADOR para demostrar el flujo
        }

        // ── Compras de prueba ────────────────────────────────────────────────
        if (concierto != null && zonaVip.getListAsientos().size() > 1) {
            // Asiento VIP-1-2 para la compra de Carlos
            Asiento asientoVip = zonaVip.getListAsientos().get(1);
            asientoVip.reservar();

            IEntrada entradaVip = new EntradaBase(
                    "ENT-01",
                    zonaVip.getPrecioBase(),
                    EstadoEntrada.ACTIVA,
                    asientoVip);

            Compra compra1 = new Compra(
                    "CMP-01",
                    "2026-05-10",
                    entradaVip.getPrecio(),
                    u1,
                    concierto,
                    EstadoCompra.CREADA);

            compra1.agregarEntrada(entradaVip);

            EstrategiaPago pagoPrueba = new PagoTarjeta(
                    "1234567891234567",
                    "Carlos Pérez García",
                    "12/30",
                    "123");

            compra1.confirmarPago(pagoPrueba);

            u1.getListCompras().add(compra1);
        }

        System.out.println("✅ Datos de prueba inicializados correctamente.");
        System.out.println("   Usuarios: " + empresa.getListUsuarios().size());
        System.out.println("   Eventos:  " + empresa.getListEventos().size());
    }

    public static void main(String[] args) {
        launch(args);
    }
}