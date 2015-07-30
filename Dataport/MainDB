package ibrdtn.dataport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
   
public class MainDB {
	 public static void main(String[] args) throws ClassNotFoundException
     {
		 
		// load the sqlite-JDBC driver using the current class loader
			Class.forName("org.sqlite.JDBC");

			Connection connection = null;
			try
			{
		// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			
			ResultSet rs = statement.executeQuery("select Hour, Minute from TimeTable where IsTerminal = 1");
			   
			// Initialize minimum
			long minimum = 1441;
			
			while(rs.next())
			 {
			   // read the result set
			   System.out.println("Time = " + rs.getInt("Hour") + ":" + rs.getInt("Minute"));
			   
			   // Create present time
			   Date presentTime = new Date();
			   
			   // Create time object
			   Time time = new Time();
			   time.setHour(rs.getInt("Hour"));
			   time.setMinute(rs.getInt("Minute"));
			   
			   // Calculate time
			   Calculation calculate = new Calculation();
			   Time result = calculate.estimatedTime(time , presentTime);
			   long minuteDiff = (result.getHour()*60)+result.getMinute();
			   
			   if(minuteDiff<minimum){
				   minimum = minuteDiff;
			   }
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
