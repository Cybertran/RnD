/**
 * 
 */
package rnd.algo.sort;

import java.util.Comparator;

import rnd.ds.stack.Stack;

/**
 * @author dev
 *
 */
public class SortingStack {
	
	private static <E> void quickSort(Stack<E> stack, Comparator<E> comparator) {
		if(stack == null || comparator == null) {
			return;
		}
		
		
	}
	
	public static <E> void sort(Stack<E> stack, Comparator<E> comparator) {
		quickSort(stack, comparator);
	}
	
	public static void main(String[] args) {
		Comparator<Integer> intComparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer x, Integer y) {
				return Integer.compare(x, y);
			}
		};
		
		int[] test = { 5, 4, 6, 3, 6, 7, 8, 2, 6, 7, 1, 0 };
		
		Stack<Integer> stack = new Stack<Integer>();
		
		for(int t : test) {
			stack.push(t);
		}
		
		sort(stack, intComparator);
		
		Integer last = Integer.MIN_VALUE;
		for(int i = test.length - 1; i >= 0; i--) {
			Integer s = stack.pop();
			boolean passed = s.intValue() >= last;
			System.out.printf("%d >= %d : %s \n", s, last, passed);
			last = s;
		}
	}
}
