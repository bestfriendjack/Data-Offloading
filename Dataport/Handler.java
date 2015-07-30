package ibrdtn.dataport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


public class Handler {

	public static String addData(int dataportId,String[] val) throws ClassNotFoundException
    {
		
		// Create FileMetadata
		// TODO Need to get from parser
		FileMetadata data = new FileMetadata();
		String fileName = val[2];
		String filePath = "'"+DataportApp.path+"'";
		int fileSize = Integer.parseInt(val[4]);
		String fileType = val[3];
		String DTNAddr = val[1];
		String MACAddr = val[0];
		String dest = val[5];
	
		// load the sqlite-JDBC driver using the current class loader
					Class.forName("org.sqlite.JDBC");

					Connection connection = null;
					try
					{
				// create a database connection
					connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
					Statement statement = connection.createStatement();
					statement.setQueryTimeout(30);  // set timeout to 30 sec.
					
					// Put the read data to object
					data.setName(fileName);
					data.setPath(filePath);
					data.setSize(fileSize);
					data.setType(fileType);
					data.setDTN(DTNAddr);
					data.setMAC(MACAddr);
					//System.out.println(data.getMAC())
					System.out.println("FIle is "+fileName);
					System.out.println("Path is "+filePath);
					System.out.println(fileSize);
					System.out.println(fileType);
					System.out.println(DTNAddr);
					System.out.println("MAC is "+MACAddr);
					String Ans = Calculation.timeCalculate(dataportId, data.getSize(), 100000000);
					System.out.println("Ans="+Ans);
				
					String[] temp = Ans.split(",");
				
					// Insert data into File table
					statement.executeUpdate("insert into File (File_Name, File_Path, File_Size, File_Type, DTN_Address, MAC_Address, Status, destination, Bus_Line, Bus_ID) values('"+data.getName()+"', "+data.getPath()+", "+data.getSize()+", '"+data.getType()+"', '"+data.getDTN()+"', '"+data.getMAC()+"', "+"1, '"+dest+"', '"+temp[1]+"', '"+temp[2]+"')");
					System.out.println("Finish sql insert");
					
					 /* ResultSet rs = statement.executeQuery("select * from TimeTable");
					 while(rs.next())
					 {
					   // read the result set
					   System.out.println("Station_Name = " + rs.getString("Station_Name"));
					   System.out.println("Station_ID = " + rs.getInt("Station_ID"));
					   System.out.println("Terminal = " + rs.getBoolean("IsTerminal"));
					   System.out.println("Time = " + rs.getInt("Hour") + ":" + rs.getInt("Minute"));
					 }*/
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
					String Ans = Calculation.timeCalculate(dataportId, data.getSize(), 100000000);
					String[] temp = Ans.split(",");
					return temp[0];
					
    }
	
	//delete data in db
	public static void delData(String []val) throws ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		try
		{
	// create a database connection
		connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30);  // set timeout to 30 sec.
		statement.executeUpdate("delete from file where DTN_Address ='"+val[1]+"'and status = 1");
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
	
	
	public static String getFileNameAndType(String source) throws SQLException, ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30);
		ResultSet rs = statement.executeQuery("select File_Name, File_Type, status from File");
		//Initialize the arraylist to store the data
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<String> typeList = new ArrayList<String>();
		ArrayList<Integer> statusList = new ArrayList<Integer>();
		String Final = "";
		
		while(rs.next()){
			
			//Put the data into arraylist
			nameList.add(rs.getString("File_Name"));
			typeList.add(rs.getString("File_Type"));
			statusList.add(rs.getInt("status"));
		}
		rs.close();
		
		for(int i=0;i<nameList.size();i++){
			if(statusList.get(i)==1){
				String temp = nameList.get(i);
				statement.executeUpdate("update File set status = 2 where File_Name = '"+temp+"'");
				String Ans = temp+","+typeList.get(i);
				Final = Final + ":" + Ans;
				
			}
		}
		//return  Final;
		if(!Final.equals("")){return Final;}
		return "404";
	}
	
	
	public static String getMACAddr(String file) throws ClassNotFoundException{
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		try{
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			// Checking the data in the table
			ResultSet rs = statement.executeQuery("select MAC_Address from File where File_Name = '"+file+"'");
			while(rs.next()){
				// read the result set
				System.out.println("MAC is " + rs.getString("MAC_Address"));
				return rs.getString("MAC_Address");
			}
		 
		}
		catch(SQLException e){
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
		finally{
			try{
				if(connection != null)connection.close();
			}
			catch(SQLException e){
				// connection close failed.
				System.err.println(e);
			}
		}
		return null;
	}
	/*
	public static long calTime() throws ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        long Ans = 0;
        try{
        	// create a database connection
        	connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
        	Statement statement = connection.createStatement();
        	statement.setQueryTimeout(30);  // set timeout to 30 sec.
        	ResultSet rs = statement.executeQuery("select Hour, Minute from TimeTable where IsTerminal");
        	// Initialize minimum
        	long minimum = 1441;
        	//long second = 1441;	
        	while(rs.next()){
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
        		//change to nextBusArrival
        		Time result1 = calculate.estimatedTime(time , presentTime);
        		//Compare com = new Compare(); 
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
        	Ans = minimum;
        }
        catch(SQLException e){
        	// if the error message is "out of memory", 
        	// it probably means no database file is found
        	System.err.println(e.getMessage());
        }
        finally{
        	try{
        		if(connection != null)
        		connection.close();
        	}
        	catch(SQLException e){
        		// connection close failed.
        		System.err.println(e);
        	}
        }
        return Ans;
	}
	*/
	public static String checkDB(String busLine, String busID) throws ClassNotFoundException, SQLException{
		// check queue in db
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try
		{
	// create a database connection
		connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30);  // set timeout to 30 sec.
		////////////////////////////
		System.out.println("Busline:"+busLine+" BusID:"+busID);
		ResultSet rs = statement.executeQuery("select File_Name, File_Path, File_Size, File_Type, DTN_Address, MAC_Address, destination from File where Bus_ID = "+busID+" AND Bus_Line = "+busLine);
		if(rs.next()){
			String Ans = rs.getString("File_Name")+","+rs.getString("File_Path")+","+rs.getInt("File_Size")+","+rs.getString("File_Type")+","+rs.getString("DTN_Address")+","+rs.getString("MAC_Address")+","+rs.getString("destination")+",";
			return Ans;
		}else{
			System.out.println("no files to send");
		}
		rs.close();
		return "";
		}
		finally{
			try{
				if(connection != null)connection.close();
			}
			catch(SQLException e){
				// connection close failed.
				System.err.println(e);
			}
		}
	
	}
	public static String checkFile(String busLine, String busID) throws ClassNotFoundException, SQLException{
		// check queue in db
		Class.forName("org.sqlite.JDBC");
		String Ans2="";
		Connection connection = null;
		try
		{
	// create a database connection
		connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30);  // set timeout to 30 sec.
		////////////////////////////
		ResultSet rs = statement.executeQuery("select File_Name, File_Path, File_Size, File_Type, DTN_Address, MAC_Address, destination from File where Bus_ID = '"+busID+"'AND Bus_Line='"+busLine+"'");
		rs.next();
		String Ans = rs.getString("File_Path")+rs.getString("File_Name");
		Ans2=Ans;
		rs.close();
		}
		finally{
			try{
				if(connection != null)connection.close();
			}
			catch(SQLException e){
				// connection close failed.
				System.err.println(e);
			}
		}
		return Ans2;
	
	}
	public static void delDB() throws ClassNotFoundException, SQLException{
		// check queue in db
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try
		{
	// create a database connection
		connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30);  // set timeout to 30 sec.
		////////////////////////////
		statement.executeUpdate("DELETE from File WHERE File_ID in (select File_ID from File Limit 1)");
		}
		finally{
			try{
				if(connection != null)connection.close();
			}
			catch(SQLException e){
				// connection close failed.
				System.err.println(e);
			}
		}
	
	}
}
