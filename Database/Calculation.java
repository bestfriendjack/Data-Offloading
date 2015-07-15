import java.util.*;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Calculation {
	public Time estimatedTime(Time input, Date current){
		// Get the current time from input
		Date date = new Date();
		Time time = new Time();
		date.setHours((int)input.getHour());
		date.setMinutes((int)input.getMinute());
		
		// Check date
		//System.out.println("Date is "+date.getHours()+":"+date.getMinutes());
		
		// Set the time format
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		
		long diffMinute = date.getMinutes() - current.getMinutes();
		long diffHour = date.getHours() - current.getHours();
		
		if(diffMinute<0){
			diffMinute = 60+diffMinute;
			diffHour = diffHour-1;
		}
		//System.out.println(diffMinute);
		time.setMinute(diffMinute);
		if(diffHour<0){
			diffHour = 24+diffHour;
		}
		//System.out.println(diffHour);
		time.setHour(diffHour);
		
		
		// Check time different
		System.out.println("Time diff: "+time.getHour()+":"+time.getMinute());

		return time;
	}
	public Time estimatedTransfer(long size, long speed){
		Time time = new Time();
		long transferTime = size/speed;
		time.setHour((transferTime/3600));
		time.setMinute((transferTime/60));
		return time;
	}
	public long totalTime(Time time1, Time time2, Time time3){
		Time time4 = new Time();
		time4.setHour(time1.getHour()+time2.getHour()+time3.getHour());
		time4.setMinute(time1.getMinute()+time2.getMinute()+time3.getMinute());
		long total = (time4.getHour()*60)+time4.getMinute();
		return total;
	}
	public long totalTransfer(Time time1, Time time2){
		Time time3 = new Time();
		time3.setHour(time1.getHour()+time2.getHour());
		time3.setMinute(time1.getMinute()+time2.getMinute());
		long total = (time3.getHour()*60)+time3.getMinute();
		return total;
	}
}


