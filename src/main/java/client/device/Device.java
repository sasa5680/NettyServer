package client.device;


import com.sasa5680.ProtoMessages.S2C.S2CLoginRequestReturn.S2C_DeviceList;

import client.client.Client;



public abstract class Device {
	
	protected Client client;
	
	public Device(Client client, String ID, String deviceType) {
		this.client = client;
		this.deviceType = deviceType;
		this.ID = ID;
	}
	
	public final String deviceType;


	private String ID;
	private  boolean connection = false;
	
	
	public String getID() {
		return ID;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public boolean isConnection() {
		return connection;
	}

	public void setConnection(boolean connection) {
		this.connection = connection;
	}	
	
	
	public abstract void NetWorkUnstable();
	
	
	
}