package messageHandler.MultiMessageHandler;

import java.util.Objects;

import client.client.CustomKey;

public class SessionKey {
	
	final String DeviceID;
	final String MessageType;
	
	public String getMessageType() {
		return MessageType;
	}
	public String getDeviceID() {
		return DeviceID;
	}

	public SessionKey(String MessageType, String DeviceID) {
		
		this.MessageType = MessageType;
		this.DeviceID 	= DeviceID;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		SessionKey other = (SessionKey) o;
		
		//!!this code compares "string" type. so use "equals" function
		//!! i can't believe that i have wasted more than 4 hours to solve this...
		return other.DeviceID.equals(DeviceID) && other.MessageType.equals(MessageType);
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(MessageType, DeviceID);
	}
	

}
