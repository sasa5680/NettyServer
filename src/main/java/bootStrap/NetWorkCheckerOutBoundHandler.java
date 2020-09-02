package bootStrap;

import client.client.Client;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class NetWorkCheckerOutBoundHandler extends ChannelOutboundHandlerAdapter {
	
	Client client;
	
	public NetWorkCheckerOutBoundHandler(Client client) {
		
		this.client = client;
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
	    ChannelFuture f = ctx.writeAndFlush(msg);
	    f.addListener(new ChannelFutureListener() {
	    	@Override
			public void operationComplete(ChannelFuture future) throws Exception{
	    		
	    		
	    		
	    		if(future.isSuccess()) {
					
	    			client.getNetworkStatue().GoodNetwork();
	    			System.out.println("Server outbound Handler : Good Network");
				} else {
					
					client.getNetworkStatue().BadNetwork();
					System.out.println("Server outbound Handler : bad Network");
				}
	    	}
	    	
	    });
	}
}
