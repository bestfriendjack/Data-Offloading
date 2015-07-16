import java.util.Date;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Calculation {
	@SuppressWarnings("deprecation")
	public Time estimatedTimeToTerminal() throws ClassNotFoundException{

		// Initialize time variable to store terminal time
		Time time = new Time();
		Time difference = new Time();
		
		// load the sqlite-JDBC driver using the current class loader
		  Class.forName("org.sqlite.JDBC");

		      Connection connection = null;
		      try
		      {
		  // create a database connection
		  connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
		  Statement statement = connection.createStatement();
		  statement.setQueryTimeout(30);  // set timeout to 30 sec.
		  
		  // Query for time to terminal
		  ResultSet rs = statement.executeQuery("select Hour, Minute from TimeTable where IsTerminal");
		   
		  while(rs.next()){
			 
			  // Put the time of terminal to time variable
			  time.setHour(rs.getInt("Hour"));
			  time.setMinute(rs.getInt("Minute"));
			  
			  // Check time variable
			  //System.out.println("Terminal time is "+time.getHour()+":"+time.getMinute());
			  
			  // Get the current time
			  Date date = new Date();
			  
			  // Check date
			  // System.out.println("Current date is "+date.getHours()+":"+date.getMinutes());
			  
			  // Calculate difference in minutes and hours
			  long diffMinute = time.getMinute() - date.getMinutes();
			  long diffHour = time.getHour() - date.getHours();
			  
			  // Convert the hours and minutes
				if(diffMinute<0){
					diffMinute = 60+diffMinute;
					diffHour = diffHour-1;
				}
				//System.out.println("Minute difference: "+diffMinute);
				difference.setMinute(diffMinute);
				if(diffHour<0){
					diffHour = 24+diffHour;
				}
				//System.out.println("Hour difference: "+diffHour);
				difference.setHour(diffHour);
				
				
				// Check time different
				// System.out.println("Time difference: "+difference.getHour()+":"+difference.getMinute());
		  }
		  
		      }
				catch(SQLException e)
				{
				 // if the error message is "out of memory", 
				 // it probably means no database file is found
				 System.err.println(e.getMessage());
				}
				finally
				{
				 try
				 {
				   if(connection != null)
				     connection.close();
				 }
				 catch(SQLException e)
				 {
				   // connection close failed.
				   System.err.println(e);
				 }
				}
		      
		      return difference;
	}
	
	public Time estimatedTransfer(long size, long speed){
		Time time = new Time();
		
		// Size must be in byte and speed must be in byte per second
		long transferTime = size/speed;
		
		// Convert second to minute and hour
		time.setHour((transferTime/3600));
		time.setMinute((transferTime/60));
		return time;
	}
	
	public long estimatedTransfer(long size, long speed){
		
		// Size must be in byte and speed must be in byte per second
		long transferTime = size/speed;
		
		return transferTime;
	}
	
	public boolean isTransferGreaterNextBus(Time time, long size, long speed){
		long timeInSecond = ((time.getHour()*3600)+(time.getMinute()*60));
		long transTime = estimatedTransfer(size, speed);
		
		// If transfer > next bus
		if(transTime>timeInSecond){
			return true;
		}
		return false;
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
