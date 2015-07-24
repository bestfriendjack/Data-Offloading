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
 
 // create Station Table (The data have to be imported)
 statement.executeUpdate("drop table if exists Station");
 statement.executeUpdate("create table Station (Station_ID integer PRIMARY KEY, Station_Name string, IsTerminal boolean)");
 
 // Initialize Station
 statement.executeUpdate("insert into Station values(1, 'Gakken-Nara-Tomigaoka', 1)");
 statement.executeUpdate("insert into Station values(2, 'Gakken-Kita-Ikoma', 0)");
 statement.executeUpdate("insert into Station values(3, 'Shiraniwadai', 0)");
 statement.executeUpdate("insert into Station values(4, 'Ikoma', 0)");
 statement.executeUpdate("insert into Station values(5, 'Shin-ishikiri', 0)");
 statement.executeUpdate("insert into Station values(6, 'Nagata', 0)");
 statement.executeUpdate("insert into Station values(7, 'Takaida', 0)");
 statement.executeUpdate("insert into Station values(8, 'Morinomiya', 0)");
 statement.executeUpdate("insert into Station values(9, 'Hanimachi-4chome', 0)");
 statement.executeUpdate("insert into Station values(10, 'Mahidoldaigakumae', 1)");
 statement.executeUpdate("insert into Station values(11, 'Narasentandai', 0)");
 statement.executeUpdate("insert into Station values(12, 'Gakuemmae', 0)");
 statement.executeUpdate("insert into Station values(13, 'IPlab', 1)");
 
 // create TimeTable table (The data have to be imported)
 statement.executeUpdate("drop table if exists TimeTable");
 statement.executeUpdate("create table TimeTable (Bus_Line, Bus_ID, Station_ID, Time time)");
 
 // 138 Line
 // First bus
 statement.executeUpdate("insert into TimeTable values(138, 1, 1, '09:00:00')");
 statement.executeUpdate("insert into TimeTable values(138, 1, 2, '09:10:00')");
 statement.executeUpdate("insert into TimeTable values(138, 1, 3, '09:20:00')");
 statement.executeUpdate("insert into TimeTable values(138, 1, 4, '09:30:00')");
 statement.executeUpdate("insert into TimeTable values(138, 1, 5, '09:40:00')");
 statement.executeUpdate("insert into TimeTable values(138, 1, 6, '09:50:00')");
 statement.executeUpdate("insert into TimeTable values(138, 1, 8, '10:10:00')");
 statement.executeUpdate("insert into TimeTable values(138, 1, 10, '10:30:00')");
 
 // Second bus
 statement.executeUpdate("insert into TimeTable values(138, 2, 1, '09:50:00')");
 statement.executeUpdate("insert into TimeTable values(138, 2, 2, '10:00:00')");
 statement.executeUpdate("insert into TimeTable values(138, 2, 3, '10:10:00')");
 statement.executeUpdate("insert into TimeTable values(138, 2, 4, '10:20:00')");
 statement.executeUpdate("insert into TimeTable values(138, 2, 5, '10:30:00')");
 statement.executeUpdate("insert into TimeTable values(138, 2, 6, '10:40:00')");
 statement.executeUpdate("insert into TimeTable values(138, 2, 8, '11:00:00')");
 statement.executeUpdate("insert into TimeTable values(138, 2, 10, '11:20:00')");
 
 // Third bus
 statement.executeUpdate("insert into TimeTable values(138, 3, 1, '13:00:00')");
 statement.executeUpdate("insert into TimeTable values(138, 3, 2, '13:10:00')");
 statement.executeUpdate("insert into TimeTable values(138, 3, 3, '13:20:00')");
 statement.executeUpdate("insert into TimeTable values(138, 3, 4, '13:30:00')");
 statement.executeUpdate("insert into TimeTable values(138, 3, 5, '13:40:00')");
 statement.executeUpdate("insert into TimeTable values(138, 3, 6, '13:50:00')");
 statement.executeUpdate("insert into TimeTable values(138, 3, 8, '14:10:00')");
 statement.executeUpdate("insert into TimeTable values(138, 3, 10, '14:30:00')");
 
 // 82 line
 // First bus
 statement.executeUpdate("insert into TimeTable values(82, 1, 1, '09:00:00')");
 statement.executeUpdate("insert into TimeTable values(82, 1, 2, '09:10:00')");
 statement.executeUpdate("insert into TimeTable values(82, 1, 3, '09:20:00')");
 statement.executeUpdate("insert into TimeTable values(82, 1, 4, '09:30:00')");
 statement.executeUpdate("insert into TimeTable values(82, 1, 5, '09:40:00')");
 statement.executeUpdate("insert into TimeTable values(82, 1, 6, '09:50:00')");
 statement.executeUpdate("insert into TimeTable values(82, 1, 11, '10:10:00')");
 statement.executeUpdate("insert into TimeTable values(82, 1, 12, '10:30:00')");
 statement.executeUpdate("insert into TimeTable values(82, 1, 13, '10:50:00')");
 
 // Second bus
 statement.executeUpdate("insert into TimeTable values(82, 2, 1, '11:00:00')");
 statement.executeUpdate("insert into TimeTable values(82, 2, 2, '11:10:00')");
 statement.executeUpdate("insert into TimeTable values(82, 2, 3, '11:20:00')");
 statement.executeUpdate("insert into TimeTable values(82, 2, 4, '11:30:00')");
 statement.executeUpdate("insert into TimeTable values(82, 2, 5, '11:40:00')");
 statement.executeUpdate("insert into TimeTable values(82, 2, 6, '11:50:00')");
 statement.executeUpdate("insert into TimeTable values(82, 2, 11, '12:10:00')");
 statement.executeUpdate("insert into TimeTable values(82, 2, 12, '12:30:00')");
 statement.executeUpdate("insert into TimeTable values(82, 2, 13, '12:50:00')");
 
 // 8 line
 //First bus
 statement.executeUpdate("insert into TimeTable values(82, 1, 1, '09:20:00')");
 statement.executeUpdate("insert into TimeTable values(82, 1, 2, '09:50:00')");
 statement.executeUpdate("insert into TimeTable values(82, 1, 5, '10:30:00')");
 statement.executeUpdate("insert into TimeTable values(82, 1, 10, '10:50:00')");
 
//Second bus
statement.executeUpdate("insert into TimeTable values(82, 2, 1, '10:00:00')");
statement.executeUpdate("insert into TimeTable values(82, 2, 2, '10:30:00')");
statement.executeUpdate("insert into TimeTable values(82, 2, 5, '11:10:00')");
statement.executeUpdate("insert into TimeTable values(82, 2, 10, '11:30:00')");

//Third bus
statement.executeUpdate("insert into TimeTable values(82, 3, 1, '13:00:00')");
statement.executeUpdate("insert into TimeTable values(82, 3, 2, '13:30:00')");
statement.executeUpdate("insert into TimeTable values(82, 3, 5, '14:10:00')");
statement.executeUpdate("insert into TimeTable values(82, 3, 10, '14:30:00')");

//Fourth bus
statement.executeUpdate("insert into TimeTable values(82, 4, 1, '13:40:00')");
statement.executeUpdate("insert into TimeTable values(82, 4, 2, '14:10:00')");
statement.executeUpdate("insert into TimeTable values(82, 4, 5, '14:50:00')");
statement.executeUpdate("insert into TimeTable values(82, 4, 10, '15:10:00')");

//Fifith bus
statement.executeUpdate("insert into TimeTable values(82, 5, 1, '16:40:00')");
statement.executeUpdate("insert into TimeTable values(82, 5, 2, '17:10:00')");
statement.executeUpdate("insert into TimeTable values(82, 5, 5, '17:50:00')");
statement.executeUpdate("insert into TimeTable values(82, 5, 10, '18:10:00')");
 

 // create File table (Only blank table at first)
 statement.executeUpdate("drop table if exists File");
 statement.executeUpdate("create table File (File_ID int PRIMARY KEY, File_Name string, File_Path string, File_Size float, File_Type String, DTN_Address string, MAC_Address string, Status int)");
 
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
