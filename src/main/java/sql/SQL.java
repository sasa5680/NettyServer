package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
	
	public Connection connection = null;
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
	
	public boolean logIn(String ID) {
		
		
		String query = "select exists (select * from userTBL where userID = "+ID+") as success;";
		int success = 0 ;
		
		try {
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				success = rs.getInt(1);
				//check whether id is in the DB. if fail,the value is 0 and if success, the value is 1
				System.out.print(success);
			}} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(success != 0 ) return true;
		else 			  return false;
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
