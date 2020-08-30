package client.LifeCycle.States;


import client.LifeCycle.CycleState;
import client.client.Client;


import java.util.concurrent.TimeUnit;
import io.netty.util.concurrent.ScheduledFuture;

public class LoginWaitState implements CycleState{

	ScheduledFuture<?> SF;
	
	@Override
	public void start(Client client) {
		// TODO Auto-generated method stub
		System.out.println("Server : start LoginWait state");
		
		SF = client.getChannel().eventLoop().schedule(new Runnable() {
			
			
			//Check Login Flag After 30S
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
				if(!(client.clientLifeCycle.isLoginflag())) {
					
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
