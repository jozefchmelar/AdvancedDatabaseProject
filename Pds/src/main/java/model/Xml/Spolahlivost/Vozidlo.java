package model.Xml.Spolahlivost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vozidlo {

    @SerializedName("poradie")
    @Expose
    private Integer poradie;

    public double getZarobok() {
        return zarobok;
    }

    public void setZarobok(double zarobok) {
        this.zarobok = zarobok;
    }

    @SerializedName("zarobok")
    @Expose
    private double zarobok;
    @SerializedName("koeficient_spolahlivosti")
    @Expose
    private Integer koeficientSpolahlivosti;
    @SerializedName("znacka")
    @Expose
    private String znacka;
    @SerializedName("typ")
    @Expose
    private String typ;
    @SerializedName("spz")
    @Expose
    private String spz;
    @SerializedName("dni_prevadzky")
    @Expose
    private Integer dniPrevadzky;
    @SerializedName("dni_oprav")
    @Expose
    private Integer dniOprav;

    public Vozidlo() {
    }

    public Integer getPoradie() {
        return poradie;
    }

    public void setPoradie(Integer poradie) {
        this.poradie = poradie;
    }

    public Integer getKoeficientSpolahlivosti() {
        return koeficientSpolahlivosti;
    }

    public void setKoeficientSpolahlivosti(Integer koeficientSpolahlivosti) {
        this.koeficientSpolahlivosti = koeficientSpolahlivosti;
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

    public String getSpz() {
        return spz;
    }

    public void setSpz(String spz) {
        this.spz = spz;
    }

    public Integer getDniPrevadzky() {
        return dniPrevadzky;
    }

    public void setDniPrevadzky(Integer dniPrevadzky) {
        this.dniPrevadzky = dniPrevadzky;
    }

    public Integer getDniOprav() {
        return dniOprav;
    }

    public void setDniOprav(Integer dniOprav) {
        this.dniOprav = dniOprav;
    }

}
