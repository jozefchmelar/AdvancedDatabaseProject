package model;

import java.util.Date;

public class Udrzba {
    private int pocetKM;
    private Date datumOD;
    private Date datumDO;
    private String popis;


    public Udrzba(int pocetKM, Date datumOD, Date datumDO, String popis) {
        this.pocetKM = pocetKM;
        this.datumOD = datumOD;
        this.datumDO = datumDO;
        this.popis = popis;
    }

    public int getPocetKM() {
        return pocetKM;
    }

    public void setPocetKM(int pocetKM) {
        this.pocetKM = pocetKM;
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
