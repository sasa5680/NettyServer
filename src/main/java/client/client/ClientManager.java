package client.client;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;




public class ClientManager {
	
	private ClientManager() {}
	
	private ConcurrentHashMap<CustomKey, Client> clientMap = new ConcurrentHashMap<CustomKey, Client>();

	public ConcurrentHashMap<CustomKey, Client> getClientMap() {
		return clientMap;
	}
	
	public void setClient_to_Map(CustomKey Key, Client client) throws Exception {
		
		if(clientMap.containsKey(Key)) {
			
			System.out.println("Already Logined");
			
			throw new Exception();
			
		} else {
			
			clientMap.put(Key, client);
		}
	
	}
	
	public Client getClient(CustomKey key) throws NullPointerException{
		//get Client's device Id as a key
		
		Client client;
		client = clientMap.get(key);
			
		return client;
			
		
		
	}
	
	public void endClient(CustomKey key) throws NullPointerException {
		
		this.clientMap.remove(key);
		
	}
	
	public ArrayList<Client> getClientListofType(String Type) {
		
		ArrayList<Client> list = new ArrayList<Client>();
		
		for(CustomKey key : clientMap.keySet()) {
			
			if(key.DeviceType.equals(Type)) {
				
				list.add(clientMap.get(key));
			}
			
		}
		
		return list;
	}
	
	public void BroadCastToAll(com.google.protobuf.Message MSG) {
		
		for(CustomKey key : clientMap.keySet()) {
			
			Client client = clientMap.get(key);
			client.getChannel().writeAndFlush(MSG);
			
		}
		
	}
	
	public void BroadCastToType(com.google.protobuf.Message MSG, String Type) {
		
		for(CustomKey key : clientMap.keySet()) {
			
			if(key.DeviceType.equals(Type)) {
				
				Client client = clientMap.get(key);
				client.getChannel().writeAndFlush(MSG);
			}
			
		}
		
	}
	
	public boolean CheckIfExistTypeofDevice(String Type) {
		
		boolean result = false;
		
		for(CustomKey key : clientMap.keySet()) {
			
			if(key.DeviceType.equals(Type)) {
				
				result = true;
				break;
			}
			
		}
	
		
		return result;
		
	}
	
	
	

	//apply singleton pattern
	final private static class ClientManagerHolder {
		
		public static final ClientManager clientManager = new ClientManager();
	}	
	
	final public static ClientManager getClientManager() {
		
		return ClientManagerHolder.clientManager;
	}



}
