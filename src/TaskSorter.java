package taskMaster;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

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
