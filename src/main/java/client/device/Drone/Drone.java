package client.device.Drone;

import com.sasa5680.CommonIndex.ConnectionState;
import com.sasa5680.CommonIndex.DeivceTypes;
import com.sasa5680.ProtoMessages.GeneralMSG.General;
import com.sasa5680.ProtoMessages.S2C.S2CDeviceUnstable.S2C_NotifyNetStatue;
import com.sasa5680.ProtoMessages.S2C.S2CDroneLoadingMSG.S2C_DroneLoadingMSG;
import com.sasa5680.ProtoMessages.S2C.S2CLoginNotify.S2C_NewDeviceNotify;

import ForwardingRetry.ForwardingFailedListener;
import ForwardingRetry.Forwarding_Retry;
import client.client.Client;
import client.client.ClientManager;
import client.device.Device;
import sql.SQL;



public class Drone extends Device{
	
	public Drone(Client client, String ID, String IP) {
		super(client,ID, com.sasa5680.CommonIndex.DeivceTypes.ANDROID.toString(), IP);
		// TODO Auto-generated constructor stub
		
	}
	

	@Override
	public void NetWorkUnstable() {
		// TODO Auto-generated method stub
		
		S2C_NotifyNetStatue msg = S2C_NotifyNetStatue.newBuilder().setCode(ConnectionState.Connection_Unstatble.toString())
																  .setID(this.getID())
																  .setType(this.deviceType)
																  .build();
		
		General G = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(msg);
		
		ClientManager.getClientManager().BroadCastToTypeWithRetry(G, DeivceTypes.ANDROID.toString(), true);
		
		//notify unstable message to Android
	}


	@Override
	public void LoginAfterAction(boolean ReLogin) {
		// TODO Auto-generated method stub
		
			//notify login to android
		S2C_NewDeviceNotify msg = S2C_NewDeviceNotify.newBuilder().setID(this.getID())
																  .setType(this.getType())
																  .setReLogin(ReLogin)
																  .build();
		
		General G = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(msg);
		
		//ClientManager.getClientManager().BroadCastToType(G
	}

	@Override
	protected void Logout() {
		// TODO Auto-generated method stub
		
		//notify logout to android
		
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
		
		//get android flag
		boolean Androidflag = ClientManager.getClientManager().CheckIfExistTypeofDevice(DeivceTypes.ANDROID.toString());
		
		S2C_DroneLoadingMSG msg = S2C_DroneLoadingMSG.newBuilder().setAndroidFlag(Androidflag).build();
		General G = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(msg);
		
		//send Loading Done Message
		Forwarding_Retry R = new Forwarding_Retry.Builder(G, this.client.getChannel()).Maximum_Count(10).build();
		R.Foward_Message();
	}
	

		



}
