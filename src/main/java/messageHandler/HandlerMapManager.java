package messageHandler;

import java.util.HashMap;

import client.client.Client;
import io.netty.channel.Channel;

public class HandlerMapManager {

	private  HashMap<String, MessageHandler> HandlerMap = new HashMap<String, MessageHandler>();
	
	//get copy of the MessageMap
	public HashMap<String, MessageHandler> getHandlerMap(Client client){
		
		HashMap<String, MessageHandler> HandlerMap = new HashMap<String, MessageHandler>();
		
		HandlerMap.putAll(this.HandlerMap);	
		
	
		HandlerMap.forEach((String, MessageHandler)->MessageHandler.clientctx = client);
		
		return HandlerMap;
	}
	
	
	public void addMessage(com.google.protobuf.Message MSG, MessageHandler MH) {
		
		String Type = MSG.getClass().getName();
		
		if(HandlerMap.containsKey(Type)) {
			
			System.out.println("Message Type already used");
		} else {
			
			HandlerMap.put(Type, MH);
		}
		
		
		//check
	}
	
	
	
	//apply Singleton pattern
	final private static class HandlerMapHolder {
		
		public static final HandlerMapManager handlerListMap = new HandlerMapManager();
	}	
	
	final public static HandlerMapManager getHandlertMap() {
		
		return HandlerMapHolder.handlerListMap;
	}
	
}
