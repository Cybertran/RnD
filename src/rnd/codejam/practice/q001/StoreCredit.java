package rnd.codejam.practice.q001;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


/*
 * Problem: -----------------------------------------------------------------
 * 
 * You receive a credit C at a local store and would like to buy two items. You
 * first walk through the store and create a list L of all available items. From
 * this list you would like to buy two items that add up to the entire value of
 * the credit. The solution you provide will consist of the two integers
 * indicating the positions of the items in your list (smaller number first).
 * 
 * Input: -------------------------------------------------------------------
 * 
 * The first line of input gives the number of cases, N. N test cases follow.
 * For each test case there will be:
 * 
 * One line containing the value C, the amount of credit you have at the store.
 * One line containing the value I, the number of items in the store. One line
 * containing a space separated list of I integers. Each integer P indicates the
 * price of an item in the store. Each test case will have exactly one solution.
 * 
 * Output: ------------------------------------------------------------------
 * 
 * For each test case, output one line containing "Case #x: " followed by the
 * indices of the two items whose price adds up to the store credit. The lower
 * index should be output first.
 * 
 * Limits: ------------------------------------------------------------------
 * 
 * 5 ≤ C ≤ 1000 
 * 1 ≤ P ≤ 1000
 * 
 * Small dataset: -----------------------------------------------------------
 * 
 * N = 10 
 * 3 ≤ I ≤ 100
 * 
 * Large dataset: -----------------------------------------------------------
 * 
 * N = 50 
 * 3 ≤ I ≤ 2000
 * 
 */
public class StoreCredit {

	private static void solve(int no, Object[] problem) {
		int C = (int) problem[0], I = (int) problem[1];
		int[] P = (int[]) problem[2];
		
		Map<Integer, List<Integer>> map = new TreeMap<Integer, List<Integer>>();
		for(int i = 0; i < I; i++) {
			List<Integer> indices = map.get(P[i]);
			if(indices == null) {
				indices = new ArrayList<Integer>();
				map.put(P[i], indices);
			}
			indices.add(i);
		}
		
		for(int i = 0; i < I; i++) {
			int betterHalf = C - P[i];
			if(1 <= betterHalf && betterHalf <= 1000) {
				List<Integer> indices = map.get(betterHalf);
				if(indices != null) {
					Integer index = indices.get(0);
					index = i == index ? indices.size() > 1 ? indices.get(1) : null : index;
					if(index != null) {
						System.out.printf("Case #%d: %d %d\n", no, Math.min(i, index)+1, Math.max(i, index)+1);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		Object[][] cases = new Object[N][]; 
		
		for(int i = 0 ; i < N; i++) {
			int C = in.nextInt();
			int I = in.nextInt();
			int[] P = new int[I];
			for(int j = 0 ; j < I; j++) {
				P[j] = in.nextInt();
			}
			cases[i] = new Object[]{ C, I, P };
		}
		
		for(int i = 0; i < N; i++) {
			solve(i+1, cases[i]);
		}
		in.close();
	}
}
