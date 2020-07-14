package ForwardingRetry;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;



public class ScheduleMap {
	
	private int Number = 0;
	
	private ConcurrentHashMap<Integer,  ScheduledFuture<?>> map = new ConcurrentHashMap<Integer,  ScheduledFuture<?>>();
	
	public synchronized void putSchedule(ScheduledFuture<?> S, MessageWrapper MW) {
		
		MW.Code = GenerateID();
		map.put(MW.Code, S);

	}
	
	public synchronized void removeSchedule( MessageWrapper MW) {
		
		try {
			
			ScheduledFuture<?> S = map.get(MW.Code);
			map.remove(MW.Code);
			S.cancel(false);
			
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			
			System.out.println("Null pointer E");
			e.printStackTrace();
		}
		
	}
	
	private int GenerateID() {
		
		this.Number++;
		
		if(Number >= 10000) {
			
			Number =  0;
			
		}
		
		return Number;
		
	}
	
	final private static class ScheduleMapClassHolder {
		
		public static final ScheduleMap Smap = new ScheduleMap();
	}	
	
	final public static ScheduleMap getScheduleMap() {
		
		return ScheduleMapClassHolder.Smap;
	}

}
