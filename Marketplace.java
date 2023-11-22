//package Marketplace;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Driver program para Marketplace
 * Tiene el rol de Controlador, y ajusta el modelo de fondo con las clases de datos
 * @version 8/10/2023
 * @author Grupo de Marketplace POO
 */
public class Marketplace {
    public static void main(String[] args) {
        boolean logedin = false;
        int op;
        Boolean Run = true;
        int logedUser = -1;
        List<Usuario> users = new ArrayList<>();
        List<Producto> products = new ArrayList<>();
        List<Orden> orders = new ArrayList<>();
        List<Vendedor> vendedors = new ArrayList<>();
        Connection connection = null;
        try {
            connection = Server.getConnection();
        } catch (Exception e) {
            System.out.println("Error al intentar conectar");
        }
        try {
            Scanner s;
            try {
            users = Server.getUsuarios(null);
            } catch (Exception e){
                System.out.println("Error al intentar obtener usuarios");
            }
            while (Run) {
                s = new Scanner(System.in);
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
                                            boolean extra = false;
                                            for (Producto cp : users.get(logedUser).getCarrito()) {
                                                if (cp.getId() == p.getId()) {
                                                    users.get(logedUser).getCarrito().get(users.get(logedUser).getCarrito().indexOf(cp)).setCantidad(users.get(logedUser).getCarrito().get(users.get(logedUser).getCarrito().indexOf(cp)).getCantidad() + amount);
                                                    extra = true;
                                                }
                                            }
                                            if (!extra) {
                                                users.get(logedUser).añadirAlCarrito(new Producto(p.getId(), p.getNombre(), p.getPrecio(), amount, p.getDescripcion(), p.getIdVendedor()));
                                            }
                                            for (Vendedor v : vendedors) {
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
                                        logedVendedor = vendedors.indexOf(v);
                                    }
                                }
                                Pages.ConfigStore(vendedors, products, logedVendedor, s);
                                
                            }
                            try {
                            Server.modificarUsuario(users.get(logedUser));
                            } catch (Exception e){
                }
                            break;
                        case 3:
                            Pages.Cart(users.get(logedUser).getCarrito());
                            op = s.nextInt();
                            if (op == 1) {
                                boolean buy = Pages.Pagar(s, users.get(logedUser).getCarrito());
                                if (buy) {
                                    int max = 0;
                                    for (Orden o : orders) {
                                        if (o.getId() > max) {
                                            max = o.getId();
                                        }
                                    }
                                    Orden compra = new Orden(max + 1, users.get(logedUser).getId(), users.get(logedUser).getCarrito(), true);
                                    orders.add(compra);
                                    try {
                                    Server.agregarOrden(op, logedUser, null, logedin);
                                    } catch (Exception e){
            }
                                    users.get(logedUser).añadirCompra(compra);

                                    Pages.Contact(users.get(logedUser).getCarrito(), vendedors);
                                }
                            } else if (op == 2) {

                                List<Producto> nCarro = Pages.RemoveItem(users.get(logedUser).getCarrito(), s);
                                users.get(logedUser).setCarrito(nCarro);
                            }
                            break;
                        case 4:
                            Pages.Tutorial();
                            break;
                        case 5:
                            Pages.Logout();
                            logedin = false;
                            logedUser = -1;
                            break;
                        case 6:
                            Run = false;
                            s.close();
                            break;
                        default:
                            break;
                    }
                } else {
                    if (op == 1) {
                        users = Pages.Login(s);
                        logedin = users.size() > 0 ;
                        logedUser = 0;
                        Pages.Status(logedin);
                    } else if (op == 2) {
                        Usuario newuser = Pages.Register(s, users);
                        if (newuser != null) {
                            users.add(newuser);
                        }
                    } else if (op == 3) {
                        Pages.Tutorial();
                        
                    } else if (op == 4) {
                        Pages.Search();
                            s.nextLine();
                            String Query = s.nextLine();
                            Pages.SearchResults(Query, products);
                            int choice = s.nextInt();
                            if (choice != 0) {
                                for (Producto p : products) {
                                if (p.getId() == choice) {
                                    Pages.ProductPage(p);
                                    break;
                                    }
                                }
                            }    
                    } else if (op == 5) {
                        Run = false;
                        s.close();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
