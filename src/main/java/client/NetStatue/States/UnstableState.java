package client.NetStatue.States;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import client.NetStatue.NetState;
import client.NetStatue.Works.NetWatcher;
import client.NetStatue.Works.Ping;
import client.client.Client;

public class UnstableState implements NetState{

	ScheduledFuture<?> fastPingPong;
	ScheduledFuture<?> Watcher;
	
	@Override
	public void start(Client client) {
		// TODO Auto-generated method stub
		
		System.out.println("Server : start UnstableState state");
		//notify Unstable 
		client.getDevice().NetWorkUnstable();
		
		//start fast PingPong
		
		//start watcher 
		
		fastPingPong = 	client.getChannel().eventLoop().schedule(new Ping(client), 3, TimeUnit.SECONDS);
		Runnable run = new NetWatcher(client, new UnstableState() , 80, 5);
		Watcher = client.getChannel().eventLoop().schedule(run, 20, TimeUnit.SECONDS);
		
	}

	@Override
	public void end(Client client) {
		// TODO Auto-generated method stub
		
		fastPingPong.cancel(true);
		Watcher.cancel(true);
	}
	

	
	
	
	

}
