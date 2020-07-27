package client.device.Drone;

import java.util.concurrent.ConcurrentHashMap;

import client.client.Client;
import client.client.ClientManager;
import client.client.CustomKey;
import client.device.DeviceManager;
import client.device.MasterMap;
import sql.SQL;

import com.sasa5680.CommonIndex.DeivceTypes;
import com.sasa5680.ProtoMessages.GeneralMSG.General;
import com.sasa5680.ProtoMessages.S2C.S2CLoginNotify.S2C_NewDeviceNotify;
import com.sasa5680.ProtoMessages.S2C.S2CLoginRequestReturn.S2C_LoginRequestReturn;

import ForwardingRetry.Forwarding_Retry;

public class DroneManager extends DeviceManager{
	
	DroneManager(){
		
		this.Type = com.sasa5680.CommonIndex.DeivceTypes.DRONE.toString();
		MasterMap MM = MasterMap.getMasterMap();
		MM.getManagerMap().put(this.Type, this);
		
	}
	
	
	@Override
	public void getdeviceFromDB() throws Exception {
		// TODO Auto-generated method stub
		
		SQL sql = new SQL();
		
		String query = "SELECT * FROM droneTBL";
		
		sql.rs = sql.st.executeQuery(query);
		

		while(sql.rs.next()) {
			
			String DroneID = sql.rs.getString("droneID");
			String UserID  = sql.rs.getString("userID");
			
			Drone drone = new Drone(null, DroneID);
			//create drone object in drone table
				
			
			drone.setConnection(false);
			
			
			this.DeviceMap.put(DroneID, drone);
		
		}

		sql.closeDB();
	 
	}
	
	@Override
	public void Login(String ID, Client client) {
		
		if(this.DeviceMap.containsKey(ID)) {
			
			//if success
			
			//set device to client
			client.device = this.DeviceMap.get(ID);
			
			boolean result 		= true;
			boolean Androidflag = false; 
			Androidflag = ClientManager.getClientManager()
									   .CheckIfExistTypeofDevice(DeivceTypes.ANDROID.toString());
			
			S2C_LoginRequestReturn LRR = S2C_LoginRequestReturn.newBuilder().setResult(result)
																			.setAndroidFlag(Androidflag)
																			.build();
			General general1 = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(LRR);
			
			Forwarding_Retry R = new Forwarding_Retry.Builder(general1, client.getChannel()).build();
			R.Foward_Message();
			
			//send notify to Android
			S2C_NewDeviceNotify NDN = S2C_NewDeviceNotify.newBuilder().setType(this.Type)
																	  .setID(ID)
																	  .build();
			
			General general2 = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(NDN);
			
			ClientManager.getClientManager().BroadCastToType(general2, DeivceTypes.ANDROID.toString());
			
		
		} else {
			
			//if fail
			boolean result = false;
			
			S2C_LoginRequestReturn LRR = S2C_LoginRequestReturn.newBuilder().setResult(result).build();
			
			General general = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(LRR);
			
			Forwarding_Retry R = new Forwarding_Retry.Builder(general, client.getChannel()).build();
			R.Foward_Message();
			
		}
		
		
		
	}
	
	final private static class DroneManagerClassHolder {
		
		public static final DroneManager dmap = new DroneManager();
	}	
		
	final public static DroneManager getDroneManager() {
			
		return DroneManagerClassHolder.dmap;
	}

}
