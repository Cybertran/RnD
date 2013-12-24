package rnd.codejam.practice.q003;

import java.util.Map;
import java.util.Scanner;

/*
 * Problem
 * 
 * The Latin alphabet contains 26 characters and telephones only have ten digits
 * on the keypad. We would like to make it easier to write a message to your
 * friend using a sequence of keypresses to indicate the desired characters. The
 * letters are mapped onto the digits as shown below. To insert the character B
 * for instance, the program would press 22. In order to insert two characters
 * in sequence from the same key, the user must pause before pressing the key a
 * second time. The space character ' ' should be printed to indicate a pause.
 * For example, 2 2 indicates AA whereas 22 indicates B.
 * 
 * 
 * Input
 * 
 * The first line of input gives the number of cases, N. N test cases follow.
 * Each case is a line of text formatted as
 * 
 * desired_message Each message will consist of only lowercase characters a-z
 * and space characters ' '. Pressing zero emits a space.
 * 
 * Output
 * 
 * For each test case, output one line containing "Case #x: " followed by the
 * message translated into the sequence of keypresses.
 * 
 * Limits
 * 
 * 1 ≤ N ≤ 100.
 * 
 * Small dataset
 * 
 * 1 ≤ length of message in characters ≤ 15.
 * 
 * Large dataset
 * 
 * 1 ≤ length of message in characters ≤ 1000.
 * 
 */
public class T9Spelling {

	private static final String[] CHARMAP = {
		"2", "22", "222",
		"3", "33", "333",
		"4","44","444",
		"5","55","555",
		"6","66","666",
		"7","77","777","7777",
		"8","88","888",
		"9","99","999","9999"
	};
	private static final int[] KEYMAP = {
		2, 2, 2,
		3, 3, 3,
		4, 4, 4,
		5, 5, 5,
		6, 6, 6,
		7, 7, 7, 7,
		8, 8, 8,
		9, 9, 9, 9
	};
	
	private static void solve(int no, Object[] objects) {
		String[] lines = (String[]) objects;
		String line = lines[0];
		
		StringBuilder b = new StringBuilder();
		int lastkey = '\n';
		for(int i = 0; i < line.length(); i++) {
			char letter = line.charAt(i);
			if(letter == ' ') {
				if(lastkey == 0) {
					b.append(' ');
				}
				b.append("0");
				lastkey = 0;
			} else {
				int index = letter - 'a';
				if(0 <= index && index <= CHARMAP.length) {
					if(lastkey == KEYMAP[index]) {
						b.append(' ');
					}
					b.append(CHARMAP[index]);
					lastkey = KEYMAP[index];
				} else {
//					System.err.println("Character '"+letter+"' not found from line \""+line+"\"");
				}
			}
		}
		System.out.printf("Case #%d: %s\n", no, b.toString());
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
			cases[i] = new String[] { line };
		}
		
		for(int i = 0; i < N; i++) {
			solve(i+1, cases[i]);
		}
		in.close();
	}
}
