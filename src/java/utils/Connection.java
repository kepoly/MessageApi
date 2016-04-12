/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kepoly
 */
public class Connection {

//    public static Connection getConnection() {
//        Connection conn = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            String jdbc = "jdbc:mysql://" + System.getenv("OPENSHIFT_MYSQL_DB_HOST") + ":"
//                    + System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/practiceblog";
//            String user = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
//            String pass = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
//            conn = (Connection) DriverManager.getConnection(jdbc, user, pass);
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return conn;
//    }
    
    public static java.sql.Connection getConnection() throws SQLException {
         try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        String host = "localhost";
        String port = "3306";
        String db = "messages";
        String user = "root";
        String pass = "";
        String jdbc = "jdbc:mysql://" + host + ":" + port + "/" + db;
        return DriverManager.getConnection(jdbc, user, pass);
    }
    
    

}
