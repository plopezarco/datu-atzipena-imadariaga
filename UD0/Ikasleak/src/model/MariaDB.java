/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import global.Global;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author izarcelaya.aritz
 */
public class MariaDB {
     static String url = "";
     
     public static Connection konektatu() {
        Connection conn = null;
        try {

            url = "jdbc:mysql://" + Global.ZERBITZARIA + "/" + Global.DATUBASEA;
            
            // create a connection to the database
            
            conn = DriverManager.getConnection(url, Global.getErabiltzailea(), Global.getPasahitza());
            
            System.out.println("Konetatu zara");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
     
      public static ObservableList<Ikaslea> datuakMemorianKargatu(){
            ObservableList<Ikaslea> data = FXCollections.observableArrayList();
          
            String sql = "SELECT * FROM ikaslea";
        
        try (Connection conn = konektatu();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                
//                System.out.println(rs.getInt("zenbakia") +  "\t" + 
//                                   rs.getString("izena") +  "\t" +
//                                   rs.getString("abizena"));

                Ikaslea ik = new Ikaslea(rs.getInt("zenbakia"),rs.getString("izena"),rs.getString("abizena"));
                data.add(ik);
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " ==> " + e.getMessage());
        }
          
        return data;
    }
     
    public static boolean gehitu(int zenbakia, String izena ,String abizena) {
        String sql = "INSERT INTO ikaslea(zenbakia,izena,abizena) VALUES(?,?,?)";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, zenbakia);
            pstmt.setString(2, izena);
            pstmt.setString(3, abizena);
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
             
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public static boolean ezabatu(Ikaslea ikaslea){
        String sql = "DELETE FROM ikaslea WHERE zenbakia = ?;";
        int zenbakia = ikaslea.getZenbakia();
        
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, zenbakia);
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
             
            System.out.println(e.getMessage());
            return false;
        }
        
       
    }
    
    public static int aldatu(int zein, String zutabea, String balioBerria){
         String sql = "UPDATE ikaslea SET " + zutabea + " = ? WHERE zenbakia = ?;";
        
        
         try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, balioBerria);
            pstmt.setInt(2, zein);
            pstmt.executeUpdate();
            return 0;
            
        } catch (SQLException e) {
             
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
