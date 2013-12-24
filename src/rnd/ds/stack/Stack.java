package rnd.ds.stack;


public class Stack<E> {
	public static class Node<E> {
		private E data;
		private Node<E> next;
		
		public Node(E data) {
			this.data = data;
		}
	}

	private Node<E> top;
	
	public void push(E item) {
		if(item == null) {
			return;
		}
		
		if(top == null) {
			top = new Node<E>(item);
		} else {
			Node<E> n = top;
			top = new Node<E>(item);
			top.next = n;
		}
	}
	
	public E pop() {
		if(top == null) {
			return null;
		}
		
		Node<E> n = top;
		top = n.next;
		return n.data;
	}
	
	public E peek() {
		if(top == null) {
			return null;
		}
		
		return top.data;
	}
	
	public boolean isEmpty() {
		return top == null;
	}
	
	public static void main(String[] args) {
		int[] test = { 5, 4, 6, 3, 6, 7, 8, 2, 6, 7, 1, 0 };
		
		Stack<Integer> stack = new Stack<Integer>();
		System.out.println(stack.pop());
		
		for(int t : test) {
			stack.push(t);
		}
		
		for(int i = test.length - 1; i >= 0; i--) {
			Integer s = stack.pop();
			System.out.printf("%d == %d : %s \n", test[i], s, test[i] == s);
		}
	}
}
