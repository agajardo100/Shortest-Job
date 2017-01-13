
//Java 1.8 using Eclipse Neon 4.6.1

/*
 * Which method gives the lower average waiting time?
 * 
 * Using a MinHeap (PriorityQueue) will result in a lower average waiting time.
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ShortestJob {
	
	static ArrayList<Job> jobInbox;		//Contains all Jobs read in from text file
	static Queue<Job> jobOutbox;	  	//Contains all Jobs that have been done
	static Queue<Job> jobQueue;
	static MinHeap jobHeap;				//Structure that maintain and performs Jobs as they 'arrive'
	
	static int jobsRead;				//Number of Jobs read in from text file
	static int heapTime;				//Number of seconds elapsed since the simulation started
	static int queueTime;
	
	
	/*
	 * Each Timer instance is a single background thread.
	 * Each of the three TimerTasks will run concurrently on
	 * their own thread. 
	 */
	static Timer counter = new Timer();		
	static Timer jobTimer = new Timer();
	static Timer heapTimer = new Timer();
	
	/*
	 * This task updates heapTime every 1000 milliseconds
	 */
	static TimerTask updateHeapTime = new TimerTask(){
		public void run(){
			if (heapTime == 90){  
				counter.cancel();
				counter.purge();
			}
			heapTime++;
		}
	};
	
	/*
	 * This task is performed every second. It dequeues a Job instance from the 
	 * jobInbox to the jobHeap if arrivalTime of the top element is equal to 
	 * heapTime
	 */
	static TimerTask sendJob = new TimerTask(){
		public void run(){
			
			try {
				if(jobInbox.get(0).getTimeOfArrival() <= heapTime){
					System.out.println(jobInbox.get(0).getName()+" has been added to Job Heap after: " +heapTime+ " seconds.");
				    jobHeap.addJob(jobInbox.remove(0));
					}
				} 
			catch (Exception e) {
					System.out.println("No more Jobs in Inbox.");
					jobTimer.cancel();
					jobTimer.purge();
				}
			}
		};
	
	/*
	 * 	This task will 'perform' the Job with the shortest exceutionTime.
	 * 	The shortest Job is removed from the jobHeap and the thread is put to 
	 * 	sleep for the duration of the execution time to simulate the action. 
	 *  While this thead sleeps, Jobs still may enter the jobHeap.
	 *  All completed Jobs are inserted into the jobOutbox
	 */
	static TimerTask performJob = new TimerTask(){
		public void run(){
			try{
				Job done = jobHeap.NextJob();
				Thread.sleep(done.getTimeToExecute()*1000);
				System.out.println(done.getName()+ " has been completed at: " +heapTime+" seconds.");
				done.setTimeOfService(heapTime);
				jobOutbox.enQ(done);
			}
			catch (Exception e){
				if(jobHeap.getCompleted() == jobsRead ){
					System.out.println("All jobs have been completed.\n");
					heapTimer.cancel();
					heapTimer.purge();
					printStatistics(heapTime,jobOutbox.size());
				}
			}
		}
	};
	
	/*
	 * This method prints each job's turnaround time and prints the
	 * average waiting time.
	 */
	public static void printStatistics(int totalTime, int count){
		int totalTurnAround = 0;
		while(jobOutbox.isEmpty() == false){
			Job tmp = jobOutbox.deQ();
			int jobTime = tmp.getTimeOfService()-tmp.getTimeOfArrival();
			System.out.println(tmp.getName()+"'s turnaround time:" + jobTime);
			totalTurnAround += jobTime;
		}
		double waitTime = ((totalTurnAround-totalTime)/(double)count);
		System.out.println("Total Time: "+totalTime+" seconds");
		System.out.println("Number of Jobs Completed: " +count);
		System.out.println("The average wait time was: " +waitTime+"s\n");
	}
	
	public static void main(String[] args){
		//Read In Jobs
		
		jobInbox = new ArrayList<Job>();
		jobOutbox = new Queue<Job>();
		jobHeap = new MinHeap();
		jobsRead = 0;
		
		File file = new File("Job_Data.txt");
		try (Scanner input = new Scanner(file)){
			input.useDelimiter(",|\n|\r\n|\\s+");
			while(input.hasNext()){
				String name = input.next();
				int arrival = input.nextInt();
				int completion = input.nextInt();
				Job task = new Job(name, arrival, completion);
				jobInbox.add(task);
				jobsRead ++;
			}
			input.close();
		}
		catch (IOException e) {
            e.printStackTrace();
		}
		System.out.println(jobsRead+" Jobs have been read");
		
		jobQueue = new Queue<Job>();  //copy jobInbox to jobQueue
		for(Job j:jobInbox){
			jobQueue.enQ(j);
		}
		queueTime = 1;
		System.out.println("******* Simulating Queue *******");
		while(jobQueue.isEmpty() == false){
			Job tmp = jobQueue.deQ();
			queueTime += tmp.getTimeToExecute();
			tmp.setTimeOfService(queueTime);
			System.out.println(tmp.getName()+ " has been completed at: " +queueTime+" seconds.");
			jobOutbox.enQ(tmp);
		}
		printStatistics(queueTime,jobOutbox.size());
		
		System.out.println("******* Simulating MinHeap *******");
		counter.scheduleAtFixedRate(updateHeapTime,0,1000);
		jobTimer.scheduleAtFixedRate(sendJob,0, 1000);
		heapTimer.scheduleAtFixedRate(performJob, 900, 950);
	}
}
