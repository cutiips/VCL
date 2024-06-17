package ch.hearc.heg.scl.db.dao;

import ch.hearc.heg.scl.business.Pays;
import ch.hearc.heg.scl.db.sources.OracleDB;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaysDAO {
    // INSERT d'un pays (selon la classe pays du package business) dans la base de données
    public static long create(Pays p){
        Connection c;
        OraclePreparedStatement pstmt = null;
        ResultSet rs;

        long rNUmero = -1;

        try {
            c = OracleDB.getOJDBCConnection();

            pstmt = (OraclePreparedStatement) c.prepareStatement("INSERT INTO PAYS (CODE, NOM) VALUES (?, ?) RETURNING NUMERO INTO ?");
            pstmt.setString(1, p.getCode());
            pstmt.setString(2, p.getNom());
            pstmt.registerReturnParameter(3, OracleTypes.NUMBER);
            pstmt.executeUpdate();

            rs = pstmt.getReturnResultSet();
            if(rs.next()){
                rNUmero = rs.getLong(1);
            }

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return rNUmero;
    }

    // SELECT d'un pays (selon la classe pays du package business) dans la base de données en fonction de son code
    public static Pays research(String code){
        Connection c;
        OraclePreparedStatement pstmt = null;
        ResultSet rs;

        Pays p = null;

        try {
            c = OracleDB.getOJDBCConnection();

            pstmt = (OraclePreparedStatement) c.prepareStatement("SELECT * FROM PAYS WHERE CODE = ?");
            pstmt.setString(1, code);
            rs = pstmt.executeQuery();

            if(rs.next()){
                p = new Pays(rs.getInt("NUMERO"), rs.getString("CODE"), rs.getString("NOM"));
            }

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return p;
    }

    // SELECT d'un pays (selon la classe pays du package business) dans la base de données en fonction de son numéro
    public static Pays researchByNumero(int numero){
        Connection c;
        OraclePreparedStatement pstmt = null;
        ResultSet rs;

        Pays p = null;

        try {
            c = OracleDB.getOJDBCConnection();

            pstmt = (OraclePreparedStatement) c.prepareStatement("SELECT * FROM PAYS WHERE NUMERO = ?");
            pstmt.setInt(1, numero);
            rs = pstmt.executeQuery();

            if(rs.next()){
                p = new Pays(rs.getInt("NUMERO"), rs.getString("CODE"), rs.getString("NOM"));
            }

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return p;
    }

    // UPDATE d'un pays (selon la classe pays du package business) dans la base de données
    public static void update(Pays p){
        Connection c;
        OraclePreparedStatement pstmt = null;

        try {
            c = OracleDB.getOJDBCConnection();

            pstmt = (OraclePreparedStatement) c.prepareStatement("UPDATE PAYS SET CODE = ?, NOM = ? WHERE NUMERO = ?");
            pstmt.setString(1, p.getCode());
            pstmt.setString(2, p.getNom());
            pstmt.setInt(3, p.getNumero());
            pstmt.executeUpdate();

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    // DELETE d'un pays (selon la classe pays du package business) dans la base de données
    public static void delete(Pays p){
        Connection c;
        OraclePreparedStatement pstmt = null;

        try {
            c = OracleDB.getOJDBCConnection();

            pstmt = (OraclePreparedStatement) c.prepareStatement("DELETE FROM PAYS WHERE NUMERO = ?");
            pstmt.setInt(1, p.getNumero());
            pstmt.executeUpdate();

            pstmt.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
