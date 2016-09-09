/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzeaccess;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 *
 * @author v003546
 */
public class AnalyzeAccess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://Anrufgruendeerfassung_be.accdb");
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * from tblDatenAnruf");
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        PrintWriter writer = new PrintWriter("AccessDB.txt", "UTF-8");
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    writer.print(columnValue + " " + rsmd.getColumnName(i)+ "\n");
                    System.out.print(columnValue + " " + rsmd.getColumnName(i)+ "\n");
            }
        }
        writer.close();
        conn.close();
        }
        catch( Exception e){
            e.printStackTrace();
        }
            
        // TODO code application logic here
    }
    
}
