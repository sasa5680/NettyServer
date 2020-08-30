package ForwardingRetry;

import client.client.Client;

public abstract class ForwardingFailedListener {

	Client client;
	
	public ForwardingFailedListener(Client client) {
		this.client= client;
	}
	
	public abstract void isFailed( );
	
}
