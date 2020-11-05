/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omarg
 */
public class Conexion {

    
    ResultSet rs;
    CallableStatement call;
    Statement st;

    public static void Conectar() {
        try {
            Connection con;
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=IMAGENES;user=sa;password=sasa;");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) {
        
    }
}
