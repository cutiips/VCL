package ch.hearc.heg.scl.db.sources;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;

public class OracleDB {
    private static OracleDataSource ods = null;

    private static final String HOST = "db.ig.he-arc.ch";
    private static final int PORT = 1521;
    private static final String SID = "ens";
    private static final String SCHEMA = "prof_francillon";
    private static final String PASSWORD = "prof_francillon";

    public static Connection getOJDBCConnection(){
        try {
            if(ods == null){
                ods = new OracleDataSource();
                ods.setDriverType("thin");
                ods.setServerName(HOST);
                ods.setPortNumber(PORT);
                ods.setDatabaseName(SID);
                ods.setUser(SCHEMA);
                ods.setPassword(PASSWORD);
            }

            return ods.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
