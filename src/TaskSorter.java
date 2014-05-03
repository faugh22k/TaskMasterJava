package taskMaster;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A custom priority queue. We need to know the order 
 * of the tasks, but we can't make them comparable and 
 * simply sort them, because they need to be sorted in 
 * many different ways simulataneously (in storage). 
 * 
 * Thus, custom queue (custom because Java's use heaps,
 * so we can't get at the order directly). 
 * 
 * 
 * @author Kim, Jackie
 *
 */
public class TaskSorter implements Iterable<Task> { 

	private LinkedList<Task> list;
	private ArrayList<Task> array;
	private Comparator<Task> compare; 
	private int size = 0;
	
	public TaskSorter(Comparator<Task> compare){
		this.compare = compare;
		list = new LinkedList<Task>();
		array = new ArrayList<Task>();
	}
	
	/**
	 * Place the task as high as possible in the queue 
	 */
	public void add(Task toAdd){ 
		
		Task current;
		for(int i = 0; i < array.size(); i++){
			//System.out.println("looking to place new task");
			current = array.get(i);
			if(compare.compare(toAdd, current) == -1){
				array.add(i, toAdd);
				return;
			}
		}
		
		array.add(toAdd);
	}  
	 
	
	public Iterator<Task> iterator(){ 
		return array.iterator();
	}
	
	public void remove(Task toRemove){
		array.remove(toRemove);
	}
	
	public int size(){ 
		return array.size();
	}
	
	public String toString(){
		return array.toString();
	}
	 
	
}
