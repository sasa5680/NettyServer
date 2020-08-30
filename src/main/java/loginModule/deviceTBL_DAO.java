package loginModule;


import java.util.ArrayList;

import sql.SQL;

public class deviceTBL_DAO {
	
	public static boolean Check(String ID) throws Exception {
		
		SQL sql = new SQL();
		
		int result = 0;
		
		String query = "select exists (select * from deviceTBL where userID = "+ID+") as success;";
		
	
		sql.rs = sql.st.executeQuery(query);
			
			while(sql.rs.next()) {
				
			result = sql.rs.getInt(1);
			//check whether id is in the DB. if fail,the value is 0 and if success, the value is 1
			System.out.print(result);
		}
			
		sql.closeDB();
		
		if(result != 0 )  return true;
		else 			  return false;
		

	}
	
	public static void setLoginTrue(String ID) throws Exception {
		
		SQL sql = new SQL();
		
		String query = "UPDATE deviceTBL SET Login = '1' WHERE deviceID = '"+ID+"'";
		sql.rs = sql.st.executeQuery(query);			
			
		sql.closeDB();
		
	}
	
	public static ArrayList<DeviceDTO> getDeviceDTO(String Type) throws Exception{
		
		SQL sql = new SQL();
		
		ArrayList<DeviceDTO> list = new ArrayList<DeviceDTO>();
		
		String query = "SELECT * FROM DEVICETBL WHERE ID = '"+Type+"'";
		
		sql.rs = sql.st.executeQuery(query);
		
		while(sql.rs.next()) {
			
			DeviceDTO dto = new DeviceDTO();
			
			dto.setDeviceID(sql.rs.getNString("deviceID"));
			dto.setDeviceType(sql.rs.getNString("deviceType"));
			dto.setLogin(sql.rs.getBoolean("Login"));
			
			list.add(dto);
			
		}
		
		sql.closeDB();
		
		return list;

	}
	
	
	public static ArrayList<DeviceDTO> getDeviceDTOes() throws Exception {
		
		SQL sql = new SQL();
		
		ArrayList<DeviceDTO> list = new ArrayList<DeviceDTO>();
		
		String query = "SELECT * FROM DEVICETBL";
		
		sql.rs = sql.st.executeQuery(query);
		
		while(sql.rs.next()) {
			
			DeviceDTO dto = new DeviceDTO();
			
			dto.setDeviceID(sql.rs.getNString("deviceID"));
			dto.setDeviceType(sql.rs.getNString("deviceType"));
			dto.setLogin(sql.rs.getBoolean("Login"));
			
			list.add(dto);
			
		}
		
		sql.closeDB();
		
		return list;		
	}
	
	public static void setLoginFalse(String ID) throws Exception {
		
		SQL sql = new SQL();
		
		String query = "UPDATE deviceTBL SET Login = '0' WHERE deviceID = '"+ID+"'";
		sql.rs = sql.st.executeQuery(query);		
		
		sql.closeDB();
		
	}

}
