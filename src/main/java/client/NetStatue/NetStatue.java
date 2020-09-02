package client.NetStatue;

import client.client.Client;

public class NetStatue {
	
	Client client;
	
	NetState Currentnet_State;
	
	public NetStatue(Client client) {
		
		this.client = client;
		
	}
	
    final public void moveState(NetState New_state) {
    	
    	if(Currentnet_State == null) {
    		
    		Currentnet_State = New_state;
    		Currentnet_State.start(client);
    		
    	} else {
    		
    		Currentnet_State.end(client);
    		
    		this.Currentnet_State = New_state;
        	Currentnet_State.start(client);
    	}
    	
    }
    
    public void FatalNetworkError() {
    	
    	//when fatal NetworkError Happened...
    	this.client.getDevice().ConnectionLost();
    	
    }
    

	private int MessageFailCount   = 0;
	private int MessageSucessCount = 0;
	
	private int MessageFailCountForRatio = 0;
	private int MessageSucessCountForRatio  = 0;
	
	public void ResetCount() {
		
		MessageFailCountForRatio    = 0;
		MessageSucessCountForRatio  = 0;
	}

	public void BadNetwork() {
		MessageFailCountForRatio++;
		MessageFailCount++;
	}
	
	public void GoodNetwork() {
		MessageSucessCountForRatio++;
		
		//reset fail counter
		MessageFailCount = 0;
		
		MessageSucessCount++;
		
	} 
	
	public int getMessageFailCount() {return MessageFailCount;}
	public int getMessageSucessCountForRatio() {return MessageSucessCountForRatio;}
	public int getMessageFailCountForRatio() {return MessageFailCountForRatio;}

}
