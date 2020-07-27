package messageHandler;


import com.sasa5680.ProtoMessages.GeneralMSG.General;

import client.client.Client;

public abstract class MessageHandler {
	
	protected Client clientctx;
	
	public MessageHandler(Client client) {this.clientctx = client;}
	
	public abstract void handle(General MSG);
		
	

}
