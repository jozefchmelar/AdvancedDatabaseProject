package model.Xml.Spolahlivost;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Report {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("vozidlo")
    @Expose
    private List<Vozidlo> vozidlo = null;

    public Report() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Vozidlo> getVozidlo() {
        return vozidlo;
    }

    public void setVozidlo(List<Vozidlo> vozidlo) {
        this.vozidlo = vozidlo;
    }

}
