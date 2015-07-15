Required components
 	Follow the steps in https://wiki.eclipse.org/Connecting_to_SQLite to get the SQLite java driver which connect SQLite to Java.
 	It will use the jar file as a reference library for database connection
DBCreate.java
Main class (Database initialization)
 	Open the class DBCreate.java and then run to create the database.
 	The database needs to be created separately.
 	You have to run this class only once to initialize all the tables.
Database Tables
 	Time Table
o	Use for storing the entire vehicle time table.
o	Data in this table are initialized at first using the vehicle stop time.
o	The SQL query: CREATE TABLE TimeTable (Station_ID integer, Station_Name String, IsTerminal Boolean, Hour integer, Minute integer)
o	Data fields:	Attribute		Data Type
•	Station_ID		Integer
•	Station_Name		String
•	IsTerminal		Boolean
•	Hour			Integer
•	Minute			Integer
o	This database will be specific for each data port.
	Each data port will only keep the data of itself and the terminal.
 	File
o	Use for storing the data and path of the file keep in data port.
o	First, this table is created with no data.
o	The data in this table will come after the file is received to the data port.
o	The SQL query: CREATE TABLE File (File_ID integer, File_Name String, File_Path String, File_Size float, File_Type String, DTN_Address String, MAC_Address String, Status integer)
o	Data fields:	Attribute		Data Type
•	File_ID			Integer
•	File_Name		String
•	File_Path		String
•	File_Size		Float
•	File_Type		String
•	DTN_Address		String
•	MAC_Address		String
•	Status			Integer
o	After the file is stored the data is put into this table
Compare.java
Main class
 	Methods
•	isSmaller(long, long)
•	getSecondSmallest(long, long, long)
Time.java
Time class
 	Attributes
•	Hour		long
•	Minute		long
 	Methods
•	setHour(long)
•	setMinute(long)
•	getHour()
•	getMinute()
FileMetadata.java
FileMetadata class
 	Attribut	es
•	ID		Integer
•	Name		String
•	Path		String
•	Size		Long
•	Type		String
•	DTN		String
•	MAC		String
•	Status		Integer
 	Methods
•	setID(int)
•	SetName(String)
•	setPath(String)
•	setSize(long)
•	setType(String)
•	setDTN(String)
•	setMAC(String)
•	setStatus(int)
•	getID()
•	getName()
•	getPath()
•	getSize()
•	getType()
•	getDTN()
•	getMAC()
•	getStatus()
Calculation.java
 	Methods
o	estimatedTime(Time, Date)
	This one calculates the current time to the time to the terminal from the time table. By get the terminal time and subtract by the current time.
o	estimatedTransfer(long, long)
	This one calculates the time use for transferring files. From the file size divide by the speed of the connection.
o	totalTime(Time, Time, Time)
	This method is created for calculate the overall time by combine all the time to data port and the total transfer time.
o	totalTransfer(Time, Time)
	Calculate all the transfer time to get the total transfer time.
	This one uses the result to compare to the time from the receiving message from the user until transfer to the vehicle.
Handler.java
 	Attributes
•	ID
•	Filename
•	Filepath
•	Filesize
•	Filetype
•	DTNAddr
•	MACAddr
 	This class use to handle the data after it passes the parser and put it to the File table in the database.
