package client.NetStatue.Works;

import com.sasa5680.ProtoMessages.GeneralMSG.General;
import com.sasa5680.ProtoMessages.S2C.S2CPing;

import client.client.Client;

public class Ping implements Runnable{
	
	public Ping(Client client) {
		
		this.client = client;
		
	}
	
	Client client;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		S2CPing.S2C_Ping inner = S2CPing.S2C_Ping.newBuilder().build();
		General G = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(inner);
		
		client.getChannel().writeAndFlush(G);
		
	}
	
	

}
