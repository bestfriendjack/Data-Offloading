public class FileMetadata {
	int id;
	String name;
	String path;
	long size;
	String type;
	String DTN;
	String MAC;
	
	public void setID(int id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public void setSize(long size){
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
	
	public int getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPath(){
		return path;
	}
	
	public long getSize(){
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
