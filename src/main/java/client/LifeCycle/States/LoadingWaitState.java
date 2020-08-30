package client.LifeCycle.States;

import java.util.concurrent.TimeUnit;

import client.LifeCycle.CycleState;
import client.client.Client;
import io.netty.util.concurrent.ScheduledFuture;

public class LoadingWaitState implements CycleState{

	ScheduledFuture<?> SF;
	
	@Override
	public void start(Client client) {
		// TODO Auto-generated method stub
		
		System.out.println("Server : start LoadingWait state");
		//wait for Loading flag...
		SF = client.getChannel().eventLoop().schedule(new Runnable() {
			
			
			//Check Login Flag After 30S
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
				if(!(client.clientLifeCycle.LoadingDone)) {
					
					//go to LoginFailedState
							
					
				}
			}
			
			
		}, 30, TimeUnit.SECONDS);
		
	}

	@Override
	public void end(Client client) {
		// TODO Auto-generated method stub
		SF.cancel(true);
		
	}

}
