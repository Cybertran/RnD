/**
 * 
 */
package rnd.amazon.ninja.connectedsets;

import java.util.Scanner;

/**
 * @author Dev Naruka
 *
 */
public class Solution {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();

		for(int i = 0 ; i < T; i++) {
			int N = in.nextInt();
			
			int [][] matrix = new int[N][N];
			for(int j = 0 ; j < N; j++) {
				for(int k = 0; k < N; k++) {
					matrix[j][k] = in.nextInt();
				}
			}
			
			int count = countConnected(matrix, N);
			System.out.println(count);
		}
		
		in.close();
	}

	private static int countConnected(int [][] matrix, int n) {
		int connected = 0;
		
		boolean [][] visited = new boolean[n][n];
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(matrix[i][j] == 1 && !visited[i][j]) {
					connected ++;
					visit(matrix, visited, i, j, n);
				}
			}
		}
		
		return connected;
	}

	private static void visit(int[][] matrix, boolean[][] visited, int i, int j, int n) {
		// mark myself
		visited[i][j] = true;
		
		// Directions    n   s   e   w  ne  nw  se  sw
		int[] iDiff = { -1,  1,  0,  0, -1, -1,  1,  1 };
		int[] jDiff = {  0,  0,  1, -1,  1, -1,  1, -1 };

		for (int a = 0; a < 8; a++) {
			int I = i + iDiff[a];
			int J = j + jDiff[a];
			
			boolean validNode = inBounds(I,n) && inBounds(J,n) && ( matrix[I][J] == 1 );
			if(validNode) {
				if( ! visited[I][J] ) {
					visit(matrix, visited, I, J, n);
				}
			}
		}
	}
	
	private static boolean inBounds(int x, int n) {
		return x >= 0 && x < n;
	}
}
