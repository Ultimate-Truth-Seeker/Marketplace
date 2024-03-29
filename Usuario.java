//package Marketplace;

import java.util.ArrayList;
import java.util.List;
/**
 * Clase usuario que implementa el usuario del marketplace
 * @version 22 nov 2023
 */
public class Usuario {
    private int id;
    private String nombre;
    private String nombreUsuario;
    private String contraseña;
    private String email;
    private List<Producto> carrito;
    private List<Orden> compras;
    private boolean esVendedor;

    public Usuario(){
        
    }
    /**
     * constructor de usuario
     * @param id
     * @param nombre
     * @param nombreUsuario
     * @param contraseña
     * @param email
     * @param esVendedor
     */
    public Usuario(int id, String nombre, String nombreUsuario, String contraseña, String email, boolean esVendedor) {
        this.id = id;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.email = email;
        this.carrito = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.esVendedor = esVendedor;
    }
/**
 * constructor de usuaior
 * @param nombre
 * @param nombreUsuario
 * @param contraseña
 * @param email
 * @param esVendedor
 */
    public Usuario(String nombre, String nombreUsuario, String contraseña, String email, boolean esVendedor) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.email = email;
        this.carrito = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.esVendedor = esVendedor;
    }
    // getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Producto> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<Producto> carrito) {
        this.carrito = carrito;
    }

    public List<Orden> getCompras() {
        return compras;
    }

    public void setCompras(List<Orden> compras) {
        this.compras = compras;
    }

    public boolean isEsVendedor() {
        return esVendedor;
    }

    public void setEsVendedor(boolean esVendedor) {
        this.esVendedor = esVendedor;
    }
    /**
     * añade al carrito productos
     * @param producto producto a añadir
     */
    public void añadirAlCarrito(Producto producto) {
        carrito.add(producto);
    }
    /**
     * añade orden de compra
     * @param orden la orden a añadir
     */
    public void añadirCompra(Orden orden) {
        compras.add(orden);
    }
}
