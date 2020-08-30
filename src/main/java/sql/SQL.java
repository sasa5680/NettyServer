package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
	
	public Connection connection  = null;
	public Statement  st 		  = null;
	public ResultSet  rs	      = null;

	public SQL() throws Exception{
		
		final String dataBase 	= "devicedb";
		final String url 		= "jdbc:mysql://localhost/"+dataBase+"?useSSL=false";
		final String id			= "root";
		final String pw			= "111111";
		
		
		Class.forName("com.mysql.jdbc.Driver");
	
			// TODO Auto-generated catch block
		
		
			//set starting db as client user db
		connection = DriverManager.getConnection(url, id, pw);
		st = connection.createStatement();

		
			// TODO Auto-generated catch block
			
		
		
		System.out.println("connected to mysql DB : "+dataBase);
	
	}	
	
	public void closeDB() throws Exception{
			
			
		if(st != null) 
			st.close();
			
				// TODO Auto-generated catch block
				
		if(connection != null)
			connection.close();
		
				// TODO Auto-generated catch block
			
	}

	
}
