//package Marketplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Clase con una variedad de mensajes y visualizaciones que se le muestran al usuario
 * Tiene el rol de Vista
 * De momento es solamente a consola
 * @version 8/10/2023
 * @author Grupo de Marketplace de POO
 */
public class Pages {

    public static void Home(boolean logedin) {
        if (logedin) {
            System.out.println("Sesión iniciada");
        }
        System.out.println("Bienvenido al Marketplace");
        if (!logedin){
            //TODO: cambiar mensaje de menú para incluir las opciones añadidas
            System.out.println("Seleccione una opción: \n1. Iniciar sesión\n2. Registrarse\n3. Tutorialr\n4.Buscar productos\n5.Salir");
        }
        else {
            System.out.println("Seleccione una opción: \n1.Buscar productos\n2. Configuración\n3. Ver Carrito\n4. Tutorial\n5. Cerrar Sesión\n6. Salir");
        }   
        
    }

    public static boolean Login(Scanner s) {
        System.out.println("Inicio de sesión");
        System.out.println("Nombre de Usuario: ");
        s.nextLine();
        String username = s.nextLine();
        System.out.println("Contraseña: ");
        String password = s.nextLine();
        String query = String.format("SELECT * FROM usuarios where nombreUsuario = '%s' and contrasena = '%s'", username, password);
        ArrayList<Usuario> users = Server.getUsuarios(query);
        return users.size() > 0;
    }

    public static void Status(boolean logedin) {
        if (logedin) {
            System.out.println("Sesión iniciada con éxito!");
        } else {
            System.out.println("Error, usuario o contraseña incorrectos");
        }
    }

    public static Usuario Register(Scanner s, List<Usuario> users) {
        System.out.println("Registrese y cree un nuevo usuario");
        System.out.println("Nombre: ");
        s.nextLine();
        String nombre = s.nextLine();
        String username = ""; boolean out = false;
        while (!out) {
            System.out.println("Nombre de usuario: ");
            username = s.nextLine(); out = true;
            String query = String.format("SELECT * FROM usuarios where nombreUsuario = '%s'", username);

            ArrayList<Usuario> validar = Server.getUsuarios(query);
            out = true;
            if(validar.size() > 0){
                System.out.println("Ese nombre ya existe, elija otro");
                out = false;
            }
        }
        System.out.println("Email: ");
        String email = s.nextLine();
        String contraseña = ""; 
        while (true) {
            System.out.println("Contraseña: ");
            contraseña = s.nextLine();
            System.out.println("Confirmar contraseña");
            if (contraseña.equals(s.nextLine())) {
                break;
            }
            else{
                System.out.println("Las Contraseñas no coinciden!!");
            }
        }
        System.out.println("¿Confirmar creación de usuario? Ingrese 0 para cancelar");
        if (!s.nextLine().equals("0")) {
            Usuario nuevo = new Usuario(nombre, username, contraseña, email, false);
            boolean res = Server.agregarUsuario(nuevo);
            if(res){
                System.err.println("Error al crear usuario");
                return null;
            }

            System.out.println("Creado con éxito!");
            String query1 = String.format("SELECT * FROM usuarios where nombreUsuario = '%s'", username);
            var usuarios = Server.getUsuarios(query1);
            return usuarios.get(0);
        }
        System.out.println("Cancelando...");
        return null;
    }

    public static void Logout() {
        System.out.println("Cerrando Sesión...");
    }

    public static void Search() {
        System.out.println("Ingrese el nombre de un producto que desee comprar:");
    }
    public static void SearchResults(String query, List<Producto> productos) {
        System.out.println("Resultados de búsqueda: ");
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().startsWith(query.toLowerCase())) {
                System.out.println("Id: " + p.getId() + ", Nombre: " + p.getNombre());

            }
        }
        System.out.println("Ingrese el id del producto que desee ver, o 0 para regresar ");
    }

    public static void ProductPage(Producto producto) {
        System.out.println("Detalles del producto: ");
        System.out.println("ID: " + producto.getId() + "\nNombre: " + producto.getNombre() + "\nPrecio: $" + producto.getPrecio() + "\nDescripción del producto: " + producto.getDescripcion() + "\nCantidad disponible: " + producto.getCantidad());
        System.out.println("Ingrese cero para regresar, o ingrese el número de items de este tipo que desea añadir al carrito");
    }
    public static void PPageMessage(int amount) {
        if (amount == 0) {
            System.out.println("Regresando...");
        } else {
            System.out.println("Añadido al carrito con éxito!");
        }
    }

    public static boolean Config(List<Usuario> users, int logedUser, Scanner s) {
        Usuario u = users.get(logedUser); boolean menu = true;
        while (menu) {
            System.out.println("Configuración de cuenta");
            System.out.println("ID:" + u.getId());
            if (!u.isEsVendedor()) {
                System.out.println("Seleccione una opción:\n1. Cambiar nombre\n2. Cambiar Nombre de usuario\n3. Cambiar contraseña\n4. Cambiar email\n5. Convertirse en Vendedor\n6. Regresar");
            } else {
                System.out.println("Seleccione una opción:\n1. Cambiar nombre\n2. Cambiar Nombre de usuario\n3. Cambiar contraseña\n4. Cambiar email\n5. Configuración de productos\n6. Regresar");
            }
            int op = s.nextInt();
            switch (op) {
                case 1:
                System.out.println("Ingrese el nuevo nombre: ");
                s.nextLine();
                users.get(logedUser).setNombre(s.nextLine());
                break;
                case 2:
                while (true) {
                    System.out.println("Ingrese el nuevo nombre de usuario: ");
                    s.nextLine(); boolean out = true;
                    String nusername = s.nextLine();
                    for (Usuario us : users) {
                        if (us.getNombreUsuario().equals(nusername) && users.indexOf(us) != logedUser) {
                            System.out.println("Ese nombre ya está en uso, elija otro");
                            out = false;
                        }
                    }
                    if (out) {
                        users.get(logedUser).setNombreUsuario(nusername);
                        break;
                    }
                }
                break;
                case 3:
                String contraseña;
                while (true) {
                    System.out.println("Contraseña: ");
                    s.nextLine();
                    contraseña = s.nextLine();
                    System.out.println("Confirmar contraseña");
                    if (contraseña.equals(s.nextLine())) {
                        break;
                    }
                }
                users.get(logedUser).setContraseña(contraseña);
                break;
                case 4:
                System.out.println("Ingrese el nuevo email: ");
                s.nextLine();
                String nemail = s.nextLine();
                users.get(logedUser).setEmail(nemail);
                break;
                case 5:
                if (!u.isEsVendedor()) {
                    System.out.println("¿Desea convertirse en vendedor del Marketplace? ");
                    System.out.println("1. Sí, 2. No");
                    if (s.nextInt() == 1) {
                        System.out.println("Ingrese un número de cuenta de depósito: ");                    
                        return true;
                    }

                } else {
                    return true;
                }

                break;
                case 6:
                System.out.println("Regresando... ");
                menu = false;
                break;
                default:
                System.out.println("Ingrese una opción válida");
                break;
            }
        }
        return false;
    }

    public static void ConfigStore(List<Vendedor> sellers, List<Producto> productos, int logedUser, Scanner s) {
        System.out.println("Configuración de productos");
        System.out.println("Productos que tiene a la venta:");
        for (Producto p: sellers.get(logedUser).getProductos()) {
            System.out.println("ID: " + p.getId() + ", Nombre: " + p.getNombre());
        }
        System.out.println("Ingrese una opción:\n1. Añadir producto\n2. Editar producto\n3. Eliminar producto\n4. Regresar");
        switch (s.nextInt()) {
            case 1:
            System.out.println("Ingrese el nombre del producto:");
            s.nextLine();
            String nombre = s.nextLine();
            System.out.println("Ingrese el precio:");
            int precio = s.nextInt();
            System.out.println("Ingrese la cantidad disponible en inventario:");
            int cantidad = s.nextInt();
            System.out.println("Ingrese una descripción del producto: ");
            s.nextLine();
            String descripcion = s.nextLine();
            int max = 0;
            for (Producto p : productos) {
                if (p.getId() > max) {
                    max = p.getId();
                }
            }
            Producto pr = new Producto(max + 1, nombre, precio, cantidad, descripcion, sellers.get(logedUser).getId());
            sellers.get(logedUser).crearProducto(pr);
            productos.add(pr);
            System.out.println("Producto añadido con éxito");
            break;
            case 2:
            s.nextLine();
            Producto edited = sellers.get(logedUser).editarProducto();
            for (Producto p : productos) {
                if (edited.getId() == p.getId()) {
                    productos.remove(productos.get(productos.indexOf(p)));
                    productos.add(edited);
                    break;
                }
            }
            break;
            case 3:
            System.out.println("Ingrese el id del producto que desee eliminar:");
            int eliminate = s.nextInt();
            for (Producto p : sellers.get(logedUser).getProductos()) {
                if (p.getId() == eliminate) {
                    sellers.get(logedUser).eliminarProducto(p);
                    productos.remove(p);
                    break;
                }
            }
            break;
            case 4:
            System.out.println("Regresando a Inicio...");
            break;
            default:
            System.out.println("Ingrese una opción válida");
            break;
        }
    }

    public static void Cart(List<Producto> items) {
        System.out.println("Productos en el carrito:");
        for (Producto p : items) {
            System.out.println("ID: " + p.getId() + ", Nombre: " + p.getNombre() + ", Cantidad: " + p.getCantidad() + ", Precio individual: " + p.getPrecio());
        }
        System.out.println("Elija una opción:\n1. Pagar\n2. Quitar item\n3. Regresar");
    }

    public static List<Producto> RemoveItem(List<Producto> items, Scanner s) {
        System.out.println("Ingrese el id del producto que desea quitar:");
        int id = s.nextInt();
        Producto pr = null;
        for (Producto p : items) {
            if (p.getId() == id) {
                pr = p;
            }
        }
        if (pr != null) {
            items.remove(items.indexOf(pr));
            
        }
        return items;
    }

    public static boolean Pagar(Scanner s, List<Producto> cart) {
        System.out.println("RESUMEN DE LA FACTURA");
        float TOTAL = 0;
        for (Producto p : cart) {
            System.out.println("" + p.getCantidad() + " * " + p.getNombre() + " --- $" + p.getCantidad()*p.getPrecio());
            TOTAL += p.getCantidad()*p.getPrecio();
        }
        System.out.println("TOTAL A PAGAR: $" + TOTAL);
        System.out.println("Ingrese su número de tarjeta: ");
        s.nextInt();
        System.out.println("Ingrese el mes de vencimiento: ");
        s.nextInt();
        System.out.println("Ingrese el año de vencimiento: ");
        s.nextInt();
        System.out.println("Ingrese el código de seguridad: ");
        s.nextInt();
        System.out.println("Esta a punto de pagar $" + TOTAL + " de su cuenta, ¿Está seguro?, ingrese 0 para cancelar.");
        s.nextLine();
        if (s.nextLine().equals("0")) {
            return false;
        }
        // Aquí verificaría la cuenta bancaria si el programa pudiera acceder a estados de cuenta bacarios
        return true;
        
    }

    public static void Contact(List<Producto> productos, List<Vendedor> sellers) {
        System.out.println("Pago realizado");
        System.out.println("Contacto con los vendedores: ");
        for (Producto p : productos) {
            for (Vendedor v: sellers) {
              //  System.out.println(""+v.getId()+p.getIdVendedor());
                if (v.getId() == p.getIdVendedor()) {
                    System.out.println("Email: " + v.getEmail());
                }
            }
        }
    }

    public static void Tutorial() {
        //TODO: mejorar tutorial
        System.out.println("Tutorial de uso");
        System.out.println("Este marketplace es intuitivo de usar, solamente debes navegar entre las opciones disponibles y estas te llevaran a diferentes páginas.");
        System.out.println("Cada página tiene una función particular, y te permite interactuar con las funcionalidades del Marketplace");
        System.out.println("Adelante, explora y averigua por ti mismo el potencial de esta aplicación");

        // Uso del menú principal
        System.out.println("\nMenú principal:\n\nAquí es donde seleccionarás la opción que desees realizar\nEn caso que estés sin iniciar sesión, podrás iniciar o salir.\nPor otro lado, si ya estás con tu sesión activa, podrás navegar por todas las opciones que brinda el marketplace.\nProcura seleccionar alguna opción de las que se despliegan, de lo contrario, no podrás acceder a ninguna opción\n");

        // Uso de la configuración del usuario
        System.out.println("\nConfiguración del usuario:\nLa configuración de tu cuenta, te dará la opción de cambiar tu nombre, nombre de usuario, email y contraseña.\nLa configuración variará un poco dependiendo de si es que eres un usuario normal, o si eres un vendedor.\nEn caso de ser un usuario, te dará la opción de convertirte en vendedor.\nPor otro lado, si eres vendeedor, puedes ver la configuración de tus productos.\nPor favor tomar en cuenta que si ingresa algún valor inválido, no hará nada hasta que selecciones una opción válida.\n");

        // Uso búsqueda de producto
        System.out.println("\nBúsqueda de producto:\nCon esta función serás capaz de buscar el producto que desees.\nEl programa lo buscará entre todos los productos registrados, y en caso que si exista, lo mostrará junto a su ID.\nEn caso que te arrepientas y desees volver al menú, debes de presionar 0.\n");

        // Uso del carrito
        System.out.println("\nCarrito de compras:\n\nEsta sección enlista los productos para los que has hecho una reserva. De esta manera puedes llevar un registro de los productos que quieras comprar. Cuando decidas hacer la compra de tus productos selecciona la opción de pagar para proceder a la página de compra. También puedes eliminar un producto del carrito de compras si así lo deseas.\n");

        // Uso de página de compra
        System.out.println("\nPágina de compra:\n\nEn esta sección te preparas para realizar la compra final de los productos de tu carrito. Se te mostrará un resumen de la factura y el total a pagar. A continuación deberás hacer lo siguiente: \n\n-Ingresaras los datos de tu tarjeta para proceder con el pago.\n-Se te mostrará un mensaje final de confirmación de la compra. Deberás ingresar \"0\" si quieres cancelar la compra. Cualquier otra respuesta confirmará la orden y se efectuará la compra de productos.\n\nProcederás a la página de contacto para comunicarte con el vendedor.\n");
        
    }
}

