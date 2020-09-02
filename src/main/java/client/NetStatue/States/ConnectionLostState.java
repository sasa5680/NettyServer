package client.NetStatue.States;

import java.util.concurrent.TimeUnit;

import client.NetStatue.NetState;
import client.client.Client;

public class ConnectionLostState implements NetState{

	@Override
	public void start(Client client) {
		// TODO Auto-generated method stub
		
		System.out.println("Server - start ConnectionLostState");
		
		//start fast Pingpong
		
		
		//call device's notify
		//client.device.NetWorkUnstable();
		
		
	}

	@Override
	public void end(Client client) {
		// TODO Auto-generated method stub
		
	}
	



}
