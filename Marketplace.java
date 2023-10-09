package Marketplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Marketplace {
    public static void main(String[] args) {
        boolean logedin = false; int op; Boolean Run = true;
        int logedUser = -1;
        List<Usuario> users = new ArrayList<>();
        List<Producto> products = new ArrayList<>();
        List<Orden> orders = new ArrayList<>();

        Scanner s = new Scanner(System.in);
        while (Run) {
            Pages.Home(logedin);
            op = s.nextInt();
            if (logedin) {
                switch (op) {
                    case 1:
                    Pages.Search();
                    s.nextLine();
                    String Query = s.nextLine();
                    Pages.SearchResults(Query, products);
                    int choice = s.nextInt();
                    if (choice == 0) {
                        break;
                    }
                    boolean found = false;
                    for (Producto p : products) {
                        if (p.getId() == choice) {
                            found = true;
                            Pages.ProductPage(p);
                            int amount = s.nextInt();
                            while (true) {
                                if (amount <= 0) {
                                    break;
                                }
                                if (amount <= p.getCantidad()) {
                                    
                                    
                                    users.get(logedUser).añadirAlCarrito(p);
                                    break;
                                }
                                amount = s.nextInt();
                            }
                            break;
                        }
                    }
                    
                    break;
                    case 2: 

                    break;
                    case 4:
                    Pages.Logout();
                    logedin = false;
                    break;
                    case 5:
                    Run = false;
                    break;
                    default:
                    break;
                }
            } else {
                if (op == 1) {
                    String[] credentials = Pages.Login(s);
                    for (Usuario u : users) {
                        if (u.getNombreUsuario() == credentials[1] && u.getContraseña() == credentials[2] ) {
                            logedUser = users.indexOf(u);
                            logedin = true;
                        }
                    }
                    Pages.Status(logedin);
                } else if (op == 2) {
                    Usuario newuser = Pages.Register(s, users);
                    if (newuser != null) {
                        users.add(newuser);
                    }
                } else if (op == 3) {
                    Run = false;
                    break;
                }
                
            }
        }
        s.close();
    }
}
