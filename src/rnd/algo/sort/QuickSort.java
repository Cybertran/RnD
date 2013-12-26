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
public class QuickSort {
	public static <T> T[] sort(T[] a, Comparator<T> c) {
		sort(a, 0, a.length - 1, c);
		return a;
	}
	
	private static <T> void sort(T[] a, int lo, int hi, Comparator<T> c) {
		/* base case: */
		if(hi - lo < 1) {
			return;
		}
		
		/* pivot for partition */
		int pi = pivot(lo, hi);
		swap(a,pi,hi);
		
		/* partitioning */
		int n = hi - 1, i, j;
		for(i=j=lo; j<=n; j++) {
			int compare = c.compare(a[j],a[hi]);
			if(compare < 0) {
				swap(a,i,j);
				i++;
			}
		}
		swap(a,i,hi);
		
		/* recursion: */
		sort(a,lo,i-1,c);
		sort(a,i+1,hi,c);
	}
	
	private static void swap(Object[] a, int x, int y) {
		Object tmp = a[x];
		a[x] = a[y];
		a[y] = tmp;
	}
	
	private static int pivot(int lo, int hi) {
		return (int) Math.round((Math.random() * (hi- lo - 1)) + lo);
//		return lo;
	}
	
	private static String printArr(Object[] a, int lo, int hi) {
        if (a == null)
            return "null";

        int iMax = hi - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = lo; i < hi ; i++) {
            b.append(String.valueOf(a[i]));
            if (i < iMax) {
                b.append(", ");
            }
        }
        return b.append(']').toString();
    }
	
	public static void main(String[] args) {
		Integer[] raw = { 3,8,2,3,5,1,3,4,7,6 };
		System.out.println(Arrays.toString(raw));
		
		Comparator<Integer> c = new Comparator<Integer>() {
			@Override
			public int compare(Integer x, Integer y) {
				return x - y;
			}
		};
		
		sort(raw, c);
		System.out.println();
		System.out.println(Arrays.toString(raw));
	}
}
