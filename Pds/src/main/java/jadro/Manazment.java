package jadro;

import com.google.gson.Gson;
import db.PdsConnection;
import db.SQL;
import kotlin.Pair;
import model.*;
import model.Xml.Spolahlivost.ReportXml;
import model.Xml.Spolahlivost.XmlReport;
import oracle.xdb.XMLType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


/*
 TODO vkladanie osoby
 */
public class Manazment {
    private Cennik cennik;
    private Vozidlo vozidlo;
    private Vypozicka vypozicka;
    private Zakaznik zakaznik;
    private Faktura faktura;
    private Gson gson = new Gson();

    public Manazment() {
    }

    public Cennik getCennik() {
        return cennik;
    }

    public void setCennik(Cennik cennik) {
        this.cennik = cennik;
    }

    public Vozidlo getVozidlo() {
        return vozidlo;
    }

    public void setVozidlo(Vozidlo vozidlo) {
        this.vozidlo = vozidlo;
    }

    public Vypozicka getVypozicka() {
        return vypozicka;
    }

    public void setVypozicka(Vypozicka vypozicka) {
        this.vypozicka = vypozicka;
    }

    public Zakaznik getZakaznik() {
        return zakaznik;
    }

    public void setZakaznik(Zakaznik zakaznik) {
        this.zakaznik = zakaznik;
    }

    public Faktura getFaktura() {
        return faktura;
    }

    public void setFaktura(Faktura faktura) {
        this.faktura = faktura;
    }

    private Zakaznik zistiTypZakaznika(String idZakaznika) {
        Zakaznik zakaznik = null;
        try {
            ResultSet rs = SQL.runToResultSet("Select * from zakaznik join firma on zakaznik.id = firma.ico where zakaznik.id = '" + idZakaznika + "'");
            if (rs.next()) {
                zakaznik = new Firma(idZakaznika, rs.getString("kontakt"), idZakaznika, rs.getString("nazov"));
            } else {
                rs = SQL.runToResultSet("Select * from zakaznik join osoba on zakaznik.id = osoba.rod_cislo where zakaznik.id = '" + idZakaznika + "'");
                if (rs.next()) {
                    zakaznik = new Osoba(idZakaznika, rs.getString("kontakt"), idZakaznika, rs.getString("meno"), rs.getString("priezvisko"));
                } else {
                    zakaznik = new Zakaznik(idZakaznika);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zakaznik;
    }


    /*
     * For GUI
     */

    /*
     * Inserty
     * vrati pocet vlozenych riadkov, alebo -1 v pripade erroru
     */
    public int pridajCennik(Cennik cennik) {
        return SQL.runInsertQuery(cennik);
    }

    public int pridajVozidlo(Vozidlo vozidlo) {
        return SQL.runInsertQuery(vozidlo);
    }

    public int pridajVypozicku(Vypozicka vypozicka) {
        return SQL.runInsertQuery(vypozicka);
    }

    public int pridajFakturu(Faktura faktura) {
        return SQL.runInsertQuery(faktura);
    }

    public int pridajOsobu(Osoba osoba) {
        return SQL.runInsertQuery(osoba);
    }

    public int pridajFirmu(Firma firma) {
        return SQL.runInsertQuery(firma);
    }

    public PoctyVozidiel poctyVozidiel() {
        String vsetky = "count (*),";
        String funkcne = "sum(case when datum_vyradenia is not null then 0 else 1 end)";
        String query = "select "
                + vsetky
                + funkcne +
                " from vozidlo";
        final PoctyVozidiel[] poctyVozidiel = {null};
        SQL.run(query, (row) -> {
            try {
                poctyVozidiel[0] = new PoctyVozidiel(row.getInt(1), row.getInt(2));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return poctyVozidiel[0];

    }

    public XmlReport<ReportXml> spolahlivostVozidiel(double percenta) {
        AtomicReference<XmlReport<ReportXml>> toReturn = new AtomicReference<>();
        SQL.run("select xmlReport_vozidla_spolahlivost(" + percenta + ") from dual", (resultSet) -> {
            org.w3c.dom.Document doc = null;
            XMLType xml = null;
            try {
                xml = (XMLType) resultSet.getObject(1);
                doc = xml.getDocument();
                String stringFromDocument = Xml.getStringFromDocument(doc);
                JSONObject jsonReport = XML.toJSONObject(stringFromDocument);
                ReportXml report = gson.fromJson(jsonReport.toString(), ReportXml.class);
                toReturn.set(new XmlReport<>(report, jsonReport.toString(), stringFromDocument));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        return toReturn.get();
    }

    public XmlReport<ReportXml> vynosyVozidiel(double percenta) {
        AtomicReference<XmlReport<ReportXml>> toReturn = new AtomicReference<>();
        SQL.run("select xmlReport_vozidla_vynosy(" + percenta + ") from dual", (resultSet) -> {
            org.w3c.dom.Document doc = null;
            XMLType xml = null;

            try {
                xml = (XMLType) resultSet.getObject(1);
                doc = xml.getDocument();
                String stringFromDocument = Xml.getStringFromDocument(doc);
                JSONObject jsonReport = XML.toJSONObject(stringFromDocument);
                ReportXml report = gson.fromJson(jsonReport.toString(), ReportXml.class);
                toReturn.set(new XmlReport<>(report, jsonReport.toString(), stringFromDocument));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        return toReturn.get();
    }


    public int pridajUdrzbu(Vozidlo vozidlo, Udrzba udrzba) {
        String vyraz = "";
        if (vozidlo.getUdrzby().isEmpty()) {
            vyraz += "update vozidlo set udrzba = udrzby(t_udrzba( " + udrzba.getPocetKM() + ", " + udrzba.getCena() + ", to_date('"
                    + new java.sql.Date(udrzba.getDatumOD().getTime()).toString() + "', 'yyyy-mm-dd'), to_date('"
                    + new java.sql.Date(udrzba.getDatumDO().getTime()).toString() + "', 'yyyy-mm-dd'), '" + udrzba.getPopis() + "')) where spz = '" + vozidlo.getSpz() + "'";
        } else {
            vyraz += "insert into table (select v.udrzba from vozidlo v where v.spz = '" + vozidlo.getSpz() + "') u values (t_udrzba( " + udrzba.getPocetKM() + ", " + udrzba.getCena() + ", to_date('"
                    + new java.sql.Date(udrzba.getDatumOD().getTime()).toString() + "', 'yyyy-mm-dd'), to_date('"
                    + new java.sql.Date(udrzba.getDatumDO().getTime()).toString() + "', 'yyyy-mm-dd'), '" + udrzba.getPopis() + "'))";
        }
        return SQL.runUpdateQuery(vyraz);
    }

    /*
     * Nacita mnoziny dat
     */
    //Pouzitie: ArrayList<Vozidlo> test = (ArrayList<Vozidlo>) man.nacitajVozidla("where znacka = 'Opel'", "datum_vyradenia", 10, 1);
    public List<Vozidlo> nacitajVozidla(String vyrazWhere, String vyrazOrder, int velkostStranky, int indexStranky) {
        String vyraz = "select * from" +
                "( select v.*, naklady_vozidla(v.id) as naklady ,vynosy_vozidla(v.id) as vynosy ,y.cena_den, rownum as rn " +
                "from ( select id,id_cennika,spz,znacka,typ,fotka,udrzba,datum_vyradenia, vytazenost_vozidla(id) as vytazenost ,poruchovost_vozidla(id) as poruchovost  from vozidlo where poruchovost_vozidla(id)  >= 0  " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by datum_vyradenia";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) v JOIN cennik y on v.id_cennika = y.id ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;
        return SQL.runQueryToList(vyraz);
    }


    public List<Cennik> nacitajCenniky(String vyrazWhere, String vyrazOrder, int velkostStranky, int indexStranky) {
        String vyraz = "select * from" +
                "( select c.*, rownum as rn " +
                "from ( select * from cennik " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by platny_od";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) c ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;
        return SQL.runQueryToList(vyraz);
    }

    //pouzitie:
    //  ArrayList<Vypozicka> test = (ArrayList<Vypozicka>) man.nacitajVypozicky("where id_vozidla < 10000", "od", 10, 1);
    public List<Vypozicka> nacitajVypozicky(String vyrazWhere, String vyrazOrder, int velkostStranky, int indexStranky) {
        String vyraz = "select * from" +
                "( select v.*, rownum as rn " +
                "from ( select * from vypozicka join vozidlo on vypozicka.id_vozidla = vozidlo.id join zakaznik on vypozicka.id_zakaznika = zakaznik.id " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by vypozicka.od";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) v ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;
        ResultSet rs = SQL.runToResultSet(vyraz);
        if (rs != null) {
            ArrayList<Vypozicka> zoznamVypoziciek = new ArrayList<>();
            try {
                while (rs.next()) {
                    Vypozicka vypozicka = new Vypozicka(rs.getInt("id"), new Vozidlo(rs.getInt("id_vozidla")),
                            new Zakaznik(rs.getString("id_zakaznika")), rs.getDate("od"), rs.getDate("do"));
                    Vozidlo vozidlo = new Vozidlo(rs.getInt("id_vozidla"), new Cennik(rs.getInt("id_cennika")),
                            rs.getString("spz"), rs.getString("znacka"), rs.getString("typ"), null, rs.getDate("datum_vyradenia")); //TODO fotka
                    Zakaznik zakaznik = zistiTypZakaznika(rs.getString("id_zakaznika"));
                    vypozicka.setVozidlo(vozidlo);
                    vypozicka.setZakaznik(zakaznik);
                    zoznamVypoziciek.add(vypozicka);
                }
                return zoznamVypoziciek;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Zakaznik nacitajOsobuAleboFirmu(Integer id) {
        String osoba = "select id, kontakt, rod_cislo , meno , priezvisko from zakaznik join osoba on id  = rod_cislo where rod_cislo = " + id;
        String firma = "select id, kontakt, ico       ,nazov , \"x\"      from zakaznik join osoba on id  = ico       where ico = " + id;

        SQL.run(osoba + "\nUNION\n" + firma, (p) -> {
            try {
                if (p.getString(2).equals("x")) {
//                    return new Firma(p.getString("ico"),p.getString("nazov"));
                } else {

                }

                while (p.next()) {
                    Cennik cennik = new Cennik(p.getInt("ID"), p.getDouble("cena_den"),
                            p.getDouble("poplatok"), p.getDate("platny_od"), p.getDate("platny_do"));
//                    resultList.add(cennik);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return null;
    }

    //pouzitie: ArrayList<Faktura> test = (ArrayList<Faktura>) man.nacitajFaktury("where suma > 400", "zaplatena", 10, 1);
    public List<Faktura> nacitajFaktury(String vyrazWhere, String vyrazOrder, int velkostStranky, int indexStranky) {
        String vyraz = "select * from" +
                "( select f.*, rownum as rn " +
                "from ( select * from faktura " +
                "join vypozicka on faktura.id_vypozicky = vypozicka.id " +
                "join vozidlo on vypozicka.id_vozidla = vozidlo.id " +
                "join zakaznik on vypozicka.id_zakaznika = zakaznik.id "
                + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by zaplatena";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) f ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;
        ResultSet rs = SQL.runToResultSet(vyraz);
        if (rs != null) {
            ArrayList<Faktura> zoznamFaktur = new ArrayList<>();
            try {
                while (rs.next()) {
                    Faktura faktura = new Faktura(rs.getInt("id"), new Vypozicka(rs.getInt("id_vypozicky")),
                            rs.getDouble("suma"), rs.getDate("vystavena"), rs.getDate("zaplatena"));
                    Vypozicka vypozicka = new Vypozicka(rs.getInt("id_vypozicky"), new Vozidlo(rs.getInt("id_vozidla")),
                            new Zakaznik(rs.getString("id_zakaznika")), rs.getDate("od"), rs.getDate("do"));
                    Vozidlo vozidlo = new Vozidlo(rs.getInt("id_vozidla"), new Cennik(rs.getInt("id_cennika")),
                            rs.getString("spz"), rs.getString("znacka"), rs.getString("typ"), null, rs.getDate("datum_vyradenia")); //TODO fotka
                    Zakaznik zakaznik = zistiTypZakaznika(rs.getString("id_zakaznika"));
                    vypozicka.setVozidlo(vozidlo);
                    vypozicka.setZakaznik(zakaznik);
                    faktura.setVypozicka(vypozicka);
                    zoznamFaktur.add(faktura);
                }
                return zoznamFaktur;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
        //return SQL.runQueryToList(vyraz);
    }


    //pouzitie: ArrayList<Osoba> test = (ArrayList<Osoba>) man.nacitajZakaznikovOsoby("where meno = 'Michaela'", "priezvisko", 10, 1);
    public List<Osoba> nacitajZakaznikovOsoby(String vyrazWhere, String vyrazOrder, int velkostStranky,
                                              int indexStranky) {
        String vyraz = "select * from" +
                "( select o.*, rownum as rn " +
                "from ( select * from osoba join zakaznik on osoba.rod_cislo = zakaznik.id " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by priezvisko";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) o ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;
        return SQL.runQueryToList(vyraz);
    }

    //pouzitie:  ArrayList<Firma> test = (ArrayList<Firma>) man.nacitajZakaznikovFirmy("where nazov like 'J%'", "nazov", 10, 1);
    public List<Firma> nacitajZakaznikovFirmy(String vyrazWhere, String vyrazOrder, int velkostStranky,
                                              int indexStranky) {
        String vyraz = "select * from" +
                "( select f.*, rownum as rn " +
                "from ( select * from firma join zakaznik on firma.ico = zakaznik.id " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by nazov";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) f ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;
        return SQL.runQueryToList(vyraz);
    }


    /*
     * Vypis vsetkych dat z tabuliek
     */


    /*
     * Delete
     * vrati pocet riadkov, v pripade erroru vrati -1
     * deletne iba data bez relacie na potomka
     */
    public int vymazVozidla(String vyrazWhere) {
        String vyraz = "delete from vozidlo where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }

    //treba znovu rozmyslat nad tym, co jasne definuje udrzbu? Tuto metodu pouzi ked chces deletnut viacere udrzby podla nejakeho atributu. Ak chces deletnu konkretnu udrzbu, tak pouzi druhu metodu.
    //pouzitie:  int vysl = man.vymazUdrzby("spz2", "popis = 'popis2'");
    public int vymazUdrzby(String spzVozidla, String vyrazWhere) {
        String vyraz = "delete from table ( select v.udrzba from vozidlo v where spz = '" + spzVozidla + "') u where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }

    //Sluzi pre delete konkretnej udrzby vozidla
    //pouzitie: int vysl = man.vymazUdrzby("spz2", new Udrzba(1, 2, new GregorianCalendar(1986, Calendar.SEPTEMBER, 29).getTime(), new GregorianCalendar(1986, Calendar.SEPTEMBER, 29).getTime(), "popis1"));
    public int vymazUdrzby(String spzVozidla, Udrzba udrzba) {
        String vyraz = "delete from table ( select v.udrzba from vozidlo v where spz = '" + spzVozidla + "') u where u.km = "
                + udrzba.getPocetKM() + " and u.cena = " + udrzba.getCena() + " and u.od = to_date('" + new java.sql.Date(udrzba.getDatumOD().getTime()).toString()
                + "', 'yyyy-mm-dd') and u.do = to_date('" + new java.sql.Date(udrzba.getDatumDO().getTime()).toString() + "', 'yyyy-mm-dd') and u.popis = '" + udrzba.getPopis() + "'";
        return SQL.runUpdateQuery(vyraz);
    }

    public int vymazCenniky(String vyrazWhere) {
        String vyraz = "delete from cennik where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }

    public int vymazVypozicky(String vyrazWhere) {
        String vyraz = "delete from vypozicka where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }

    public int vymazFaktury(String vyrazWhere) {
        String vyraz = "delete from faktura where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }

    public int vymazOsobu(String rodCislo) {
        String vyraz = "delete from osoba where rod_cislo = '" + rodCislo + "'";
        int vysl = SQL.runUpdateQuery(vyraz);
        if (vysl > 0) {
            vyraz = "delete from zakaznik where id = '" + rodCislo + "'";
            return SQL.runUpdateQuery(vyraz);
        }
        return vysl;
    }

    public int vymazFirmu(String ico) {
        String vyraz = "delete from firma where rod_cislo = '" + ico + "'";
        int vysl = SQL.runUpdateQuery(vyraz);
        if (vysl > 0) {
            vyraz = "delete from zakaznik where id = '" + ico + "'";
            return SQL.runUpdateQuery(vyraz);
        }
        return vysl;
    }

    /*
     * Update
     * vrati pocet riadkov, v pripade erroru vrati -1
     */
    //pouzitie: int vysl = man.updateVozidla("spz='test'", "spz='test2'");
    public int updateVozidla(Vozidlo stareVozidlo, Vozidlo noveVozidlo) {
        String vyraz = "Update vozidlo set id_cennika = ?, spz = ?, znacka = ?, typ = ?, fotka = ?, datum_vyradenia = ? where spz = '" + stareVozidlo.getSpz() + "'";
        try {
            PreparedStatement ps = PdsConnection.getInstance().getConnection().prepareStatement(vyraz);
            ps.setInt(1, noveVozidlo.getCennik().getId());
            ps.setString(2, noveVozidlo.getSpz());
            ps.setString(3, noveVozidlo.getZnacka());
            ps.setString(4, noveVozidlo.getTyp());
            if (noveVozidlo.getFotkaCesta().equals("")) {
                ps.setBinaryStream(5, null);
            } else {
                File imgfile = new File(noveVozidlo.getFotkaCesta());
                FileInputStream fin = new FileInputStream(imgfile);
                ps.setBinaryStream(5, fin, (int) imgfile.length());
            }
            ps.setDate(6, new java.sql.Date(noveVozidlo.getDatum_vyradenia().getTime()));
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int updateCenniky(String vyrazWhere, String vyrazSet) {
        String vyraz = "Update cennik set " + vyrazSet + " Where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }

    public int updateVypozicky(String vyrazWhere, String vyrazSet) {
        String vyraz = "Update vypozicka set " + vyrazSet + " Where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }

    public int updateFaktury(String vyrazWhere, String vyrazSet) {
        String vyraz = "Update faktura set " + vyrazSet + " Where " + vyrazWhere;
        return SQL.runUpdateQuery(vyraz);
    }

    //tu to bolo moc komplikovane, tak radsej takto cez objekty
    public int updateOsoby(Osoba staraOsoba, Osoba novaOsoba) {
        String vyraz = "";
        //ci doslo k zmene PK
        if (!staraOsoba.getRodCislo().equals(novaOsoba.getRodCislo())) {
            //insertne sa novy zakaznik
            Zakaznik novyZakaznik = new Zakaznik(novaOsoba.getRodCislo(), novaOsoba.getKontakt());
            if (SQL.runInsertQuery(novyZakaznik) <= 0) {
                return -1;
            }
            //update vsetkych child zaznamov
            int kontrola = SQL.runUpdateQuery("Update vypozicka set id_zakaznika = '" + novaOsoba.getRodCislo() + "' where id_zakaznika = '" + staraOsoba.getRodCislo() + "'");
            kontrola = SQL.runUpdateQuery("Update osoba set rod_cislo = '" + novaOsoba.getRodCislo() + "', meno = '" + novaOsoba.getMeno()
                    + "', priezvisko = '" + novaOsoba.getPrizvisko() + "' where rod_cislo = '" + staraOsoba.getRodCislo() + "'");
            //deletne sa stary zaznam
            return SQL.runUpdateQuery("Delete from zakaznik where id = '" + staraOsoba.getRodCislo() + "'");
        } else {
            int kontrola = SQL.runUpdateQuery("Update zakaznik set kontakt = '" + novaOsoba.getKontakt() + "'");
            return SQL.runUpdateQuery("Update osoba set meno = '" + novaOsoba.getMeno()
                    + "', priezvisko = '" + novaOsoba.getPrizvisko() + "' where rod_cislo = '" + staraOsoba.getRodCislo() + "'");
        }
    }

    public int updateFirmy(Firma staraFirma, Firma novaFirma) {
        String vyraz = "";
        //ci doslo k zmene PK
        if (!staraFirma.getIco().equals(novaFirma.getIco())) {
            //insertne sa novy zakaznik
            Zakaznik novyZakaznik = new Zakaznik(novaFirma.getIco(), novaFirma.getKontakt());
            if (SQL.runInsertQuery(novyZakaznik) <= 0) {
                return -1;
            }
            //update vsetkych child zaznamov
            int kontrola = SQL.runUpdateQuery("Update vypozicka set id_zakaznika = '" + novaFirma.getIco() + "' where id_zakaznika = '" + staraFirma.getIco() + "'");
            kontrola = SQL.runUpdateQuery("Update firma set ico = '" + novaFirma.getIco() + "', nazov = '" + novaFirma.getNazov()
                    + "' where ico = '" + staraFirma.getIco() + "'");
            //deletne sa stary zaznam
            return SQL.runUpdateQuery("Delete from zakaznik where id = '" + staraFirma.getIco() + "'");
        } else {
            int kontrola = SQL.runUpdateQuery("Update zakaznik set kontakt = '" + novaFirma.getKontakt() + "'");
            return SQL.runUpdateQuery("Update firma set nazov = '" + novaFirma.getNazov()
                    + "' where ico = '" + staraFirma.getIco() + "'");
        }
    }

    //tu je problem, ako rozlisit udrzby? Nemame tam ID, ani nic unikatne, tak povedzme ze unikatnost udrzby bude podla kombinacie vsetkych jej atributov
    public int updateUdrzby(Vozidlo vozidloSUdrzbami, Udrzba staraUdrzba, Udrzba novaUdrzba) {
        String vyraz = "update table ( select v.udrzba from vozidlo v where v.spz = '" + vozidloSUdrzbami.getSpz() + "') u set u.km = "
                + novaUdrzba.getPocetKM() + ", u.cena = " + novaUdrzba.getCena() + ", u.od = to_date('" + new java.sql.Date(novaUdrzba.getDatumOD().getTime()).toString()
                + "', 'yyyy-mm-dd'), u.do = to_date('" + new java.sql.Date(novaUdrzba.getDatumDO().getTime()).toString() + "', 'yyyy-mm-dd'), u.popis = '" + novaUdrzba.getPopis() + "'"
                + " where u.km = " + staraUdrzba.getPocetKM() + " and u.cena = " + staraUdrzba.getCena() + " and u.od = to_date('"
                + new java.sql.Date(staraUdrzba.getDatumOD().getTime()).toString() + "', 'yyyy-mm-dd') and u.do = to_date('" + new java.sql.Date(staraUdrzba.getDatumDO().getTime()).toString() + "', 'yyyy-mm-dd')";
        if (staraUdrzba.getPopis().isEmpty()) {
            vyraz += " and u.popis is null";
        } else {
            vyraz += " and u.popis = '" + staraUdrzba.getPopis() + "'";
        }
        String lol = new java.sql.Date(staraUdrzba.getDatumDO().getTime()).toString();
        return SQL.runUpdateQuery(vyraz);
    }

    /*

     String vyraz = "select * from" +
                "( select v.*, naklady_vozidla(v.id) as naklady ,vynosy_vozidla(v.id) as vynosy ,y.cena_den, rownum as rn " +
                "from ( select id,id_cennika,spz,znacka,typ,fotka,udrzba,datum_vyradenia, vytazenost_vozidla(id) as vytazenost ,poruchovost_vozidla(id) as poruchovost  from vozidlo where poruchovost_vozidla(id)  >= 0  " + vyrazWhere;
        if (vyrazOrder.equals("")) {
            vyraz += " order by datum_vyradenia";
        } else {
            vyraz += " order by " + vyrazOrder;
        }
        vyraz += " ) v JOIN cennik y on v.id_cennika = y.id ) where rn between " + ((indexStranky) * velkostStranky - velkostStranky) + " and " + velkostStranky * indexStranky;


    * */
    public List<Vozidlo> vozidlaBezZisku(String from, String to, int velkostStranky, int indexStranky) {
        String vyraz = "select id,spz,zarobok from " +
                "    (   select id,spz, zarobok_vozidla_datum(id,'" + from + "','" + to + "') as zarobok, rownum as rn " +
                "        from ( vozidlo) v " +
                "        where zarobok_vozidla_datum(id,'" + from + "','" + to + "') <= 0 " +
                "        order by zarobok " +
                "    )  where rn between  + ((" + indexStranky + ") * " + velkostStranky + " - " + velkostStranky + ")   and   " + velkostStranky + " * " + indexStranky;


        System.out.println(vyraz);
        System.out.println();
        ArrayList<Vozidlo> v = new ArrayList<>();
        SQL.run(vyraz, (row) -> {
            try {
                while (row.next()) {
                    Vozidlo voz = new Vozidlo((row.getInt(1)));
                    voz.setSpz(row.getString("spz"));
                    voz.setVynosy(row.getFloat("zarobok"));
                    v.add(voz);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return v;
    }

    public List<Pair<String, Double>> vytazenostZnaciek() {
        String query = "SELECT\n" +
                "   *\n" +
                "FROM\n" +
                "    (\n" +
                "        SELECT\n" +
                "            znacka,\n" +
                "            (SUM(vytazenost_vozidla(id))) AS vytazenost\n" +
                "        FROM\n" +
                "            ( vozidlo ) v\n" +
                "         \n" +
                "        GROUP BY znacka\n" +
                "        ORDER BY\n" +
                "            vytazenost\n" +
                "    )\n".replace("\n", "");
        ArrayList<Pair<String, Double>> v = new ArrayList<>();
        SQL.run(query, (row) -> {
            try {
                while (row.next()) {
                    String znacka = ((row.getString(1)));
                    Double vytazenost = ((row.getDouble(2)));
                    v.add(new Pair<String, Double>(znacka, vytazenost));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return v;
    }

    public int pocetAutZakaznik(@Nullable String from, @Nullable String to, @NotNull String ico) {
        AtomicInteger retu = new AtomicInteger();
        String query = "select selectPocetAutZakaznik(" + "'" + from + "','" + to + "','" + ico + "') from dual";
        System.out.println(query);
        SQL.run(query, (row) -> {
            try {
                retu.set(row.getInt(1));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        return retu.get();
    }

    public Pair<Integer, Integer> poctyPreVozidla(@Nullable String from, @Nullable String to, @NotNull Integer id, @NotNull String znacka) {
        AtomicReference<Pair<Integer, Integer>> p = new AtomicReference<>(new Pair<>(0, 0));
        String query = "select selectPocetAutZnacka(" + "'" + from + "','" + to + "','" + znacka + "'), selectPocetAutAuto" + "('" + from + "','" + to + "','" + id.toString() + "')" + " from dual";
        System.out.println(query);
        SQL.run(query, (row) -> {
            try {
                p.set(new Pair<>(row.getInt(1), (row.getInt(2))));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        return p.get();
    }
}


