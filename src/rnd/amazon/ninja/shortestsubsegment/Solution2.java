/**
 * 
 */
package rnd.amazon.ninja.shortestsubsegment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Given a paragraph of text, write a program to find the first shortest
 * sub-segment that contains each of the given k words at least once. A segment
 * is said to be shorter than other if it contains less number of words. <br />
 * <br />
 * 
 * Ignore characters other than [a-z][A-Z] in the text. Comparison between the
 * strings should be case-insensitive. <br />
 * <br />
 * 
 * If no sub-segment is found then the program should output “NO SUBSEGMENT
 * FOUND”. <br />
 * <br />
 * 
 * 
 * 
 * @author Dev Naruka
 * 
 */
public class Solution2 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String source = in.nextLine();
		
		int K = in.nextInt(); in.nextLine();
		Set<String> words = new HashSet<String>(K);
		
		for(int i = 0; i < K; i++) {
			String word = in.nextLine();
			words.add(word.trim().toLowerCase());
		}
		in.close();

		findSegment(source, words);
	}

	private static void findSegment(String source, Set<String> words) {
		final WordMatcher matcher = new WordMatcher(words);
		String refined = source.replaceAll("[^A-Za-z ]", " ").replaceAll("[\\s]+", " ");
		
		int head = 0, tail = 0, wordsAdded = 0, bestCount = Integer.MAX_VALUE;
		boolean headChange = true;
		
		StringBuilder builder = null;
		String bestMatch = null;
		
		while(tail < refined.length()) {
			char c = refined.charAt(tail);
			
			if(matcher.hasWordFor(c)) {
				int s = tail;
				int e = wordEnd(refined, s);
				tail = e;
				
				String word = refined.substring(s, e);
				if(matcher.matches(word)) {
					if(builder == null) builder = new StringBuilder();
					else builder.append(' ');
					
					builder.append(word); wordsAdded++;

					if( headChange ) { head = tail; headChange = false; }
					
					if(matcher.allMatched()) {
						String str = builder.toString();
						int count = countWords(str);
						
						if( count < bestCount ) {
							bestCount = count;
							bestMatch = str;
						}
						if( count == words.size() ) break; 		// best case
						
						matcher.reset();
						builder = null;
						tail = head;
						headChange = true;
						wordsAdded = 0;
					}
					
					if( wordsAdded > bestCount ) {
						matcher.reset();
						builder = null;
						tail = head;
						headChange = true;
						wordsAdded = 0;
					}
				} else {
					if(builder != null) {
						builder.append(' ');
						builder.append(word); wordsAdded++;
					}
				}
			} else {
				int s = tail;
				int e = wordEnd(refined, s);
				tail = e;

				String word = refined.substring(s, e);
				if(builder != null) {
					builder.append(' ');
					builder.append(word); wordsAdded++;
				}
			}
			tail = skipSpaces(refined, tail);
		}
		
		if( bestMatch != null ) {
			System.out.println(bestMatch);
		} else {
			System.out.println("NO SUBSEGMENT FOUND");
		}
	}
	
	private static int countWords(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		
		int spaces = 0, cursor = 0, ix;
		while ((ix = str.indexOf(' ', cursor)) != -1) {
			++spaces;
			cursor = ix + 1;
		}
		return spaces + 1;
	}
	
	private static int wordEnd(String source, int offset) {
		int i = offset;
		while(i < source.length() && source.charAt(i) != ' ') i++;
		return i;
	}
	
	private static int skipSpaces(String source, int offset) {
		int i = offset;
		while(i < source.length() && source.charAt(i) == ' ') i++;
		return i;
	}

	private static class WordMatcher {
		private static final int MAX_CHARS = 26;
		private static final String[] EMPTY_STRING_ARRAY = new String[] {};
		
		private final String[][] wordTable = new String[MAX_CHARS][];
		private final boolean[][] markTable = new boolean[MAX_CHARS][];
		private final int[] unMatchCountTable = new int[MAX_CHARS];
		
		public WordMatcher(Set<String> words) {
			assert words == null : "Parameter words should be not null.";
			
			@SuppressWarnings("unchecked")
			List<String>[] wordTable = new List[MAX_CHARS];
			
			for(String word: words) {
				word = word.toLowerCase();
				char c = word.charAt(0);
				int index = c - 'a';
			
				List<String> cWords = wordTable[index];
				if(cWords == null) { cWords = new ArrayList<String>(); wordTable[index] = cWords; }
						
				cWords.add(word);
			}
			
			for(int i = 0; i < MAX_CHARS; i++) {
				List<String> table = wordTable[i];
				
				if(table != null) {
					Collections.sort(table);
					
					this.wordTable[i] = table.toArray(EMPTY_STRING_ARRAY);
					this.markTable[i] = new boolean[table.size()];
					this.unMatchCountTable[i] = 0;
				}
			}
		}
		
		private static int toIndex(char c) {
			return c > 90 ? c - 'a' : c - 'A';
		}
		
		public boolean hasWordFor(char c) {
			int i = toIndex(c);

			return wordTable[i] != null && wordTable[i].length > 0;
		}

		public void reset() {
			for(int i = 0; i < MAX_CHARS; i++) {
				if(wordTable[i] != null) {
					markTable[i] = new boolean[ wordTable[i].length ];
				}
				unMatchCountTable[i] = 0;
			}
		}

		public boolean matches(String word) {
			int index = toIndex(word.charAt(0));
			int matched = matches(wordTable[index], word); 
			if(matched >= 0) {
				if( ! markTable[index][matched] ) unMatchCountTable[index]++;
				markTable[index][matched] = true;
				return true;
			}
			return false;
		}
		
		private static int matches(String[] words, String test) {
			for(int i = 0; i < words.length; i++) {
				if(words[i].length() == test.length()) {
					boolean found = true;
					for(int j = 1; j < test.length(); j++) {
						if(words[i].charAt(j) != test.charAt(j)) {
							found = false;
							break;
						}
					}
					if(found) return i;
				}
			}
			return -1;
		}
		
		public boolean allMatched() {
			for(int i = 0; i < MAX_CHARS; i++) {
				if(wordTable[i] != null && wordTable[i].length != unMatchCountTable[i]) {
					return false;
				}
			}
			return true;
		}
	}
}
