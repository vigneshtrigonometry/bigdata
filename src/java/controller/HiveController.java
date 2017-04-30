/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

/**
 *
 * @author Vignesh
 */
@Stateless
public class HiveController {

    private static final String driverName = "org.apache.hive.jdbc.HiveDriver";
    private String tableName = "";
    Connection con;
    private ResultSet res;
    private Statement stmt;
    private String sql = "";

    public HiveController() throws SQLException {
        try {
            System.out.println("###################Starting####################");
            try {
                Class.forName(driverName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            con = DriverManager.getConnection("jdbc:hive2://192.168.40.131:10000/bigdata", "hive", "");
            stmt = con.createStatement();
            String sql = ("show tables");
            res = stmt.executeQuery(sql);
            while (res.next()) {
                System.out.println(res.getString(1));
            }
            System.out.println("###################END####################");
        } catch (SQLException ex) {
            System.out.println("###################ERROR####################");
        }
    }

    public ResultSet getTransactionsForCity(String city) throws SQLException {
        System.out.println("###################Starting####################");
        String sql = ("select t.* from transactions t join devices d on t.deviceid = d.deviceid where d.location like '%" + city + "%' limit 100");
        try {
            res = stmt.executeQuery(sql);
            System.out.println("###################Ending####################");
            return res;
        } catch (SQLException ex) {
            Logger.getLogger(HiveController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

//    public void describeTable() throws SQLException {
//        // describe table
//        sql = "describe " + tableName;
//        System.out.println("Running: " + sql);
//        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            System.out.println(res.getString(1) + "\t" + res.getString(2));
//        }
//    }
//
//    public void loadData() throws SQLException {
//        // load data into table
//        // NOTE: filepath has to be local to the hive server
//        // NOTE: /tmp/a.txt is a ctrl-A separated file with two fields per line
//        String filepath = "/tmp/a.txt";
//        sql = "load data local inpath '" + filepath + "' into table " + tableName;
//        System.out.println("Running: " + sql);
//        res = stmt.executeQuery(sql);
//    }
//
//    public void selectData() throws SQLException {
//        // select * query
//        sql = "select * from " + tableName;
//        System.out.println("Running: " + sql);
//        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2));
//        }
//    }
//
//    public void hiveQuery() throws SQLException {
//        // regular hive query
//        sql = "select count(1) from " + tableName;
//        System.out.println("Running: " + sql);
//        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            System.out.println(res.getString(1));
//        }
//    }
}
