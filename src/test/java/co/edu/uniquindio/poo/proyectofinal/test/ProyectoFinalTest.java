package co.edu.uniquindio.poo.proyectofinal.test;

import co.edu.uniquindio.poo.proyectofinal.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProyectoFinalTest {


    /**
     * Verifica que el evento se clone correctamente.
     */
    @Test
    public void testCloneEvento() throws CloneNotSupportedException {

        Evento evento = Evento.crear(
                "1",
                "Concierto",
                "Evento musical",
                "Armenia",
                "2026-01-01",
                "8PM",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.CANCELACION
        );

        Evento copia = evento.clone();

        assertNotNull(copia);
        assertEquals(evento.getIdEvento(), copia.getIdEvento());
    }

    /**
     * Verifica que se encuentre un usuario por id.
     */
    @Test
    public void testBuscarUsuario() {

        Administrador admin = new Administrador("1", "Admin", "a", "1");

        Usuario usuario = new Usuario.Builder()
                .idUsuario("u1")
                .nombreCompleto("Juan")
                .correo("juan@gmail.com")
                .build();

        List<Usuario> lista = new ArrayList<>();
        lista.add(usuario);

        Usuario encontrado = admin.buscarUsuario("u1", lista);

        assertNotNull(encontrado);
    }

    /**
     * Verifica que se elimine un usuario.
     */
    @Test
    public void testEliminarUsuario() {

        Administrador admin = new Administrador("1", "Admin", "a", "1");

        Usuario usuario = new Usuario.Builder()
                .idUsuario("u1")
                .nombreCompleto("Juan")
                .correo("juan@gmail.com")
                .build();

        List<Usuario> lista = new ArrayList<>();
        lista.add(usuario);

        boolean resultado = admin.eliminarUsuario("u1", lista);

        assertTrue(resultado);
        assertEquals(0, lista.size());
    }

    /**
     * Verifica el bloqueo de un asiento.
     */
    @Test
    public void testBloquearAsiento() {

        Administrador admin = new Administrador("1", "Admin", "a", "1");

        Asiento asiento = new Asiento(
                "1",
                "A",
                1,
                EstadoAsiento.DISPONIBLE,
                null
        );

        boolean resultado = admin.bloquearAsiento(asiento);

        assertTrue(resultado);
        assertEquals(EstadoAsiento.BLOQUEADO, asiento.getEstadoAsiento());
    }

    /**
     * Verifica liberar un asiento bloqueado.
     */
    @Test
    public void testLiberarAsientoBloqueado() {

        Administrador admin = new Administrador("1", "Admin", "a", "1");

        Asiento asiento = new Asiento(
                "1",
                "A",
                1,
                EstadoAsiento.BLOQUEADO,
                null
        );

        boolean resultado = admin.liberarAsientoBloqueado(asiento);

        assertTrue(resultado);
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstadoAsiento());
    }

    /**
     * Verifica registrar incidencia en compra.
     */
    @Test
    public void testRegistrarIncidencia() {

        Administrador admin = new Administrador("1", "Admin", "a", "1");

        Compra compra = new Compra(
                "1",
                "2025",
                0,
                null,
                null,
                EstadoCompra.CREADA
        );

        Incidencia incidencia = new Incidencia(
                "1",
                "Pago",
                "Error",
                "2025-01-01",
                EntidadAfectada.COMPRA
        );

        admin.registrarIncidenciaEnCompra(compra, incidencia);

        assertEquals(1, compra.getListIncidencias().size());
    }



    /**
     * Verifica reservar un asiento.
     */
    @Test
    public void testReservarAsiento() {

        Asiento asiento = new Asiento(
                "1",
                "A",
                1,
                EstadoAsiento.DISPONIBLE,
                null
        );

        boolean resultado = asiento.reservar();

        assertTrue(resultado);
        assertEquals(EstadoAsiento.RESERVADO, asiento.getEstadoAsiento());
    }

    /**
     * Verifica liberar un asiento.
     */
    @Test
    public void testLiberarAsiento() {

        Asiento asiento = new Asiento(
                "1",
                "A",
                1,
                EstadoAsiento.RESERVADO,
                null
        );

        boolean resultado = asiento.liberar();

        assertTrue(resultado);
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstadoAsiento());
    }

    /**
     * Verifica disponibilidad del asiento.
     */
    @Test
    public void testEstaDisponible() {

        Asiento asiento = new Asiento(
                "1",
                "A",
                1,
                EstadoAsiento.DISPONIBLE,
                null
        );

        assertTrue(asiento.estaDisponible());
    }



    /**
     * Verifica agregar entrada a compra.
     */
    @Test
    public void testAgregarEntrada() {

        Compra compra = new Compra(
                "1",
                "2025",
                0,
                null,
                null,
                EstadoCompra.CREADA
        );

        Entrada entrada = new Entrada(
                "1",
                50000,
                EstadoEntrada.ACTIVA,
                null
        );

        boolean resultado = compra.agregarEntrada(entrada);

        assertTrue(resultado);
        assertEquals(1, compra.getListEntradas().size());
    }

    /**
     * Verifica calcular total de compra.
     */
    @Test
    public void testCalcularTotal() {

        Compra compra = new Compra(
                "1",
                "2025",
                0,
                null,
                null,
                EstadoCompra.CREADA
        );

        Entrada e1 = new Entrada("1", 10000, EstadoEntrada.ACTIVA, null);
        Entrada e2 = new Entrada("2", 20000, EstadoEntrada.ACTIVA, null);

        compra.agregarEntrada(e1);
        compra.agregarEntrada(e2);

        compra.calcularTotal();

        assertEquals(30000, compra.getTotal());
    }

    /**
     * Verifica confirmar pago.
     */
    @Test
    public void testConfirmarPago() {

        Compra compra = new Compra(
                "1",
                "2025",
                0,
                null,
                null,
                EstadoCompra.CREADA
        );

        Asiento asiento = new Asiento(
                "1",
                "A",
                1,
                EstadoAsiento.RESERVADO,
                null
        );

        Entrada entrada = new Entrada(
                "1",
                10000,
                EstadoEntrada.ACTIVA,
                asiento
        );

        compra.agregarEntrada(entrada);

        boolean resultado = compra.confirmarPago();

        assertTrue(resultado);
        assertEquals(EstadoCompra.PAGADA, compra.getEstadoCompra());
    }

    /**
     * Verifica cancelar compra.
     */
    @Test
    public void testCancelarCompra() {

        Compra compra = new Compra(
                "1",
                "2025",
                0,
                null,
                null,
                EstadoCompra.CREADA
        );

        boolean resultado = compra.cancelar();

        assertTrue(resultado);
        assertEquals(EstadoCompra.CANCELADA, compra.getEstadoCompra());
    }

    /**
     * Verifica si la compra puede modificarse.
     */
    @Test
    public void testPuedeModificarse() {

        Compra compra = new Compra(
                "1",
                "2025",
                0,
                null,
                null,
                EstadoCompra.CREADA
        );

        assertTrue(compra.puedeModificarse());
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
        assertEquals(EstadoEntrada.ANULADA, entrada.getEstadoEntrada());
    }

    /**
     * Verifica marcar entrada usada.
     */
    @Test
    public void testMarcarEntradaUsada() {

        Entrada entrada = new Entrada(
                "1",
                10000,
                EstadoEntrada.ACTIVA,
                null
        );

        boolean resultado = entrada.marcarUsada();

        assertTrue(resultado);
        assertEquals(EstadoEntrada.USADA, entrada.getEstadoEntrada());
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
     * Verifica singleton de empresa.
     */
    @Test
    public void testGetInstanceEmpresa() {

        Empresa e1 = Empresa.getInstance();
        Empresa e2 = Empresa.getInstance();

        assertEquals(e1, e2);
    }

    /**
     * Verifica registrar usuario.
     */
    @Test
    public void testRegistrarUsuario() {

        Empresa empresa = Empresa.getInstance();

        Usuario usuario = new Usuario.Builder()
                .idUsuario("1")
                .nombreCompleto("Kevin")
                .correo("k@gmail.com")
                .build();

        boolean resultado = empresa.registrarUsuario(usuario);

        assertTrue(resultado);
    }

    /**
     * Verifica buscar usuario.
     */
    @Test
    public void testBuscarUsuarioEmpresa() {

        Empresa empresa = Empresa.getInstance();

        Usuario usuario = new Usuario.Builder()
                .idUsuario("99")
                .nombreCompleto("Kevin")
                .correo("99@gmail.com")
                .build();

        empresa.registrarUsuario(usuario);

        Usuario encontrado = empresa.buscarUsuario("99");

        assertNotNull(encontrado);
    }



    /**
     * Verifica crear evento.
     */
    @Test
    public void testCrearEvento() {

        Evento evento = Evento.crear(
                "1",
                "Concierto",
                "Evento",
                "Armenia",
                "2025",
                "8pm",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.CANCELACION
        );

        assertNotNull(evento);
    }

    /**
     * Verifica publicar evento.
     */
    @Test
    public void testPublicarEvento() {

        Evento evento = Evento.crear(
                "1",
                "Concierto",
                "Evento",
                "Armenia",
                "2025",
                "8pm",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.CANCELACION
        );

        Recinto recinto = new Recinto(
                "1",
                "Recinto",
                "Calle",
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
                "1",
                "Concierto",
                "Evento",
                "Armenia",
                "2025",
                "8pm",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.CANCELACION
        );

        evento.setEstadoEvento(EstadoEvento.PUBLICADO);

        boolean resultado = evento.pausar();

        assertTrue(resultado);
    }

    /**
     * Verifica cancelar evento.
     */
    @Test
    public void testCancelarEvento() {

        Evento evento = Evento.crear(
                "1",
                "Concierto",
                "Evento",
                "Armenia",
                "2025",
                "8pm",
                CategoriaEvento.CONCIERTO,
                PoliticaEvento.CANCELACION
        );

        boolean resultado = evento.cancelar();

        assertTrue(resultado);
    }



    /**
     * Verifica incidencia valida.
     */
    @Test
    public void testIncidenciaValida() {

        Incidencia incidencia = new Incidencia(
                "1",
                "Pago",
                "Error",
                "2025-01-01",
                EntidadAfectada.COMPRA
        );

        assertTrue(incidencia.esValida());
    }

    /**
     * Verifica filtrar incidencias por tipo.
     */
    @Test
    public void testFiltrarPorTipo() {

        List<Incidencia> lista = new ArrayList<>();

        lista.add(new Incidencia(
                "1",
                "Pago",
                "Error",
                "2025",
                EntidadAfectada.COMPRA
        ));

        List<Incidencia> resultado = Incidencia.filtrarPorTipo(lista, "Pago");

        assertEquals(1, resultado.size());
    }

    /**
     * Verifica filtrar incidencias por entidad.
     */
    @Test
    public void testFiltrarPorEntidad() {

        List<Incidencia> lista = new ArrayList<>();

        lista.add(new Incidencia(
                "1",
                "Pago",
                "Error",
                "2025",
                EntidadAfectada.COMPRA
        ));

        List<Incidencia> resultado = Incidencia.filtrarPorEntidad(
                lista,
                EntidadAfectada.COMPRA
        );

        assertEquals(1, resultado.size());
    }

    /**
     * Verifica filtrar incidencias por fecha.
     */
    @Test
    public void testFiltrarPorFecha() {

        List<Incidencia> lista = new ArrayList<>();

        lista.add(new Incidencia(
                "1",
                "Pago",
                "Error",
                "2025-01-01",
                EntidadAfectada.COMPRA
        ));

        List<Incidencia> resultado = Incidencia.filtrarPorFecha(
                lista,
                "2025-01-01",
                "2025-12-31"
        );

        assertEquals(1, resultado.size());
    }



    /**
     * Verifica agregar zona.
     */
    @Test
    public void testAgregarZona() {

        Recinto recinto = new Recinto(
                "1",
                "Recinto",
                "Calle",
                "Armenia"
        );

        Zona zona = new Zona(
                "1",
                "VIP",
                10,
                10000,
                SectorZona.VIP
        );

        boolean resultado = recinto.agregarZona(zona);

        assertTrue(resultado);
    }

    /**
     * Verifica obtener zona por sector.
     */
    @Test
    public void testGetZonaPorSector() {

        Recinto recinto = new Recinto(
                "1",
                "Recinto",
                "Calle",
                "Armenia"
        );

        Zona zona = new Zona(
                "1",
                "VIP",
                10,
                10000,
                SectorZona.VIP
        );

        recinto.agregarZona(zona);

        Zona encontrada = recinto.getZonaPorSector(SectorZona.VIP);

        assertNotNull(encontrada);
    }

    /**
     * Verifica capacidad total del recinto.
     */
    @Test
    public void testGetCapacidadTotal() {

        Recinto recinto = new Recinto(
                "1",
                "Recinto",
                "Calle",
                "Armenia"
        );

        recinto.agregarZona(new Zona(
                "1",
                "VIP",
                10,
                10000,
                SectorZona.VIP
        ));

        assertEquals(10, recinto.getCapacidadTotal());
    }



    /**
     * Verifica agregar asiento.
     */
    @Test
    public void testAgregarAsiento() {

        Zona zona = new Zona(
                "1",
                "VIP",
                2,
                10000,
                SectorZona.VIP
        );

        Asiento asiento = new Asiento(
                "1",
                "A",
                1,
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
    public void testGetAsientosDisponibles() {

        Zona zona = new Zona(
                "1",
                "VIP",
                2,
                10000,
                SectorZona.VIP
        );

        zona.agregarAsiento(new Asiento(
                "1",
                "A",
                1,
                EstadoAsiento.DISPONIBLE,
                null
        ));

        assertEquals(1, zona.getAsientosDisponibles().size());
    }

    /**
     * Verifica porcentaje de ocupacion.
     */
    @Test
    public void testGetPorcentajeOcupacion() {

        Zona zona = new Zona(
                "1",
                "VIP",
                2,
                10000,
                SectorZona.VIP
        );

        zona.agregarAsiento(new Asiento(
                "1",
                "A",
                1,
                EstadoAsiento.RESERVADO,
                null
        ));

        assertEquals(50, zona.getPorcentajeOcupacion());
    }

    /**
     * Verifica capacidad disponible.
     */
    @Test
    public void testTieneCapacidad() {

        Zona zona = new Zona(
                "1",
                "VIP",
                2,
                10000,
                SectorZona.VIP
        );

        zona.agregarAsiento(new Asiento(
                "1",
                "A",
                1,
                EstadoAsiento.DISPONIBLE,
                null
        ));

        assertTrue(zona.tieneCapacidad());
    }



    /**
     * Verifica precio entrada base.
     */
    @Test
    public void testEntradaBasePrecio() {

        IEntrada entrada = new EntradaBase(10000);

        assertEquals(10000, entrada.getPrecio());
    }

    /**
     * Verifica descripcion entrada base.
     */
    @Test
    public void testEntradaBaseDescripcion() {

        IEntrada entrada = new EntradaBase(10000);

        assertEquals("Entrada Basica", entrada.getDescripcion());
    }

    /**
     * Verifica precio VIP.
     */
    @Test
    public void testEntradaVIPPrecio() {

        IEntrada entrada = new EntradaVIP(new EntradaBase(10000));

        assertEquals(60000, entrada.getPrecio());
    }

    /**
     * Verifica descripcion VIP.
     */
    @Test
    public void testEntradaVIPDescripcion() {

        IEntrada entrada = new EntradaVIP(new EntradaBase(10000));

        assertTrue(entrada.getDescripcion().contains("VIP"));
    }

    /**
     * Verifica precio seguro cancelacion.
     */
    @Test
    public void testSeguroCancelacionPrecio() {

        IEntrada entrada = new SeguroCancelacion(new EntradaBase(10000));

        assertEquals(20000, entrada.getPrecio());
    }

    /**
     * Verifica descripcion seguro cancelacion.
     */
    @Test
    public void testSeguroCancelacionDescripcion() {

        IEntrada entrada = new SeguroCancelacion(new EntradaBase(10000));

        assertTrue(entrada.getDescripcion().contains("Seguro"));
    }

    /**
     * Verifica precio descuento.
     */
    @Test
    public void testDescuentoPrecio() {

        IEntrada entrada = new Descuento(new EntradaBase(10000), 0.2);

        assertEquals(8000, entrada.getPrecio());
    }

    /**
     * Verifica descripcion descuento.
     */
    @Test
    public void testDescuentoDescripcion() {

        IEntrada entrada = new Descuento(new EntradaBase(10000), 0.2);

        assertTrue(entrada.getDescripcion().contains("Descuento"));
    }



    /**
     * Verifica agregar compra al usuario.
     */
    @Test
    public void testAgregarCompraUsuario() {

        Usuario usuario = new Usuario.Builder()
                .idUsuario("1")
                .nombreCompleto("Kevin")
                .correo("k@gmail.com")
                .build();

        Compra compra = new Compra(
                "1",
                "2025",
                0,
                usuario,
                null,
                EstadoCompra.CREADA
        );

        usuario.agregarCompra(compra);

        assertEquals(1, usuario.getListCompras().size());
    }

    /**
     * Verifica compras por estado.
     */
    @Test
    public void testGetComprasPorEstado() {

        Usuario usuario = new Usuario.Builder()
                .idUsuario("1")
                .nombreCompleto("Kevin")
                .correo("k@gmail.com")
                .build();

        Compra compra = new Compra(
                "1",
                "2025",
                0,
                usuario,
                null,
                EstadoCompra.CREADA
        );

        usuario.agregarCompra(compra);

        List<Compra> resultado = usuario.getComprasPorEstado(EstadoCompra.CREADA);

        assertEquals(1, resultado.size());
    }

    /**
     * Verifica validar datos usuario.
     */
    @Test
    public void testValidarDatosUsuario() {

        Usuario usuario = new Usuario.Builder()
                .idUsuario("1")
                .nombreCompleto("Kevin")
                .correo("k@gmail.com")
                .build();

        assertTrue(usuario.validarDatos());
    }
}