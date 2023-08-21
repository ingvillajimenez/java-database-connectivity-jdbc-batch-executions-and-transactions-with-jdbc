package com.loonycorn;

import java.sql.Connection; // interface Connection
import java.sql.SQLException; // class SQLException
import java.sql.Statement; // interface Statement

public class JDBCAdvanced {

    public static void main(String[] args) throws SQLException {

        Statement stmnt;
        String query;
        Connection conn = null;

        try {

            conn = DBUtils.getMysqlConnection("DeliveryService");
            conn.setAutoCommit(false);

            stmnt = conn.createStatement();

            query = "insert into delpartners values (107, 'Kylie', 'Kass', 22.0, false)";
            stmnt.executeUpdate(query);

            query = "insert into delvehicles values (17, 'Orange', 'Van', 'LOONY17')";
            stmnt.executeUpdate(query);

            conn.commit();
            System.out.println("Rows have been successfully inserted");
            //Rows have been successfully inserted

        }
        catch (SQLException ex) {
            System.out.println("An exception was thrown. Rolling back the Tx...");
            conn.rollback();
            ex.printStackTrace();
        }
        finally {
            conn.close();
        }

//        try {
//
//            conn = DBUtils.getMysqlConnection("DeliveryService");
//            conn.setAutoCommit(false);
//
//            stmnt = conn.createStatement();
//
//            query = "insert into delpartners values (107, 'Kylie', 'Kass', 22.0, false)";
//            stmnt.executeUpdate(query);
//
//            query = "insert into delvehicles values (16, 'Orange', 'Van', 'LOONY17')";
//            stmnt.executeUpdate(query);
//            //java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '16' for key 'delvehicles.PRIMARY'
//            //	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:117)
//            //	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)
//            //	at com.mysql.cj.jdbc.StatementImpl.executeUpdateInternal(StatementImpl.java:1334)
//            //	at com.mysql.cj.jdbc.StatementImpl.executeLargeUpdate(StatementImpl.java:2084)
//            //	at com.mysql.cj.jdbc.StatementImpl.executeUpdate(StatementImpl.java:1245)
//            //	at com.loonycorn.JDBCAdvanced.main(JDBCAdvanced.java:26)
//
//            conn.commit();
//            System.out.println("Rows have been successfully inserted");
//
//        }
//        catch (SQLException ex) {
//            System.out.println("An exception was thrown. Rolling back the Tx...");
//            //An exception was thrown. Rolling back the Tx...
//            conn.rollback();
//            ex.printStackTrace();
//        }
//        finally {
//            conn.close();
//        }

    }
}
