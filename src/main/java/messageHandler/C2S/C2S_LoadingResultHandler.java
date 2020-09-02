package messageHandler.C2S;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.sasa5680.ProtoMessages.GeneralMSG.General;
import com.sasa5680.ProtoMessages.C2S.C2SLoadingResult;
import com.sasa5680.ProtoMessages.C2S.C2SLoadingResult.C2S_LoadingResult;

import client.LifeCycle.States.ActivateState;
import client.client.Client;
import messageHandler.MessageHandler;

public class C2S_LoadingResultHandler extends MessageHandler{

	public C2S_LoadingResultHandler(Client client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(General MSG) {
		// TODO Auto-generated method stub
			
			System.out.println("C2S_Loading");
		
			Any any = MSG.getInnerMSG(0);
			
			try {
				C2S_LoadingResult LR  = any.unpack(C2S_LoadingResult.class);
				
				if(LR.getResult()) {
					
					//Loading done
					//go to activate state
					this.clientctx.getClientLifeCycle().moveState(new ActivateState());
					
					
				} else {
					
					//try Loading again
					try {
						this.clientctx.getDevice().Loading();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
