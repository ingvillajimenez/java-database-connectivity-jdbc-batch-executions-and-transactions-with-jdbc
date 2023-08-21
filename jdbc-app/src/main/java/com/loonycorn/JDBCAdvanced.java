package com.loonycorn;

import java.sql.Connection; // interface Connection
import java.sql.SQLException; // class SQLException
import java.sql.Statement; // interface Statement

public class JDBCAdvanced {

    public static void main(String[] args) {

        Statement stmnt;
        String query;

        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {

            stmnt = conn.createStatement();

            query = "insert into delpartners values (107, 'Kylie', 'Kass', 22.0, false)";
            stmnt.executeUpdate(query);

            query = "insert into delvehicles values (16, 'Orange', 'Van', 'LOONY17')";
            stmnt.executeUpdate(query);
            //java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '16' for key 'delvehicles.PRIMARY'
            //	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:117)
            //	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)
            //	at com.mysql.cj.jdbc.StatementImpl.executeUpdateInternal(StatementImpl.java:1334)
            //	at com.mysql.cj.jdbc.StatementImpl.executeLargeUpdate(StatementImpl.java:2084)
            //	at com.mysql.cj.jdbc.StatementImpl.executeUpdate(StatementImpl.java:1245)
            //	at com.loonycorn.JDBCAdvanced.main(JDBCAdvanced.java:22)
            // the partner with 107 was inserted but her vehicle with id 16 was not so that it is an inconsistency

            System.out.println("Rows have been successfully inserted");

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

//        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {
//
//            stmnt = conn.createStatement();
//
//            query = "insert into delpartners values (106, 'Kylie', 'Kass', 22.0, false)";
//            stmnt.executeUpdate(query);
//            //java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '106' for key 'delpartners.PRIMARY'
//            //	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:117)
//            //	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)
//            //	at com.mysql.cj.jdbc.StatementImpl.executeUpdateInternal(StatementImpl.java:1334)
//            //	at com.mysql.cj.jdbc.StatementImpl.executeLargeUpdate(StatementImpl.java:2084)
//            //	at com.mysql.cj.jdbc.StatementImpl.executeUpdate(StatementImpl.java:1245)
//            //	at com.loonycorn.JDBCAdvanced.main(JDBCAdvanced.java:19)
//            // vehicle with id 17 was nos inserted because of the previous exception
//
//            query = "insert into delvehicles values (17, 'Orange', 'Van', 'LOONY17'";
//            stmnt.executeUpdate(query);
//
//            System.out.println("Rows have been successfully inserted");
//
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }

//        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {
//
//            stmnt = conn.createStatement();
//
//            query = "insert into delpartners values (106, 'Pablo', 'Hernandez', 20.0, false)";
//            stmnt.executeUpdate(query);
//
//            query = "insert into delvehicles values (16, 'Red', 'Truck', 'LOONY16')";
//            stmnt.executeUpdate(query);
//
//            System.out.println("Rows have been successfully inserted");
//            //Rows have been successfully inserted
//
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }

    }
}
