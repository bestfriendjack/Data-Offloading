import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
   
public class Main {
	 public static void main(String[] args) throws ClassNotFoundException
     {
		 
		 // Initialize the station id (3 Shiraniwadai)
		 int thisStationId = 3;
		 
		// load the sqlite-JDBC driver using the current class loader
			Class.forName("org.sqlite.JDBC");

			Connection connection = null;
			try
			{
		// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			
			// Calculate the time for next bus
			ResultSet bus = statement.executeQuery("select Hour, Minute from TimeTable where NOT IsTerminal AND Station_ID = " + thisStationId);
			
			// Initialize medium
			long medium = 1441;
			
			// Flag
			boolean flag = false;
			
			// Initialize pass
			
			while(bus.next())
			{
				// Check the bus stop
				System.out.println("Next bus: " + bus.getInt("Hour") + ":" + bus.getInt("Minute"));
				
				// Calculate total Transfer time from user to data port and data port to vehicle
				Calculation calculate = new Calculation();
				Time userToDataport = calculate.estimatedTransfer(100, 1000);
				Time dataportToVehicle = calculate.estimatedTransfer(100000, 50);
				long transfer = calculate.totalTransfer(userToDataport, dataportToVehicle);
				
				// Check the total transfer time
				System.out.println("Total Transfer: " + (transfer/60) + ":" + (transfer%60));
				
				// Create present time
				   Date presentTime = new Date();
				   
				// Create time object
				Time time = new Time();
				time.setHour(bus.getInt("Hour"));
				time.setMinute(bus.getInt("Minute"));
				
				// Create Compare Object
				Compare com = new Compare();
				   
				// Calculate time
				Time result = calculate.estimatedTime(time , presentTime);
				
				// Check the result
				//System.out.println("Time to next bus: " + result.getHour() + ":" + result.getMinute());
				
				long compare = (result.getHour()*60) + result.getMinute();
				
				// Check for medium and compare
				System.out.println(compare);
				System.out.println(medium);
				System.out.println(transfer);
				
				// If the next bus time is less than before and transfer time less than next bus time then change
				if(com.isSmaller(transfer, compare)){
					if(com.isSmaller(compare, medium)){
						medium = compare;
					}
				}
				if(com.isSmaller(compare, transfer)){
					flag = true;
				}
				
				System.out.println("Minimum bus time: " + (medium/60) +":"+ (medium%60));
				
			}
			
			ResultSet rs = statement.executeQuery("select Hour, Minute from TimeTable where IsTerminal");
			   
			// Initialize minimum
			long minimum = 1441;
			long second = 1441;
			
			while(rs.next())
			 {
			   // read the result set
			   // System.out.println("Time = " + rs.getInt("Hour") + ":" + rs.getInt("Minute"));
			   
			   // Create present time
			   Date presentTime = new Date();
			   
			   // Create time object
			   Time time = new Time();
			   time.setHour(rs.getInt("Hour"));
			   time.setMinute(rs.getInt("Minute"));
			   
			   // Calculate time
			   Calculation calculate = new Calculation();
			   Time result = calculate.estimatedTime(time , presentTime);
			   Compare com = new Compare();
			   
			   // Transfer Time
			   // TODO Get size and connection speed (size,speed,size,speed)
			   // From user to data port
			   Time userToDataport = calculate.estimatedTransfer(10, 1000);
			   Time dataportToVehicle = calculate.estimatedTransfer(10, 50);
			   
			   long minuteDiff = calculate.totalTime(result, userToDataport, dataportToVehicle);
			   
			   long temp = minimum;
			   
			   second = com.getSecondSmallest(minimum, minuteDiff, temp);
			   
			   if(minuteDiff<minimum){
				   minimum = minuteDiff;
			   }
			   
			 }
			 
			if(flag = true){
				minimum = second;
			}
			 // Check minimum
			 System.out.println("Minimum is "+(minimum/60)+":"+(minimum%60));
			
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
	 

	
	
     }
}
