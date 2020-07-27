package client.clientState;


import client.client.Client;
import java.util.concurrent.TimeUnit;
import io.netty.util.concurrent.ScheduledFuture;

public class LoginWaitState implements ClientState{
	
	ScheduledFuture<?> SF;
	
	@Override
	public void Action(Client C) {
		// TODO Auto-generated method stub
		
	SF = C.getChannel().eventLoop().schedule(new Runnable() {
			
		
			//Check Login Flag After 30S
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
				if(!(C.Login)) {
					
					//go to LoginFailedState
					C.moveClientState(new LoginFailState());
					
				}
			}
			
			
		}, 30, TimeUnit.SECONDS);
		
	}

	@Override
	public void ActionCancle(Client C) {
		// TODO Auto-generated method stub
		
		//cancel LoginWatcher
		SF.cancel(true);
		
		
	}



}
