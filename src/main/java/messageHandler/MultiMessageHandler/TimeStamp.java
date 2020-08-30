package messageHandler.MultiMessageHandler;

public class TimeStamp {
	
	public TimeStamp(int total, int current) {
		
		this.current = current;
		this.total	 = total;	
	}
	
	private final int total;
	private final int current;	
	
	public int getTotal() {
		return total;
	}
	public int getCurrent() {
		return current;
	}

}
