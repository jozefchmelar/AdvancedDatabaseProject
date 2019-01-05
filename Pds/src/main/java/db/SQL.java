package db;

import jadro.*;
import model.*;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

final public class SQL {

    public static void run(String query, Consumer<OracleResultSet> onResult) {
        try {
            Connection connection = PdsConnection.getInstance().getConnection();
            OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            OracleResultSet p = (OracleResultSet) rs;
            if (onResult != null) {
                while (p.next()) {
                    onResult.accept(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void run(String query) {
        run(query, null);
    }

    //returns list of values from single column
    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> runQueryToList(String query, PodporovaneTypy type, String columnLabel) {
        try {
            PreparedStatement ps = PdsConnection.getInstance().getConnection().prepareStatement(query);
            ResultSet p = ps.executeQuery();
            ArrayList resultList = null;
            switch (type) {
                case STRING:
                    resultList = new ArrayList<String>();
                    while (p.next()) {
                        resultList.add(p.getString(columnLabel));
                    }
                    break;
                case INTEGER:
                    resultList = new ArrayList<Integer>();
                    while (p.next()) {
                        resultList.add(p.getInt(columnLabel));
                    }
                    break;
                case DOUBLE:
                    resultList = new ArrayList<Double>();
                    while (p.next()) {
                        resultList.add(p.getDouble(columnLabel));
                    }
                    break;
                default:
                    System.err.println("Unsupported type usage in SQL.runToList.");
                    break;
            }
            return resultList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //returns list of rows from single table
    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> runQueryToList(String query) {
        try {
            PreparedStatement ps = PdsConnection.getInstance().getConnection().prepareStatement(query);
            ResultSet p = ps.executeQuery();
            ArrayList resultList = null;
            switch (p.getMetaData().getColumnLabel(2)) {
                case "CENA_DEN":
                    resultList = new ArrayList<Cennik>();
                    while (p.next()) {
                        Cennik cennik = new Cennik(p.getInt("ID"), p.getDouble("cena_den"),
                                p.getDouble("poplatok"), p.getDate("platny_od"), p.getDate("platny_do"));
                        resultList.add(cennik);
                    }
                    break;
                case "ID_VYPOZICKY":
                    resultList = new ArrayList<Faktura>();
                    while (p.next()) {
                        Faktura faktura = new Faktura(p.getInt("id"), new Vypozicka(p.getInt("id_vypozicky")),
                                p.getDouble("suma"), p.getDate("vystavena"), p.getDate("zaplatena"));
                        resultList.add(faktura);
                    }
                    break;
                case "NAZOV":
                    resultList = new ArrayList<Firma>();
                    while (p.next()) {
                        Firma firma = new Firma(p.getString("id"), p.getString("kontakt"),
                                p.getString("ico"), p.getNString("nazov"));
                        resultList.add(firma);
                    }
                    break;
                case "MENO":
                    resultList = new ArrayList<Osoba>();
                    while (p.next()) {
                        Osoba osoba = new Osoba(p.getString("id"), p.getString("kontakt"),
                                p.getString("rod_cislo"), p.getString("meno"), p.getString("priezvisko"));
                        resultList.add(osoba);
                    }
                    break;
                case "ID_CENNIKA":
                    resultList = new ArrayList<Vozidlo>();
                    while (p.next()) {
                        Vozidlo vozidlo = new Vozidlo(p.getInt("id"), new Cennik(p.getInt("id_cennika")),
                                p.getString("spz"), p.getString("znacka"), p.getString("typ"), p.getBlob("fotka"), p.getDate("datum_vyradenia"));
                        Array array = p.getArray("udrzba");
                        if (array != null) {
                            Object[] zoznamUdrzieb = (Object[]) array.getArray();
                            for (int i = 0; i < zoznamUdrzieb.length; i++) {
                                Struct udrzbaRaw = (Struct) zoznamUdrzieb[i];
                                Object[] attributes = udrzbaRaw.getAttributes();
                                vozidlo.getUdrzby().add(new Udrzba(attributes));
                            }
                        }
                        resultList.add(vozidlo);
                    }
                    break;
                case "ID_VOZIDLA":
                    resultList = new ArrayList<Vypozicka>();
                    while (p.next()) {
                        Vypozicka vypozicka = new Vypozicka(p.getInt("id"), new Vozidlo(p.getInt("id_vozidla")), new Zakaznik(p.getString("id_zakaznika")), p.getDate("od"), p.getDate("do"));
                        resultList.add(vypozicka);
                    }
                    break;
                case "CENA":
                    resultList = new ArrayList<Vozidlo>();
                    while (p.next()) {
                        Vozidlo vozidlo = new Vozidlo(p.getInt("id"), new Cennik(p.getInt("id_cennika")),
                                p.getString("spz"), p.getString("znacka"), p.getString("typ"), null, p.getDate("datum_vyradenia"));
                        Udrzba udrzba = new Udrzba(p.getLong("km"), p.getDouble("cena"), p.getDate("od"), p.getDate("do"), p.getString("popis"));
                        vozidlo.getUdrzby().add(udrzba);
                        resultList.add(vozidlo);
                    }
                    break;
                default:
                    System.err.println("Unsupported type usage in SQL.runToList.");
                    break;
            }
            return resultList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    @SuppressWarnings("unchecked")
    public static <T> int runInsertQuery(T data) {
        try {
            PreparedStatement ps = null;
            if (data instanceof Cennik) {
                String query = "Insert into Cennik ( id, cena_den, poplatok, platny_od, platny_do ) Values (?,?,?,?,?)";
                ps = ((Cennik) data).insertStatement(PdsConnection.getInstance().getConnection().prepareStatement(query));
            } else if (data instanceof Vozidlo) {
                Vozidlo vozidlo = (Vozidlo) data;
                String query = "Insert into Vozidlo Values (" + vozidlo.getId() + ", " + vozidlo.getCennik().getId() + ", '" + vozidlo.getSpz() + "', '"
                        + vozidlo.getZnacka() + "', '" + vozidlo.getTyp();
                if (vozidlo.getFotkaCesta() == null) {
                    query += "', null ";
                } else {
                    //TODO fotka
                }
                if (vozidlo.getUdrzby().isEmpty()) {
                    query += ", null ";
                } else {
                    query += ", udrzby(";
                    for (int i = 0; i < vozidlo.getUdrzby().size(); i++) {
                        if (i != 0) {
                            query += ", ";
                        }
                        query += "t_udrzba(" + vozidlo.getUdrzby().get(i).getPocetKM() + ", " + vozidlo.getUdrzby().get(i).getCena()
                                + ", to_date('" + new java.sql.Date(vozidlo.getUdrzby().get(i).getDatumOD().getTime()).toString() + "', 'yyyy-mm-dd')" + ", to_date('" + new java.sql.Date(vozidlo.getUdrzby().get(i).getDatumDO().getTime()).toString()
                                + "', 'yyyy-mm-dd')" + ", '" + vozidlo.getUdrzby().get(i).getPopis() + "')";
                    }
                    query += ")";
                }
                if (vozidlo.getDatum_vyradenia() == null) {
                    query += ", null )";
                } else {
                    query += ", to_date('" + new java.sql.Date(vozidlo.getDatum_vyradenia().getTime()).toString() + "', 'yyyy-mm-dd'))";
                }
                ps = PdsConnection.getInstance().getConnection().prepareStatement(query);
            } else if (data instanceof Vypozicka) {
                String query = "Insert into Vypozicka ( id, id_vozidla, id_zakaznika, od, do ) Values (?,?,?,?,?)";
                ps = ((Vypozicka) data).insertStatement(PdsConnection.getInstance().getConnection().prepareStatement(query));
            } else if (data instanceof Faktura) {
                String query = "Insert into Faktura ( id, id_vypozicky, suma, vystavena, zaplatena ) Values (?,?,?,?,?)";
                ps = ((Faktura) data).insertStatement(PdsConnection.getInstance().getConnection().prepareStatement(query));
            } else if (data instanceof Osoba) {
                String query = "Insert into Zakaznik ( id, kontakt ) Values (" + String.join(",", Arrays.asList(
                        ((Osoba) data).getRodCislo(),
                        ((Osoba) data).getKontakt()
                )) + ")";
                ps = ((Zakaznik) data).insertStatementZakaznik(PdsConnection.getInstance().getConnection().prepareStatement(query));
                int result = ps.executeUpdate();
                if (result > 0) {
                    query = "Insert into Osoba ( rod_cislo, meno, priezvisko ) Values (" + String.join(",", Arrays.asList(
                            ((Osoba) data).getRodCislo(),
                            ((Osoba) data).getMeno(),
                            ((Osoba) data).getPrizvisko()
                    )) + ")";
                    ps = ((Osoba) data).insertStatement(PdsConnection.getInstance().getConnection().prepareStatement(query));
                } else {
                    return 0;
                }
            } else if (data instanceof Firma) {
                String query = "Insert into Zakaznik ( id, kontakt ) Values (?,?)";
                ps = ((Zakaznik) data).insertStatementZakaznik(PdsConnection.getInstance().getConnection().prepareStatement(query));
                int result = ps.executeUpdate();
                if (result > 0) {
                    query = "Insert into Firma ( ico, nazov ) Values (?,?)";
                    ps = ((Firma) data).insertStatement(PdsConnection.getInstance().getConnection().prepareStatement(query));
                } else {
                    return 0;
                }
            } else if (data instanceof Zakaznik) {
                String query = "Insert into Zakaznik ( id, kontakt ) Values (?,?)";
                ps = ((Zakaznik) data).insertStatementZakaznik(PdsConnection.getInstance().getConnection().prepareStatement(query));
            }
            int vysl = ps.executeUpdate();
            return vysl;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    public static <T> int runUpdateQuery(String query) {
        try {
            PreparedStatement ps = PdsConnection.getInstance().getConnection().prepareStatement(query);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    public static <T> ResultSet runToResultSet(String query) {
        try {
            PreparedStatement ps = PdsConnection.getInstance().getConnection().prepareStatement(query);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
