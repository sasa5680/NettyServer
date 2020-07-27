package messageHandler.C2S;


import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.sasa5680.ProtoMessages.GeneralMSG.General;
import com.sasa5680.ProtoMessages.S2C.S2CLoginRequestReturn.S2C_LoginRequestReturn;

import client.client.Client;
import client.device.MasterMap;
import messageHandler.MessageHandler;

import com.sasa5680.CommonIndex.*;


import com.sasa5680.ProtoMessages.C2S.C2SLoginRequest.C2S_LoginRequest;



public class C2S_LoginRequestHandler extends MessageHandler{

	public C2S_LoginRequestHandler(Client client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(General MSG) {
		// TODO Auto-generated method stub
		
		Any any = MSG.getInnerMSG(0);
		try {
			
			C2S_LoginRequest msg = any.unpack(C2S_LoginRequest.class);
			String ID 	= msg.getID();
			String Type = msg.getType();
			
		    MasterMap.getMasterMap().getManager(Type).Login(ID, clientctx);
			
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}