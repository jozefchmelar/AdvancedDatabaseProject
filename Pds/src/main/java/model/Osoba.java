package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Osoba extends Zakaznik {
    private String rodCislo;
    private String meno;
    private String priezvisko;

    public Osoba(String id, String kontakt, String rodCislo, String meno, String priezvisko) {
        super(id, kontakt);
        this.rodCislo = rodCislo;
        this.meno = meno;
        this.priezvisko = priezvisko;
    }

    public String getRodCislo() {
        return rodCislo;
    }

    public void setRodCislo(String rodCislo) {
        this.rodCislo = rodCislo;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getPrizvisko() {
        return priezvisko;
    }

    public void setPrizvisko(String prizvisko) {
        this.priezvisko = prizvisko;
    }

    public PreparedStatement insertStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, rodCislo);
        ps.setString(2, meno);
        ps.setString(3, priezvisko);
        return ps;
    }

    @Override
    public String toString() {
        return "Osoba{" +
                "rodCislo='" + rodCislo + '\'' +
                ", meno='" + meno + '\'' +
                ", preizvisko='" + priezvisko + '\'' +
                ", kontakt=" + super.getKontakt() +
                '}';
    }
}