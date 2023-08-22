package com.loonycorn;

import java.sql.*;
import java.util.Arrays;

public class JDBCAdvanced {

    public static void main(String[] args) throws SQLException {

        int[] count = {};
        Connection conn = null;
        Savepoint sp = null;

        try {

            conn = DBUtils.getMysqlConnection("DeliveryService");
            conn.setAutoCommit(false);

            PreparedStatement insertPS = DBUtils.getInsertVehiclePS(conn);

            DBUtils.addToInsertVehicleBatch(insertPS, 16, "Red", "Truck", "LOONY16");
            DBUtils.addToInsertVehicleBatch(insertPS, 17, "Orange", "Van", "LOONY17");
            DBUtils.addToInsertVehicleBatch(insertPS, 18, "Gray", "Van", "LOONY18");
            DBUtils.addToInsertVehicleBatch(insertPS, 19, "Pink", "Truck", "LOONY19");

            count = insertPS.executeBatch();
            sp = conn.setSavepoint("BatchOne");
            System.out.println("First batch executed: " + Arrays.toString(count));
            //First batch executed: [1, 1, 1, 1]

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
            //	at com.loonycorn.JDBCAdvanced.main(JDBCAdvanced.java:34)
            //Caused by: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '19' for key 'delvehicles.PRIMARY'
            //	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:117)
            //	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)
            //	at com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:916)
            //	at com.mysql.cj.jdbc.ClientPreparedStatement.executeUpdateInternal(ClientPreparedStatement.java:1061)
            //	at com.mysql.cj.jdbc.ClientPreparedStatement.executeBatchSerially(ClientPreparedStatement.java:795)
            //	... 3 more
            System.out.println("Second batch executed: " + Arrays.toString(count));

            conn.commit();
        }
        catch (SQLException ex) {

            if (sp != null) {

                System.out.println("An exception was thrown. Rolling back to " + sp.getSavepointName());
                //An exception was thrown. Rolling back to BatchOne
                conn.rollback(sp);
                conn.commit();
            }
            else {
                System.err.println("Errors detected. Rolling back everything...");
                conn.rollback();
            }

            ex.printStackTrace();
        }
        finally {
            conn.close();
        }
    }
}
