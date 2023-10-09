package Marketplace;

public class Producto {
    private int id;
    private String nombre;
    private float precio;
    private int cantidad;
    private String descripcion;
    private int idVendedor;

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0){
            return;
        }
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        if (precio < 0){
            return;
        }
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad < 0){
            return;
        }
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Producto(int id, String nombre, float precio, int cantidad, String descripcion, int idVendedor) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.idVendedor = idVendedor;
    }
}
