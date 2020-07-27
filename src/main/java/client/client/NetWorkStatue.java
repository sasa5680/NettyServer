package client.client;

import client.clientState.ConnectionUnstableState;

public class NetWorkStatue {
	
	Client client;
	
	public NetWorkStatue(Client client) {this.client = client;}
	
	//private int MessageSuceessCount = 0;
	private int MessageFailCount	   = 0;
	
	//move Client state if Network Failed N times in a row
	public void BadNetwork() {
		
		MessageFailCount++;
		
		if(MessageFailCount >= com.sasa5680.CommonIndex.Network.MAXIMUM_NetworkUnstableCount) {
			
			client.moveClientState(new ConnectionUnstableState());
		}
	}

	
	public void GoodNetwork() {
		
		MessageFailCount = 0;
	}
}
