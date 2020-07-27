package client.device.Drone;

import client.client.Client;
import client.device.Device;
import sql.SQL;



public class Drone extends Device{
	
	public Drone(Client client, String ID) {
		super(client,ID, com.sasa5680.CommonIndex.DeivceTypes.ANDROID.toString());
		// TODO Auto-generated constructor stub
		
	}
	

	@Override
	public void NetWorkUnstable() {
		// TODO Auto-generated method stub
		
		
		//notify unstable message to Android
	}
	

		



}
