/**
 * 
 */
package rnd.ds.queue;


/**
 * @author dev
 *
 */
public class Queue<E> {
	public static class Node<E> {
		private E data;
		private Node<E> next;
		
		public Node(E data) {
			this.data = data;
		}
	}
	
	private Node<E> first;
	private Node<E> last;
	
	public void enqueue(E item) {
		if(item == null) {
			return;
		}
		
		if(last != null) {
			last.next = new Node<E>(item);
			last = last.next;
		} else {
			first = last = new Node<E>(item);
		}
	}
	
	public E dequeue() {
		if(first == null) {
			return null;
		}
		
		Node<E> n = first;
		first = n.next;
		return n.data;
	}
	
	public static void main(String[] args) {
		Queue<Integer> queue = new Queue<Integer>();
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
