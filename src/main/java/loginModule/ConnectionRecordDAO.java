package loginModule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import client.client.Client;
import sql.SQL;

public class ConnectionRecordDAO {
	
	public static int addLogin(ConnectionRecordDTO dto) throws Exception {
		
		SQL sql = new SQL();
		
		int Num = 0;
		
		String query = "insert INTO loginrecord (deviceid, deviceType, logintime, IP, isfirstlogin) "
				+ "values ("+dto.getID()+", "+dto.getType()+", '"+dto.getLoginTime()+"', "+dto.getIP()+", "+dto.getFirstLogin()+")";
		
		sql.st.execute(query);
		

		//update Connection Record
		
		
		sql.closeDB();
		return Num;
	}
	
	public static void addLogOut(ConnectionRecordDTO dto , int NUM) throws Exception {
		
		SQL sql = new SQL();
	
		String query = "UPDATE loginrecord SET LogoutTime = '"+dto.getLogoutTime()+"', NormalExit = "+dto.getLogoutTime()+" SET WHERE NUM = "+NUM;
		
		sql.st.execute(query);
		
		
		sql.closeDB();
	}
	
	public static ArrayList<ConnectionRecordDTO> getRecords(Client client) throws Exception{
		
		SQL sql = new SQL();
		
		ArrayList<ConnectionRecordDTO> array = new ArrayList<ConnectionRecordDTO>();
		
		String query = "SELECT * FROM DEVICETBL where deviceID = '"+client.device.getID()+"'";
		
		sql.rs = sql.st.executeQuery(query);
		
		while(sql.rs.next()) {
			
			ConnectionRecordDTO dto = new ConnectionRecordDTO();
			
			dto.setID(sql.rs.getString("deviceID"));
			dto.setIP(sql.rs.getString("IP"));
			dto.setLoginTime(sql.rs.getString("logintime"));
			dto.setLogoutTime(sql.rs.getString("LogoutTime"));
			dto.setNormalLogout(sql.rs.getBoolean("normalExit"));;
			dto.setFirstLogin(sql.rs.getBoolean("isFirstLogin"));
			dto.setType(sql.rs.getString("deviceType"));
			
			array.add(dto);
		}
		
		sql.closeDB();
		
		return array;

	}

}
