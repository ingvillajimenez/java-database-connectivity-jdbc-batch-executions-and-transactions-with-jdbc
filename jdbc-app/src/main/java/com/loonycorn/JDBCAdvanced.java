package com.loonycorn;

import java.sql.Connection; // interface Connection
import java.sql.SQLException; // class SQLException
import java.sql.Statement; // interface Statement
import java.util.Arrays; // class Arrays
import java.sql.PreparedStatement; // interface PreparedStatement

public class JDBCAdvanced {

    public static void main(String[] args) {

        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {

            PreparedStatement insertPS = DBUtils.getInsertVehiclePS(conn);

            DBUtils.addToInsertVehicleBatch(insertPS, 16, "Red", "Truck", "LOONY16");
            DBUtils.addToInsertVehicleBatch(insertPS, 17, "Orange", "Van", "LOONY17");
            DBUtils.addToInsertVehicleBatch(insertPS, 18, "Grey", "Van", "LOONY18");
            DBUtils.addToInsertVehicleBatch(insertPS, 19, "Pink", "Truck", "LOONY19");

            int[] count = insertPS.executeBatch();

            System.out.println("Values inserted successfully: \n" + Arrays.toString(count));
            //Values inserted successfully:
            //[1, 1, 1, 1]
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
