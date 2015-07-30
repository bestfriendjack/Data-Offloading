package ibrdtn.dataport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import org.apache.commons.codec.binary.Base64;
import org.ibrdtnapi.BpApplication;
import org.ibrdtnapi.entities.Bundle;

public class DataportApp extends BpApplication{ 
	public static String path = "/home/chanthawat/";
	public static DataportApp bpApp;
	public static DataportApp bpApp2;
	public static int dataportId = 5;
	public  int nbThreads =  Thread.getAllStackTraces().keySet().size();

    public static void main  (String[] args)throws InterruptedException {
        bpApp2=new DataportApp();
    	bpApp2.setEid("dtnapp2");
    	bpApp = new DataportApp();
    	bpApp.setEid("dtnapp");
    	
        System.out.println(bpApp.getEid());
      
    }

    public void sendConfrimationMessage(String dest, String mac,int type,String time) throws ClassNotFoundException{
    	File f3 = new File(path+"temp2.txt");
    	File f4 = new File(path+"newfile1.txt");
    	//long Ans = Handler.calTime();
    	Bundle bundle3 = new Bundle(dest,("MacAddress:"+mac+","+"Type:"+type+";It will arrive in"+time+"Are you sure?").getBytes());
    	System.out.println(bundle3.getDestination());
        bpApp2.send(bundle3);
        System.out.println("Send confirmation message");
        f3.delete();
        f4.delete();
    }
   public void sendResult(String dest, String mac, int type){
    	//DataportApp dpApp2 = new DataportApp();
	   
    	File f3 = new File(path+"temp2.txt");
    	File f4 = new File(path+"newfile1.txt");
    	System.out.println("dest:"+dest);
    	Bundle bundle3 = new Bundle(dest,("MacAddress:"+mac+","+"Type:"+type+";Are you sure?").getBytes());
    	System.out.println(bundle3.getDestination()+"eiei");
        bpApp2.send(bundle3);
        System.out.println("Send success message");
        f3.delete();
        f4.delete();
        
    }
   public void sendFileInfo(String dest,String Info){
	   File f3 = new File(path+"temp2.txt");//for delete file
	   File f4 = new File(path+"newfile1.txt");
	   System.out.println("before send file info");
	   String[] temp = Info.split(",");
	   Bundle bundle3 = new Bundle(dest,("DTNMessage;MacAddr,"+"00:00:00:00:00:00,"+"sourceEID,"+"dtn://chanthawat-X550JD/dtnapp,"+"filename,"+temp[0]+","+"filetype,"+temp[3]+","+"size,"+temp[2]+","+"destination,"+temp[6]+","+"Action,Transfer,").getBytes());
	   System.out.println("After send file info");
	   bpApp2.send(bundle3);
	   f3.delete();
       f4.delete();
   }
   public void sendFile(String dest,String Info) throws IOException{
	   File f3 = new File(path+"temp2.txt");//for delete file
	   File f4 = new File(path+"newfile1.txt");
	   Path filePath = Paths.get(Info);
	   byte[] data = Files.readAllBytes(filePath);
	   Bundle bundle3 = new Bundle(dest,data);

	   bpApp.send(bundle3);
	   f3.delete();
       f4.delete();
   }
   
    
    @Override
    public void bundleReceived(Bundle b) {
    	System.out.println("something is coming");
    	System.out.println("fffffffffffffffffffffffffffffffffffffff");
    	String result;
    	File f2 = new File("/home/chanthawat/isFinished.txt");
    	while(f2.exists()){
    	}
		try {
		result = Parser.parse("newfile1",dataportId);
		String nodeName = b.getSource();
		//Receive File
		System.out.println("Received bundle");
		System.out.println(result);
		if(result.equals("0")){
			//check in db
			//To do
			
			System.out.println("Received File");
			try {
				String temp=Handler.getFileNameAndType(b.getSource());
				String temp2=temp.substring(1, temp.length());
				System.out.println("temp"+temp);
				if(!temp.equals("404")){
					//decode file
					System.out.println("Start decoding");
					String[] parts = temp2.split(",");
					System.out.println(temp2);
				
					String fName= parts[0];
					String fType= parts[1];
					File file = new File("/home/chanthawat/"+fName+fType);
					//write file
					try (BufferedReader br = new BufferedReader(new FileReader("/home/chanthawat/temp2.txt")))
					{
						System.out.println("Check file");
						String sCurrentLine;
						FileOutputStream out = new FileOutputStream(file);
						while ((sCurrentLine = br.readLine()) != null) {
							byte[] content = Base64.decodeBase64(sCurrentLine);
							out.write(content);
						}
						out.close();
			 
					} catch (IOException e) {
						e.printStackTrace();
					} 
					System.out.println("Finished");
					String dest=nodeName;
					String  destMac=Handler.getMACAddr(fName);
					sendResult(dest, destMac, 2);
					//bpApp2.kill();
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//fin
			//bpApp2.kill();
		}
		//Receive Message
		else{
			System.out.println("Received message");
			String dest = nodeName;
			String[] temp2 = result.split(",");
			if(temp2[0].equals("2")){						//receive transfer message
				String busID=temp2[3];
				String busLine=temp2[4];
				System.out.println("Receive message from vehicle");
				if(temp2[2].equals("Retrieve")){
					//check in db
					//check busID and busLine
					try {
							String Ans =Handler.checkDB(busLine,busID);
							if(!(Ans.equals(""))){
								sendFileInfo(dest,Ans);
							}				
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				if(temp2[2].equals("ACKInfo")){
					try {
						System.out.println("check");
						String Ans = Handler.checkFile(busLine, busID);
						sendFile(dest,Ans);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
				
				if(temp2[2].equals("ACKData")){
					//delete file in db
					System.out.println("ACKDATA");
					Handler.delDB();
					String Ans =Handler.checkDB(busLine,busID);
					if(!(Ans.equals(""))){
						sendFileInfo(dest,Ans);
					}
				}
			}
			else{
				String destN = nodeName;
				if(temp2[2].equals("Cancel")){
					System.out.println("Deleted");
				}
				else{
					try {
						sendConfrimationMessage(destN, temp2[1], 1, temp2[3]);
					} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
			System.out.println("Success");
			
			}
			
		}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		

}
