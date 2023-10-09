package Marketplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Driver program para Marketplace
 * Tiene el rol de Controlador, e implementa el modelo
 * @version 8/10/2023
 * @author Grupo de Marketplace POO
 */
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
                    
                    for (Producto p : products) {
                        if (p.getId() == choice) {
                            Pages.ProductPage(p);
                            int amount = s.nextInt();
                            while (true) {
                                if (amount <= 0) {
                                    Pages.PPageMessage(amount);
                                    break;
                                }
                                if (amount <= p.getCantidad()) {
                                    users.get(logedUser).añadirAlCarrito(new Producto(p.getId(), p.getNombre(), p.getPrecio(), amount, p.getDescripcion(), p.getIdVendedor()));
                                    for (Vendedor v: vendedors) {
                                        if (v.getId() == p.getIdVendedor()) {
                                            vendedors.get(vendedors.indexOf(v)).getProductos().get(vendedors.get(vendedors.indexOf(v)).getProductos().indexOf(p)).setCantidad(vendedors.get(vendedors.indexOf(v)).getProductos().get(vendedors.get(vendedors.indexOf(v)).getProductos().indexOf(p)).getCantidad() - amount);
                                        }
                                    }
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
                        int logedVendedor = -1;
                        for (Vendedor v : vendedors) {
                            if (v.getId() == users.get(logedUser).getId()) {
                                logedVendedor = v.getId();
                            }
                        }
                        Pages.ConfigStore(vendedors, products, logedVendedor, s);
                     }
                    break;
                    case 3:
                    Pages.Cart(users.get(logedUser).getCarrito());
                    op = s.nextInt();
                    if (op == 1) {
                        boolean buy = Pages.Pagar(s, users.get(logedUser).getCarrito());
                        if (buy) {
                            int max = 0;
                            for (Orden o: orders) {
                                if (o.getId() > max) {
                                    max = o.getId();
                                }
                            }
                            Orden compra = new Orden(max +1, users.get(logedUser).getId(), users.get(logedUser).getCarrito(), true);
                            orders.add(compra);
                            users.get(logedUser).añadirCompra(compra);
                        }
                    } else if (op == 2) {
                        users.get(logedUser).setCarrito(Pages.RemoveItem(users.get(logedUser).getCarrito(), s));

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
