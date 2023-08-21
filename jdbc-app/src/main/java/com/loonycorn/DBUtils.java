package com.loonycorn;

import com.mysql.cj.jdbc.MysqlDataSource; // class MysqlDataSource

import java.sql.Connection; // interface Connection
import java.sql.SQLException; // class SQLException
import javax.sql.rowset.JdbcRowSet; // interface JdbcRowSet
import javax.sql.rowset.RowSetProvider; // class RowSetProvider
import javax.sql.rowset.CachedRowSet; // interface CachedRowSet
import java.sql.PreparedStatement; // interface PreparedStatement

public class DBUtils {

    private static final String dbURL = "jdbc:mysql://localhost:3306/";
    private static final String username = "root";
    private static final String password = "admin123";

    public static Connection getMysqlConnection(String schemaName) {

        MysqlDataSource mysqlDS = null;
        Connection conn = null;

        try {
            mysqlDS = new MysqlDataSource();

            mysqlDS.setURL(dbURL + schemaName);
            mysqlDS.setUser(username);
            mysqlDS.setPassword(password);

            conn = mysqlDS.getConnection();
        }
        catch (SQLException ex) {

            ex.printStackTrace();
        }

        return conn;
    }

    public static JdbcRowSet getJdbcRowSet(String schemaName) {

        JdbcRowSet jdbcRS = null;

        try {

            jdbcRS = RowSetProvider.newFactory().createJdbcRowSet();

            jdbcRS.setUrl(dbURL + schemaName);
            jdbcRS.setUsername(username);
            jdbcRS.setPassword(password);
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }

        return jdbcRS;
    }

    public static CachedRowSet getCachedRowSet(String schemaName) {

        CachedRowSet cachedRS = null;

        try {
            cachedRS = RowSetProvider.newFactory().createCachedRowSet();

            cachedRS.setUrl(dbURL + schemaName);
            cachedRS.setUsername(username);
            cachedRS.setPassword(password);

        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }

        return cachedRS;
    }

    public static PreparedStatement getInsertVehiclePS(Connection conn) {

        try {
            return conn.prepareStatement("insert into delvehicles values(?, ?, ?, ?)");
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }

        return null;
    }

    public static boolean addToInsertVehicleBatch(PreparedStatement ps, int id,
                                                  String color, String vehicleType,
                                                  String lPlate) {
        boolean addedQuery = false;

        try {
            ps.setInt(1, id);
            ps.setString(2, color);
            ps.setString(3, vehicleType);
            ps.setString(4, lPlate);
            ps.addBatch();

            addedQuery = true;
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }

        return addedQuery;
    }

}
