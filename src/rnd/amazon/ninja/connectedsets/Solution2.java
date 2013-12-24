/**
 * 
 */
package rnd.amazon.ninja.connectedsets;

import java.util.Scanner;

/**
 * @author Dev Naruka
 *
 */
public class Solution2 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();

		for(int i = 0 ; i < T; i++) {
			int N = in.nextInt();
			
			int[][] buffer = new int[2][N];
			boolean [][] visited = new boolean[N][N];
			int connected = 0;
	
			for(int j = 0; j < N; j++) {
				for(int k = 0; k < N; k++) {
					buffer[1][k] = in.nextInt();

					if(buffer[1][k] == 1 && !visited[j][k]) {
						int duplicate = visit(buffer, visited, j, k, N);
//						connected ++;
//						connected -= duplicate;
						connected += duplicate;
					}
				}
				shift(buffer, N);
			}
			
			System.out.println(connected);
		}
		
		in.close();
	}
	
	private static void shift(int[][] buffer, int n) {
		for(int i = 0; i < n; i++) {
			buffer[0][i] = buffer[1][i];
			buffer[1][i] = 0;
		}
	}

	private static int visit(int[][] buffer, boolean[][] visited, int i, int j, int n) {
		// mark myself
		visited[i][j] = true;
		
		// Directions    w  nw   n  ne
		int[] iDiff = {  0, -1, -1, -1 };
		int[] jDiff = { -1, -1,  0,  1 };

		int alreadyVisited = 1;
		
		for (int a = 0; a < 4; a++) {
			int bi = 1 + iDiff[a];
			int I = i + iDiff[a];
			int J = j + jDiff[a];
			
			boolean validNode = inBounds(bi,2) && inBounds(I,n) && inBounds(J,n) && ( buffer[bi][J] == 1 );
			if(validNode) {
				if(visited[I][J]) {
					alreadyVisited--;
				} else {
					visited[I][J] = true;
					alreadyVisited++;
				}
			}
		}
		
		return alreadyVisited;
	}
	
	private static boolean inBounds(int x, int n) {
		return x >= 0 && x < n;
	}
}
