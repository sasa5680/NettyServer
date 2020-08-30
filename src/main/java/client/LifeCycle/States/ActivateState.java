package client.LifeCycle.States;

import client.LifeCycle.CycleState;
import client.client.Client;

public class ActivateState implements CycleState{

	@Override
	public void start(Client client) {
		// TODO Auto-generated method stub
		
		System.out.println("Server : start Activate state");
		//client.sendSavedMessages();
	}

	@Override
	public void end(Client client) {
		// TODO Auto-generated method stub
		
	}
	

}
