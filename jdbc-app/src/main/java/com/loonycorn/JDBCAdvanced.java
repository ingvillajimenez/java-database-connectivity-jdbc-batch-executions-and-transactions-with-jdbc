package com.loonycorn;

import java.sql.Connection; // interface Connection
import java.sql.SQLException; // class SQLException
import java.sql.Statement; // interface Statement
import java.util.Arrays; // class Arrays
import java.sql.PreparedStatement; // interface PreparedStatement

public class JDBCAdvanced {

    public static void main(String[] args) {

        int[] count = {};

        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {

            PreparedStatement insertPS = DBUtils.getInsertVehiclePS(conn);

            DBUtils.addToInsertVehicleBatch(insertPS, 20, "Yellow", "Truck", "LOONY20");
            DBUtils.addToInsertVehicleBatch(insertPS, 19, "Pink", "Truck", "LOONY19");
            DBUtils.addToInsertVehicleBatch(insertPS, 21, "Green", "Van", "LOONY21");

            count = insertPS.executeBatch();
            //java.sql.BatchUpdateException: Duplicate entry '19' for key 'delvehicles.PRIMARY'
            //	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
            //	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
            //	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
            //	at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:500)
            //	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:481)
            //	at com.mysql.cj.util.Util.handleNewInstance(Util.java:192)
            //	at com.mysql.cj.util.Util.getInstance(Util.java:167)
            //	at com.mysql.cj.util.Util.getInstance(Util.java:174)
            //	at com.mysql.cj.jdbc.exceptions.SQLError.createBatchUpdateException(SQLError.java:224)
            //	at com.mysql.cj.jdbc.ClientPreparedStatement.executeBatchSerially(ClientPreparedStatement.java:816)
            //	at com.mysql.cj.jdbc.ClientPreparedStatement.executeBatchInternal(ClientPreparedStatement.java:418)
            //	at com.mysql.cj.jdbc.StatementImpl.executeBatch(StatementImpl.java:795)
            //	at com.loonycorn.JDBCAdvanced.main(JDBCAdvanced.java:23)
            //Caused by: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '19' for key 'delvehicles.PRIMARY'
            //	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:117)
            //	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)
            //	at com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:916)
            //	at com.mysql.cj.jdbc.ClientPreparedStatement.executeUpdateInternal(ClientPreparedStatement.java:1061)
            //	at com.mysql.cj.jdbc.ClientPreparedStatement.executeBatchSerially(ClientPreparedStatement.java:795)
            //	... 3 more
            // In spite of the exception, vehicles with id 20 and 21 were inserted into the table delvehicles

            System.out.println("Values inserted successfully: \n" + Arrays.toString(count));

        }
        catch (SQLException ex) {
            System.out.println("Inserts performed: \n" + Arrays.toString(count));
            //Inserts performed:
            //[]
            ex.printStackTrace();
        }

    }
}
