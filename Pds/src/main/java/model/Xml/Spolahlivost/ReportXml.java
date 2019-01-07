package model.Xml.Spolahlivost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportXml {

    @SerializedName("report")
    @Expose
    private Report report;

    public ReportXml() {
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

}
