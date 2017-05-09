package com.swe.dbConnections;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
	
    private Statement statement;
    private ResultSet resultSet;
    private static PreparedStatement preparedStatement;
    static Connection connection = null;
    
    public static Connection getConnection() // singleton pattern 34an n3ml one connection bs 3al database
    {
    	 // law howa already connected hay return el connection
        
        if (connection != null) return connection; 
        
        // else hay3ml connection m3 l database we yrg3o brdo
        else 
        {
        	try
            {   
        		connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/project?"+
										"user=root&password=123456789&characterEncoding=utf8");
            }
        	
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return connection; 
        }
    }
    
   
    
    public static void insert (String query) throws SQLException  // 34an nst5dm el fun dy kol ma n3ml insert 
    {
    	preparedStatement = (PreparedStatement)getConnection().prepareStatement(query) ;  // hena by get el connection el awel b3den y3ml insert
		int res = preparedStatement.executeUpdate() ;
    }
   

	
}
