package client.client;

import java.util.Objects;

public class CustomKey {
	
	final String DeviceType;
	final String DeviceID;
	
	public String getDeviceType() {
		return DeviceType;
	}
	public String getDeviceID() {
		return DeviceID;
	}

	public CustomKey(String DeviceType, String DeviceID) {
		
		this.DeviceType = DeviceType;
		this.DeviceID 	= DeviceID;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		CustomKey other = (CustomKey) o;
		
		//!!this code have to compare "string" type. so use "equals" function
		//!! i can't believe that i have wasted more than 4 hours to solve this...
		return other.DeviceID.equals(DeviceID) && other.DeviceType.equals(DeviceType);
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(DeviceType, DeviceID);
	}
	

}
