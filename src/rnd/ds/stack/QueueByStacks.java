/**
 * 
 */
package rnd.ds.stack;


/**
 * @author dev
 *
 */
public class QueueByStacks<E> {
	private Stack<E> head = new Stack<E>();
	private Stack<E> tail = new Stack<E>();
	
	public void enqueue(E item) {
		head.push(item);
	}
	
	public E dequeue() {
		while(head.peek() != null) {
			tail.push(head.pop());
		}
		
		E item = tail.pop();
		
		while(tail.peek() != null) {
			head.push(tail.pop());
		}
		
		return item;
	}
	
	public static void main(String[] args) {
		QueueByStacks<Integer> queue = new QueueByStacks<Integer>();
		int[] test = { 5, 4, 6, 3, 6, 7, 8, 2, 6, 7, 1, 0 };
		
		System.out.println(queue.dequeue());
		
		for(int t : test) {
			queue.enqueue(t);
		}
		
		for(int t : test) {
			Integer i = queue.dequeue();
			System.out.printf("%d == %d : %s \n", t, i, t == i);
		}
	}
}
