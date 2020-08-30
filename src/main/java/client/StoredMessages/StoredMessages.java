package client.StoredMessages;

import java.util.ArrayList;

import com.sasa5680.ProtoMessages.GeneralMSG.General;

import client.client.Client;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class StoredMessages {
	
	Client client;
	
	public StoredMessages(Client client){
		
		this.client = client;
	}
	
	private ArrayList<General> savedMessage = new ArrayList<General>();
	
	public void sendSavedMessages() {
		
		for(General msg : savedMessage) {
			
			ChannelFuture f = this.client.getChannel().writeAndFlush(msg);
			f.addListener(new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					// TODO Auto-generated method stub
					
					if(f.isSuccess()) {
						
						//remove message from List if Success
						savedMessage.remove(msg);
					}
					
				}
			});
		}
	}
	
	public void addMessage(General msg) {
		
		savedMessage.add(msg);
		
		
	}

}
