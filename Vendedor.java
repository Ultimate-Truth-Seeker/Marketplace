//package Marketplace;

//Clase vendedor
import java.util.ArrayList;
import java.util.Scanner;

public class Vendedor extends Usuario{
    private int cuentaDeDeposito;
    private  ArrayList<Producto> productos;

    public int getCuentaDeDeposito() {
        return cuentaDeDeposito;
    }

    public void setCuentaDeDeposito(int cuentaDeDeposito) {
        this.cuentaDeDeposito = cuentaDeDeposito;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public Vendedor(int cuentaDeDeposito, Usuario u){
        super(u.getId(), u.getNombre(), u.getNombreUsuario(), u.getContraseña(), u.getEmail(), u.isEsVendedor());
        this.cuentaDeDeposito = cuentaDeDeposito;
        this.productos =  new ArrayList<Producto>();
    }

    public static Vendedor crearVendedor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el ID del vendedor:");
        int id = scanner.nextInt();
        System.out.println("Ingrese el nombre del vendedor:");
        String nombre = scanner.next();
        System.out.println("Ingrese el nombre de usuario del vendedor:");
        String nombreUsuario = scanner.next();
        System.out.println("Ingrese la contraseña del vendedor:");
        String contraseña = scanner.next();
        System.out.println("Ingrese el email del vendedor:");
        String email = scanner.next();
        System.out.println("Ingrese la cuenta de depósito del vendedor:");
        int cuentaDeDeposito = scanner.nextInt();
        
        Usuario usuario = new Usuario(id, nombre, nombreUsuario, contraseña, email, true);
        Vendedor vendedor = new Vendedor(cuentaDeDeposito, usuario);
        
        return vendedor;
        
    }

    public void crearProducto(Producto producto){
        this.productos.add(producto);
    }

    public void eliminarProducto(Producto producto){
        for(int i=0; i<productos.size(); i++){
            if(producto.getId() == productos.get(i).getId()){
                productos.remove(i);
                return;
            }
        }
    }

    public Producto editarProducto(){
        System.out.println("Seleccione el producto que desea editar");
        for(int i=0; i<productos.size(); i++){
            System.out.println((i+1)+". "+productos.get(i).getNombre()+ " precio: "+productos.get(i).getPrecio());
        }
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt() -1;
        Producto aux = productos.get(option);
        System.out.println("Modificar nombre: ");
        sc.nextLine();
        aux.setNombre(sc.nextLine());
        System.out.println("Modificar precio: ");
        aux.setPrecio(sc.nextInt());
        System.out.println("Modificar cantidad: ");
        aux.setCantidad(sc.nextInt());
        System.out.println("Modificar descripcion: ");
        sc.nextLine();
        aux.setDescripcion(sc.nextLine());
        //sc.close();
        return aux;
    }
    public void concretarPedido(Orden orden){
        ArrayList<Producto> ordenProductos = new ArrayList<>(orden.Productos);
        for(int i=0; i<ordenProductos.size(); i++){
            for(int j=0; j<productos.size(); j++){
                Producto aux = productos.get(j);
                if(ordenProductos.get(i).getId() == aux.getId()){
                    int nuevaCantidad = aux.getCantidad() - ordenProductos.get(i).getCantidad();
                    aux.setCantidad(nuevaCantidad);
                    break;
                }
            }
        }
    }
}
