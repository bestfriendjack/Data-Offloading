package ibrdtn.dataport;


public class Time {
	long Hour;
	long Minute;
	
	public void setHour(long h){
		Hour = h;
	}
	
	public void setMinute(long m){
		Minute = m;
	}
	
	public long getHour(){
		return Hour;
	}
	
	public long getMinute(){
		return Minute;
	}
}
