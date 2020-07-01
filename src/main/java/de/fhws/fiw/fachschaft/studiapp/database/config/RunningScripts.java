package de.fhws.fiw.fachschaft.studiapp.database.config;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;

public class RunningScripts {
    public static void main(String[] args) throws Exception {
        //Registering the Driver
        DriverManager.registerDriver(new org.postgresql.Driver());
        //Getting the connection
        String mysqlUrl = "jdbc:postgresql://127.0.0.1:5433/project";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "postgres");
        System.out.println("Connection established......");
        //Initialize the script runner
        ScriptRunner sr = new ScriptRunner(con);
        //Creating a reader object
        Reader reader = new BufferedReader(new FileReader("src/main/resources/migration.sql"));
        //Running the script
        sr.runScript(reader);
    }
}
