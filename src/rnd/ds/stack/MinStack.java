package rnd.ds.stack;

public class MinStack<E> {
	private int capacity;
	private Object[] buffer;
	private int top = -1;
	private Stack<E> minimum;
	
	public MinStack() {
		this(100);
	}
	public MinStack(int capacity) {
		this.capacity = capacity;
		this.buffer = new Object[capacity];
		minimum = new Stack<E>();
	}
	
	public void push(E item) {
		if(top + 1 < capacity) {
			buffer[++top] = item;
			
			E min = minimum();
			if(min == null || compare(item, min) < 0) {
				minimum.push(item);
			}
		} else {
			throw new RuntimeException("Stack overflow");
		}
	}
	
	private int compare(E x, E y) {
		// TODO implement comparison
		return -1;
	}
	
	public E pop() {
		if(top - 1 >= 0) {
			@SuppressWarnings("unchecked")
			E item = (E) buffer[top--];
			
			E min = minimum();
			if(compare(min, item) == 0) {
				minimum.pop();
			}
			
			return item;
		} else {
			throw new RuntimeException("Stack underflow");
		}
	}
	
	public E peek() {
		if(top > 0) {
			@SuppressWarnings("unchecked")
			E item = (E) buffer[top];
			return item;
		}
		return null;
	}
	
	public E minimum() {
		return minimum.peek();
	}
}
