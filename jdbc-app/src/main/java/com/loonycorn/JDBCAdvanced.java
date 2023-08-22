package com.loonycorn;

import java.sql.Connection; // interface Connection
import java.sql.SQLException; // class SQLException
import java.sql.Statement; // interface Statement
import java.sql.Savepoint; // interface Savepoint

public class JDBCAdvanced {

    public static void main(String[] args) throws SQLException {

        Statement stmnt;
        String query;
        Connection conn = null;
        Savepoint sp = null;

        try {

            conn = DBUtils.getMysqlConnection("DeliveryService");
            conn.setAutoCommit(false);

            stmnt = conn.createStatement();

            query = "insert into delpartners value (108, 'Brian', 'Walters', 22.0, false)";
            stmnt.executeUpdate(query);

            query = "insert into delvehicles values (18, 'Grey', 'Van', 'LOONY18')";
            stmnt.executeUpdate(query);

            sp = conn.setSavepoint("OnePlusOne");
            System.out.println("Save point created: " + sp.getSavepointName());
            //Save point created: OnePlusOne

            query = "insert into delvehicles values (19, 'Pink', 'Truck', 'LOONY19')";
            stmnt.executeUpdate(query);

            query = "insert into delpartners values (108, 'Aisha', 'Hussain', 22.0, false)";
            stmnt.executeUpdate(query);
            //java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '108' for key 'delpartners.PRIMARY'
            //	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:117)
            //	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)
            //	at com.mysql.cj.jdbc.StatementImpl.executeUpdateInternal(StatementImpl.java:1334)
            //	at com.mysql.cj.jdbc.StatementImpl.executeLargeUpdate(StatementImpl.java:2084)
            //	at com.mysql.cj.jdbc.StatementImpl.executeUpdate(StatementImpl.java:1245)
            //	at com.loonycorn.JDBCAdvanced.main(JDBCAdvanced.java:37)

            sp = conn.setSavepoint("TwoPlusTwo");
            System.out.println("Save pointe created: " + sp.getSavepointName());

            query = "insert into delvehicles values (20, 'Yellow', 'Truck', 'LOONY20')";
            stmnt.executeUpdate(query);

            query = "insert into delpartners values (110, 'Cuthbert', 'Crumble', 22.0, false)";
            stmnt.executeUpdate(query);

            conn.commit();
            System.out.println("Rows have been successfully inserted");

        }
        catch (SQLException ex) {

            if (sp != null) {

                System.out.println("An exception was thrown. Rolling back to " + sp.getSavepointName());
                //An exception was thrown. Rolling back to OnePlusOne
                conn.rollback(sp);
                conn.commit();
            }
            else {
                System.out.println("Errors detected. Rolling back everything...");
                conn.rollback();
            }

            ex.printStackTrace();
        }
        finally {
            conn.close();
        }
    }
}
