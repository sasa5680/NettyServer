package loginModule;

public class DeviceDTO {
	
	private String deviceID;
	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public boolean isLogin() {
		return Login;
	}

	public void setLogin(boolean login) {
		Login = login;
	}

	private String deviceType;
	
	private boolean Login;

}
