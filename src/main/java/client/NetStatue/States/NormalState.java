package client.NetStatue.States;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.sasa5680.CommonIndex.Network;

import client.NetStatue.NetState;
import client.client.Client;

public class NormalState implements NetState{

	private final int WatcherInterval = 10;
	
	ScheduledFuture<?> PingPong;
	ScheduledFuture<?> Watcher;
	
	@Override
	public void start(Client client) {
		// TODO Auto-generated method stub
		
		System.out.println("Server : start Normal state");
		//start Net Watcher
		
		//check ratio of Good and BadNetwork
		
		//start ping pong //start Ping Pong Schedule
		PingPong = client.getChannel().eventLoop().schedule(this.Pingpong()
				, com.sasa5680.CommonIndex.Network.PingPong_Interval, TimeUnit.SECONDS);
		
		Watcher = client.getChannel().eventLoop().schedule(this.Watcher(client), 
				WatcherInterval, TimeUnit.SECONDS);
	}

	@Override
	public void end(Client client) {
		// TODO Auto-generated method stub
		
		PingPong.cancel(true);
		Watcher.cancel(true);
		
	}
	
	private Runnable Pingpong() {

		Runnable work = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				//MessageForm MSG = S2C_Ping_Forwarding.getMessage();
				//C.getChannel().writeAndFlush(MSG);
			}
		};
		return work;
	}
	
	private Runnable Watcher(Client client) {
		
		Runnable work = new Runnable() {

			int fail  = 0;
			int suc   = 0;
			int total = 0;
			
			double Fail_Suc_Ratio = 0.0;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				//check ratio of good Network and Bad Network...
				fail = client.networkStatue.getMessageFailCountForRatio();
				suc  = client.networkStatue.getMessageSucessCountForRatio();
				
				total = fail+suc;
				Fail_Suc_Ratio =(suc/total)*100;
				
				//if percentage of Success Message is under certain percent
				if(Network.NetUnstableRatio<Fail_Suc_Ratio) {
					
					//unstable
					client.networkStatue.moveState(new UnstableState());
				} else {
					
					client.networkStatue.ResetCount();
	
				}
				
				//if message failed N times in a row, consider as Unstable

				if(client.networkStatue.getMessageFailCount()>Network.MAXIMUM_NetworkUnstableCount) {
					
					client.networkStatue.moveState(new UnstableState());
				} 
					
			}
		};
		return work;
		
	}
		
}
