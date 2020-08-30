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
	
	public Client(Channel channel) {
		
		//init Client
		this.channel = channel;
		this.networkStatue.moveState(new NormalState());
		this.clientLifeCycle.moveState(new LoginWaitState());
	}
	
	private Channel channel = null;
	
	public Channel getChannel() {
		return channel;
	}
	
	public void setChannel(Channel channel) {
		
		this.channel = channel;
	}

	public Device device;
	
	public final NetStatue networkStatue = new NetStatue(this);
	public final ClientLifeCycle clientLifeCycle = new ClientLifeCycle(this);
	public final StoredMessages storedMessages = new StoredMessages(this);
	public final SessionMap sessionMap = new SessionMap();
	

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
