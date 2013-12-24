/**
 * 
 */
package rnd.algo.string.search;

/**
 * @author Dev Naruka
 *
 */
public class ShobhitStringSearch {
	
	public static int search(String document, String query) {
		int index = -1, i =0, j = 0, k = query.length() - 1, l = query.length() - 1;
		
		if(query.length() < document.length()) {
			for(; k < document.length(); i++) {
				if(query.charAt(j) == document.charAt(i) && query.charAt(l) == document.charAt(k)) {
					if(index == -1) { index = i; }
					if(j == l || j+1 == l) {
						break;
					} else {
						j++;
						l--;
						k--;
					}
				} else {
					index = -1;
					j = 0;
					l = query.length() - 1;
					k = i + l + 1;
				}
			}
		}
		return index;
	}
	
	public static void main(String[] args) {
		String[] docs = {
			"abcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcdabcd",
			"aaadadddabcdabbdaccdbcadabcd",
			"jdhduialksem;alneifaop;sifj;aoej;j8a737alisuhyuagos7fyo8qyeoaiy3lir8yalkulchblably3io8 laweh uhasifahleuifhalhdkljah 83lrhawfh lahsfl"
		};
		String[] queries = {
			"abcd",
			"abcd",
			"8 l"
		};
		
		
		for(int i = 0; i < docs.length; i++) {
			System.out.println(search(docs[i], queries[i]));
		}
	}
}
