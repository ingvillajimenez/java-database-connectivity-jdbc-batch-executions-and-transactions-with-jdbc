package com.loonycorn;

import java.sql.Connection; // interface Connection
import java.sql.SQLException; // class SQLException
import java.sql.Statement; // interface Statement
import java.util.Arrays; // class Arrays

public class JDBCAdvanced {

    public static void main(String[] args) {

        Statement stmnt;

        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {

            conn.setAutoCommit(false);

            stmnt = conn.createStatement();

            stmnt.addBatch("insert into delvehicles values (20, 'Yellow', 'Truck', 'LOONY20')");
            stmnt.addBatch("insert into delvehicles values (21, 'Green', 'Van', 'LOONY21')");

            int[] count = stmnt.executeBatch();

            conn.commit();

            System.out.println("Values inserted successfully: \n" + Arrays.toString(count));
            //Values inserted successfully:
            //[1, 1]

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

//        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {
//
//            stmnt = conn.createStatement();
//
//            stmnt.addBatch("insert into delvehicles values (16, 'Red', 'Truck', 'LOONY16')");
//            stmnt.addBatch("insert into delvehicles values (17, 'Orange', 'Van', 'LOONY17')");
//            stmnt.addBatch("insert into delvehicles values (18, 'Grey', 'Van', 'LOONY18')");
//            stmnt.addBatch("insert into delvehicles values (19, 'Pink', 'Truck', 'LOONY19')");
//            //java.sql.BatchUpdateException: Duplicate entry '19' for key 'delvehicles.PRIMARY'
//            //	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
//            //	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
//            //	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
//            //	at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:500)
//            //	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:481)
//            //	at com.mysql.cj.util.Util.handleNewInstance(Util.java:192)
//            //	at com.mysql.cj.util.Util.getInstance(Util.java:167)
//            //	at com.mysql.cj.util.Util.getInstance(Util.java:174)
//            //	at com.mysql.cj.jdbc.exceptions.SQLError.createBatchUpdateException(SQLError.java:224)
//            //	at com.mysql.cj.jdbc.StatementImpl.executeBatchInternal(StatementImpl.java:891)
//            //	at com.mysql.cj.jdbc.StatementImpl.executeBatch(StatementImpl.java:795)
//            //	at com.loonycorn.JDBCAdvanced.main(JDBCAdvanced.java:23)
//            //Caused by: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '19' for key 'delvehicles.PRIMARY'
//            //	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:117)
//            //	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)
//            //	at com.mysql.cj.jdbc.StatementImpl.executeUpdateInternal(StatementImpl.java:1334)
//            //	at com.mysql.cj.jdbc.StatementImpl.executeBatchInternal(StatementImpl.java:857)
//            //	... 2 more
//
//            int[] count = stmnt.executeBatch();
//
//            System.out.println("Values inserted successfully: \n" + Arrays.toString(count));
//            //Values inserted successfully:
//            //[1, 1, 1, 1]
//
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }
    }
}
