package messageHandler.MultiMessageHandler;

import client.client.Client;

public final class Session {
	
	public int CurrentMessageCount;
	public int TotalMessageCount;
	
	private final String MessageType;
	private final String ClientID;
	private final Client client;
	
	public Session(String MessageType, String ClientID, Client client) {
		
		this.MessageType = MessageType;
		this.ClientID    = ClientID;
		this.client		 = client;
		
		
	}
	
	public String getMessageType() {
		
		return this.MessageType;
	}

	public String getClientID() {
		
		return this.ClientID;
	}
	

	public void startSession(TimeStamp timeStamp) {
		
		//add Session to Map
		client.getSessionMap().getMap().put(new SessionKey(MessageType, ClientID), this);
		//SessionMap.getSessionMap().Map.put(new SessionKey(MessageType, ClientID), this);
		this.TotalMessageCount = timeStamp.getTotal();
		CurrentMessageCount = 1;
	}
	

	public boolean CheckNewMessageOrder(TimeStamp timestamp) {
		
		CurrentMessageCount++;
		
		if(timestamp.getCurrent() != CurrentMessageCount) {
			
			//error
			return true;
		} else {
			
			return false;
		}

	}
	
	public boolean completeChecker() {
		
		if(TotalMessageCount == CurrentMessageCount) {
			
			return true;
		} else {
			
			return false;

		}
	}
	
	public void endSession() {
		
		client.getSessionMap().getMap().remove(new SessionKey(MessageType, ClientID));
		//SessionMap.getSessionMap().Map.remove(new SessionKey(MessageType, ClientID));
		
	}
	
}
