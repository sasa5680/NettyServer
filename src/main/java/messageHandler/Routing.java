package messageHandler;

import com.sasa5680.ProtoMessages.GeneralMSG;
import com.sasa5680.ProtoMessages.GeneralMSG.General;

import client.client.Client;
import client.client.ClientManager;
import client.client.CustomKey;

public class Routing {
	
	public static void routing(General MSG) {
		
		GeneralMSG.RoutingInfo routinginfo = MSG.getRoutingInfo(0);
		String desID   = routinginfo.getDestinationId();
		String desType = routinginfo.getDestinationType();
		
		if(desType.equals(com.sasa5680.CommonIndex.DeivceTypes.BroadCast.toString())) {
			
			//broadcast to all
			ClientManager.getClientManager().BroadCastToAll(MSG);
		
		} else if(desID.equals(com.sasa5680.CommonIndex.DeivceTypes.BroadCast.toString())) {
			
			//broadcast to Type
			ClientManager.getClientManager().BroadCastToType(MSG, desType);
		} else {
			
			Client client = ClientManager.getClientManager().getClient(new CustomKey(desType, desID));
			client.getChannel().writeAndFlush(MSG);
		}

	}

}
