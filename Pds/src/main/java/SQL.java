import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

final class SQL {

    static  void run(String query, Consumer<ResultSet> onResult) {
        try {
            PreparedStatement ps = PdsConnection.getInstance().getConnection().prepareStatement(query);
            ResultSet p = ps.executeQuery();
            if (onResult != null) {
                while (p.next()) {
                    onResult.accept(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void run(String query) {
        run(query, null);
    }
}
