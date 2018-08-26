/*
Connects to mysql db and returns a Connection object.
 */
package Reception;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBConnector {

    private final String url = "jdbc:mysql://localhost:3306/medicare";
    private final String user = "root";
    private final String password = "";
    private final String driver = "com.mysql.jdbc.Driver";
    
    private Connection conn;
    
    
    public Connection getDBConnection(){
    
        conn = null;
        
        try {
        
            Class.forName(driver);
            
            conn = DriverManager.getConnection(url, user, password);
            
        }
        catch(ClassNotFoundException cnfexc){
            System.err.println("jdbc driver not installed in library");
            cnfexc.printStackTrace();
        } 
        catch (SQLException sqlex) {
            System.err.println("SQL Exception");
            sqlex.printStackTrace();
        }
        
        return conn;
    }
    
}
