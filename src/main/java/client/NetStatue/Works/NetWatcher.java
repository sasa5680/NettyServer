package client.NetStatue.Works;

import com.sasa5680.CommonIndex.Network;

import client.NetStatue.NetState;
import client.client.Client;

public class NetWatcher implements Runnable{
	
	public NetWatcher (Client client, NetState movestate, double ratio, int maximumcount) {
		
		this.client = client;
		this.movestate = movestate;
		this.ratio = ratio;
		this.maximumcount = maximumcount;
	}
	
	private final Client client;
	private final NetState movestate;
	private final double ratio;
	private final int maximumcount;
	
	private int fail  = 0;
	private int suc   = 0;
	private int total = 0;
	
	private double Fail_Suc_Ratio = 0.0;
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//check ratio of good Network and Bad Network...
		
		fail = client.getNetworkStatue().getMessageFailCountForRatio();
		suc  = client.getNetworkStatue().getMessageSucessCountForRatio();
		
		total = fail+suc;
		Fail_Suc_Ratio =(suc/total)*100;
		
		//if percentage of Success Message is under certain percent
		if(Network.NetUnstableRatio<ratio) {
			
			//unstable
			client.getNetworkStatue().moveState(movestate);
		} else {
			
			client.getNetworkStatue().ResetCount();

		}
		
		//if message failed N times in a row, consider as Unstable

		if(client.getNetworkStatue().getMessageFailCount()>maximumcount) {
			
			client.getNetworkStatue().moveState(movestate);
		} 
		
		
	}

}
