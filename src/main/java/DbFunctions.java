import java.sql.Connection;
import java.sql.DriverManager;

public class DbFunctions {
    public Connection connectToDb(String dbName, String user, String password){
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbName, user, password);

            if (connection != null)
                System.out.println("Connection Established");
            else
                System.out.println("Connection Failed");

        } catch (Exception e) {
            System.out.println(e);
        }

        return  connection;
    }
}
