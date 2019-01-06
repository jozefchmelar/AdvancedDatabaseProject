package model;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Vozidlo {
    private int id;
    private Cennik cennik;
    private String spz;
    private String znacka;
    private String typ;
    private String fotkaCesta;
    private ArrayList<Udrzba> udrzby;
    private Date datum_vyradenia;



    private Float vynosy;
    private Float naklady;

    public Vozidlo(int id) {
        this.id = id;
    }

    public Vozidlo(int id, Cennik cennik, String spz, String znacka, String typ, String fotkaCesta, Date datum_vyradenia) {
        this.id = id;
        this.cennik = cennik;
        this.spz = spz;
        this.znacka = znacka;
        this.typ = typ;
        this.fotkaCesta = fotkaCesta;
        this.udrzby = new ArrayList<>();
        this.datum_vyradenia = datum_vyradenia;
    }

    public Vozidlo(int id, Cennik cennik, String spz, String znacka, String typ, Date datum_vyradenia) {
        this.id = id;
        this.cennik = cennik;
        this.spz = spz;
        this.znacka = znacka;
        this.typ = typ;
        this.fotkaCesta = "";
        this.udrzby = new ArrayList<>();
        this.datum_vyradenia = datum_vyradenia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cennik getCennik() {
        return cennik;
    }

    public void setCennik(Cennik cennik) {
        this.cennik = cennik;
    }

    public String getSpz() {
        return spz;
    }

    public void setSpz(String spz) {
        this.spz = spz;
    }

    public String getZnacka() {
        return znacka;
    }

    public void setZnacka(String znacka) {
        this.znacka = znacka;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getFotkaCesta() {
        return fotkaCesta;
    }


    public Float getVynosy() {
        return vynosy;
    }

    public void setVynosy(Float vynosy) {
        this.vynosy = vynosy;
    }

    public Float getNaklady() {
        return naklady;
    }

    public void setNaklady(Float naklady) {
        this.naklady = naklady;
    }

    public void setFotkaCesta(String fotkaCesta) {
        this.fotkaCesta = fotkaCesta;
    }

    public ArrayList<Udrzba> getUdrzby() {
        return udrzby;
    }

    public void setUdrzby(ArrayList<Udrzba> udrzby) {
        this.udrzby = udrzby;
    }

    public Date getDatum_vyradenia() {
        return datum_vyradenia;
    }

    public void setDatum_vyradenia(Date datum_vyradenia) {
        this.datum_vyradenia = datum_vyradenia;
    }

    public PreparedStatement insertStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
        ps.setInt(2, cennik.getId());
        ps.setString(3, spz);
        ps.setString(4, znacka);
        ps.setString(5, typ);
//        ps.setBlob(6, null); //TODO
//        ps.setString(7, "null"); //TODO
        return ps;
    }

    @Override
    public String toString() {
        return "Vozidlo{" +
                "id=" + id +
                ", cennik=" + cennik.getId() +
                ", spz='" + spz + '\'' +
                ", znacka='" + znacka + '\'' +
                ", typ='" + typ + '\'' +
                ", fotkaCesta='" + fotkaCesta + '\'' +
                ", udrzby=" + udrzby +
                ", datum_vyradenia=" + datum_vyradenia +
                '}';
    }
}
