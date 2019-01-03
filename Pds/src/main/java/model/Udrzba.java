package model;

import java.math.BigDecimal;
import java.util.Date;

public class Udrzba {
    private long pocetKM;
    private double cena;
    private Date datumOD;
    private Date datumDO;
    private String popis;

    public Udrzba() {
    }

    public Udrzba(Object[] attributes) {
        int counter = 0;
        if (counter < attributes.length) {
            pocetKM = ((BigDecimal) attributes[counter]).longValue();
            counter++;
        }
        if (counter < attributes.length) {
            cena = ((BigDecimal) attributes[counter]).doubleValue();
            counter++;
        }
        if (counter < attributes.length) {
            datumOD = (Date) attributes[counter];
            counter++;
        }
        if (counter < attributes.length) {
            datumDO = (Date) attributes[counter];
            counter++;
        }
        if (counter < attributes.length) {
            popis = (String) attributes[counter];
            counter++;
        }
    }

    public Udrzba(long pocetKM, double cena, Date datumOD, Date datumDO, String popis) {
        this.pocetKM = pocetKM;
        this.cena = cena;
        this.datumOD = datumOD;
        this.datumDO = datumDO;
        this.popis = popis;
    }

    public long getPocetKM() {
        return pocetKM;
    }

    public void setPocetKM(long pocetKM) {
        this.pocetKM = pocetKM;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
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

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    @Override
    public String toString() {
        return "Udrzba{" +
                "pocetKM=" + pocetKM +
                ", datumOD=" + datumOD +
                ", datumDO=" + datumDO +
                ", popis='" + popis + '\'' +
                '}';
    }
}
