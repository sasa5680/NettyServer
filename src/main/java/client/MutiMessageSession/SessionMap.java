package client.MutiMessageSession;

import java.util.concurrent.ConcurrentHashMap;

import messageHandler.MultiMessageHandler.Session;
import messageHandler.MultiMessageHandler.SessionKey;

public class SessionMap {
	
	private ConcurrentHashMap<SessionKey, Session> Map = new ConcurrentHashMap<SessionKey, Session>();
	
	
	public ConcurrentHashMap<SessionKey, Session> getMap(){
		
		return this.Map;
	}
}
