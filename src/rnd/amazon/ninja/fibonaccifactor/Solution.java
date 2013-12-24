/**
 * 
 */
package rnd.amazon.ninja.fibonaccifactor;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Given a number K, find the smallest Fibonacci number that shares a common
 * factor( other than 1 ) with it. A number is said to be a common factor of two
 * numbers if it exactly divides both of them.
 * 
 * Output two separate numbers, F and D, where F is the smallest fibonacci
 * number and D is the smallest number other than 1 which divides K and F.
 * 
 * @author Dev Naruka
 * 
 */
public class Solution {
	
	private static final long MAX = 1000_000_000_000_000_000L;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		
		for(int i = 0; i < T; i++) {
			int K = in.nextInt();
			
			findFactor(K, i);
		}
		
		in.close();
	}

	private static void findFactor(int k, int i) {
		FibonacciGenerator fib = new FibonacciGenerator();
		
		long F = 0, D = 0;
		
		while(D <= 1 && F <= MAX) {
			F = fib.next();
			D = gcd(F, k);
		}
		
		System.out.printf("%d %d\n", F, D);
	}
	
	private static long gcd(long a, long b) {
		return (b == 0) ? a : gcd(b, a % b);
	}

	private static class FibonacciGenerator {
		private static final Map<Long, Long> CACHE = new HashMap<Long, Long>();
		
		private long f1, f2;
		
		public FibonacciGenerator() {
			f1 = f2 = 1;
		}
		
		public long next() {
			Long nxt = CACHE.get(f2);
			if(nxt == null) {
				nxt = f1 + f2;
			}

			CACHE.put(f2, nxt);

			f1 = f2;
			f2 = nxt;

			return nxt;
		}
	}
}
