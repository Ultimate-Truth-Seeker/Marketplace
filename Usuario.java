import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private String nombreUsuario;
    private String contraseña;
    private String email;
    private List<Producto> carrito;
    private List<Orden> compras;
    private boolean esVendedor;

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
}
