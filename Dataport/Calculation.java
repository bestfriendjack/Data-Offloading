package ibrdtn.dataport;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Calculation {

	public static long estimatedTransfer(long size, long speed){
		
		// Size must be in bit and speed must be in bit per second
		long transferTime = size/speed;
		
		return transferTime;
	}

	public static String timeCalculate(int id, long size, long speed) throws ClassNotFoundException{
		// Initialize time variable to store bus time
		long transfer = estimatedTransfer(size, speed);
		String time = null;
		
		// load the sqlite-JDBC driver using the current class loader
		  Class.forName("org.sqlite.JDBC");

		      Connection connection = null;
		      try
		      {
		  // create a database connection
		  connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
		  Statement statement = connection.createStatement();
		  statement.setQueryTimeout(30);  // set timeout to 30 sec.
		  System.out.println("id: "+id);
		  // Query for time to next bus to this stop
		  ResultSet rs1 = statement.executeQuery("select Bus_Line ,Bus_ID , Time from TimeTable AS T JOIN Station AS S ON T.Station_ID = S.Station_ID WHERE S.Station_ID = "+id+" AND "+transfer+" < (((CAST(strftime('%H',TIME) AS INTEGER) * 60 ) + (CAST(strftime('%M',TIME)AS INTEGER)))*60) - (((CAST(strftime('%H',TIME(CURRENT_TIMESTAMP,'localtime')) AS INTEGER) * 60 ) + (CAST(strftime('%M',TIME(CURRENT_TIMESTAMP,'localtime'))AS INTEGER)))*60) ORDER BY Time");
		  int busLine, busID;
		  if(rs1.next()){
			  busLine = rs1.getInt("Bus_Line");
			  System.out.println(busLine);
			  busID = rs1.getInt("Bus_ID");
			  System.out.println(busID);
		  
			  System.out.println("checkdb");
			  ResultSet rs2 = statement.executeQuery("select Time from TimeTable AS T JOIN Station AS S ON T.Station_ID = S.Station_ID WHERE IsTerminal AND Bus_Line = "+busLine+" AND Bus_ID = "+busID+" AND TIME(CURRENT_TIMESTAMP,'localtime') < strftime('%H:%M',Time) ORDER BY Time");		  
		  
		  	if(rs2.next()){
		  		time = rs2.getString("Time");
		  		time = time + ","+busLine+","+busID;
		  	}
		  	return time;
		  }else {
			  System.out.println("no result");
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
		      return time;
	}
	
	
}
