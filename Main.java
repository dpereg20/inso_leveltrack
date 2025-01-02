import dao.UserDAOImpl;
import model.UserBase;
import util.DatabaseConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            if (connection != null) {
                System.out.println("¡Conexión exitosa a la base de datos !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


