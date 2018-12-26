import java.sql.*;

public class Main {

    public static void main(String[] args) {

        SQL.run("drop table test");

        SQL.run("create table test(id integer)");
        for (int i = 0; i < 10; i++) {
            SQL.run("insert into test values("+i+")");
        }

        SQL.run("select * from test", (row) -> {
            try {
                System.out.println(row.getInt("ID"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}