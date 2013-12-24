/**
 * 
 */
package rnd.algo.string.search;

/**
 * @author dev
 * 
 */
public class UniqueCharacter {
	public static boolean isUniqueChars(String str) {
		int checker = 0;
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			int val = c - 'a';
			if ((checker & (1 << val)) > 0)
				return false;
			checker |= (1 << val);
		}
		return true;
	}
	
	public static String removeDuplicates(char[] str) {
		int checker = 0;
		int s = 0;
		for (int i = 0; i < str.length; ++i) {
			char c = str[i];
			int val = c - 'a';
			if ((checker & (1 << val)) > 0) {
				s++;
			} else {
				str[i - s] = c;
			}
			checker |= (1 << val);
		}
		return new String(str, 0, str.length - s);
	}
	
	public static void main(String[] args) {
		String[] test = {
			"aaaaaaaa",
			"abcdefgh",
			"zgfedcba",
			"ijklmnop",
			"abcdefga",
			"abcdabcd",
			"abababab",
			"bcdabcda",
			"aaaaabcda"
			
		};
		
		for(String t : test) {
			boolean unique = isUniqueChars(t);
			System.out.println(t + ": has unique characters? " + unique + " unique: " + removeDuplicates(t.toCharArray()));
		}
		
		System.out.println((long)0x10FFFF);
		
		long t2 = 0L;
		for(int i = 0 ; i < 128; i++) {
			long tmp = (1L << i);
			System.out.printf("1 << %3d = %20d\t%20d %64s\n", i, tmp, t2 += tmp, Long.toBinaryString(1L << i));
//			if(tmp < 0) {
//				break;
//			}
		}
		
//		for(int i = 0 ; i < 128; i++) {
//			int tmp = (i >> 1);
//			System.out.printf("%3d >> 1 = %d\n", i, tmp);
////			if(tmp < 0) {
////				break;
////			}
//		}
	}
}
