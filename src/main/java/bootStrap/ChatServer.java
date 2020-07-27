package bootStrap;

import java.net.InetAddress;
import java.net.UnknownHostException;

import client.device.MasterMap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;



public class ChatServer {
    private final int port;
 
    public ChatServer(int port) {
        super();
        this.port = port;
    }
    
    public static void main(String[] args) throws Exception {
    	
    	MasterMap.getMasterMap().InitDeviceManager();
    	
    	//MessageInitializer CI = new MessageInitializer();
    	//CI.Init();
    	new ChatServer(5001).run();
    	
    }
    
    public void run() throws Exception {
        
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
        	
        	
            bootstrap.bind(port).sync().channel().closeFuture().sync();
            
            
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}


