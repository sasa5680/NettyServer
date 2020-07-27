package bootStrap;

import com.sasa5680.ProtoMessages.GeneralMSG;

import client.client.Client;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

 
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
	
	
	@Override
    protected void initChannel(SocketChannel arg0) throws Exception {
        ChannelPipeline pipeline = arg0.pipeline();
        
        Client client = new Client(null);
        //pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        
        //---------InBound---------
       // pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(GeneralMSG.General.getDefaultInstance()));
        
        pipeline.addLast(new ChatServerHandler(client));
        
      //--------OutnBound---------
        pipeline.addLast(new NetWorkCheckerOutBoundHandler(client));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());
        //pipeline.addLast(new OutBoundError());

    }
 
}


