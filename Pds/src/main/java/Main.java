import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:oracle:thin:@obelix.fri.uniza.sk:1521:orcl", "chmelar2", "");
            PreparedStatement ps = connection.prepareStatement("select * from student");
            ResultSet p = ps.executeQuery();
            while(p.next()){
                int os_cislo = p.getInt("OS_CISLO");
                System.out.println(os_cislo);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}