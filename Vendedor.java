//Clase vendedor
import java.util.ArrayList;
import java.util.Scanner;

public class Vendedor  {
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

    public Vendedor(){
        productos =  new ArrayList<Producto>();
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

    public void editarProducto(){
        System.out.println("Seleccione el producto que desea editar");
        for(int i=0; i<productos.size(); i++){
            System.out.println((i+1)+". "+productos.get(i).getNombre()+ " precio: "+productos.get(i).getPrecio());
        }
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt() -1;
        Producto aux = productos.get(option);
        System.out.println("Modificar nombre: ");
        aux.setNombre(sc.nextLine());
        System.out.println("Modificar precio: ");
        aux.setPrecio(sc.nextInt());
        System.out.println("Modificar cantidad: ");
        aux.setCantidad(sc.nextInt());
        System.out.println("Modificar descripcion: ");
        aux.setDescripcion(sc.nextLine());
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
