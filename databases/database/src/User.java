
import java.util.Vector;

public class User {

  private int id;
  private String name;
  private String surname1;
  private String surname2;
  private Vector<Integer> author = new Vector<Integer>();
  private Vector<Integer> moderator = new Vector<Integer>();
  //private byte permission;
  private int permission;
  
  public void setId(int id){
		
	this.id = id;
		
  }

  public int getId (){
		
	return id;
  }

  public void setName(String name){
		
	this.name=name;
  }

  public String getName(){
		
	return name;
		
  }

  public void setSurName1(String surname1){
		
	this.surname1=surname1;
  }

  public String getSurName1(){
		
	return surname1;
		
  }

  public void setSurName2(String surname2){
		
	this.surname2=surname2;
  }

  public String getSurName2(){
		
	return surname2;
		
  }

  public void setAuthor(int authorIndex){
		
	author.addElement(authorIndex);
		
  }
	
  public Vector<Integer> getAuthor(){		
		
	return 	author;
		
  }

 public void setModerator(int moderatorIndex){
		
	moderator.addElement(moderatorIndex);
		
  }
	
  public Vector<Integer> getModerator(){		
		
	return 	moderator;
		
  }

  /*public void setPermission(Byte permission){
		
	this.permission = permission;
		
	}
	
	public String getPermission (){
		
		return permission;
	}
*/

    public void setPermission(int permission){
		
	this.permission = permission;
		
	}
	
    public int getPermission (){
		
		return permission;
	}

} 