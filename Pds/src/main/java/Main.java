import java.sql.*;

public class Main {

    public static void main(String[] args) {

        SQL.run("drop table test");

        SQL.run("create table test(id integer)");
        for (int i = 0; i < 10; i++) {
            SQL.run("insert into test values("+i+")");
        }

        SQL.run("select * from test", (r) -> {
            try {
                System.out.println(r.getInt("ID"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}