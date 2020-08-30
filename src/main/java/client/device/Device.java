package client.device;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.sasa5680.CommonIndex.DeivceTypes;
import com.sasa5680.ProtoMessages.GeneralMSG.General;
import com.sasa5680.ProtoMessages.S2C.S2CLoginRequestReturn.S2C_DeviceList;
import com.sasa5680.ProtoMessages.S2C.S2CLoginRequestReturn.S2C_LoginRequestReturn;

import ForwardingRetry.Forwarding_Retry;
import client.client.Client;
import client.client.ClientManager;
import loginModule.ConnectionRecordDAO;
import loginModule.ConnectionRecordDTO;
import loginModule.deviceTBL_DAO;



public abstract class Device {
	
	protected Client client;
	
	public Device(Client client, String deviceID, String deviceType, String IP) {
		
		this.client     = client;
		this.deviceType = deviceType;
		this.deviceID   = deviceID;
		this.IP  		= IP;
	}
	
	public final String deviceType;
	private final String deviceID;
	private final String IP;
	public int LoginNum = 0;

	public String getIP() { 
		return this.IP;
	}
	
	public String getType() {
		return this.deviceType;
	}
	
	public String getID() {
		return this.deviceID;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public abstract void NetWorkUnstable();
	
	public abstract void ConnectionLost();

	protected abstract void LoginAfterAction(boolean ReLogin);
	
	public abstract void Loading() throws Exception;
	
	public abstract void ReLogin();
	
	//common Login part
	public void doLogin(boolean ReLogin) throws Exception {
		
		//common part among devices...
		
		//check whether id is in deviceTBL
		
		boolean result = deviceTBL_DAO.Check(this.deviceID);
		
		
		//notify 
		if(result) {
			
			//login done. update connection record and device statue in database
			ConnectionRecordDTO CR = new ConnectionRecordDTO();
			
			//get current time
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy / MM / dd / HH:mm:ss");
			String Time = sdf.format(cal.getTime());
			
			CR.setLoginTime(Time);
			CR.setID(this.deviceID);
			CR.setType(this.deviceType);
			
			//insert to new record to database
			LoginNum = ConnectionRecordDAO.addLogin(CR);
			
			//set Client to Manager
			this.client.setClienttoManager();
			
			boolean androidflag = ClientManager.getClientManager().CheckIfExistTypeofDevice(DeivceTypes.ANDROID.toString());
			
			//send result to client
			S2C_LoginRequestReturn msg = S2C_LoginRequestReturn.newBuilder().setResult(true)
																			.setAndroidFlag(androidflag)
																			.build();
			
			General G = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(msg);
			
			Forwarding_Retry R = new Forwarding_Retry.Builder(G, this.client.getChannel()).build();
			R.Foward_Message();
			
			
			//call abstract method 
			LoginAfterAction(ReLogin);
			
		} else {
			
			//send fail notify and do nothing, and move out client
			
			S2C_LoginRequestReturn msg = S2C_LoginRequestReturn.newBuilder().setResult(false).build();
			General G = com.sasa5680.ProtoMessages.MessageWrapper.Wrap_NonRouting(msg);
			
			Forwarding_Retry R = new Forwarding_Retry.Builder(G, this.client.getChannel()).build();
			R.Foward_Message();
			this.client.getChannel().writeAndFlush(G);
			
			this.client.removeClient();
		}

	}
	
	protected abstract void Logout();
	
	//common logout part
	public void doLogout(boolean normalLogout, String LogoutTime) throws Exception {
		
		//same logout part...
		
		//update DB statue(set login as false)
		deviceTBL_DAO.setLoginFalse(deviceID);
				
		//update Connection record
		ConnectionRecordDTO dto = new ConnectionRecordDTO();
		dto.setLogoutTime(LogoutTime);
		dto.setNormalLogout(normalLogout);
		
		ConnectionRecordDAO.addLogOut(dto, LoginNum);
		
		//call abstract method
		Logout();
		
		
	}
	
	
	
}