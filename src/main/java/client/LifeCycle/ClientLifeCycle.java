package client.LifeCycle;

import client.client.Client;

public class ClientLifeCycle {
	
	Client client;
	CycleState Current_State;
	
	private boolean Loginflag;
	public  boolean LoadingDone = false;
	
	public boolean isLoginflag() {
		return Loginflag;
	}

	public void setLoginflag(boolean loginflag) {
		Loginflag = loginflag;
	}

	public ClientLifeCycle(Client client) {
		
		this.client = client;
	}
	
	 final public void moveState(CycleState New_state) {
	    
		 if(Current_State == null) {
			 
			 Current_State = New_state;
			 Current_State.start(client);
			 return;
		 }
		 
		 if(Current_State.getClass().getSimpleName().equals(New_state.getClass().getSimpleName())) {
			 
			 //if new State is same with current state
			 
			 //do nothing
		 } else {
		 
			Current_State.end(client);
	    	
	    	this.Current_State = New_state;
	    	
	    	Current_State.start(client);
		 	}
	    
	 }
}
