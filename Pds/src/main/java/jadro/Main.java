package jadro;

import db.SQL;
import model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) {


//        SQL.run("drop table test");
//
//        SQL.run("create table test(id integer)");
//        for (int i = 0; i < 10; i++) {
//            SQL.run("insert into test values(" + i + ")");
//        }
//
//        SQL.run("select * from test", (row) -> {
//            try {
//                System.out.println(row.getInt("ID"));
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });


//        ArrayList<Integer> skuska = SQL.runQueryToList("Select * from test", PodporovaneTypy.STRING, "ID");
//        ArrayList<Cennik> skuskaCennik = SQL.runQueryToList("Select * from cennik");
//        Cennik cennik = new Cennik(5555, 55555, 55, new java.util.Date(), new java.util.Date());


//        Faktura faktura = new Faktura(55555, new Vypozicka(420), 999, new Date(), new Date());
//        Osoba osoba = new Osoba("5555555555", "9408218666", "5555555555", "test", "test");

        //Vozidlo vozidlo = new Vozidlo(555555555, new Cennik(444), "test", "Opel", "test", null, null);
        //SQL.runInsertQuery(vozidlo);
//        ArrayList<Integer> hlp = SQL.runQueryToList("select * from vozidlo where spz = 'test'",PodporovaneTypy.INTEGER, "id");
//        Vypozicka vypozicka = new Vypozicka(555555555, new Vozidlo(hlp.get(0)), new Zakaznik("6973427800"), new Date(), new Date());
//        SQL.runInsertQuery(vypozicka);

//        ArrayList<Osoba> zoznamZakaznikovOsob = SQL.runQueryToList("Select * from zakaznik join osoba on zakaznik.id = osoba.rod_cislo " + "where zakaznik.id = '2604953300'");
//        ArrayList<Vozidlo> skuska = SQL.runQueryToList("Select * from vozidlo p where p.spz = 'SN092HY'");
        Manazment man = new Manazment();
        //ArrayList<Faktura> test = (ArrayList<Faktura>) man.nacitajFaktury("where suma > 400", "zaplatena", 10, 1);
        //ArrayList<Vozidlo> test = (ArrayList<Vozidlo>) man.nacitajUdrzby("where spz = 'SN092HY' and km > 10000", "km", 2, 1);

//        SQL.run("delete from zakaznik where id = '1234567822'");
//        SQL.run("delete from osoba where rod_cislo = '1234567891'");
//        SQL.run("delete from zakaznik where id = '1234567822'");
//        SQL.run("insert into zakaznik values ('1234567891', 'test@test.com')");
//        SQL.run("insert into osoba values ('1234567891', 'fero', 'testovaci')");
//        Osoba staraOsoba = new Osoba("1234567891", "test@test.com", "1234567891", "fero", "testovaci");
//        Osoba novaOsoba = new Osoba("1234567822", "test2@test2.com", "1234567822", "Marek", "Utestovany");

//        int vysl = man.updateUdrzby(new Vozidlo(9999, new Cennik(9999), "SN092HY", "test", "adsf", null, new Date()),
//                new Udrzba(4155, 33, new GregorianCalendar(1986, Calendar.SEPTEMBER, 29).getTime(), new GregorianCalendar(1986, Calendar.SEPTEMBER, 30).getTime(), "test"),
//                new Udrzba(4152, 33, new GregorianCalendar(1986, Calendar.SEPTEMBER, 29).getTime(), new GregorianCalendar(1986, Calendar.SEPTEMBER, 30).getTime(), ""));

        //SQL.run("insert into zakaznik values ('1234567801', 'fadfadssfgmail.com' )");
        //int vysl = man.updateOsoby(staraOsoba, novaOsoba);

        //int vysl = SQL.runInsertQuery(new Cennik(1234567893, 99, 999, new Date(), new Date()));

        //SQL.run("Insert into zakaznik values ('1234567893', 'lol')");
       //int vysl = man.updateVozidla("spz='test'", "spz='test2'");
//        ArrayList<Firma> test = (ArrayList<Firma>) man.nacitajZakaznikovFirmy("where nazov like 'J%'", "nazov", 10, 1);
        //int result = man.vymazVozidla("spz = 'test'");
        int pom = 10;
    }
}
