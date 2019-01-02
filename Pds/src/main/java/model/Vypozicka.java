package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Vypozicka {
    private int id;
    private Vozidlo vozidlo;
    private Zakaznik zakaznik;
    private Date datumOD;
    private Date datumDO;

    public Vypozicka(int id) {
        this.id = id;
    }

    public Vypozicka(int id, Vozidlo vozidlo, Zakaznik zakaznik, Date datumOD, Date datumDO) {
        this.id = id;
        this.vozidlo = vozidlo;
        this.zakaznik = zakaznik;
        this.datumOD = datumOD;
        this.datumDO = datumDO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vozidlo getVozidlo() {
        return vozidlo;
    }

    public void setVozidlo(Vozidlo vozidlo) {
        this.vozidlo = vozidlo;
    }

    public Zakaznik getZakaznik() {
        return zakaznik;
    }

    public void setZakaznik(Zakaznik zakaznik) {
        this.zakaznik = zakaznik;
    }

    public Date getDatumOD() {
        return datumOD;
    }

    public void setDatumOD(Date datumOD) {
        this.datumOD = datumOD;
    }

    public Date getDatumDO() {
        return datumDO;
    }

    public void setDatumDO(Date datumDO) {
        this.datumDO = datumDO;
    }

    public PreparedStatement insertStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
        ps.setInt(2, vozidlo.getId());
        ps.setString(3, zakaznik.getId());
        ps.setDate(4, new java.sql.Date(datumOD.getTime()));
        ps.setDate(5, new java.sql.Date(datumDO.getTime()));
        return ps;
    }

    @Override
    public String toString() {
        return "Vypozicka{" +
                "id=" + id +
                ", vozidlo=" + vozidlo.getId() +
                ", zakaznik='" + zakaznik.getId() + '\'' +
                ", datumOD=" + datumOD +
                ", datumDO=" + datumDO +
                '}';
    }
}
