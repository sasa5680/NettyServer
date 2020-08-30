package client.device;

import java.util.concurrent.ConcurrentHashMap;

public class MasterMap {
	
	private MasterMap() {}
	
	private ConcurrentHashMap<String, DeviceManager> ManagerMap = new ConcurrentHashMap<String, DeviceManager>();

	public ConcurrentHashMap<String, DeviceManager> getManagerMap() {
		return ManagerMap;
	}
	
	public void put_Manager(String dev_Type, DeviceManager deviceManager) {
		
		ManagerMap.put(dev_Type, deviceManager);
		//do error handling
	}
	
	public DeviceManager getManager(String Type) throws NullPointerException{
		//get Client's device Id as a key
		
		DeviceManager managerMap = ManagerMap.get(Type);
		
		return managerMap;
	}
	
	public void InitDeviceManager() throws Exception {
		
		for(String key : this.ManagerMap.keySet()) {
			
			DeviceManager DM = this.ManagerMap.get(key);
			DM.getdeviceFromDB();
			
		}
		
	}

	//apply singleton pattern
	final private static class MasterMapHolder {
		
		public static final MasterMap masterMap = new MasterMap();
	}	
	
	final public static MasterMap getMasterMap() {
		
		return MasterMapHolder.masterMap;
	}

}
