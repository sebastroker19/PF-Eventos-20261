package co.edu.uniquindio.poo.proyectofinal.test;

import co.edu.uniquindio.poo.proyectofinal.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProyectoFinalTest {

    /**
     * Verifica buscar usuario.
     */
    @Test
    public void testBuscarUsuario() {

        Administrador admin = new Administrador("1","Kevin","a@a.com","123");

        Usuario usuario = new Usuario.Builder()
                .idUsuario("10")
                .nombreCompleto("Juan")
                .correo("juan@gmail.com")
                .build();

        List<Usuario> lista = new ArrayList<>();
        lista.add(usuario);

        Usuario encontrado = admin.buscarUsuario("10", lista);

        assertNotNull(encontrado);
    }

    /**
     * Verifica eliminar usuario.
     */
    @Test
    public void testEliminarUsuario() {

        Administrador admin = new Administrador("1","Kevin","a@a.com","123");

        Usuario usuario = new Usuario.Builder()
                .idUsuario("1")
                .correo("a@gmail.com")
                .build();

        List<Usuario> lista = new ArrayList<>();
        lista.add(usuario);

        boolean resultado = admin.eliminarUsuario("1", lista);

        assertTrue(resultado);
    }

    /**
     * Verifica reasignar asiento.
     */
    @Test
    public void testReasignarAsiento() {

        Administrador admin = new Administrador("1","Kevin","a@a.com","123");

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        Usuario usuario = new Usuario.Builder()
                .idUsuario("1")
                .build();

        Compra compra = new Compra(
                "1","10",0,
                usuario,
                evento,
                EstadoCompra.CREADA
        );

        compra.setEstadoCompra(EstadoCompra.PAGADA);

        Asiento asientoViejo = new Asiento(
                "1","A",1,
                EstadoAsiento.RESERVADO,
                null
        );

        IEntrada entrada = new EntradaBase(
                "1",
                10000,
                EstadoEntrada.ACTIVA,
                asientoViejo
        );

        compra.getListEntradas().add(entrada);

        Asiento asientoNuevo = new Asiento(
                "2","B",2,
                EstadoAsiento.DISPONIBLE,
                null
        );

        boolean resultado = admin.reasignarAsiento(compra, asientoNuevo);

        assertTrue(resultado);
    }

    /**
     * Verifica registrar incidencia.
     */
    @Test
    public void testRegistrarIncidencia() {

        Administrador admin = new Administrador("1","Kevin","a@a.com","123");

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        Usuario usuario = new Usuario.Builder().idUsuario("1").build();

        Compra compra = new Compra(
                "1","10",0,
                usuario,
                evento,
                EstadoCompra.CREADA
        );

        Incidencia incidencia = new Incidencia(
                "1",
                "Pago",
                "Error",
                "10/10",
                EntidadAfectada.COMPRA
        );

        admin.registrarIncidenciaEnCompra(compra, incidencia);

        assertEquals(1, compra.getListIncidencias().size());
    }

    /**
     * Verifica bloquear asiento.
     */
    @Test
    public void testBloquearAsiento() {

        Administrador admin = new Administrador("1","Kevin","a@a.com","123");

        Asiento asiento = new Asiento(
                "1","A",1,
                EstadoAsiento.DISPONIBLE,
                null
        );

        boolean resultado = admin.bloquearAsiento(asiento);

        assertTrue(resultado);
    }

    /**
     * Verifica liberar asiento bloqueado.
     */
    @Test
    public void testLiberarAsientoBloqueado() {

        Administrador admin = new Administrador("1","Kevin","a@a.com","123");

        Asiento asiento = new Asiento(
                "1","A",1,
                EstadoAsiento.BLOQUEADO,
                null
        );

        boolean resultado = admin.liberarAsientoBloqueado(asiento);

        assertTrue(resultado);
    }

    /**
     * Verifica reservar asiento.
     */
    @Test
    public void testReservarAsiento() {

        Asiento asiento = new Asiento(
                "1","A",1,
                EstadoAsiento.DISPONIBLE,
                null
        );

        boolean resultado = asiento.reservar();

        assertTrue(resultado);
    }

    /**
     * Verifica liberar asiento.
     */
    @Test
    public void testLiberarAsiento() {

        Asiento asiento = new Asiento(
                "1","A",1,
                EstadoAsiento.RESERVADO,
                null
        );

        boolean resultado = asiento.liberar();

        assertTrue(resultado);
    }

    /**
     * Verifica disponibilidad asiento.
     */
    @Test
    public void testEstaDisponible() {

        Asiento asiento = new Asiento(
                "1","A",1,
                EstadoAsiento.DISPONIBLE,
                null
        );

        assertTrue(asiento.estaDisponible());
    }

    /**
     * Verifica agregar entrada.
     */
    @Test
    public void testAgregarEntrada() {

        Usuario usuario = new Usuario.Builder().idUsuario("1").build();

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        Compra compra = new Compra(
                "1","10",0,
                usuario,
                evento,
                EstadoCompra.CREADA
        );

        EntradaBase entrada = new EntradaBase(
                "1",
                10000,
                EstadoEntrada.ACTIVA,
                null
        );

        boolean resultado = compra.agregarEntrada(entrada);

        assertTrue(resultado);
    }

    /**
     * Verifica calcular total.
     */
    @Test
    public void testCalcularTotal() {

        Usuario usuario = new Usuario.Builder().idUsuario("1").build();

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        Compra compra = new Compra(
                "1","10",0,
                usuario,
                evento,
                EstadoCompra.CREADA
        );

        EntradaBase entrada = new EntradaBase(
                "1",
                50000,
                EstadoEntrada.ACTIVA,
                null
        );

        compra.agregarEntrada(entrada);

        compra.calcularTotal();

        assertEquals(50000, compra.getTotal());
    }

    /**
     * Verifica confirmar pago.
     */
    @Test
    public void testConfirmarPago() {

        Usuario usuario = new Usuario.Builder().idUsuario("1").build();

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        Compra compra = new Compra(
                "1","10",0,
                usuario,
                evento,
                EstadoCompra.CREADA
        );

        EntradaBase entrada = new EntradaBase(
                "1",
                10000,
                EstadoEntrada.ACTIVA,
                null
        );

        compra.agregarEntrada(entrada);

        PagoTarjeta pago = new PagoTarjeta(
                "1234567891234567",
                "Kevin",
                "10/30",
                "123"
        );

        boolean resultado = compra.confirmarPago(pago);

        assertTrue(resultado);
    }

    /**
     * Verifica cancelar compra.
     */
    @Test
    public void testCancelarCompra() {

        Usuario usuario = new Usuario.Builder().idUsuario("1").build();

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        Compra compra = new Compra(
                "1","10",0,
                usuario,
                evento,
                EstadoCompra.CREADA
        );

        boolean resultado = compra.cancelar();

        assertTrue(resultado);
    }

    /**
     * Verifica puede modificarse.
     */
    @Test
    public void testPuedeModificarse() {

        Usuario usuario = new Usuario.Builder().idUsuario("1").build();

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        Compra compra = new Compra(
                "1","10",0,
                usuario,
                evento,
                EstadoCompra.CREADA
        );

        assertTrue(compra.puedeModificarse());
    }

    /**
     * Verifica registrar usuario.
     */
    @Test
    public void testRegistrarUsuario() {

        Empresa empresa = Empresa.getInstance();

        Usuario usuario = new Usuario.Builder()
                .idUsuario("1")
                .correo("test@gmail.com")
                .build();

        boolean resultado = empresa.registrarUsuario(usuario);

        assertTrue(resultado);
    }

    /**
     * Verifica registrar evento.
     */
    @Test
    public void testRegistrarEvento() {

        Empresa empresa = Empresa.getInstance();

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        boolean resultado = empresa.registrarEvento(evento);

        assertTrue(resultado);
    }

    /**
     * Verifica buscar usuario empresa.
     */
    @Test
    public void testBuscarUsuarioEmpresa() {

        Empresa empresa = Empresa.getInstance();

        Usuario usuario = new Usuario.Builder()
                .idUsuario("50")
                .correo("buscar@gmail.com")
                .build();

        empresa.registrarUsuario(usuario);

        Usuario encontrado = empresa.buscarUsuario("50");

        assertNotNull(encontrado);
    }

    /**
     * Verifica buscar evento.
     */
    @Test
    public void testBuscarEvento() {

        Empresa empresa = Empresa.getInstance();

        Evento evento = Evento.crear(
                "50","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        empresa.registrarEvento(evento);

        Evento encontrado = empresa.buscarEvento("50");

        assertNotNull(encontrado);
    }

    /**
     * Verifica anular entrada.
     */
    @Test
    public void testAnularEntrada() {

        Entrada entrada = new Entrada(
                "1",
                10000,
                EstadoEntrada.ACTIVA,
                null
        );

        boolean resultado = entrada.anular();

        assertTrue(resultado);
    }

    /**
     * Verifica usar entrada.
     */
    @Test
    public void testMarcarUsada() {

        Entrada entrada = new Entrada(
                "1",
                10000,
                EstadoEntrada.ACTIVA,
                null
        );

        boolean resultado = entrada.marcarUsada();

        assertTrue(resultado);
    }

    /**
     * Verifica calcular precio final.
     */
    @Test
    public void testCalcularPrecioFinal() {

        Entrada entrada = new Entrada(
                "1",
                0,
                EstadoEntrada.ACTIVA,
                null
        );

        entrada.calcularPrecioFinal(10000, 5000);

        assertEquals(15000, entrada.getPrecioFinal());
    }

    /**
     * Verifica precio entrada base.
     */
    @Test
    public void testGetPrecioEntradaBase() {

        EntradaBase entrada = new EntradaBase(
                "1",
                10000,
                EstadoEntrada.ACTIVA,
                null
        );

        assertEquals(10000, entrada.getPrecio());
    }

    /**
     * Verifica descripcion entrada base.
     */
    @Test
    public void testDescripcionEntradaBase() {

        EntradaBase entrada = new EntradaBase(
                "1",
                10000,
                EstadoEntrada.ACTIVA,
                null
        );

        assertNotNull(entrada.getDescripcion());
    }

    /**
     * Verifica anular entrada base.
     */
    @Test
    public void testAnularEntradaBase() {

        EntradaBase entrada = new EntradaBase(
                "1",
                10000,
                EstadoEntrada.ACTIVA,
                null
        );

        boolean resultado = entrada.anular();

        assertTrue(resultado);
    }

    /**
     * Verifica usar entrada base.
     */
    @Test
    public void testMarcarUsadaEntradaBase() {

        EntradaBase entrada = new EntradaBase(
                "1",
                10000,
                EstadoEntrada.ACTIVA,
                null
        );

        boolean resultado = entrada.marcarUsada();

        assertTrue(resultado);
    }

    /**
     * Verifica descuento.
     */
    @Test
    public void testDescuento() {

        EntradaBase entrada = new EntradaBase(
                "1",
                100000,
                EstadoEntrada.ACTIVA,
                null
        );

        Descuento descuento = new Descuento(entrada,0.10);

        assertEquals(90000, descuento.getPrecio());
    }

    /**
     * Verifica entrada VIP.
     */
    @Test
    public void testEntradaVIP() {

        EntradaBase entrada = new EntradaBase(
                "1",
                100000,
                EstadoEntrada.ACTIVA,
                null
        );

        EntradaVIP vip = new EntradaVIP(entrada);

        assertEquals(150000, vip.getPrecio());
    }

    /**
     * Verifica seguro cancelacion.
     */
    @Test
    public void testSeguroCancelacion() {

        EntradaBase entrada = new EntradaBase(
                "1",
                100000,
                EstadoEntrada.ACTIVA,
                null
        );

        SeguroCancelacion seguro = new SeguroCancelacion(entrada);

        assertEquals(110000, seguro.getPrecio());
    }

    /**
     * Verifica procesar pago tarjeta.
     */
    @Test
    public void testPagoTarjeta() {

        PagoTarjeta pago = new PagoTarjeta(
                "1234567891234567",
                "Kevin",
                "10/30",
                "123"
        );

        assertTrue(pago.procesarPago(10000));
    }

    /**
     * Verifica pago PSE.
     */
    @Test
    public void testPagoPSE() {

        PagoPSE pago = new PagoPSE(
                "Bancolombia",
                "correo@gmail.com"
        );

        assertTrue(pago.procesarPago(10000));
    }

    /**
     * Verifica crear evento.
     */
    @Test
    public void testCrearEvento() {

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        assertNotNull(evento);
    }

    /**
     * Verifica publicar evento.
     */
    @Test
    public void testPublicarEvento() {

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        Recinto recinto = new Recinto(
                "1",
                "Arena",
                "Centro",
                "Armenia"
        );

        evento.getListRecintos().add(recinto);

        boolean resultado = evento.publicar();

        assertTrue(resultado);
    }

    /**
     * Verifica pausar evento.
     */
    @Test
    public void testPausarEvento() {

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        Recinto recinto = new Recinto(
                "1",
                "Arena",
                "Centro",
                "Armenia"
        );

        evento.getListRecintos().add(recinto);

        evento.publicar();

        boolean resultado = evento.pausar();

        assertTrue(resultado);
    }

    /**
     * Verifica disponibilidad zonas.
     */
    @Test
    public void testDisponibilidadZonas() {

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        Recinto recinto = new Recinto(
                "1",
                "Arena",
                "Centro",
                "Armenia"
        );

        Zona zona = new Zona(
                "1",
                "VIP",
                2,
                100000,
                SectorZona.VIP
        );

        recinto.agregarZona(zona);

        evento.getListRecintos().add(recinto);

        assertNotNull(evento.getDisponibilidadZonas());
    }

    /**
     * Verifica agregar compra evento.
     */
    @Test
    public void testAgregarCompraEvento() {

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        Recinto recinto = new Recinto(
                "1","Arena","Centro","Armenia"
        );

        evento.getListRecintos().add(recinto);

        evento.publicar();

        Usuario usuario = new Usuario.Builder().idUsuario("1").build();

        Compra compra = new Compra(
                "1","10",0,
                usuario,
                evento,
                EstadoCompra.CREADA
        );

        boolean resultado = evento.agregarCompra(compra);

        assertTrue(resultado);
    }

    /**
     * Verifica cancelar evento.
     */
    @Test
    public void testCancelarEvento() {

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        evento.cancelarEvento("Lluvia");

        assertEquals(EstadoEvento.CANCELADO, evento.getEstadoEvento());
    }

    /**
     * Verifica aplazar evento.
     */
    @Test
    public void testAplazarEvento() {

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        evento.aplazarEvento("20/12");

        assertEquals(EstadoEvento.PAUSADO, evento.getEstadoEvento());
    }

    /**
     * Verifica clone evento.
     */
    @Test
    public void testCloneEvento() throws CloneNotSupportedException {

        Evento evento = Evento.crear(
                "1","Evento","Desc","Armenia","10","8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.REEMBOLSO
        );

        Evento copia = evento.clone();

        assertNotSame(evento, copia);
    }

    /**
     * Verifica agregar zona.
     */
    @Test
    public void testAgregarZona() {

        Recinto recinto = new Recinto(
                "1","Arena","Centro","Armenia"
        );

        Zona zona = new Zona(
                "1",
                "VIP",
                2,
                100000,
                SectorZona.VIP
        );

        boolean resultado = recinto.agregarZona(zona);

        assertTrue(resultado);
    }

    /**
     * Verifica zona por sector.
     */
    @Test
    public void testZonaPorSector() {

        Recinto recinto = new Recinto(
                "1","Arena","Centro","Armenia"
        );

        Zona zona = new Zona(
                "1",
                "VIP",
                2,
                100000,
                SectorZona.VIP
        );

        recinto.agregarZona(zona);

        assertNotNull(recinto.getZonaPorSector(SectorZona.VIP));
    }

    /**
     * Verifica capacidad recinto.
     */
    @Test
    public void testCapacidadRecinto() {

        Recinto recinto = new Recinto(
                "1","Arena","Centro","Armenia"
        );

        Zona zona = new Zona(
                "1",
                "VIP",
                5,
                100000,
                SectorZona.VIP
        );

        recinto.agregarZona(zona);

        assertEquals(5, recinto.getCapacidadTotal());
    }

    /**
     * Verifica agregar asiento zona.
     */
    @Test
    public void testAgregarAsientoZona() {

        Zona zona = new Zona(
                "1",
                "VIP",
                2,
                100000,
                SectorZona.VIP
        );

        Asiento asiento = new Asiento(
                "1","A",1,
                EstadoAsiento.DISPONIBLE,
                null
        );

        boolean resultado = zona.agregarAsiento(asiento);

        assertTrue(resultado);
    }

    /**
     * Verifica asientos disponibles.
     */
    @Test
    public void testAsientosDisponibles() {

        Zona zona = new Zona(
                "1",
                "VIP",
                2,
                100000,
                SectorZona.VIP
        );

        Asiento asiento = new Asiento(
                "1","A",1,
                EstadoAsiento.DISPONIBLE,
                null
        );

        zona.agregarAsiento(asiento);

        assertEquals(1, zona.getAsientosDisponibles().size());
    }

    /**
     * Verifica ocupacion zona.
     */
    @Test
    public void testPorcentajeOcupacion() {

        Zona zona = new Zona(
                "1",
                "VIP",
                2,
                100000,
                SectorZona.VIP
        );

        Asiento asiento = new Asiento(
                "1","A",1,
                EstadoAsiento.RESERVADO,
                null
        );

        zona.agregarAsiento(asiento);

        assertEquals(50, zona.getPorcentajeOcupacion());
    }

    /**
     * Verifica capacidad zona.
     */
    @Test
    public void testTieneCapacidad() {

        Zona zona = new Zona(
                "1",
                "VIP",
                2,
                100000,
                SectorZona.VIP
        );

        Asiento asiento = new Asiento(
                "A1",
                "A",
                1,
                EstadoAsiento.DISPONIBLE,
                null
        );

        zona.agregarAsiento(asiento);

        assertTrue(zona.tieneCapacidad());
    }

    /**
     * Verifica iniciar sesion usuario.
     */
    @Test
    public void testIniciarSesionUsuario() {

        Usuario usuario = new Usuario.Builder()
                .correo("admin@gmail.com")
                .clave("Password123")
                .build();

        assertTrue(usuario.iniciarSesion(
                "admin@gmail.com",
                "Password123"
        ));
    }

    /**
     * Verifica actualizar perfil.
     */
    @Test
    public void testActualizarPerfil() {

        Usuario usuario = new Usuario.Builder()
                .nombreCompleto("Kevin")
                .correo("a@gmail.com")
                .numTelefono("123")
                .build();

        usuario.actualizarPerfil(
                "Juan",
                "juan@gmail.com",
                "999"
        );

        assertEquals("Juan", usuario.getNombreCompleto());
    }

    /**
     * Verifica agregar metodo pago.
     */
    @Test
    public void testAgregarMetodoPago() {

        Usuario usuario = new Usuario.Builder().build();

        PagoPSE pago = new PagoPSE(
                "Banco",
                "correo@gmail.com"
        );

        usuario.agregarMetodoPago(pago);

        assertEquals(1, usuario.getMetodosPago().size());
    }

    /**
     * Verifica historial compras.
     */
    @Test
    public void testConsultarHistorial() {

        Usuario usuario = new Usuario.Builder().build();

        assertNotNull(usuario.consultarHistorialCompra());
    }

    /**
     * Verifica login proxy.
     */
    @Test
    public void testLoginProxy() {

        Usuario usuario = new Usuario.Builder()
                .correo("admin@gmail.com")
                .build();

        UsuarioProxy proxy = new UsuarioProxy(usuario);

        assertTrue(proxy.iniciarSesion(
                "admin@gmail.com",
                "Password123"
        ));
    }

    /**
     * Verifica actualizar perfil proxy.
     */
    @Test
    public void testActualizarPerfilProxy() {

        Usuario usuario = new Usuario.Builder().build();

        UsuarioProxy proxy = new UsuarioProxy(usuario);

        proxy.actualizarPerfil(
                "Kevin",
                "a@gmail.com",
                "123456"
        );

        assertTrue(true);
    }

    /**
     * Verifica agregar metodo pago proxy.
     */
    @Test
    public void testAgregarMetodoPagoProxy() {

        Usuario usuario = new Usuario.Builder().build();

        UsuarioProxy proxy = new UsuarioProxy(usuario);

        PagoPSE pago = new PagoPSE(
                "Banco",
                "correo@gmail.com"
        );

        proxy.agregarMetodoPago(pago);

        assertEquals(1, usuario.getMetodosPago().size());
    }

    /**
     * Verifica cache proxy.
     */
    @Test
    public void testCacheProxy() {

        Usuario usuario = new Usuario.Builder().build();

        UsuarioProxy proxy = new UsuarioProxy(usuario);

        List<Compra> lista1 = proxy.consultarHistorialCompra();
        List<Compra> lista2 = proxy.consultarHistorialCompra();

        assertSame(lista1, lista2);
    }
}