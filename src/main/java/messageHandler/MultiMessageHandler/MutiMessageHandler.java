package messageHandler.MultiMessageHandler;

import com.sasa5680.ProtoMessages.GeneralMSG.General;

import client.client.Client;
import messageHandler.MessageHandler;

public abstract class MutiMessageHandler extends MessageHandler
{
	public MutiMessageHandler(Client client) {
		super(client);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public final void handle(General MSG) {
		// TODO Auto-generated method stub
		

		try {
			//get Message's time stamp
			TimeStamp stamp = getTimeStamp(MSG);
			
			//if first message
			if(stamp.getCurrent() == 1) {
				
				Session session = new Session(MSG.getMessageType(), this.clientctx.device.getID(), clientctx);
				session.startSession(stamp);
				
				//call handler
				dohandle(MSG);
			} else {
				
				//search Session
				Session session = clientctx.sessionMap.getMap().get(new SessionKey(MSG.getMessageType(), this.clientctx.device.getID()));
				//Session session = SessionMap.getSessionMap().Map.get(new SessionKey(MSG.getMessageType(), this.clientctx.device.getID()));
				
				if(session.CheckNewMessageOrder(stamp)) {
					
					FailListner(this.clientctx);

				} else {
					
					dohandle(MSG);
					
					//check Session is done
					if(session.completeChecker()) {
						session.endSession();
						finallyDoneListner();
					}
					
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//if something went wrong
			
			FailListner(this.clientctx);
			e.printStackTrace();
		}
	

	}
	
	public abstract TimeStamp getTimeStamp(General G);
	
	public abstract void FailListner(Client client);
	
	public abstract void dohandle(General G);
	
	public abstract void finallyDoneListner();
	

}
