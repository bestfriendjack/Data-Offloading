package ibrdtn.dataport;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class CalculateFunction {
	public static void cal() throws ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try
        {
    // create a database connection
    connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
    Statement statement = connection.createStatement();
    statement.setQueryTimeout(30);  // set timeout to 30 sec.

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
	   Time result1 = calculate.estimatedTime(time , presentTime);
	   Compare com = new Compare();
	   
	   // Transfer Time
	   // TODO Get size and connection speed (size,speed,size,speed)
	   // From user to data port
	   Time userToDataport = calculate.estimatedTransfer(10, 1000);
	   Time dataportToVehicle = calculate.estimatedTransfer(10, 50);
	   
	   long minuteDiff = calculate.totalTime(result1, userToDataport, dataportToVehicle);
	   
	   if(minuteDiff<minimum){
		   minimum = minuteDiff;
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
}

	}

