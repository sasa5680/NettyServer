package client.device;

import java.util.concurrent.ConcurrentHashMap;

import client.client.Client;


public abstract class DeviceManager {
	
	public ConcurrentHashMap<String, Device> DeviceMap = new ConcurrentHashMap<String, Device>();
	 
	protected String Type;
	
	public abstract void getdeviceFromDB() throws Exception;
	
	public abstract void Login(String ID, Client client);
	

}
