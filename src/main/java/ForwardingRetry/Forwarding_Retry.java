package ForwardingRetry;

import java.util.concurrent.TimeUnit;

import com.sasa5680.ProtoMessages.GeneralMSG.General;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import ForwardingRetry.ForwardingFailedListener;
import ForwardingRetry.ForwardingSuccessListener;
import ForwardingRetry.MessageWrapper;

public class Forwarding_Retry {
	
	ChannelFuture CF;
	
	private final int Maximum_Count;
	private final int Interval;
	private final int InitDelay;
	
	private  ForwardingFailedListener  FFL;
	private  ForwardingSuccessListener FSL;
	
	private final General MSG;
	private final Channel channel;
	
	public static class Builder{
		
		//required param
		public Builder(General MSG, Channel channel) {
			
			this.MSG 	 = MSG;
			this.channel = channel;
		}
		
		//default value
		private int Interval  	  = 1;
		private int InitDelay 	  = 3;
		private int Maximum_Count = 3;
		
		private ForwardingFailedListener  FFL = null;
		private ForwardingSuccessListener FSL = null;
		
		private General MSG;
		private Channel channel;
		
		public Builder Maximum_Count(int val) {
			Maximum_Count = val;
			return this;
		}
		
		public Builder Interval(int val) {
			Interval = val;
			return this;
		}
		
		public Builder InitDelay(int val) {
			InitDelay = val;
			return this;
		}
		
		public Forwarding_Retry build() {
			
			return new Forwarding_Retry(this);
		}
		
	}
	
	private Forwarding_Retry(Builder b){
		
		Maximum_Count = b.Maximum_Count;
		Interval	  = b.Interval;
		InitDelay	  = b.InitDelay;
		FFL			  = b.FFL;
		FSL			  = b.FSL;
		MSG			  = b.MSG;
		channel       = b.channel;	
	}
	
	public void ForwardingFailedListener(ForwardingFailedListener val) {this.FFL = val;}
	
	public void ForwardingSuccessListener(ForwardingSuccessListener val) {this.FSL = val;}
	
	public void Foward_Message(){

		CF = channel.writeAndFlush(MSG);
		
		CF.addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				// TODO Auto-generated method stub
				if(future.isSuccess()) {
					
					if(FSL != null) FSL.Succes();
							
					
				}
				
				if(!(future.isSuccess())) {
					
					//if fail to Send message
					//System.out.println("Message Failed");
					final MessageWrapper MW = new MessageWrapper(MSG);
					ScheduledFuture<?> SF = channel.eventLoop().scheduleAtFixedRate(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							ChannelFuture Re_CF = channel.writeAndFlush(MSG);
							Re_CF.addListener(new ChannelFutureListener() {

								public void operationComplete(ChannelFuture future) throws Exception {
									// TODO Auto-generated method stub
        							if(FSL != null) FSL.Succes();
        						else {
        							
        							//System.out.println("failed again");
        							
        							MW.TryCount++;
        							//System.out.println("TryCount : "+MF.TryCount+" code : "+ MF.Code);
        							
        							if(MW.TryCount>=Maximum_Count) {
        								
        								//System.out.println("finally failed");
        								ScheduleMap Smap = ScheduleMap.getScheduleMap();
        								Smap.removeSchedule(MW);
        								
        								//call fail Listener
        								if(FFL != null) FFL.isFailed();
        							}									
									
									
									}
								}
								
							});
							
						}
						
					}, Interval, InitDelay, TimeUnit.SECONDS);
					
				}
			}
	
		});
	}
}

