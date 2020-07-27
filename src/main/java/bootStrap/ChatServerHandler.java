package bootStrap;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.sasa5680.ProtoMessages.GeneralMSG.General;

import ForwardingRetry.Forwarding_Retry;
import client.client.Client;
import client.clientState.LoginWaitState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

 
public class ChatServerHandler extends ChannelInboundHandlerAdapter {
 
    
	public ChatServerHandler(Client client) {this.client = client;}
	
	private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    
    Client client;
    
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
         
    	
    	client.setChannel(ctx.channel());
    	System.out.println("handlerAdded of [SERVER]");
        /*Channel incoming = ctx.channel();
     
        channelGroup.add(incoming);
        
        for (Channel channel : channelGroup) {
            //����ڰ� �߰��Ǿ��� �� ���� ����ڿ��� �˸�
            channel.write("[SERVER] - " + incoming.remoteAddress() + " has joined!\n");
            
           
        }*/
        	
    }
 
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // ����ڰ� �������� �� ������ ǥ��.
        
    	client.setChannel(ctx.channel());
    	client.moveClientState(new LoginWaitState());
    

    }
 
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        /*
    	System.out.println("handlerRemoved of [SERVER]");
        Channel incoming = ctx.channel();
        for (Channel channel : channelGroup) {
            //����ڰ� ������ �� ���� ����ڿ��� �˸�
            channel.write("[SERVER] - " + incoming.remoteAddress() + "has left!\n");
        }
        HearBeatMessage.cancel(false);
        channelGroup.remove(incoming);*/
    }
 
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
 
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*String message = null;
        message = (String)msg;
        System.out.println("channelRead of [SERVER]" +  message);
        Channel incoming = ctx.channel();
        for (Channel channel : channelGroup) {
            if (channel != incoming) {
                //�޽��� ����.
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + message + "\n");
            }
        }
        if ("bye".equals(message.toLowerCase())) {
            ctx.close();
        }*/
    	
    	General MSG = (General) msg;
    	String MSGType = MSG.getMessageType();
    	
    	
    	
    }
    
    
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("Error !!");
        
        if (cause instanceof java.io.IOException) {
        	
        	System.out.println("fatal Error : Client exited without order");
        	ctx.close();
        }
        
        
        
        
        
        
    }
}


