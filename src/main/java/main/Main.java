package main;

import java.net.InetAddress;
import java.net.UnknownHostException;

import bootStrap.NettyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Main {
	
	public static void main(String args[]) {
		
		 
    	InetAddress local; 
    	try 
    	{ 	
    		local = InetAddress.getLocalHost(); 
    		String ip = local.getHostAddress(); 
    		System.out.println("local ip : "+ip);
    		
    	} catch (UnknownHostException e1) { 
    		
    		e1.printStackTrace(); }

    	
    	
    	
    

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new NettyServerInitializer());
            
        	System.out.println("active");    
        	
        	
            bootstrap.bind(com.sasa5680.CommonIndex.Network.port).sync().channel().closeFuture().sync();
            
            
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


	
	

}
