package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Cennik {
    private int id;
    private double cena_den;
    private double poplatok;
    private Date platny_od;
    private Date platny_do;

    public Cennik() {
    }

    public Cennik(int id) {
        this.id = id;
    }

    public Cennik(int id, double cena_den, double poplatok, Date platny_od, Date platny_do) {
        this.id = id;
        this.cena_den = cena_den;
        this.poplatok = poplatok;
        this.platny_od = platny_od;
        this.platny_do = platny_do;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCena_den() {
        return cena_den;
    }

    public void setCena_den(double cena_den) {
        this.cena_den = cena_den;
    }

    public double getPoplatok() {
        return poplatok;
    }

    public void setPoplatok(double poplatok) {
        this.poplatok = poplatok;
    }

    public Date getPlatny_od() {
        return platny_od;
    }

    public void setPlatny_od(Date platny_od) {
        this.platny_od = platny_od;
    }

    public Date getPlatny_do() {
        return platny_do;
    }

    public void setPlatny_do(Date platny_do) {
        this.platny_do = platny_do;
    }

    public PreparedStatement insertStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
        ps.setDouble(2, cena_den);
        ps.setDouble(3, poplatok);
        ps.setDate(4, new java.sql.Date(platny_od.getTime()));
        ps.setDate(5, new java.sql.Date(platny_do.getTime()));
        return ps;
    }

    @Override
    public String toString() {
        return "Cennik{" +
                "id=" + id +
                ", cena_den=" + cena_den +
                ", poplatok=" + poplatok +
                ", platny_od=" + platny_od +
                ", platny_do=" + platny_do +
                '}';
    }
}

