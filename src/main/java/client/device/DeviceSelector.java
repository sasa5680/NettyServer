package client.device;

import com.sasa5680.CommonIndex.DeivceTypes;

import client.client.Client;
import client.device.Android.Android;
import client.device.Drone.Drone;

public class DeviceSelector {
	
	public static Device selector(String Type, Client client, String DeviceID, String IP){
		
		Device device;
		
		if(Type.equals(DeivceTypes.ANDROID.toString())) {
			
			System.out.println("New device : Android");

			device = new Android(client, DeviceID, IP);
		} else if(Type.equals(DeivceTypes.DRONE.toString())) {
			
			System.out.println("New device : Drone");
			device = new Drone(client, DeviceID, IP);
		} else {
			
			System.out.println("New device : Null");
			device = null;
		}
		
		return device;
		
	}

}
