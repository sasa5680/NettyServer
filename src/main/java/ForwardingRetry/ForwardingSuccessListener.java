package ForwardingRetry;

import client.client.Client;

public abstract class ForwardingSuccessListener {
	
	Client client;
	
	public ForwardingSuccessListener(Client client) {
		
		this.client = client;
	}
	
	public abstract void Succes();
}
