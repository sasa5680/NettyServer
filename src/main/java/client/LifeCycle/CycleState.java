package client.LifeCycle;

import client.client.Client;

public interface CycleState {
	
	public void start(Client client);
	
	public void end(Client client);
	
	

}
