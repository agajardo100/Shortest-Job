import java.util.NoSuchElementException;

/*  Array Implementation of a generic queue class  */

@SuppressWarnings("unchecked")
public class Queue<T> {
	
	private T[] arr;
	private int max;
	private int count;
	private int front, rear;
	
	/*
	 * Default Constructor
	 */
	public Queue(){
		max = 10;
		count = 0;
		front = 0;
		rear = -1;
		arr = (T[]) new Object[10];
	}
	
	/*
	 * Constructor that accepts an integer value as the
	 * capacity of the underlying array 
	 */
	public Queue(int capacity){
		if(capacity < 0)
			throw new NegativeArraySizeException("Queue cannot have a negative capacity");
		max = capacity;
		arr = (T[]) new Object[max];
		count = 0;
		front = 0;
		rear = -1;
		
	}
	
	/* Check is Queue is empty */
	public boolean isEmpty(){
		if (count == 0)
			return true;
		else 
			return false;
	}
	
	/* Return number of elements in queue */
	public int size(){
		return count;	
	}
	
	/*
	 * Remove front element from queue and return it
	 * Best/Average/Worst Case: O(1)
	 */
	public T deQ(){
		if (isEmpty())
			throw new NoSuchElementException("Queue is empty");
		T oldFront = arr[front];
		front = (front +1) % max;
		count--;
		return oldFront;
	}
	
	/*
	 * Add element to end of queue.  If underlying array is full,
	 * double the size
	 * Best/Average Case: O(1)
	 * Worst Case: O(n) if resize is needed
	 */
	public void enQ(T data){
		if (count == max ){ //if Queue and array are full, resize
			T[] newArr = (T[]) new Object[2*max]; 
			for(int i = 0; i < max; i++){
				newArr[i] = arr[front];
				front = (front +1) % max;
			}
			arr = newArr; 
			rear = max -1;
			front = 0; 
			max *= 2;
			
		}
		rear = (rear +1) % max;
		arr[rear] = data;
		count++;
	}
	
	/* Return element in the front of the queue 
	 * Best/Average/Worst Case: O(1)
	 */
	public T peek(){
		if (isEmpty()== true)
			throw new NoSuchElementException("Queue is empty");
		return arr[front];
	}
	
	/* Makes Queue logically empty */
	public void makeEmpty(){
		if (isEmpty())
			throw new NoSuchElementException("Queue is already empty");
		count = 0;
		front = 0 ;
		rear = -1;
	}
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < count; i++)
			s.append(arr[(front + i)% max ].toString()+" ");
		return s.toString();
	}
	
	//TODO: Iterator

}
