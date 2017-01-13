
public class Job implements Comparable<Job>{
	private String name;
	private int arrivalTime;		//Time when Job enters Queue
	private int executionTime;		//Time it takes to execute Job
	private int serviceTime;		//Time when Job is started
	
	
	public Job(String name, int arrive, int toComplete){
		this.name = name;
		arrivalTime = arrive;
		executionTime = toComplete;
	}
	
	public Job(int time){
		executionTime = time;
	}
	
	/* Accessor Functions */
	public String getName(){
		return this.name;
	}
	
	public int getTimeOfArrival(){
		return arrivalTime;
	}
	
	public int getTimeToExecute(){
		return executionTime;
	}
	
	public int getTimeOfService(){
		return serviceTime;
	}
	
	/* Mutator Functions */
	public void setName(String name){
		this.name = name;
	}
	
	public void setTimeToComplete(int time){
		this.executionTime = time;
	}
	
	public void setTimeOfArrival(int time){
		arrivalTime = time;
	}
	
	public void setTimeOfService(int time){
		serviceTime = time;
	}

	@Override
	public int compareTo(Job other) {
		// TODO Auto-generated method stub
		int result = this.executionTime > other.executionTime ? 1 : this.executionTime < other.executionTime ? -1: 0;
		return result;
	}
	
	@Override
	public String toString(){
		String s = "";
		s = this.name + ":    Arrived: " +this.arrivalTime + "s   Time To Complete: " +this.executionTime +"s";
		return s;
	}
	
}
