//package Marketplace;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase para métodos que trabajan conexiones con el servidor
 * @author Grupo de Marketplace
 * @version 7-11-2023
 */
public class Server {
    private static Connection connection;
    private static String nameDB = "proyectopoo";
    private static String user = "poo";
    private static String pass = "secret";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Carga el controlador para la base de datos
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establece la conexión con la base de datos, obteniendo el url, usuario y contraseña
                String url = String.format("jdbc:mysql://5.161.118.98:33002/%s",nameDB);
                connection = DriverManager.getConnection(url, user, pass);
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println(e.getMessage());
            }
            catch(Exception e){
                System.err.println("Error desconocido");
                System.err.println(e.getMessage());
            }
        }
        return connection;
    }

    //Añadir método para consultar datos al servidor
    public static ArrayList<Usuario> getUsuarios(String query){
        Statement st = null;
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                Usuario aux = new Usuario();
                aux.setId(rs.getInt("id"));
                aux.setNombre(rs.getString("nombre"));
                aux.setNombreUsuario(rs.getString("nombreUsuario"));
                aux.setContraseña(rs.getString("contrasena"));
                aux.setEmail(rs.getString("email"));
                aux.setEsVendedor(Boolean.parseBoolean(rs.getString("esVendedor")));
               usuarios.add(aux);
            }
        } catch (SQLException e) {
            System.err.println("Hay un error ");
            System.err.println(e.getMessage());
        }
        catch(Exception e){
                System.err.println("Error desconocido");
                System.err.println(e.getMessage());
            }
        return usuarios;
    }

    // Añadir método para añadir datos al servidor

    public static boolean agregarUsuario(Usuario usuario){
        String query = "INSERT INTO usuarios (nombre, nombreUsuario, contrasena, email, esVendedor) values (?,?,?,?,?)" ;
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, usuario.getNombre());
            preparedStmt.setString(2, usuario.getNombreUsuario());
            preparedStmt.setString(3, usuario.getContraseña());
            preparedStmt.setString(4, usuario.getEmail());
            preparedStmt.setBoolean(5, usuario.isEsVendedor());

            return preparedStmt.execute();
        } catch (SQLException e) {
            System.err.println("Hay un error");
            System.err.println(e.getMessage());
        }
        catch(Exception e){
                System.err.println("Error desconocido");
                System.err.println(e.getMessage());
            }
        return false;
    }
    
    // Añadir método para modificar datos al servidor
    
    public static int modificarUsuario(Usuario usuario){
        String set = "";
        String query = "UPDATE usuario SET ";
        int modificados = 0;
        try {
            set = String.format("nombre='%s', nombreUsuario='%s', contrasena='%s', email='%s', esVendedor=%s WHERE id=%d",
                    usuario.getNombre(),
                    usuario.getNombreUsuario(),
                    usuario.getContraseña(),
                    usuario.getEmail(),
                    usuario.isEsVendedor(),
                    usuario.getId());
            query = String.format("%s %s",query,set);
            Statement st = connection.createStatement();
            modificados = st.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Hay un error");
            System.err.println(e.getMessage());
        }
        catch(Exception e){
            System.err.println("Error desconocido");
            System.err.println(e.getMessage());
        }
        return modificados;
    }
    
    //Añadir método para eliminar datos al servidor

    public static int eliminarUsuario(int id){
        String query = "DELETE FROM usuario WHERE id=%d";
        int eliminados = 0;
        try {
            query = String.format(query, id);
            Statement st = connection.createStatement();
            eliminados = st.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Hay un error");
            System.err.println(e.getMessage());
        }
        catch(Exception e){
            System.err.println("Error desconocido");
            System.err.println(e.getMessage());
        }
        return eliminados;
    }

    //Añadir método para agregar datos a la tabla Orden

    public static int agregarOrden(int id, int idUsuario, String productos, boolean reserva) {
        String query = "INSERT INTO Orden (Id, IdUsuario, Productos, Reserva) VALUES ('%s','%s','%s','%s')";
        int insertados = 0;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setInt(2, idUsuario);
            pstmt.setString(3, productos);
            pstmt.setBoolean(4, reserva);

            insertados = pstmt.executeUpdate();

            if (insertados > 0) {
                System.out.println("Se ha insertado la orden con éxito.");
            } else {
                System.err.println("Error al insertar la orden.");
            }

            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Hay un error");
            System.err.println(e.getMessage());
        }
        catch(Exception e){
            System.err.println("Error desconocido");
                System.err.println(e.getMessage());
            }

        return insertados;
    }

    public static int modificarOrden(Orden orden){
        String set = "";
        String query = "UPDATE orden SET ";
        int modificados = 0;
        try {
            set = String.format("idUsuario='%s', isReserva='%s' WHERE id=%d",
                    orden.getIdUsuario(),
                    orden.isReserva(),
                    orden.getId());
            query = String.format("%s %s",query,set);
            Statement st = connection.createStatement();
            modificados = st.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Hay un error");
            System.err.println(e.getMessage());
        }
        catch(Exception e){
            System.err.println("Error desconocido");
            System.err.println(e.getMessage());
        }
        return modificados;
    }
    public static int eliminarOrden(int idOrden) {
        String query = "DELETE FROM Orden WHERE Id = ?";
        int eliminados = 0;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, idOrden);

            eliminados = pstmt.executeUpdate();

            if (eliminados > 0) {
                System.out.println("Se ha eliminado la orden con éxito.");
            } else {
                System.err.println("No se encontró ninguna orden con ese ID.");
            }

            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Hay un error");
            System.err.println(e.getMessage());
        }
        catch(Exception e){
            System.err.println("Error desconocido");
            System.err.println(e.getMessage());
        }
        return eliminados;
    }


    public static int agregarProducto(Producto producto) {
        String query = "INSERT INTO Producto (Id, Nombre, Precio, Cantidad, Descripcion, IdVendedor) VALUES ('%s','%s','%s','%s','%s','%s')";
        int insertados = 0;
    
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, producto.getId());
            pstmt.setString(2, producto.getNombre());
            pstmt.setFloat(3, producto.getPrecio());
            pstmt.setInt(4, producto.getCantidad());
            pstmt.setString(5, producto.getDescripcion());
            pstmt.setInt(6, producto.getIdVendedor());
    
            insertados = pstmt.executeUpdate();
    
            if (insertados > 0) {
                System.out.println("Se ha agregado el producto con éxito.");
            } else {
                System.err.println("Error al agregar el producto.");
            }
    
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Hay un error");
            System.err.println(e.getMessage());
        }
        catch(Exception e){
            System.err.println("Error desconocido");
            System.err.println(e.getMessage());
        }
    
        return insertados;
    }
    public static int modificarProducto(Producto producto){
        String set = "";
        String query = "UPDATE producto SET ";
        int modificados = 0;
        try {
            set = String.format("nombre='%s', precio='%s', cantidad='%s', descripcion='%s', idVendedor='%s' WHERE id=%d",
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getCantidad(),
                    producto.getDescripcion(),
                    producto.getIdVendedor(),
                    producto.getId());
            query = String.format("%s %s",query,set);
            Statement st = connection.createStatement();
            modificados = st.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Hay un error");
            System.err.println(e.getMessage());
        }
        catch(Exception e){
            System.err.println("Error desconocido");
            System.err.println(e.getMessage());
        }
        return modificados;
    }
    
    public static int eliminarProducto(int idProducto) {
        String query = "DELETE FROM Producto WHERE Id = ?";
        int eliminados = 0;
    
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, idProducto);
    
            eliminados = pstmt.executeUpdate();
    
            if (eliminados > 0) {
                System.out.println("Se ha eliminado el producto con éxito.");
            } else {
                System.err.println("No se encontró ningún producto con ese ID.");
            }
    
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Hay un error");
            System.err.println(e.getMessage());
        }
        catch(Exception e){
            System.err.println("Error desconocido");
            System.err.println(e.getMessage());
        }
    
        return eliminados;
    }

    }


