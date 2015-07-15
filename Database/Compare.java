public class Compare {

	public boolean isSmaller(long value1, long value2){
		if(value1<value2){
			return true;
		}
		return false;
	}
	public long getSecondSmallest(long value1, long value2, long value3){
		if(value1>value2){
			long temp = value1;
			value1 = value2;
			value2 = temp;
		}
		if(value2>value3){
			long temp = value2;
			value2 = value3;
			value3 = temp;
		}
		if(value1>value2){
			long temp = value1;
			value1 = value2;
			value2 = temp;
		}
		return value2;
	}
	
}
