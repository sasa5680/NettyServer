package loginModule;

public class ConnectionRecordDTO {
	

	
	//Auto increment
	
	public int getNum() {
		return Num;
	}
	
	public boolean getNormalLogout() {
		return normalLogout;
	}
	
	public void setNormalLogout(boolean logout) {
		 this.normalLogout = logout;
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getLoginTime() {
		return LoginTime;
	}
	public void setLoginTime(String time) {
		LoginTime = time;
	}
	
	public String getLogoutTime() {
		return LogoutTime;
	}

	public void setLogoutTime(String logoutTime) {
		LogoutTime = logoutTime;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}
	
	public boolean getFirstLogin() {
		return isFirstLogin;
	}

	public void setFirstLogin(boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	private int Num;
	private String ID;
	private String Type;
	private String LoginTime;
	private String LogoutTime;
	private String IP;
	private boolean isFirstLogin;
	private boolean normalLogout;



	
	
	
}
