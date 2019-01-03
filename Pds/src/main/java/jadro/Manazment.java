package jadro;

import db.SQL;
import model.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        vyraz += " ) v ) where rn between " + (indexStranky - 1) * velkostStranky + 1 + " and " + velkostStranky * indexStranky;
        return SQL.runQueryToList(vyraz);
    }

    public void nacitajCenniky(String vyrazWhere) {
        ArrayList<Cennik> zoznamCennikov = SQL.runQueryToList("Select * from cennik " + vyrazWhere);
    }

    //pouzitie:
    //  ArrayList<Vypozicka> test = (ArrayList<Vypozicka>) man.nacitajVypozicky("where id_vozidla < 10000", "od", 10, 1);
    public List<Vypozicka> nacitajVypozicky(String vyrazWhere, String vyrazOrder, int velkostStranky, int indexStranky) {
        String vyraz = "select * from" +
                "( select v.*, rownum as rn " +
                "from ( select * from vypozicka " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by od";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) v ) where rn between " + (indexStranky - 1) * velkostStranky + 1 + " and " + velkostStranky * indexStranky;
        return SQL.runQueryToList(vyraz);
    }


    //pouzitie: ArrayList<Faktura> test = (ArrayList<Faktura>) man.nacitajFaktury("where suma > 400", "zaplatena", 10, 1);
    public List<Faktura> nacitajFaktury(String vyrazWhere, String vyrazOrder, int velkostStranky, int indexStranky) {
        String vyraz = "select * from" +
                "( select f.*, rownum as rn " +
                "from ( select * from faktura " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by zaplatena";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) f ) where rn between " + (indexStranky - 1) * velkostStranky + 1 + " and " + velkostStranky * indexStranky;
        return SQL.runQueryToList(vyraz);
    }

    //TODO, pre prehladavanie vsetkych udrzieb bude asi treba spravil nejaku proceduru v db. Pripadne to nerobit a robit iba prehladavanie udrzieb konkretneho vozidla
    public void nacitajUdrzby(String vyrazWhere) {
        ArrayList<Udrzba> zoznamUdrzieb = SQL.runQueryToList("Select * from udrzba " + vyrazWhere);
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
        vyraz += " ) o ) where rn between " + (indexStranky - 1) * velkostStranky + 1 + " and " + velkostStranky * indexStranky;
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
        vyraz += " ) f ) where rn between " + (indexStranky - 1) * velkostStranky + 1 + " and " + velkostStranky * indexStranky;
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
    public int updateVozidla(String vyrazWhere, String vyrazSet) {
        String vyraz = "update vozidlo set " + vyrazSet + " where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }
}

