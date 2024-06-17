package ch.hearc.heg.scl.db.dao;

import ch.hearc.heg.scl.business.Pays;
import ch.hearc.heg.scl.business.Ville;
import ch.hearc.heg.scl.db.sources.OracleDB;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VilleDAO {
    // INSERT d'une ville (selon la classe ville du package business) dans la base de données
    public static int create(Ville v){
        Connection c;
        OraclePreparedStatement pstmt = null;
        ResultSet rs;

        int rNUmero = -1;

        try {
            c = OracleDB.getOJDBCConnection();

            // Test si le numero du pays est un nombre entier positif
            if(v.getPays().getNumero() < 0){
                v.setPays(PaysDAO.research(v.getPays().getCode()));
            }

            pstmt = (OraclePreparedStatement) c.prepareStatement("INSERT INTO VILLE (OPENWEATHERMAPID, NOM, LATITUDE, LONGITUDE, TIMEZONE, NUM_PAYS) VALUES (?, ?, ?, ?, ?, ?) RETURNING NUMERO INTO ?");
            pstmt.setInt(1, v.getOpenWeatherMapId());
            pstmt.setString(2, v.getNom());
            pstmt.setDouble(3, v.getLatitude());
            pstmt.setDouble(4, v.getLongitude());
            pstmt.setInt(5, v.getTimezone());
            pstmt.setLong(6, v.getPays().getNumero());
            pstmt.registerReturnParameter(7, OracleTypes.NUMBER);
            pstmt.executeUpdate();

            rs = pstmt.getReturnResultSet();
            if(rs.next()){
                rNUmero = rs.getInt(1);
            }

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return rNUmero;
    }

    // SELECT d'une ville (selon la classe ville du package business) dans la base de données en fonction de son openWeatherMapId
    public static Ville research(int openWeatherMapId){
        Connection c;
        OraclePreparedStatement pstmt = null;
        ResultSet rs;

        Ville v = null;

        try {
            c = OracleDB.getOJDBCConnection();

            pstmt = (OraclePreparedStatement) c.prepareStatement("SELECT * FROM VILLE WHERE OPENWEATHERMAPID = ?");
            pstmt.setInt(1, openWeatherMapId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                v = new Ville();
                v.setNumero(rs.getInt("NUMERO"));
                v.setOpenWeatherMapId(rs.getInt("OPENWEATHERMAPID"));
                v.setNom(rs.getString("NOM"));
                v.setLatitude(rs.getDouble("LATITUDE"));
                v.setLongitude(rs.getDouble("LONGITUDE"));
                v.setTimezone(rs.getInt("TIMEZONE"));
                v.setPays(PaysDAO.researchByNumero(rs.getInt("NUM_PAYS")));
            }

            //Recuperation des mesures
            if(v != null) {
                v.setMesures(MesureDAO.research(v.getNumero()));
            }

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return v;
    }

    public static Ville researchByNumero(int numero){
        Connection c;
        OraclePreparedStatement pstmt = null;
        ResultSet rs;

        Ville v = null;

        try {
            c = OracleDB.getOJDBCConnection();

            pstmt = (OraclePreparedStatement) c.prepareStatement("SELECT * FROM VILLE WHERE NUMERO = ?");
            pstmt.setInt(1, numero);
            rs = pstmt.executeQuery();

            if(rs.next()){
                v = new Ville();
                v.setNumero(rs.getInt("NUMERO"));
                v.setOpenWeatherMapId(rs.getInt("OPENWEATHERMAPID"));
                v.setNom(rs.getString("NOM"));
                v.setLatitude(rs.getDouble("LATITUDE"));
                v.setLongitude(rs.getDouble("LONGITUDE"));
                v.setTimezone(rs.getInt("TIMEZONE"));
                v.setPays(PaysDAO.researchByNumero(rs.getInt("NUM_PAYS")));
            }

            //Recuperation des mesures
            if(v != null) {
                v.setMesures(MesureDAO.research(v.getNumero()));
            }

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return v;
    }

    // SELECT des villes (selon la classe ville du package business) dans la base de données en fonction de leur pays
    public static List<Ville> researchByPays(Pays p){
        Connection c;
        OraclePreparedStatement pstmt = null;
        ResultSet rs;

        List<Ville> villes = null;

        try {
            c = OracleDB.getOJDBCConnection();

            pstmt = (OraclePreparedStatement) c.prepareStatement("SELECT * FROM VILLE WHERE NUMEROPAYS = ?");
            pstmt.setInt(1, p.getNumero());
            rs = pstmt.executeQuery();

            while(rs.next()){
                Ville v = new Ville();
                v.setNumero(rs.getInt("NUMERO"));
                v.setOpenWeatherMapId(rs.getInt("OPENWEATHERMAPID"));
                v.setNom(rs.getString("NOM"));
                v.setLatitude(rs.getDouble("LATITUDE"));
                v.setLongitude(rs.getDouble("LONGITUDE"));
                v.setTimezone(rs.getInt("TIMEZONE"));
                v.setPays(p);

                //Recuperation des mesures
                v.setMesures(MesureDAO.research(v.getNumero()));

                villes.add(v);
            }

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return villes;
    }

    // LIST des villes (selon la classe ville du package business) dans la base de données
    public static Map<Integer, Ville> list(){
        Connection c;
        OraclePreparedStatement pstmt = null;
        ResultSet rs;

        Map<Integer, Ville> villes = new HashMap<>();

        try {
            c = OracleDB.getOJDBCConnection();

            pstmt = (OraclePreparedStatement) c.prepareStatement("SELECT * FROM VILLE");
            rs = pstmt.executeQuery();

            while(rs.next()){
                Ville v = new Ville();
                v.setNumero(rs.getInt("NUMERO"));
                v.setOpenWeatherMapId(rs.getInt("OPENWEATHERMAPID"));
                v.setNom(rs.getString("NOM"));
                v.setLatitude(rs.getDouble("LATITUDE"));
                v.setLongitude(rs.getDouble("LONGITUDE"));
                v.setTimezone(rs.getInt("TIMEZONE"));
                v.setPays(PaysDAO.researchByNumero(rs.getInt("NUM_PAYS")));

                //Recuperation des mesures
                v.setMesures(MesureDAO.research(v.getNumero()));

                villes.put(v.getOpenWeatherMapId(), v);
            }

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return villes;
    }

    // UPDATE d'une ville (selon la classe ville du package business) dans la base de données
    public static void update(Ville v){
        Connection c;
        OraclePreparedStatement pstmt = null;

        try {
            c = OracleDB.getOJDBCConnection();

            // Test si le numero du pays est un nombre entier positif
            if(v.getPays().getNumero() < 0){
                v.setPays(PaysDAO.research(v.getPays().getCode()));
            }

            pstmt = (OraclePreparedStatement) c.prepareStatement("UPDATE VILLE SET OPENWEATHERMAPID = ?, NOM = ?, LATITUDE = ?, LONGITUDE = ?, TIMEZONE = ?, NUMEROPAYS = ? WHERE NUMERO = ?");
            pstmt.setInt(1, v.getOpenWeatherMapId());
            pstmt.setString(2, v.getNom());
            pstmt.setDouble(3, v.getLatitude());
            pstmt.setDouble(4, v.getLongitude());
            pstmt.setInt(5, v.getTimezone());
            pstmt.setLong(6, v.getPays().getNumero());
            pstmt.setLong(7, v.getNumero());
            pstmt.executeUpdate();

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    // DELETE d'une ville (selon la classe ville du package business) dans la base de données et de ses mesures
    public static void delete(Ville v){
        Connection c;
        OraclePreparedStatement pstmt = null;

        try {
            c = OracleDB.getOJDBCConnection();

            pstmt = (OraclePreparedStatement) c.prepareStatement("DELETE FROM MESURE WHERE NUMEROVILLE = ?");
            pstmt.setLong(1, v.getNumero());
            pstmt.executeUpdate();

            MesureDAO.delete(v.getNumero());

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
