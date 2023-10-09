package Marketplace;

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

    public static String[] Login(Scanner s) {
        System.out.println("Inicio de sesión");
        System.out.println("Nombre de Usuario: ");
        s.nextLine();
        String username = s.nextLine();
        System.out.println("Contraseña: ");
        String password = s.nextLine();
        String[] data = {username, password};
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
        System.out.println("Nombre de usuario: ");
        String username = s.nextLine();
        System.out.println("Email: ");
        String email = s.nextLine();
        String contraseña = ""; 
        while (true) {
            System.out.println("Contraseña: ");
            contraseña = s.nextLine();
            System.out.println("Confirmar contraseña");
            if (contraseña == s.nextLine()) {
                break;
            }
        }
        System.out.println("¿Confirmar creación de usuario? Ingrese 0 para cancelar");
        if (s.nextInt() != 0) {
            int max = 0;
            for (Usuario u: users) {
                if (u.getId() > max) {
                    max = u.getId();
                }
            }
            return new Usuario(max + 1, nombre, username, contraseña, email, false);
        }
        return null;
    }

    public static void Logout() {
        System.out.println("Cerrando Sesión...");
    }

    public static void Search() {
        System.out.println("Ingrese el nombre de un producto qu desee comprar:");
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
}

