import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

final class PdsConnection {

    final Connection getConnection() {
        return connection;
    }

    private static Connection connection = null;
    private static PdsConnection instance = null;

    private PdsConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            List<String> lines = Files.readAllLines(Paths.get("connection.txt"));
            connection = DriverManager.getConnection(lines.get(0), lines.get(1), lines.get(2));
        } catch (SQLException e) {
            System.out.println("sql connection err");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("connection.txt not found");
        } catch (ClassNotFoundException e) {
            System.out.println("aren't you missing /lib/ojdbc6.jar driver?");
            e.printStackTrace();
        }
    }

    static PdsConnection getInstance() {
        if (instance == null){
            instance = new PdsConnection();
        }
        return instance;
    }
}
