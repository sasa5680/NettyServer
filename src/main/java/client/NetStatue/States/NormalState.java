package client.NetStatue.States;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.sasa5680.CommonIndex.Network;
import com.sasa5680.ProtoMessages.GeneralMSG.General;
import com.sasa5680.ProtoMessages.S2C.S2CPing.S2C_Ping;


import client.NetStatue.NetState;
import client.client.Client;

public class NormalState implements NetState{

	private final int WatcherInterval = 5;
	
	ScheduledFuture<?> PingPong;
	ScheduledFuture<?> Watcher;
	
	@Override
	public void start(Client client) {
		// TODO Auto-generated method stub
		
		System.out.println("Server : start Normal state");
		//start Net Watcher
		
		//check ratio of Good and BadNetwork
		
		//start ping pong //start Ping Pong Schedule	
		PingPong = client.getChannel().eventLoop().scheduleAtFixedRate
				(Pingpong(client), 1, WatcherInterval, TimeUnit.SECONDS);
			
		
		
		//Watcher = client.getChannel().eventLoop().schedule(this.Watcher(client), WatcherInterval, TimeUnit.SECONDS);
	}

	@Override
	public void end(Client client) {
		// TODO Auto-generated method stub
		
		System.out.println("end NormalState");
		try {
			PingPong.cancel(true);
			//Watcher.cancel(true);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Runnable Pingpong(Client client) {

		Runnable work = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				S2C_Ping inner = S2C_Ping.newBuilder().build();
				
				General G = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(inner);
				client.getChannel().writeAndFlush(G);
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
				fail = client.getNetworkStatue().getMessageFailCountForRatio();
				suc  = client.getNetworkStatue().getMessageSucessCountForRatio();
				
				total = fail+suc;
				Fail_Suc_Ratio =(suc/total)*100;
				
				//if percentage of Success Message is under certain percent
				if(Network.NetUnstableRatio<Fail_Suc_Ratio) {
					
					//unstable
					client.getNetworkStatue().moveState(new UnstableState());
				} else {
					
					client.getNetworkStatue().ResetCount();
	
				}
				
				//if message failed N times in a row, consider as Unstable

				if(client.getNetworkStatue().getMessageFailCount()>Network.MAXIMUM_NetworkUnstableCount) {
					
					client.getNetworkStatue().moveState(new UnstableState());
				} 
					
			}
		};
		return work;
		
	}
		
}
