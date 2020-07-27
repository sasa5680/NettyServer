package client.clientState;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import client.client.Client;

public class ActivateState implements ClientState{
	
	ScheduledFuture<?> PingPong;
	ScheduledFuture<?> PongChecker;
	
	@Override
	public void Action(Client C) {
		// TODO Auto-generated method stub
		
		//start Ping Pong Schedule
		PingPong = C.getChannel().eventLoop().schedule(new Runnable() {
			
			@Override
			public void run() {
				
				//MessageForm MSG = S2C_Ping_Forwarding.getMessage();
				//C.getChannel().writeAndFlush(MSG);
			}
					
					
		}, com.sasa5680.CommonIndex.Network.PingPong_Interval, TimeUnit.SECONDS);
		
		PongChecker = C.getChannel().eventLoop().schedule(new Runnable() {
			
			@Override
			public void run() {
				
				if(C.Connection_Check) C.networkStatue.BadNetwork();
			}
		}, com.sasa5680.CommonIndex.Network.PingPong_Interval, TimeUnit.SECONDS);
		
	}

	@Override
	public void ActionCancle(Client C) {
		// TODO Auto-generated method stub
		PingPong.cancel(false);
		PongChecker.cancel(false);
		//remove Schedule
	}


}
