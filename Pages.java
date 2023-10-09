package Marketplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pages {

    public static void Home(boolean logedin) {
        if (logedin) {
            System.out.println("Sesión iniciada");
        }
        System.out.println("Bienvenido al Marketplace");
        if (!logedin){
            System.out.println("Seleccione una opción: \n1. Iniciar sesión\n2. Registrarse\n3. Salir");
        }
        else {
            System.out.println("Seleccione una opción: \n1.Buscar productos\n2. Configuración\n3. Ver Carrito\n4. Cerrar Sesión\n5. Salir");
        }   
        
    }

    public static List<String> Login(Scanner s) {
        System.out.println("Inicio de sesión");
        System.out.println("Nombre de Usuario: ");
        s.nextLine();
        String username = s.nextLine();
        System.out.println("Contraseña: ");
        String password = s.nextLine();
        List<String> data = new ArrayList<>();
        data.add(username); data.add(password);
        return data;

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
            for (Usuario u : users) {
                if (u.getNombreUsuario().equals(username)) {
                    System.out.println("Ese nombre ya existe, elija otro");
                    out = false;
                }
                if (out) {
                    break;
                }
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
        }
        System.out.println("¿Confirmar creación de usuario? Ingrese 0 para cancelar");
        if (!s.nextLine().equals("0")) {
            int max = 0;
            for (Usuario u: users) {
                if (u.getId() > max) {
                    max = u.getId();
                }
            }
            System.out.println("Creado con éxito!");
        
            return new Usuario(max + 1, nombre, username, contraseña, email, false);
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

    public static void ConfigStore(List<Vendedor> sellers, int logedUser, Scanner s) {
        System.out.println("Configuración de productos");
        System.out.println("Ingrese una opción:\n1. Añadir producto\n2. Editar producto\n3. Eliminar producto\n4. Regresar");
    }
}

