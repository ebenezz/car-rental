/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental_managment_system;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author abenezer
 */
public class Database {
    
    public static Connection connectDb(){
    try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection connect=DriverManager.getConnection("jdbc:mysql://localhost/rentcar", "root", "");
        //root is the difault username while "" or empty is for the password
        return connect;
    }catch(Exception e){e.printStackTrace();}
   return null;
}
}


















