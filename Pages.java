package Marketplace;

import java.util.Scanner;

public class Pages {

    public static void Home(boolean logedin) {
        if (logedin) {
            System.out.println("Sesión iniciada");
        }
        System.out.println("Bienvenido al Marketplace");
        if (!logedin){
            System.out.println("Seleccione una opción: \n1.Iniciar sesión\n2. Salir");
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

    public static void Logout() {
        System.out.println("Cerrando Sesión...");
    }

    public static void Search() {
        System.out.println("Ingrese el nombre de un producto qu desee comprar:");
    }
}

