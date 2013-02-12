package arc;

public class Rate {

	private int userId;
	private int tablonId;
	public float rate;

	public int getUserId(){
		return userId;
	}
	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getTablonId(){
		return tablonId;
	}
	public void setTablonId(int tablonId){
		this.tablonId = tablonId;
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
