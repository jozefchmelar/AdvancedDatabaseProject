package model;

import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Zakaznik {
    private String id;
    @Nullable
    private String kontakt;

    public Zakaznik(String id) {
        this.id = id;
    }

    public Zakaznik(String id, String kontakt) {
        this.id = id;
        this.kontakt = kontakt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public PreparedStatement insertStatementZakaznik(PreparedStatement ps) throws SQLException {
        ps.setString(1, id);
        ps.setString(2, kontakt);
        return ps;
    }
}


