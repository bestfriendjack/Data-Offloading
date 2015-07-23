import java.util.Date;
import java.util.PriorityQueue;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Calculation {
	
	public long estimatedTransfer(long size, long speed){
		
		// Size must be in bit and speed must be in bit per second
		long transferTime = size/speed;
		
		return transferTime;
	}
	
	@SuppressWarnings("deprecation")
	public long nextBusArrival(int id) throws ClassNotFoundException{
		// Initialize time variable to store bus time
		Time time = new Time();
		Time difference = new Time();
		long differenceInMin = 0;
		
		//Initialize minimum
		long minimum = 1441;
		
		// load the sqlite-JDBC driver using the current class loader
		  Class.forName("org.sqlite.JDBC");

		      Connection connection = null;
		      try
		      {
		  // create a database connection
		  connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
		  Statement statement = connection.createStatement();
		  statement.setQueryTimeout(30);  // set timeout to 30 sec.
		  
		  // Query for time to next bus to this stop
		  ResultSet rs = statement.executeQuery("select Hour, Minute from TimeTable where NOT IsTerminal and Station_ID = "+id);
		   
		  
		  while(rs.next()){
				 
			  // Put the time of next bus to time variable
			  time.setHour(rs.getInt("Hour"));
			  time.setMinute(rs.getInt("Minute"));
			  
			  // Check time variable
			  System.out.println("Next bus time is "+time.getHour()+":"+time.getMinute());
			  
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
				
				differenceInMin = (difference.getHour()*60)+(difference.getMinute());
				
				if(differenceInMin<minimum){
					minimum = differenceInMin;
				}
				
				
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
		      return minimum;
	}
	
	@SuppressWarnings("deprecation")
	public long secondBusArrival(int id) throws ClassNotFoundException{
		// Initialize time variable to store bus time
		Time time = new Time();
		Time difference = new Time();
		long differenceInMin = 0;
		
		//Initialize minimum
		PriorityQueue<Long> minimum = new PriorityQueue<Long>();
		
		// load the sqlite-JDBC driver using the current class loader
		  Class.forName("org.sqlite.JDBC");

		      Connection connection = null;
		      try
		      {
		  // create a database connection
		  connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
		  Statement statement = connection.createStatement();
		  statement.setQueryTimeout(30);  // set timeout to 30 sec.
		  
		  // Query for time to next bus to this stop
		  ResultSet rs = statement.executeQuery("select Hour, Minute from TimeTable where NOT IsTerminal and Station_ID = "+id);
		   
		  
		  while(rs.next()){
				 
			  // Put the time of next bus to time variable
			  time.setHour(rs.getInt("Hour"));
			  time.setMinute(rs.getInt("Minute"));
			  
			  // Check time variable
			  System.out.println("Next bus time is "+time.getHour()+":"+time.getMinute());
			  
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
				
				differenceInMin = (difference.getHour()*60)+(difference.getMinute());
				
				minimum.add(differenceInMin);
				
				
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
		      minimum.poll();
		      long answer = minimum.poll();
		      return answer;
	}
	
	@SuppressWarnings("deprecation")
	public long estimatedTimeToTerminal() throws ClassNotFoundException{

		// Initialize time variable to store terminal time
		Time time = new Time();
		Time difference = new Time();
		long differenceInMin = 0;
		
		// Initialize minimum
      		long minimum = 1441;
		
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
			  // System.out.println("Terminal time is "+time.getHour()+":"+time.getMinute());
			  
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
				
				differenceInMin = (difference.getHour()*60)+(difference.getMinute());
				
				if(differenceInMin<minimum){
					minimum = differenceInMin;
				}
				
				
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
		      
		      return minimum;
	}
	
	@SuppressWarnings("deprecation")
	public long secondTimeToTerminal() throws ClassNotFoundException{

		// Initialize time variable to store terminal time
		Time time = new Time();
		Time difference = new Time();
		long differenceInMin = 0;
		
		//Initialize minimum
				PriorityQueue<Long> minimum = new PriorityQueue<Long>();
		
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
			  // System.out.println("Terminal time is "+time.getHour()+":"+time.getMinute());
			  
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
				
				differenceInMin = (difference.getHour()*60)+(difference.getMinute());
				
				minimum.add(differenceInMin);
				
				
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
		      minimum.poll();
		      long answer = minimum.poll();
		      return answer;
	}
}
