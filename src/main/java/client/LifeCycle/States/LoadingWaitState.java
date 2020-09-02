package client.LifeCycle.States;

import java.util.concurrent.TimeUnit;

import com.sasa5680.ProtoMessages.S2C.S2CDroneLoadingMSG;

import client.LifeCycle.CycleState;
import client.client.Client;
import io.netty.util.concurrent.ScheduledFuture;

public class LoadingWaitState implements CycleState{

	ScheduledFuture<?> LoadingChecker;
	
	@Override
	public void start(Client client) {
		// TODO Auto-generated method stub
	
		try {
			client.getDevice().Loading();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Server : start LoadingWait state");
		//wait for Loading flag...
		LoadingChecker = client.getChannel().eventLoop().schedule(new Runnable() {
			
			
			//Check Login Flag After 30S
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
				if(!(client.getClientLifeCycle().LoadingDone)) {
					
					//go to LoginFailedState
							
					
				}
			}
			
			
		}, 30, TimeUnit.SECONDS);
		
	}

	@Override
	public void end(Client client) {
		// TODO Auto-generated method stub
		LoadingChecker.cancel(true);
		
	}

}
