package model.Xml.Spolahlivost;

public class XmlReport<T> {
    private T Report;
    private String json;

    public T getReport() {
        return Report;
    }

    public String getJson() {
        return json;
    }

    public String getXml() {
        return xml;
    }

    public XmlReport(T report, String json, String xml) {
        Report = report;
        this.json = json;
        this.xml = xml;
    }

    private String xml;
}
