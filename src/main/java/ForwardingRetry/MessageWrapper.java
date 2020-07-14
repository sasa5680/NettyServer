package ForwardingRetry;


public class MessageWrapper {
	
	public int TryCount = 0;
	public int Code 	= 0;
	public com.google.protobuf.Message MSG;
	
	protected MessageWrapper(com.google.protobuf.Message MSG) {
		
		this.MSG = MSG;
	}

}
