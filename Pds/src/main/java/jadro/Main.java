package jadro;

import db.SQL;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        SQL.run("drop table test");

        SQL.run("create table test(id integer)");
        for (int i = 0; i < 10; i++) {
            SQL.run("insert into test values(" + i + ")");
        }

        SQL.run("select * from test", (row) -> {
            try {
                System.out.println(row.getInt("ID"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


//        ArrayList<Integer> skuska = SQL.runQueryToList("Select * from test", PodporovaneTypy.STRING, "ID");
//        ArrayList<Cennik> skuskaCennik = SQL.runQueryToList("Select * from cennik");
//        Cennik cennik = new Cennik(5555, 55555, 55, new java.util.Date(), new java.util.Date());
//        Vozidlo vozidlo = new Vozidlo(555555555, new Cennik(444), "test", "Opel", "test", null, null);
//        Vypozicka vypozicka = new Vypozicka(555555555, new Vozidlo(9834), new Zakaznik(6973427800L), new Date(), new Date());
//        Faktura faktura = new Faktura(55555, new Vypozicka(420), 999, new Date(), new Date());
//        Osoba osoba = new Osoba("5555555555", "9408218666", "5555555555", "test", "test");
//        SQL.runInsertQuery(osoba);
//        ArrayList<Osoba> zoznamZakaznikovOsob = SQL.runQueryToList("Select * from zakaznik join osoba on zakaznik.id = osoba.rod_cislo " + "where zakaznik.id = '2604953300'");
    }
}
