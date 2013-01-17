package arc;

public class Rate {

	private int userId;
	private String spaceId;
	public float rate;

	public int getUserId(){
		return userId;
	}
	public void setUserId(int userId){
		this.userId = userId;
	}

	public String getSpaceId(){
		return spaceId;
	}

	public void setSpaceId(String spaceId){
		this.spaceId = spaceId;
	}

	public float getRate(){
		return rate;
	}
	public void setRate(float rate){
		this.rate = rate;
	}


	public void setRateDDBB(){

		DataBaseManager manager = new DataBaseManager();		
		manager.setRateDDBB(this);

	}



}