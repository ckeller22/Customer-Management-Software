/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskcrm.DAO;

import cskcrm.util.Constants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ckeller22
 */
public class DAO {

    private static Connection myConn;

    public static Connection initConnection() {
        try {
            myConn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASS);
            System.out.println("Connected to database : " + Constants.DB_NAME);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());

        }
        return myConn;
    }

    public static Connection getConnection() {
        return myConn;
    }

    public static void closeConnection() {
        try {
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Connection closed.");
        }
    }
}
