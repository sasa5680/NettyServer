package client.client;

import java.sql.SQLException;
import java.util.ArrayList;

import com.sasa5680.ProtoMessages.GeneralMSG.General;

import client.LifeCycle.ClientLifeCycle;
import client.LifeCycle.States.LoginWaitState;
import client.MutiMessageSession.SessionMap;
import client.NetStatue.NetStatue;
import client.NetStatue.States.NormalState;
import client.StoredMessages.StoredMessages;
import client.device.Device;
import io.netty.channel.Channel;


public class Client {
	
	private NetStatue networkStatue;
	private ClientLifeCycle clientLifeCycle;
	private StoredMessages storedMessages;
	private SessionMap sessionMap;
	
	
	public NetStatue getNetworkStatue() {
		return networkStatue;
	}

	public ClientLifeCycle getClientLifeCycle() {
		return clientLifeCycle;
	}

	public StoredMessages getStoredMessages() {
		return storedMessages;
	}

	public SessionMap getSessionMap() {
		return sessionMap;
	}

	public Client(Channel channel) {
		
		//init Client
		this.channel = channel;

	}
	
	private Channel channel = null;
	private Device device;
	
	public Channel getChannel() {
		return channel;
	}
	
	public void setChannel(Channel channel) {
		
		this.channel = channel;
	}
	
	public void setDevice(Device device) {
		
		this.device = device;
	}

	public Device getDevice() {
		
		return this.device;
	}
	

	public void InitClient() {
		
		networkStatue = new NetStatue(this);
		clientLifeCycle = new ClientLifeCycle(this);
		storedMessages = new StoredMessages(this);
		SessionMap sessionMap = new SessionMap();
		
		this.networkStatue.moveState(new NormalState());
		this.clientLifeCycle.moveState(new LoginWaitState());
	}
	
	

	public void setClienttoManager() throws Exception, ClassNotFoundException, SQLException {

		//set Map to Client
		ClientManager CM = ClientManager.getClientManager();
		CM.setClient_to_Map(new CustomKey(this.device.deviceType, this.device.getID()), this);
		
		//get SQL Connection
		//sql = new SQL();
	}
	
	public void removeClient() {
		
		//remove client from map
		ClientManager.getClientManager().endClient(this);
		
		//close Channel
		this.channel.close();
	}
	

	

}
