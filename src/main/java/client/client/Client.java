package client.client;

import java.sql.SQLException;

import client.clientState.ClientState;
import client.device.Device;
import io.netty.channel.Channel;
import sql.SQL;

public class Client {
	
	public Client(Channel channel) {
		
		this.channel = channel;
	}
	
	private Channel channel = null;
	
	public Channel getChannel() {
		return channel;
	}
	
	public void setChannel(Channel channel) {
		
		this.channel = channel;
	}

	 public String getClientType() {
		return ClientType;
	}

	public void setClientType(String clientType) {
		ClientType = clientType;
	}

	public String getClientID() {
		return ClientID;
	}

	public void setClientID(String clientID) {
		ClientID = clientID;
	}

	String ClientType;
	String ClientID;
	
	public Device device;
	
	public NetWorkStatue networkStatue = new NetWorkStatue(this);
	
	public boolean Connection_Check = true;
	public boolean Login			= false;
		
	//SQL sql = null;
	
	public void setClient(String Type, String ID) throws Exception, ClassNotFoundException, SQLException {
		
		this.ClientType = Type;
		this.ClientID	= ID;
		
		//set Map to Client
		ClientManager CM = ClientManager.getClientManager();
		CM.setClient_to_Map(new CustomKey(Type, ID), this);
		
		//get SQL Connection
		//sql = new SQL();
	}
	
	public void removeClient() {
		
		
		
	}
	
	private ClientState CS;
	
	public void moveClientState(ClientState CS) {
		
		//cancel current work
		this.CS.ActionCancle(this);
		
		//set new State
		this.CS = CS;
		
		//call new Action
		this.CS.Action(this);
		
		
	}
	
	
			
	

}
