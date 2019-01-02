package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Firma extends Zakaznik {
    private String ico;
    private String nazov;

    public Firma(String id, String kontakt, String ico, String nazov) {
        super(id, kontakt);
        this.ico = ico;
        this.nazov = nazov;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public PreparedStatement insertStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, ico);
        ps.setString(2, nazov);
        return ps;
    }

    @Override
    public String toString() {
        return "Firma{" +
                "ico='" + ico + '\'' +
                ", nazov='" + nazov + '\'' +
                ", kontakt=" + super.getKontakt() +
                '}';
    }
}

