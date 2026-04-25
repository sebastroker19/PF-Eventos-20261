package co.edu.uniquindio.poo.proyectofinal.model;

public class Compra {

    private String idCompra;
    private String fechaCreacion;
    private double total;
    private Usuario usuario;
    private EstadoCompra estadoCompra;
    private List<Incidencia> listIncidencias;


    public Compra(String idCompra, String fechaCreacion, double total, Usuario usuario, EstadoCompra estadoCompra){

        this.idCompra = idCompra;
        this.fechaCreacion = fechaCreacion;
        this.total = total;
        this.usuario = usuario;
        this.estadoCompra = estadoCompra;
        this.listIncidencias = new ArrayList<>();


    }


    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EstadoCompra getEstadoCompra() {
        return estadoCompra;
    }

    public void setEstadoCompra(EstadoCompra estadoCompra) {
        this.estadoCompra = estadoCompra;
    }

    public List<Incidencia> getListIncidencias() {
        return listIncidencias;
    }

    public void setListIncidencias(List<Incidencia> listIncidencias) {
        this.listIncidencias = listIncidencias;
    }
}
