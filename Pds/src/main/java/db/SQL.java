package db;

import jadro.PodporovaneTypy;
import model.*;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

final public class SQL {

    public static void call(String procedure){
        try {
            CallableStatement st = PdsConnection.getInstance().getConnection().prepareCall("{call "+ procedure+" }");
            st.execute();
            System.out.println(st.getString(1));
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void run(String query, Consumer<OracleResultSet> onResult) {
        try {
            OraclePreparedStatement ps = (OraclePreparedStatement) PdsConnection.getInstance().getConnection().prepareStatement(query);
            OracleResultSet p = (OracleResultSet) ps.executeQuery();
          //  p.next();
         //   System.out.println(p.getSQLXML("report_one").getString());
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
                                p.getString("spz"), p.getString("znacka"), p.getString("typ"), null, p.getDate("datum_vyradenia"));
                        vozidlo.setNaklady(p.getFloat("naklady"));
                        vozidlo.setVynosy(p.getFloat("vynosy"));
                        vozidlo.setPoruchovost(p.getFloat("poruchovost"));
                        vozidlo.setVytazenost(p.getFloat("vytazenost"));
                        vozidlo.getCennik().setCena_den(p.getDouble("cena_den"));
                        InputStream is = p.getBinaryStream("fotka");
                        if (is != null) {
                            String nazovFotky = vozidlo.getSpz();
                            nazovFotky = nazovFotky.replaceAll("\\s+","");
                            nazovFotky += ".jpg";
                            File image = new File("fotky/" + nazovFotky);
                            FileOutputStream fos = new FileOutputStream(image);
                            byte[] buffer = new byte[1];
                            while (is.read(buffer) > 0) {
                                fos.write(buffer);
                            }
                            fos.close();
                            vozidlo.setFotkaCesta(nazovFotky);
                        }

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
                String vyraz = "insert into vozidlo values(?,?,?,?,?,?,";
                Vozidlo vozidlo = (Vozidlo) data;

                if (!vozidlo.getUdrzby().isEmpty()) {
                    vyraz += "udrzby(";
                    for (Udrzba u : vozidlo.getUdrzby()) {
                        if (u == vozidlo.getUdrzby().get(0)) {
                            vyraz += "t_udrzba(?,?,?,?,?)";
                        } else {
                            vyraz += ",t_udrzba(?,?,?,?,?)";
                        }
                    }
                    vyraz += ")";
                } else {
                    vyraz += "?";
                }
                vyraz += ",?)";

                ps = PdsConnection.getInstance().getConnection().prepareStatement(vyraz);
                ps.setInt(1, vozidlo.getId());
                ps.setInt(2, vozidlo.getCennik().getId());
                ps.setString(3, vozidlo.getSpz());
                ps.setString(4, vozidlo.getZnacka());
                ps.setString(5, vozidlo.getTyp());
                if (vozidlo.getFotkaCesta().equals("")) {
                    ps.setBinaryStream(6, null);
                } else {
                    System.out.println(vozidlo.getFotkaCesta());
                  File imgfile = new File(vozidlo.getFotkaCesta());
                  FileInputStream fin = new FileInputStream(imgfile);
                  ps.setBinaryStream(6, fin, (int) imgfile.length());
                }
                int counter = 7;
                if (vozidlo.getUdrzby().isEmpty()) {
                    ps.setNull(counter, Types.ARRAY, "UDRZBY");
                    counter++;
                } else {
                    for (Udrzba u : vozidlo.getUdrzby()) {
                        ps.setLong(counter,u.getPocetKM());
                        counter++;
                        ps.setDouble(counter,u.getCena());
                        counter++;
                        ps.setDate(counter, new java.sql.Date(u.getDatumOD().getTime()));
                        counter++;
                        ps.setDate(counter, new java.sql.Date(u.getDatumDO().getTime()));
                        counter++;
                        ps.setString(counter, u.getPopis());
                        counter++;
                    }
                }
                if(vozidlo.getDatum_vyradenia()!=null)
                    ps.setDate(counter, new java.sql.Date(vozidlo.getDatum_vyradenia().getTime()));
                else
                    ps.setDate(counter, null);

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
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
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

//    public static void testxml(String query) {
//        try {
//            PreparedStatement ps = PdsConnection.getInstance().getConnection().prepareStatement(query);
//            OraclePreparedStatement ops = (OraclePreparedStatement) ps;
//            ResultSet rs = ops.executeQuery();
//            OracleResultSet ors = (OracleResultSet) rs;
//            Document xml_doc = null;
//            while (ors.next()) {
//                XMLType poxml = XMLType.createXML(ors.getOPAQUE("report_one"));
//                //print the full xmltype
//                System.out.println(poxml.getStringVal());
//                //below method is deprecated
//                //xml_column = (Document)poxml.getDOM();
//                xml_doc = (Document)poxml.getDocument();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
