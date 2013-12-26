/**
 * 
 */
package rnd.ds.stack;

/**
 * @author dev
 *
 */
public class TripleStack<E> {
	public final int capacity;
	public final Object[] table;
	
	public TripleStack(int capacity) {
		assert capacity < 1 : "capacity should be at least 1";
		
		this.capacity = capacity;
		this.table = new Object[3 * capacity];
	}
	
	private int top[] = {-3, -2, -1};
	
	public void push(int s, E item) {
		if(s < 0 && s > 2) {
			throw new IllegalArgumentException("Illegal stack index.");
		}
		if(top[s] + 3 > table.length) {
			throw new RuntimeException("Stack[" + (s + 1) + "] overflow");
		}
		
		top[s] += 3;
		table[ top[s] ] = item;
	}
	
	public E pop(int s) {
		if(s < 0 && s > 2) {
			throw new IllegalArgumentException("Illegal stack index.");
		}
		if(top[s] - 3 < 0) {
			throw new RuntimeException("Stack[" + (s + 1) + "] underflow");
		}
		
		@SuppressWarnings("unchecked")
		E item = (E) table[ top[s] ];
		top[s] -= 3;
		
		return item;
	}
	
	public E peek(int s) {
		if(s < 0 && s > 2) {
			throw new IllegalArgumentException("Illegal stack index.");
		}
		if(top[s] < 0) {
			throw new RuntimeException("Stack[" + (s + 1) + "] underflow");
		}
		
		@SuppressWarnings("unchecked")
		E item = (E) table[ top[s] ];
		return item;
	}
	
	public boolean isEmpty(int s) {
		if(s < 0 && s > 2) {
			throw new IllegalArgumentException("Illegal stack index.");
		}
		return top[s] < 0;
	}
}
