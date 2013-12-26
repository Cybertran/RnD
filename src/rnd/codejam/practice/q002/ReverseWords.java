package rnd.codejam.practice.q002;

import java.util.Scanner;

/**
 * Problem
 * 
 * Given a list of space separated words, reverse the order of the words. Each
 * line of text contains L letters and W words. A line will only consist of
 * letters and space characters. There will be exactly one space character
 * between each pair of consecutive words.
 * 
 * Input
 * 
 * The first line of input gives the number of cases, N. N test cases follow.
 * For each test case there will a line of letters and space characters
 * indicating a list of space separated words. Spaces will not appear at the
 * start or end of a line.
 * 
 * Output
 * 
 * For each test case, output one line containing "Case #x: " followed by the
 * list of words in reverse order.
 * 
 * Limits
 * 
 * Small dataset
 * 
 * N = 5
 * 1 ≤ L ≤ 25
 * 
 * Large dataset
 * 
 * N = 100
 * 1 ≤ L ≤ 1000
 * 
 */
public class ReverseWords {

	private static void solve(int no, Object[] objects) {
		String[] words = (String[]) objects;
		
		if(words != null && words.length > 0) {
			System.out.printf("Case #%d: ", no);
			for(int i = words.length - 1; i >= 0; i--) {
				System.out.print(words[i]);
				if(i > 0) {
					System.out.print(' ');
				}
			}
			System.out.print('\n');
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		Object[][] cases = new Object[N][]; 
		
		in.nextLine();
		for(int i = 0 ; i < N; i++) {
			String line = in.nextLine();
			String[] words = line.split(" ");
			cases[i] = words;
		}
		
		for(int i = 0; i < N; i++) {
			solve(i+1, cases[i]);
		}
		in.close();
	}
}
