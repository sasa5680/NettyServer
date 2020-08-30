package client.client;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.sasa5680.ProtoMessages.GeneralMSG.General;

import ForwardingRetry.ForwardingFailedListener;
import ForwardingRetry.ForwardingSuccessListener;
import ForwardingRetry.Forwarding_Retry;




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
	
	public void endClient(CustomKey key) {
		
		if(this.clientMap.containsKey(key)) {
			
			this.clientMap.remove(key);
			
		} else {
			
			System.out.println("already gone");
		}
	}
	
	public void endClient(Client client)  {
		
		if(this.clientMap.containsValue(client)) {
			
			this.clientMap.remove(new CustomKey(client.device.deviceType, client.device.getID()));
			
		} else {
			
			System.out.println("already gone");
		}
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
	
	public synchronized void BroadCastToAll(General MSG) {
		
		for(CustomKey key : clientMap.keySet()) {
		
			Client client = clientMap.get(key);
			client.getChannel().writeAndFlush(MSG);

		}
	}
	
	public synchronized void BroadCastToAllWithRetry(General MSG, boolean saveOption) {
		
		for(CustomKey key : clientMap.keySet()) {
			
			Client client = clientMap.get(key);
			Forwarding_Retry R = new Forwarding_Retry.Builder(MSG, client.getChannel()).build();
			
			if(saveOption) {
				
				R.ForwardingFailedListener(new ForwardingFailedListener(client) {

					@Override
					public void isFailed() {
						// TODO Auto-generated method stub
						client.storedMessages.addMessage(MSG);
						
						}
				});
				
				R.Foward_Message();
			} else {
				
				R.Foward_Message();
			}
			
			
		}
		
	}
	
	public synchronized void BroadCastToTypeWithRetry(General MSG, String Type, boolean saveOption) {
		
		for(CustomKey key : clientMap.keySet()) {
			
			if(key.DeviceType.equals(Type)) {
				
				Client client = clientMap.get(key);
				Forwarding_Retry R = new Forwarding_Retry.Builder(MSG, client.getChannel()).build();
				
				if(saveOption) {
					
					R.ForwardingFailedListener(new ForwardingFailedListener(client) {

						@Override
						public void isFailed() {
							// TODO Auto-generated method stub
							client.storedMessages.addMessage(MSG);
							
							}
					});
				
				
				} else {
					
					R.Foward_Message();
				}
			}
			
		}
		
		
	}
	
	public synchronized void BroadCastToType(com.google.protobuf.Message MSG, String Type) {
		
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
