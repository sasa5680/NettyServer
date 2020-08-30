package messageHandler.MultiMessageHandler;

import java.util.concurrent.ConcurrentHashMap;



public class SessionMap {
	
	ConcurrentHashMap<SessionKey, Session> Map = new ConcurrentHashMap<SessionKey, Session>();
	
	//singleton pattern

	final private static class SessionMapHolder {
		
		public static final SessionMap sessionMap = new SessionMap();
	}	
	
	final public static SessionMap getSessionMap() {
		
		return SessionMapHolder.sessionMap;
	}
	
	public Session serchSession() {
		
		Session session = null;
		
		return session;
	}
}
