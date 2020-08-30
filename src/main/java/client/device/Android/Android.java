package client.device.Android;

import java.util.ArrayList;

import com.sasa5680.CommonIndex.DeivceTypes;
import com.sasa5680.ProtoMessages.GeneralMSG.General;
import com.sasa5680.ProtoMessages.MessageWrapper;
import com.sasa5680.ProtoMessages.S2C.S2CLoginNotify.S2C_NewDeviceNotify;
import com.sasa5680.ProtoMessages.S2C.S2CLoginRequestReturn.S2C_DeviceList;
import com.sasa5680.ProtoMessages.S2C.S2CLoginRequestReturn.S2C_LoginRequestReturn;

import ForwardingRetry.Forwarding_Retry;
import client.client.Client;
import client.client.ClientManager;
import client.device.Device;
import client.device.Drone.Drone;
import loginModule.DeviceDTO;
import loginModule.deviceTBL_DAO;
import sql.SQL;

public class Android extends Device{
	
	public Android(Client client, String ID, String IP) {
		super(client, ID, com.sasa5680.CommonIndex.DeivceTypes.ANDROID.toString(), IP);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void NetWorkUnstable() {
		// TODO Auto-generated method stub
		//broadcast Unstable Message to Drones
		
		//send Controller Unstable to Drones
		
	}

	@Override
	public void LoginAfterAction(boolean ReLogin) {
		// TODO Auto-generated method stub
		
		//send notify to robots
		S2C_NewDeviceNotify inner = S2C_NewDeviceNotify.newBuilder().setType(this.getType())
																	.setID(getID())
																	.setType(getType())
																	.setIP(this.getIP())
																	.setReLogin(ReLogin)
																	.build();
		
		General G = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(inner);
		
		ClientManager.getClientManager().BroadCastToAll(G);
		try {
			Loading();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//error Happened
		}
		
	}
	


	@Override
	protected void Logout() {
		// TODO Auto-generated method stub
		
		//send notify to android
	}

	@Override
	public void ReLogin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ConnectionLost() {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void Loading() throws Exception {
		// TODO Auto-generated method stub
		
		//send deviceInfo to Android
		
			//ArrayList<Client> list = ClientManager.getClientManager().getClientListofType(DeivceTypes.DRONE.toString());
			
			ArrayList<DeviceDTO> list = deviceTBL_DAO.getDeviceDTO(com.sasa5680.CommonIndex.DeivceTypes.DRONE.toString());
			
			int size = list.size();
			int current = 1;
			
			for(DeviceDTO deviceDTO : list) {
				
				S2C_DeviceList deviceList = S2C_DeviceList.newBuilder().setTotal(size)
																	   .setCurrent(current)
																	   .setID(deviceDTO.getDeviceID())
																   	   .setType(deviceDTO.getDeviceType())
																   	   .setCon(deviceDTO.isLogin())
																   	   .build();
				General G = MessageWrapper.Wrap_NonRouting(deviceList); 												   
				
				 Forwarding_Retry R = new Forwarding_Retry.Builder(G, this.client.getChannel()).build();
				 R.Foward_Message();
				 current++;
			}
	}

	
}
