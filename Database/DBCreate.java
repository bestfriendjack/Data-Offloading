package ibrdtn.dataport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// uncomment this to check the resultset
// import java.sql.ResultSet;

// Shiraniwadai Data port
public class DBCreate {
	public static void main(String[] args) throws ClassNotFoundException {
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try
		{
 // create a database connection
 connection = DriverManager.getConnection("jdbc:sqlite:tables.db");
 Statement statement = connection.createStatement();
 statement.setQueryTimeout(30);  // set timeout to 30 sec.
 
 // create TimeTable table (The data have to be imported)
 statement.executeUpdate("drop table if exists TimeTable");
 statement.executeUpdate("create table TimeTable (Station_ID integer, Station_Name string, IsTerminal boolean, Hour int, Minute int)");
 statement.executeUpdate("insert into TimeTable values(1, 'Gakken-Nara-Tamigaoka', 1, 15, 58)");
 //statement.executeUpdate("insert into TimeTable values(2, 'Gakken-Kita-Ikoma', 0, 15, 40)");
 statement.executeUpdate("insert into TimeTable values(3, 'Shiraniwadai', 0, 15, 30)");
 //statement.executeUpdate("insert into TimeTable values(4, 'Ikoma', 0, 15, 26)");
 statement.executeUpdate("insert into TimeTable values(10, 'Cosmosquare', 1, 13, 10)");
 statement.executeUpdate("insert into TimeTable values(1, 'Gakken-Nara-Tamigaoka', 1, 16, 18)");
 //statement.executeUpdate("insert into TimeTable values(2, 'Gakken-Kita-Ikoma', 0, 16, 00)");
 statement.executeUpdate("insert into TimeTable values(3, 'Shiraniwadai', 0, 15, 50)");
 //statement.executeUpdate("insert into TimeTable values(4, 'Ikoma', 0, 15, 46)");
 statement.executeUpdate("insert into TimeTable values(10, 'Cosmosquare', 1, 13, 30)");
 
 
 // create File table (Only blank table at first)
 statement.executeUpdate("drop table if exists File");
 statement.executeUpdate("create table File (File_ID int, File_Name string, File_Path string, File_Size float, File_Type String, DTN_Address string, MAC_Address string, Status int)");
 
 // Checking the data in the table
 /* ResultSet rs = statement.executeQuery("select * from TimeTable");
 while(rs.next())
 {
   // read the result set
   System.out.println("Station_Name = " + rs.getString("Station_Name"));
   System.out.println("Station_ID = " + rs.getInt("Station_ID"));
   System.out.println("Terminal = " + rs.getBoolean("IsTerminal"));
   System.out.println("Time = " + rs.getInt("Hour") + ":" + rs.getInt("Minute"));
 }
 
 ResultSet rs1 = statement.executeQuery("select * from File");
 while(rs1.next())
 {
   // read the result set
   System.out.println("File_Name = " + rs1.getString("File_Name"));
 } */
 
 
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
