package Marketplace;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para métodos que trabajan conexiones con el servidor
 * @author Grupo de Marketplace
 * @version 7-11-2023
 */
public class Server {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Carga el controlador para la base de datos
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establece la conexión con la base de datos, obteniendo el url, usuario y contraseña
                connection = DriverManager.getConnection("jdbc:mysql://tu-base-de-datos-remota:3306/tu-base-de-datos", "usuario", "contrasena");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    //TODO: Añadir método para consultar datos al servidor

    //TODO: Añadir método para añadir datos al servidor
    
    //TODO: Añadir método para modificar datos al servidor
    
    //TODO: Añadir método para eliminar datos al servidor

}
