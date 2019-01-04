package jadro;

import db.PdsConnection;
import db.SQL;
import model.*;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/*
 TODO vkladanie osoby
 */
public class Manazment {
    private Cennik cennik;
    private Vozidlo vozidlo;
    private Vypozicka vypozicka;
    private Zakaznik zakaznik;
    private Faktura faktura;

    public Manazment() {
    }

    public Cennik getCennik() {
        return cennik;
    }

    public void setCennik(Cennik cennik) {
        this.cennik = cennik;
    }

    public Vozidlo getVozidlo() {
        return vozidlo;
    }

    public void setVozidlo(Vozidlo vozidlo) {
        this.vozidlo = vozidlo;
    }

    public Vypozicka getVypozicka() {
        return vypozicka;
    }

    public void setVypozicka(Vypozicka vypozicka) {
        this.vypozicka = vypozicka;
    }

    public Zakaznik getZakaznik() {
        return zakaznik;
    }

    public void setZakaznik(Zakaznik zakaznik) {
        this.zakaznik = zakaznik;
    }

    public Faktura getFaktura() {
        return faktura;
    }

    public void setFaktura(Faktura faktura) {
        this.faktura = faktura;
    }


    /*
     * For GUI
     */

    /*
     * Inserty
     */
    public void pridajCennik(Cennik cennik) {
        SQL.runInsertQuery(cennik);
    }

    public void pridajVozidlo(Vozidlo vozidlo) {
        SQL.runInsertQuery(vozidlo);
    }

    public void pridajUdrzbu(Udrzba udrzba) {
        SQL.runInsertQuery(udrzba);
    }

    public void pridajVypozicku(Vypozicka vypozicka) {
        SQL.runInsertQuery(vypozicka);
    }

    public void pridajFakturu(Faktura faktura) {
        SQL.runInsertQuery(faktura);
    }

    public void pridajOsobu(Osoba osoba) {
        SQL.runInsertQuery(osoba);
    }

    public void pridajFirmu(Firma firma) {
        SQL.runInsertQuery(firma);
    }

    /*
     * Nacita mnoziny dat
     */
    //Pouzitie: ArrayList<Vozidlo> test = (ArrayList<Vozidlo>) man.nacitajVozidla("where znacka = 'Opel'", "datum_vyradenia", 10, 1);
    public List<Vozidlo> nacitajVozidla(String vyrazWhere, String vyrazOrder, int velkostStranky, int indexStranky) {
        String vyraz = "select * from" +
                "( select v.*, rownum as rn " +
                "from ( select * from vozidlo " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by datum_vyradenia";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) v ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;
        return SQL.runQueryToList(vyraz);
    }

    public List<Cennik> nacitajCenniky(String vyrazWhere, String vyrazOrder, int velkostStranky, int indexStranky) {
        String vyraz = "select * from" +
                "( select c.*, rownum as rn " +
                "from ( select * from cennik " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by platny_od";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) c ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;
        return SQL.runQueryToList(vyraz);
    }

    //pouzitie:
    //  ArrayList<Vypozicka> test = (ArrayList<Vypozicka>) man.nacitajVypozicky("where id_vozidla < 10000", "od", 10, 1);
    public List<Vypozicka> nacitajVypozicky(String vyrazWhere, String vyrazOrder, int velkostStranky, int indexStranky) {
        String vyraz = "select * from" +
                "( select v.*, rownum as rn " +
                "from ( select * from vypozicka join vozidlo on vypozicka.id_vozidla = vozidlo.id join zakaznik on vypozicka.id_zakaznika = zakaznik.id " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by vypozicka.od";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) v ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;
        ResultSet rs = SQL.runToResultSet(vyraz);
        if (rs != null) {
            ArrayList<Vypozicka> zoznamVypoziciek = new ArrayList<>();
            try {
                while (rs.next()) {
                    Vypozicka vypozicka = new Vypozicka(rs.getInt("id"), new Vozidlo(rs.getInt("id_vozidla")),
                            new Zakaznik(rs.getString("id_zakaznika")), rs.getDate("od"), rs.getDate("do"));
                    Vozidlo vozidlo = new Vozidlo(rs.getInt("id_vozidla"), new Cennik(rs.getInt("id_cennika")),
                            rs.getString("spz"), rs.getString("znacka"), rs.getString("typ"), null, rs.getDate("datum_vyradenia")); //TODO fotka
                    Zakaznik zakaznik = new Zakaznik(rs.getString("id_zakaznika"), rs.getString("kontakt"));
                    vypozicka.setVozidlo(vozidlo);
                    vypozicka.setZakaznik(zakaznik);
                    zoznamVypoziciek.add(vypozicka);
                }
                return zoznamVypoziciek;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    //pouzitie: ArrayList<Faktura> test = (ArrayList<Faktura>) man.nacitajFaktury("where suma > 400", "zaplatena", 10, 1);
    public List<Faktura> nacitajFaktury(String vyrazWhere, String vyrazOrder, int velkostStranky, int indexStranky) {
        String vyraz = "select * from" +
                "( select f.*, rownum as rn " +
                "from ( select * from faktura join vypozicka on faktura.id_vypozicky = vypozicka.id join vozidlo on vypozicka.id_vozidla = vozidlo.id join zakaznik on vypozicka.id_zakaznika = zakaznik.id "
                + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by zaplatena";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) f ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;
        ResultSet rs = SQL.runToResultSet(vyraz);
        if (rs != null) {
            ArrayList<Faktura> zoznamFaktur = new ArrayList<>();
            try {
                while (rs.next()) {
                    Faktura faktura = new Faktura(rs.getInt("id"), new Vypozicka(rs.getInt("id_vypozicky")),
                            rs.getDouble("suma"), rs.getDate("vystavena"), rs.getDate("zaplatena"));
                    Vypozicka vypozicka = new Vypozicka(rs.getInt("id_vypozicky"), new Vozidlo(rs.getInt("id_vozidla")),
                            new Zakaznik(rs.getString("id_zakaznika")), rs.getDate("od"), rs.getDate("do"));
                    Vozidlo vozidlo = new Vozidlo(rs.getInt("id_vozidla"), new Cennik(rs.getInt("id_cennika")),
                            rs.getString("spz"), rs.getString("znacka"), rs.getString("typ"), null, rs.getDate("datum_vyradenia")); //TODO fotka
                    Zakaznik zakaznik = new Zakaznik(rs.getString("id_zakaznika"), rs.getString("kontakt"));
                    vypozicka.setVozidlo(vozidlo);
                    vypozicka.setZakaznik(zakaznik);
                    faktura.setVypozicka(vypozicka);
                    zoznamFaktur.add(faktura);
                }
                return zoznamFaktur;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
        //return SQL.runQueryToList(vyraz);
    }

    //TODO, pre prehladavanie vsetkych udrzieb bude asi treba spravil nejaku proceduru v db. Pripadne to nerobit a robit iba prehladavanie udrzieb konkretneho vozidla
    //vrati zoznam vyhovujucich udrzieb v podobe zoznamu listu vozidiel. Kazde z tych vozidiel obsahuje jednu udrzbu, ktora je vysledkom tohto dotazu
    //ak chceme prehladat iba udrzby konkretneho vozidla, tak vo where jednoducho definujeme spz tohto vozidla
    //pouzitie: ArrayList<Vozidlo> test = (ArrayList<Vozidlo>) man.nacitajUdrzby("where spz = 'SN092HY' and km > 10000", "km", 2, 1);
    public List<Vozidlo> nacitajUdrzby(String vyrazWhere, String vyrazOrder, int velkostStranky, int indexStranky) {
        String vyraz = "select * from " +
                "( select o.*, rownum as rn " +
                "from ( select a.*, c.id, c.id_cennika, c.spz, c.znacka, c.typ, c.fotka, c.datum_vyradenia from vozidlo c cross join table(c.udrzba) a " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by od";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) o ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;
        return SQL.runQueryToList(vyraz);
    }

    //pouzitie: ArrayList<Osoba> test = (ArrayList<Osoba>) man.nacitajZakaznikovOsoby("where meno = 'Michaela'", "priezvisko", 10, 1);
    public List<Osoba> nacitajZakaznikovOsoby(String vyrazWhere, String vyrazOrder, int velkostStranky, int indexStranky) {
        String vyraz = "select * from" +
                "( select o.*, rownum as rn " +
                "from ( select * from osoba join zakaznik on osoba.rod_cislo = zakaznik.id " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by priezvisko";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) o ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;
        return SQL.runQueryToList(vyraz);
    }

    //pouzitie:  ArrayList<Firma> test = (ArrayList<Firma>) man.nacitajZakaznikovFirmy("where nazov like 'J%'", "nazov", 10, 1);
    public List<Firma> nacitajZakaznikovFirmy(String vyrazWhere, String vyrazOrder, int velkostStranky, int indexStranky) {
        String vyraz = "select * from" +
                "( select f.*, rownum as rn " +
                "from ( select * from firma join zakaznik on firma.ico = zakaznik.id " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by nazov";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) f ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;
        return SQL.runQueryToList(vyraz);
    }


    /*
     * Vypis vsetkych dat z tabuliek
     */

    @SuppressWarnings("unchecked")
    public void vypisVozidiel(JList list) {
        DefaultListModel newModel = new DefaultListModel();
        SQL.run("select * from vozidlo", (row) -> {
            try {
                Vozidlo vozidlo = new Vozidlo(row.getInt("id"), new Cennik(row.getInt("id_cennika")),
                        row.getString("spz"), row.getString("znacka"), row.getString("typ"), null, row.getDate("datum_vyradenia"));
                newModel.addElement(vozidlo.toString());
                list.setModel(newModel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /*
     * Delete
     * vrati pocet riadkov, v pripade erroru vrati -1
     * deletne iba data bez relacie na potomka
     */
    public int vymazVozidla(String vyrazWhere) {
        String vyraz = "delete from vozidlo where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }


    /*
     * Update
     * vrati pocet riadkov, v pripade erroru vrati -1
     */
    //pouzitie: int vysl = man.updateVozidla("spz='test'", "spz='test2'");
    public int updateVozidla(String vyrazWhere, String vyrazSet) {
        String vyraz = "Update vozidlo set " + vyrazSet + " Where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }

    public int updateCenniky(String vyrazWhere, String vyrazSet) {
        String vyraz = "Update cennik set " + vyrazSet + " Where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }

    public int updateVypozicky(String vyrazWhere, String vyrazSet) {
        String vyraz = "Update vypozicka set " + vyrazSet + " Where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }

    public int updateFaktury(String vyrazWhere, String vyrazSet) {
        String vyraz = "Update faktura set " + vyrazSet + " Where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }

    //toto nie je otestovane, lebo ma stale lockuje db
    //tu to bolo moc komplikovane, tak radsej takto cez objekty
    public int updateOsoby(Osoba staraOsoba, Osoba novaOsoba) {
        String vyraz = "";
        //ci doslo k zmene PK
        if (!staraOsoba.getRodCislo().equals(novaOsoba.getRodCislo())) {
            //insertne sa novy zakaznik
            Zakaznik novyZakaznik = new Zakaznik(novaOsoba.getRodCislo(), novaOsoba.getKontakt());
            if (SQL.runInsertQuery(novyZakaznik) <= 0) {
                return -1;
            }
            //update vsetkych child zaznamov
            int kontrola = SQL.runUpdateQuery("Update vypozicka set id_zakaznika = '" + novaOsoba.getRodCislo() + "' where id_zakaznika = '" + staraOsoba.getRodCislo() + "'");
            kontrola = SQL.runUpdateQuery("Update osoba set rod_cislo = '" + novaOsoba.getRodCislo() + "', meno = '" + novaOsoba.getMeno()
                    + "', priezvisko = '" + novaOsoba.getPrizvisko() + "' where rod_cislo = '" + staraOsoba.getRodCislo() + "'");
            //deletne sa stary zaznam
            return SQL.runUpdateQuery("Delete from zakaznik where id = '" + staraOsoba.getRodCislo() + "'");
        } else {
            int kontrola = SQL.runUpdateQuery("Update zakaznik set kontakt = '" + novaOsoba.getKontakt() + "'");
            return SQL.runUpdateQuery("Update osoba set meno = '" + novaOsoba.getMeno()
                    + "', priezvisko = '" + novaOsoba.getPrizvisko() + "' where rod_cislo = '" + staraOsoba.getRodCislo() + "'");
        }
    }

//    public int updateVozidla(String vyrazWhere, String vyrazSet) {
//        String vyraz = "Update vozidlo set " + vyrazSet + " Where " + vyrazWhere;
//        return SQL.runUpdateQuery(vyraz);
//    }
}

