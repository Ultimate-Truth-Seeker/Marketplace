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
        List<Vendedor> vendedors = new ArrayList<>();

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
                                    Pages.PPageMessage(amount);
                                    break;
                                }
                                if (amount <= p.getCantidad()) {
                                    users.get(logedUser).añadirAlCarrito(new Producto(p.getId(), p.getNombre(), p.getPrecio(), amount, p.getDescripcion(), p.getIdVendedor()));
                                    products.get(products.indexOf(p)).setCantidad(p.getCantidad() - amount);
                                    Pages.PPageMessage(amount);
                                    break;
                                }
                                amount = s.nextInt();
                            }
                            break;
                        }
                    }
                    
                    break;
                    case 2: 
                    boolean sconfig = Pages.Config(users, logedUser, s);
                    if (sconfig) {
                        if (users.get(logedUser).isEsVendedor() == false) {
                            users.get(logedUser).setEsVendedor(true);
                            vendedors.add(new Vendedor(s.nextInt(), users.get(logedUser)));
                        }
                        
                     }
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
                    List<String> credentials = Pages.Login(s);
                    for (Usuario u : users) {
                        if (u.getNombreUsuario().equals(credentials.get(0)) && u.getContraseña().equals(credentials.get(1)) ) {
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
