package ForwardingRetry;

import com.sasa5680.ProtoMessages.GeneralMSG.General;

public class MessageWrapper {
	
	public int TryCount = 0;
	public int Code 	= 0;
	public General MSG;
	
	protected MessageWrapper(General MSG) {
		
		this.MSG = MSG;
	}

}
