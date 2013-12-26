/**
 * 
 */
package rnd.amazon.ninja.shortestsubsegment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
public class Solution {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String source = in.nextLine();
		
		int K = in.nextInt(); in.nextLine();
		String[] words = new String[K];
		
		for(int i = 0; i < K; i++) {
			String word = in.nextLine();
			words[i] = word.trim().toLowerCase();
		}
		in.close();

//		long time = System.currentTimeMillis();
//		System.out.println("Started with time in ms: " + time);
//		findSegment(source, words);
		String segment = new SubSegmentFinder(source, words).find();
		if(segment == null) segment = "NO SUBSEGMENT FOUND";
		System.out.println(segment);
//		System.out.println("\nOutput in " + (System.currentTimeMillis() - time) + "ms");
	}
	
	private static class SubSegmentFinder {
		private final String[] words;
		private final char[] refined;
		private final WordMatcher matcher;
		
		private int head, tail, wordsAdded, bestCount;
		private boolean firstRun = true;
		
		int [] current = new int[2];
		private Slice bestMatch = null;
		
		public SubSegmentFinder(String source, String[] words) {
			this.words = words;
			this.matcher = new WordMatcher(words);
			this.refined = source.toCharArray();
			tail = skipSpaces(refined, 0);
			bestCount = Integer.MAX_VALUE;
		}
		
		public String find() {
			
			current[0] = 0;
			while(tail < refined.length) {
				char c = refined[tail];
				
				if(matcher.hasWordFor(c)) {
					Slice word = wordEnd(refined, tail);
					int s = tail;
					int e = tail + word.length;
					tail = e;
					
					if( ! firstRun ) wordsAdded++;
					
					if(matcher.matches(word, s)) {
						current[1] = e;

						if( firstRun ) { head = current[0] = s; firstRun = false; wordsAdded = 1; }
						
						if( wordsAdded > bestCount ) reset();
						else if(matcher.allMatched()) {
							int count = wordsAdded;

							if( count < bestCount ) {
								bestCount = count;
								bestMatch = new Slice(refined, current[0], current[1]);
							}
							if( count == words.length ) break; 		// best case
						
							reset();
						}
					}
				} else {
					Slice word = wordEnd(refined, tail);
					tail += word.length;

					if( ! firstRun ) wordsAdded++;
				}
				tail = skipSpaces(refined, tail);
			}
			
			if( bestMatch != null ) {
				return bestMatch.toString();
			}
			return null;
		}
		
		private void reset() {
			head = matcher.guessPointer();
			tail = head;
			current = new int[] { head, tail };
			matcher.reset();
			firstRun = true;
			wordsAdded = 0;
		}
		
		private static Slice wordEnd(char[] source, int offset) {
			int i = offset;
			while(i < source.length && isAlpahbet(source[i])) i++;
			return new Slice(source, offset, i);
		}
		
		private static int skipSpaces(char[] source, int offset) {
			int i = offset;
			while(i < source.length && !isAlpahbet(source[i])) i++;
			return i;
		}
		
		private static boolean isAlpahbet(char c) {
			return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
		}
	}
	
	private static class Slice {
		private final char[] data;
		private final int start;
		private final int end;
		public final int length;
		
		public Slice(char[] data, int start, int end) {
			this.data = data;
			this.start = start;
			this.end = end;
			this.length = end - start;
		}
		
		public char get(int index) {
			if(index < 0 && index >= length)
				throw new ArrayIndexOutOfBoundsException(index);
			
			return data [ start + index ];
		}
		
		private String str = null;
		@Override
		public String toString() {
			if(str != null) return str;
			
			StringBuilder b = new StringBuilder();
			
			boolean alpha = false;
			for(int i = start; i < end; i++) {
				char c = data[i];
				if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
					if(!alpha) { b.append(' '); }
					b.append(c);
					alpha = true;
				} else {
					alpha = false;
				}
			}
			
			return str = b.toString();
		}
	}

	private static class WordMatcher {
		private static final int CASE_DIFF = 'a'-'A';
		private static final int MAX_CHARS = 26;
		private static final String[] EMPTY_STRING_ARRAY = new String[] {};

		private final int[][] frequencyTable = new int[MAX_CHARS][];
		private final String[][] wordTable = new String[MAX_CHARS][];
		private final boolean[][] markTable = new boolean[MAX_CHARS][];
		private final int[] unMatchCountTable = new int[MAX_CHARS];
		private final int[][] pointerTable = new int[MAX_CHARS][];
		
		public WordMatcher(String[] words) {
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
					this.frequencyTable[i] = new int[table.size()];
					this.pointerTable[i] = new int[table.size()];
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
		
		public int guessPointer() {
			int p = Integer.MAX_VALUE, f = Integer.MAX_VALUE;
			
			for(int i = 0; i < MAX_CHARS; i++) {
				if(pointerTable[i] == null) continue;
				
				for(int j = 0; j < pointerTable[i].length; j++) {
					if(pointerTable[i][j] < p && frequencyTable[i][j] < f) {
						p = pointerTable[i][j];
						f = frequencyTable[i][j];
					} else if(pointerTable[i][j] > p && frequencyTable[i][j] > f) {
						p = pointerTable[i][j];
						f = frequencyTable[i][j];
					}
				}
			}
			return p;
		}
		
		public void reset() {
			for(int i = 0; i < MAX_CHARS; i++) {
				if(wordTable[i] != null) {
					markTable[i] = new boolean[ wordTable[i].length ];
					frequencyTable[i] = new int[ wordTable[i].length ];
					pointerTable[i] = new int[ wordTable[i].length ];
				}
				unMatchCountTable[i] = 0;
			}
		}

		public boolean matches(Slice word, int pointer) {
			int index = toIndex(word.get(0));
			int matched = matches(wordTable[index], word); 

			if(matched >= 0) {
				if( ! markTable[index][matched] ) unMatchCountTable[index]++;
				markTable[index][matched] = true;
				frequencyTable[index][matched]++;
				pointerTable[index][matched] = pointer;
				
				return true;
			}
			return false;
		}
		
		private static int matches(String[] words, Slice test) {
			for(int i = 0; i < words.length; i++) {
				if(words[i].length() == test.length) {
					boolean found = true;
					for(int j = 1; j < test.length; j++) {
						char c = test.get(j);
						if(c >= 'A' && c <= 'Z') c += CASE_DIFF;
						if(words[i].charAt(j) != c) {
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
