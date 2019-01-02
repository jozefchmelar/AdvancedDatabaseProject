package jadro;

import db.SQL;
import model.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

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
     * @Jozo tu si to poprisposobuj podla potrieb :D
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
    public void nacitajVozidla(String vyrazWhere) {
        ArrayList<Vozidlo> zoznamVozidiel = SQL.runQueryToList("Select * from vozidlo " + vyrazWhere);
    }

    public void nacitajCenniky(String vyrazWhere) {
        ArrayList<Cennik> zoznamCennikov = SQL.runQueryToList("Select * from cennik " + vyrazWhere);
    }

    public void nacitajVypozicky(String vyrazWhere) {
        ArrayList<Cennik> zoznamVypoziciek = SQL.runQueryToList("Select * from vypozicka " + vyrazWhere);
    }

    public void nacitajFaktury(String vyrazWhere) {
        ArrayList<Faktura> zoznamFaktur = SQL.runQueryToList("Select * from faktura " + vyrazWhere);
    }

    //TODO
    public void nacitajUdrzby(String vyrazWhere) {
        ArrayList<Udrzba> zoznamUdrzieb = SQL.runQueryToList("Select * from udrzba " + vyrazWhere);
    }

    public void nacitajZakaznikovOsoby(String vyrazWhere) {
        ArrayList<Osoba> zoznamZakaznikovOsob = SQL.runQueryToList("Select * from zakaznik join osoba on zakaznik.id = osoba.rod_cislo " + vyrazWhere);
    }

    public void nacitajZakaznikovFirmy(String vyrazWhere) {
        ArrayList<Firma> zoznamZakaznikovFiriem = SQL.runQueryToList("Select * from zakaznik join firma on zakaznik.id = firma.ico " + vyrazWhere);
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
}

