import java.util.PriorityQueue;
import java.util.NoSuchElementException;

//MinHeap class implementing Java's built-in PrioirtyQueue

public class MinHeap {
	PriorityQueue<Job> pQueue;
	int elapsedTime = 0;		//sum of the times of each Job performed
	int completed = 0;			// number of Jobs performed
	
	//Constructor
	public MinHeap() {
		pQueue = new PriorityQueue<Job>();
	}
	
	/*
	 * Insert Job into Minheap 
	 * Best/Average Case: 	O( 1 )
	 * Worst Case: 			O(lg n)
	 */
	public void addJob(Job item){
		pQueue.offer(item);
	}

	
	/*
	 * Removes Job with shortest expected run time
	 * Average/Worst Case: O (lg n)
	 */
	public Job NextJob() throws Exception{
		if (isEmpty())
			throw new NoSuchElementException("No Jobs In MinHeap.");
		Job toRemove = pQueue.poll();
		elapsedTime += toRemove.getTimeToExecute();
		completed++;
		return toRemove;
	}
	
	/*
	 * Check is MinHeap is empty
	 * If there are no Jobs in MinHeap
	 * return true, else return false
	 */
	public Boolean isEmpty(){
		if (pQueue.isEmpty())
			return true;
		else return false;
	}
	
	/*
	 * Return total number of Jobs that have been 'run'
	 * or removed.
	 */
	public int getCompleted(){
		return completed;
	}
	
	/*
	 * Returns Job at the top of MinHeap, but does not remove
	 * Best/Avg/Worst Case: O(1)
	 */
	public Job peek(){
		if(isEmpty())
			throw new NoSuchElementException("No Jobs in MinHeap.");
		return pQueue.peek();
	}
	
	
}
