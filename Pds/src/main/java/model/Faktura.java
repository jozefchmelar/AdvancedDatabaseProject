package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Faktura {
    private int id;
    private Vypozicka vypozicka;
    private double suma;
    private Date datumVystavenia;
    private Date datumZaplatenia;


    public Faktura(int id, Vypozicka vypozicka, double suma, Date datumVystavenia, Date datumZaplatenia) {
        this.id = id;
        this.vypozicka = vypozicka;
        this.suma = suma;
        this.datumVystavenia = datumVystavenia;
        this.datumZaplatenia = datumZaplatenia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vypozicka getVypozicka() {
        return vypozicka;
    }

    public void setVypozicka(Vypozicka vypozicka) {
        this.vypozicka = vypozicka;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public Date getDatumVystavenia() {
        return datumVystavenia;
    }

    public void setDatumVystavenia(Date datumVystavenia) {
        this.datumVystavenia = datumVystavenia;
    }

    public Date getDatumZaplatenia() {
        return datumZaplatenia;
    }

    public void setDatumZaplatenia(Date datumZaplatenia) {
        this.datumZaplatenia = datumZaplatenia;
    }

    public PreparedStatement insertStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
        ps.setInt(2, vypozicka.getId());
        ps.setDouble(3, suma);
        ps.setDate(4, new java.sql.Date(datumVystavenia.getTime()));
        ps.setDate(5, new java.sql.Date(datumZaplatenia.getTime()));
        return ps;
    }

    @Override
    public String toString() {
        return "Faktura{" +
                "id=" + id +
                ", vypozicka=" + vypozicka +
                ", suma=" + suma +
                ", datumVystavenia=" + datumVystavenia +
                ", datumZaplatenia=" + datumZaplatenia +
                '}';
    }
}
