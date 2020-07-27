package client.device.Android;

import com.sasa5680.ProtoMessages.GeneralMSG.General;
import com.sasa5680.ProtoMessages.S2C.S2CLoginNotify;
import com.sasa5680.ProtoMessages.S2C.S2CLoginNotify.S2C_NewDeviceNotify;
import com.sasa5680.ProtoMessages.S2C.S2CLoginRequestReturn.S2C_DeviceList;
import com.sasa5680.ProtoMessages.S2C.S2CLoginRequestReturn.S2C_LoginRequestReturn;

import ForwardingRetry.Forwarding_Retry;
import client.client.Client;
import client.client.ClientManager;
import client.device.Device;
import client.device.DeviceManager;
import client.device.MasterMap;
import client.device.Drone.Drone;
import client.device.Drone.DroneManager;
import sql.SQL;

public class AndroidManager extends DeviceManager{
	
	
	AndroidManager(){
		
		this.Type = com.sasa5680.CommonIndex.DeivceTypes.ANDROID.toString();
		MasterMap MM = MasterMap.getMasterMap();
		MM.getManagerMap().put(this.Type, this);
	}
	
	final private static class AndroidManagerClassHolder {
		
		public static final AndroidManager Amap = new AndroidManager();
	}	
		
	final public static AndroidManager getAndroidManager() {
			
		return AndroidManagerClassHolder.Amap;
	}

	@Override
	public void getdeviceFromDB() {
		// TODO Auto-generated method stub
		
		try {
		
			SQL sql = new SQL();
	
			String query = "SELECT * FROM userTBL";

			AndroidManager a = AndroidManager.getAndroidManager();
	
			sql.rs = sql.st.executeQuery(query);
			
			while(sql.rs.next()) {
				
				String ID = sql.rs.getString("userID");
				
				Android android = new Android(null, ID);
				//drone.setID(sql.rs.getString("droneID"));
				android.setConnection(false);
				a.DeviceMap.put(ID, android);
				sql.closeDB();
				
				
			}
		
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
	}

	@Override
	public void Login(String ID, Client client) {
		// TODO Auto-generated method stub
	
			if(this.DeviceMap.containsKey(ID)) {
				
				//login success
				boolean result = true;
				
				S2C_LoginRequestReturn LRR = S2C_LoginRequestReturn.newBuilder().setResult(result).build();
				General general1 = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(LRR);
				
				//set Device and client 
				Android android = (Android) this.DeviceMap.get(ID);
				client.device = android;
				android.setClient(client);
				
				Forwarding_Retry R = new Forwarding_Retry.Builder(general1, client.getChannel()).build();
				R.Foward_Message();
				
				//send robot data to Android Client
				
				DroneManager DM = DroneManager.getDroneManager();
				
				//set total message size 
				float total = DM.DeviceMap.size();
				float current = 0;
				
				for(String key : DM.DeviceMap.keySet()) {
					
					current++;
					
					Drone drone = (Drone) DM.DeviceMap.get(key);
					
					S2C_DeviceList DL = S2C_DeviceList.newBuilder()
													   .setCon(drone.isConnection())
													   .setID(drone.getID())
													   .setType(drone.deviceType)
													   .setTotal(total)
													   .setCurrent(current)
													   .build();
					
					General general2 = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(DL);
					
					
					client.getChannel().writeAndFlush(general2);
					
					//send notify to Drones
					
					S2C_NewDeviceNotify NDN = S2C_NewDeviceNotify.newBuilder().setID(ID)
																			  .setType(android.deviceType)
																			  .setReLogin(false)
																			  .build();
					
					General general3 = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(NDN);
					
					ClientManager.getClientManager().BroadCastToType(general3, this.Type);
					
				}
				
				
				
			} else {
				
				boolean result = false;
				
				S2C_LoginRequestReturn LRR = S2C_LoginRequestReturn.newBuilder().setResult(result).build();
				General general = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(LRR);
				
				Forwarding_Retry R = new Forwarding_Retry.Builder(general, client.getChannel()).build();
				R.Foward_Message();
			}
			

		
	}



}
