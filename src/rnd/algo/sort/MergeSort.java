/**
 * 
 */
package rnd.algo.sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Dev Naruka
 *
 */
public class MergeSort {
	public static <T> T[] sort(T[] a, Comparator<T> c) {
		T[] sorted = sort(a, 0, a.length, c);
		for(int i = 0; i < a.length; i++) {
			a[i] = sorted[i];
		}
		return a;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T[] sort(T[] a, int lo, int hi, Comparator<T> c) {
		/* base case */
		if(lo == hi - 1) {
			return (T[]) new Object[]{ a[lo] };
		}
		
		/* recursion */
		int n = hi - lo;
		int half = lo + (n / 2);
		T[] left = sort(a, lo, half, c);
		T[] right = sort(a, half, hi, c);
		
		/* combine */
		T[] combined = (T[]) new Object[n];
		for(int i = 0, j = 0, k = 0; k < n; k++) {

			if(j == right.length) {
				// push left to combined
				combined[k] = left[i++];
			} else if(i == left.length) {
				// push right to combined
				combined[k] = right[j++];
			} else {
				int comparison = c.compare(left[i], right[j]);
				// left[i] < right[j]
				if(comparison < 0) {
					combined[k] = left[i++];
				} else if(comparison > 0) {
					combined[k] = right[j++];
				} else {
					combined[k] = left[i++];
					combined[++k] = right[j++];
				}
			}
		}
		
		return combined;
	}
	
	public static void main(String[] args) {
		Integer[] raw = { 5,4,5,8,2,3,0,6,1,2 };
		System.out.println(Arrays.toString(raw));
		
		Comparator<Integer> c = new Comparator<Integer>() {
			@Override
			public int compare(Integer x, Integer y) {
				return y - x;
			}
		};
		
		sort(raw, c);
		System.out.println();
		System.out.println(Arrays.toString(raw));
	}
}
