package ibrdtn.dataport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
public class Parser {
	//Parser - get the message / file
	// if it is a file info., it will keep the info. in the database with dataport id
	public static String parse (String fileName, int dataportid) throws ClassNotFoundException{
		try (BufferedReader br = new BufferedReader(new FileReader(DataportApp.path+fileName+".txt")))
		{
			String sCurrentLine = br.readLine();
			String type[] = new String[7];
			String val[] = new String[7];
			Scanner scanner = new Scanner(sCurrentLine);
		    scanner.useDelimiter(",");
		    for(int i=0;i<7;i++){
		    	 if (scanner.hasNext()){
		    	      //assumes the line has a certain structure
		    	      type[i] = scanner.next();
		    	      System.out.println("Type["+i+"] :" +type[i]);
		    	      if (scanner.hasNext()){
		    	    	  val[i] = scanner.next();
		    	      }
		    	      System.out.println("Val["+i+"]:"+val[i]);
		    	      }
		    }
		    System.out.println(type[0]);
		    scanner.close();
		    //check format for upload request
		    if(type[0].equals("DTNMessage;MacAddr")&&type[1].equals("sourceEID")&&type[2].equals("filename")&&type[3].equals("filetype")&&type[4].equals("size")&&type[5].equals("destination")&&type[6].equals("Action")){
		    	// add index in database
		    	try {
		    		System.out.println("Jack " + val[0] + " Jack " + val[1] + val[2] + val[3] + val[4] + val[5] + val[6]);
					Handler.addData(dataportid,val);
					if(val[6].equals("Cancel")){
						Handler.delData(val);
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	int s = Integer.parseInt(val[4]);
		    	String temp = Calculation.timeCalculate(dataportid, s, 100000000);
				String[] temp2 = temp.split(",");
		    	String Ans = "1,"+val[0]+","+val[6]+","+temp2[0];
		    	return Ans;
		    }
		    
		    
		    //to do
		    //check format for transfer file request from vehicle
		    if(type[0].contains("DTNMessage")&&type[2].equals("Action")){
		    	//Todo Authentication?
		    	//

		    	String Ans = "2,"+val[0]+","+val[2]+","+val[3]+","+val[4];
		    	return Ans;
		    }
		    
		    
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("parcheck");
		return "0"; 
	}
}
