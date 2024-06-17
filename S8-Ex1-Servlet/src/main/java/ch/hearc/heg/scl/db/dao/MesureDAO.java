package ch.hearc.heg.scl.db.dao;

import ch.hearc.heg.scl.business.Mesure;
import ch.hearc.heg.scl.business.Vent;
import ch.hearc.heg.scl.db.sources.OracleDB;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MesureDAO {
    // INSERT d'une mesure liée à une ville (selon la classe mesure du package business) dans la base de données
    public static long create(Mesure m, int numeroVille){
        Connection c;
        OraclePreparedStatement pstmt = null;
        ResultSet rs;

        long rNUmero = -1;

        System.out.println("Numéro de la ville : " + numeroVille);

        try {
            c = OracleDB.getOJDBCConnection();

            pstmt = (OraclePreparedStatement) c.prepareStatement("INSERT INTO MESURE (DT, DESCRIPTION, TEMPERATURE, TEMPERATURE_RESSENTIE, PRESSION, HUMIDITE, NUM_VILLE) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING NUMERO INTO ?");
            pstmt.setLong(1, m.getDateTime());
            pstmt.setString(2, m.getDescription());
            pstmt.setDouble(3, m.getTemperature());
            pstmt.setDouble(4, m.getTemperature_ressentie());
            pstmt.setDouble(5, m.getPression());
            pstmt.setDouble(6, m.getHumidite());
            pstmt.setInt(7, numeroVille);
            pstmt.registerReturnParameter(8, OracleTypes.NUMBER);
            pstmt.executeUpdate();

            rs = pstmt.getReturnResultSet();
            if(rs.next()){
                rNUmero = rs.getLong(1);
            }

            // Insertion du vent lié à la mesure
            pstmt = (OraclePreparedStatement) c.prepareStatement("INSERT INTO VENT (VITESSE, DIRECTION, NUM_MESURE) VALUES (?, ?, ?)");
            pstmt.setDouble(1, m.getVent().getVitesse());
            pstmt.setDouble(2, m.getVent().getDirection());
            pstmt.setLong(3, rNUmero);
            pstmt.executeUpdate();

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return rNUmero;
    }

    // SELECT d'une map de mesures (selon la classe mesure du package business) dans la base de données en fonction du numero de la ville
    public static Map<Long, Mesure> research(int numeroVille){
        Connection c;
        OraclePreparedStatement pstmt = null;
        ResultSet rs;

        Map<Long, Mesure> mesures = new HashMap<>();

        try {
            c = OracleDB.getOJDBCConnection();

            pstmt = (OraclePreparedStatement) c.prepareStatement("SELECT * FROM MESURE WHERE NUM_VILLE = ?");
            pstmt.setInt(1, numeroVille);
            rs = pstmt.executeQuery();

            while(rs.next()){
                Mesure m = new Mesure();
                m.setNumero(rs.getInt("NUMERO"));
                m.setDateTime(rs.getLong("DT"));
                m.setDescription(rs.getString("DESCRIPTION"));
                m.setTemperature(rs.getDouble("TEMPERATURE"));
                m.setTemperature_ressentie(rs.getDouble("TEMPERATURE_RESSENTIE"));
                m.setPression(rs.getDouble("PRESSION"));
                m.setHumidite(rs.getDouble("HUMIDITE"));

                // Recherche du vent lié à la mesure
                pstmt = (OraclePreparedStatement) c.prepareStatement("SELECT * FROM VENT WHERE NUM_MESURE = ?");
                pstmt.setLong(1, m.getNumero());
                ResultSet rsVent = pstmt.executeQuery();

                if(rsVent.next()){
                    Vent v = new Vent();
                    v.setNumero(rsVent.getInt("NUMERO"));
                    v.setVitesse(rsVent.getDouble("VITESSE"));
                    v.setDirection(rsVent.getDouble("DIRECTION"));
                    m.setVent(v);
                }

                mesures.put(m.getDateTime(), m);
            }

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return mesures;
    }

    // SELECT d'une mesure (selon la classe mesure du package business) dans la base de données en fonction de la ville et du datetime
    public static Mesure research(int numeroVille, long datetime){
        Connection c;
        OraclePreparedStatement pstmt = null;
        ResultSet rs;

        Mesure m = null;

        try {
            c = OracleDB.getOJDBCConnection();

            pstmt = (OraclePreparedStatement) c.prepareStatement("SELECT * FROM MESURE WHERE NUM_VILLE = ? AND DT = ?");
            pstmt.setInt(1, numeroVille);
            pstmt.setLong(2, datetime);
            rs = pstmt.executeQuery();

            if(rs.next()){
                m = new Mesure();
                m.setNumero(rs.getInt("NUMERO"));
                m.setDateTime(rs.getLong("DT"));
                m.setDescription(rs.getString("DESCRIPTION"));
                m.setTemperature(rs.getDouble("TEMPERATURE"));
                m.setTemperature_ressentie(rs.getDouble("TEMPERATURE_RESSENTIE"));
                m.setPression(rs.getDouble("PRESSION"));
                m.setHumidite(rs.getDouble("HUMIDITE"));

                // Recherche du vent lié à la mesure
                pstmt = (OraclePreparedStatement) c.prepareStatement("SELECT * FROM VENT WHERE NUM_MESURE = ?");
                pstmt.setLong(1, m.getNumero());
                ResultSet rsVent = pstmt.executeQuery();

                if(rsVent.next()){
                    Vent v = new Vent();
                    v.setNumero(rsVent.getInt("NUMERO"));
                    v.setVitesse(rsVent.getDouble("VITESSE"));
                    v.setDirection(rsVent.getDouble("DIRECTION"));
                    m.setVent(v);
                }
            }

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return m;
    }

    // DELETE d'une ou plusieurs mesures (selon la classe mesure du package business) dans la base de données en fonction de la ville
    public static void delete(int numeroVille){
        Connection c;
        OraclePreparedStatement pstmt = null;

        try {
            c = OracleDB.getOJDBCConnection();

            // Suppression des vents liés aux mesures
            pstmt = (OraclePreparedStatement) c.prepareStatement("DELETE FROM VENT WHERE NUM_MESURE IN (SELECT NUMERO FROM MESURE WHERE NUM_VILLE = ?)");
            pstmt.setInt(1, numeroVille);
            pstmt.executeUpdate();

            pstmt = (OraclePreparedStatement) c.prepareStatement("DELETE FROM MESURE WHERE NUM_VILLE = ?");
            pstmt.setInt(1, numeroVille);
            pstmt.executeUpdate();

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
