package ibrdtn.dataport;


public class FileMetadata {
	String name;
	String path;
	int size;
	String type;
	String DTN;
	String MAC;
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public void setSize(int size){
		this.size = size;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setDTN(String DTN){
		this.DTN = DTN;
	}
	
	public void setMAC(String MAC){
		this.MAC = MAC;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPath(){
		return path;
	}
	
	public int getSize(){
		return size;
	}
	
	public String getType(){
		return type;
	}
	
	public String getDTN(){
		return DTN;
	}
	
	public String getMAC(){
		return MAC;
	}
}
