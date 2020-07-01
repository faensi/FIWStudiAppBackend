package de.fhws.fiw.fachschaft.studiapp.database.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

    static Connection con;

    public static Connection getConnection() throws Exception {

        try {
        	
          final String db_driver = "com.mysql.jdbc.Driver";
          final String url="jdbc:mysql://localhost:3306/studyapp";
          final String user="studyapp";
          final String password="studyapp";
          Class.forName(db_driver); //Driver loading
            
          con = DriverManager.getConnection(url, user, password);

            System.out.println("Connected");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection con) throws Exception {

        try {
            con.close();
            System.out.println("Connection was closed");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

	

