import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbFunctions {
    private Connection connection;

    private Connection connectToDb(String dbName, String user, String password) {
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

        return connection;
    }

    public DbFunctions(String dbName, String user, String password) {
        this.connection = connectToDb(dbName, user, password);
    }

    public void createTable(String tableName) {
        Statement statement;
        try {
            String query = String.format("""
                    CREATE TABLE %s (
                    band_id SERIAL,
                    name VARCHAR(64),
                    label VARCHAR(64),
                    genre VARCHAR(32),
                    foundation_year SMALLINT,
                    PRIMARY KEY(band_id));
                    """, tableName);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Created");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertRow(String tableName, String name, String label, String genre, int foundationYear) {
        Statement statement;
        try {
            String query = String.format("""
                    INSERT INTO %s(name, label, genre, foundation_year)
                    VALUES('%s', '%s', '%s', '%d');
                    """, tableName, name, label, genre, foundationYear);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void printHeader() {
        System.out.printf("""
                +----+--------------------------------+--------------------------------+----------------+----+
                | id |              name              |              label             |     genre      |year|
                +----+--------------------------------+--------------------------------+----------------+----+
                """);
    }

    public void readData(String tableName) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            String query = String.format("""
                    SELECT *
                    FROM %s
                    """, tableName);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            printHeader();
            while (resultSet.next()) {
                System.out.printf(" %-4s %-32s %-32s %-16s %d%n",
                        resultSet.getString("band_id"),
                        resultSet.getString("name"),
                        resultSet.getString("label"),
                        resultSet.getString("genre"),
                        resultSet.getInt("foundation_year"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateValue(String tableName, String name, String columnName, String newValue) {
        Statement statement;
        try {
            String query = String.format("""
                            UPDATE %s
                            SET %s = '%s'
                            WHERE name = '%s'
                            """,
                    tableName,
                    columnName,
                    newValue,
                    name);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data Updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void searchDataByValue(String tableName, String columnName, String value) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            String query = String.format("""
                            SELECT *
                            FROM %s
                            WHERE %s = '%s'
                            """,
                    tableName,
                    columnName,
                    value);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            printHeader();
            while (resultSet.next()) {
                System.out.printf("%-4s %-32s %-32s %-16s %d\n",
                        resultSet.getString("band_id"),
                        resultSet.getString("name"),
                        resultSet.getString("label"),
                        resultSet.getString("genre"),
                        resultSet.getInt("foundation_year"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteDataByValue(String tableName, String columnName, String value) {
        Statement statement;
        try {
            String query = String.format("""
                            DELETE FROM %s
                            WHERE %s = '%s'
                            """,
                    tableName,
                    columnName,
                    value);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data Deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteTable(String tableName) {
        Statement statement;
        try {
            String query = String.format("DROP TABLE %s", tableName);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
