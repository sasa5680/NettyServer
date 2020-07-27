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
					
	    			client.networkStatue.GoodNetwork();
					
				} else {
					
					client.networkStatue.BadNetwork();
				}
	    	}
	    	
	    });
	}
}
