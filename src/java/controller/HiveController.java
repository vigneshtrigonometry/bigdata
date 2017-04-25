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

/**
 *
 * @author Vignesh
 */
public class HiveController {

    private static final String driverName = "org.apache.hive.jdbc.HiveDriver";
    private String tableName = "";
    private ResultSet res;
    private Statement stmt;
    private String sql = "";

    public HiveController() throws SQLException {
        try {
            System.out.println("###################Starting####################");
            try {
                Class.forName(driverName);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.exit(1);
            }
            //replace "hive" here with the name of the user the queries should run as
            Connection con = DriverManager.getConnection("jdbc:hive2://192.168.40.131:10000/bigdata", "hive", "");
            Statement stmt = con.createStatement();
            //String tableName = "vignesh";
            //stmt.execute("drop table if exists " + tableName);
            //stmt.execute("create table " + tableName + " (key int, value string)");
            // show tables
            // String sql = "show tables '" + tableName + "'";
            String sql = ("show tables");
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                System.out.println(res.getString(1));
            }
            System.out.println("###################END####################");
        } catch (SQLException ex) {
            System.out.println("###################ERROR####################");
        }
    }

    public void describeTable() throws SQLException {
        // describe table
        sql = "describe " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1) + "\t" + res.getString(2));
        }
    }

    public void loadData() throws SQLException {
        // load data into table
        // NOTE: filepath has to be local to the hive server
        // NOTE: /tmp/a.txt is a ctrl-A separated file with two fields per line
        String filepath = "/tmp/a.txt";
        sql = "load data local inpath '" + filepath + "' into table " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
    }

    public void selectData() throws SQLException {
        // select * query
        sql = "select * from " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2));
        }
    }

    public void hiveQuery() throws SQLException {
        // regular hive query
        sql = "select count(1) from " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1));
        }
    }
}
