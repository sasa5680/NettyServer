package client.device.Android;

import com.sasa5680.ProtoMessages.GeneralMSG.General;
import com.sasa5680.ProtoMessages.S2C.S2CLoginRequestReturn.S2C_DeviceList;
import com.sasa5680.ProtoMessages.S2C.S2CLoginRequestReturn.S2C_LoginRequestReturn;

import ForwardingRetry.Forwarding_Retry;
import client.client.Client;
import client.device.Device;
import sql.SQL;

public class Android extends Device{
	
	public Android(Client client, String ID) {
		super(client, ID, com.sasa5680.CommonIndex.DeivceTypes.ANDROID.toString());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void NetWorkUnstable() {
		// TODO Auto-generated method stub
		//broadcast Unstable Message to Drones
		
		//send Controller Unstable to Drones
		
	}

	
}
