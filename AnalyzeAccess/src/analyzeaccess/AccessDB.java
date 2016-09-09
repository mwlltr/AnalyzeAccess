/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyzeaccess;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DriverManager;
import java.util.ArrayList;


/**
 *
 * @author v003546
 */
public class AccessDB {
    Connection conn;
    Statement st;

public AccessDB(){
    try{
    Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    }
    catch(Exception e){
        e.printStackTrace();
    }
}
   
public static void main(String[] args){
    
AccessDB myAccessDB = new AccessDB();
myAccessDB.createConnection("Anrufgruendeerfassung_be.accdb");
myAccessDB.executeSQL("Select * from tblDatenAnruf");
System.out.println(myAccessDB.getAllColumns());

}

public void createConnection(String pathToAccessDB){
    try{
       this.conn = DriverManager.getConnection("jdbc:ucanaccess://" + pathToAccessDB);
       this.st = conn.createStatement();
    }
    catch(Exception e){
        e.printStackTrace();
    }      
}

public void closeConnection(){
  try{
     this.conn.close(); 
   }  
   catch(Exception e){
     e.printStackTrace();
   }
 }

 public void executeSQL(String sql){
      try{
          ResultSet rs = this.st.executeQuery(sql);
          ResultSetMetaData rsmd = rs.getMetaData();
          while(rs.next()){
            int columnsNumber = rsmd.getColumnCount();
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i)+ "\n");
             }
      }
      
          }catch(Exception e){
          e.printStackTrace();
      }
}
 
public ArrayList getAllTables(){
    String Name;
    ArrayList<String> tblNamen = new ArrayList<String>();
    try{
          DatabaseMetaData md = this.conn.getMetaData();
          ResultSet rs = md.getTables(null,null, "%",null);
          while(rs.next()){
              Name = rs.getString(3);
              tblNamen.add(Name);
            }
           }catch(Exception e){
          e.printStackTrace();
      }
    return tblNamen;
}

public ArrayList getAllColumns(){
    String tblName;
    String clmnName;
    ArrayList<String> clmnNamen = new ArrayList<String>();
    ArrayList<String> tblNamen = new ArrayList<String>();
    tblNamen = this.getAllTables();
    
    try{
        DatabaseMetaData md = this.conn.getMetaData();
    }
    catch(Exception e){
        e.printStackTrace();
    }
       
    for (int i = 0 ; i < tblNamen.size() ; i++){
        tblName = tblNamen.get(i);
        try{
        DatabaseMetaData md = this.conn.getMetaData();
        ResultSet rs = md.getColumns(null, null,tblName , null);
        while(rs.next()){
            clmnName = rs.getString(4);
            clmnNamen.add(clmnName);
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    }
    return clmnNamen;
}

public ArrayList getColumns(String tblName){
    ArrayList<String> clmnNamen = new ArrayList<String>();
    String clmnName;
    try{
        DatabaseMetaData md = this.conn.getMetaData();
        ResultSet rs= md.getColumns(null,null,tblName,null);
        while(rs.next()){
            clmnName = rs.getString(4);
            clmnNamen.add(clmnName);
        }
    }
    catch(Exception e){
        e.printStackTrace();
    }
    return clmnNamen;
}

}

