package bootStrap;

import com.sasa5680.ProtoMessages.C2S.C2SLoadingResult.C2S_LoadingResult;
import com.sasa5680.ProtoMessages.C2S.C2SLoginRequest.C2S_LoginRequest;
import com.sasa5680.ProtoMessages.C2S.C2SPong.C2S_Pong;

import messageHandler.HandlerMapManager;
import messageHandler.C2S.C2S_LoadingResultHandler;
import messageHandler.C2S.C2S_LoginRequestHandler;
import messageHandler.C2S.C2S_PongHandler;

public class MessageInitializer {
	
	
	public static void Init() {
		
	   	//add Messages and Handlers in this Class
    	HandlerMapManager Map = HandlerMapManager.getHandlertMap();
		
    	/*---------------C2S Messages---------------*/
    	Map.addMessage(C2S_LoginRequest.getDefaultInstance(), new C2S_LoginRequestHandler(null));
		Map.addMessage(C2S_Pong.getDefaultInstance(), new C2S_PongHandler(null));
		Map.addMessage(C2S_LoadingResult.getDefaultInstance(), new C2S_LoadingResultHandler(null));
		
		System.out.println("Message Handler Init");
		
	}

}
