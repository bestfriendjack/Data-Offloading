import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class Handler {

	public static void main(String[] args) throws ClassNotFoundException
    {
		
		// Create FileMetadata
		// TODO Need to get from parser
		FileMetadata data = new FileMetadata();
		int id = 1;
		String fileName = "'Name'";
		String filePath = "'/DOC/MAIN/PATH'";
		int fileSize = 10;
		String fileType = "'mp3'";
		String DTNAddr = "'DTN://jack'";
		String MACAddr = "'AD:PQ:48:9P'";
		
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
					data.setID(id);
					data.setName(fileName);
					data.setPath(filePath);
					data.setSize(fileSize);
					data.setType(fileType);
					data.setDTN(DTNAddr);
					data.setMAC(MACAddr);
					
					
					// Insert data into File table
					 statement.executeUpdate("insert into File values("+data.getID()+", "+data.getName()+", "+data.getPath()+", "+data.getSize()+", "+data.getType()+", "+data.getDTN()+", "+data.getMAC()+")");
					
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