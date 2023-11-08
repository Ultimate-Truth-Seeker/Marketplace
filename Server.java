package Marketplace;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
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
    private static String nameDB = "marketplace";
    private static String user = "usuario";
    private static String pass = "contrasena";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Carga el controlador para la base de datos
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establece la conexión con la base de datos, obteniendo el url, usuario y contraseña
                String url = String.format("jdbc:mysql://localhost:3306/%s",nameDB);
                connection = DriverManager.getConnection(url, user, pass);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    //TODO: Añadir método para consultar datos al servidor
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
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return usuarios;
    }

    //TODO: Añadir método para añadir datos al servidor
    
    //TODO: Añadir método para modificar datos al servidor
    
    //TODO: Añadir método para eliminar datos al servidor

}
