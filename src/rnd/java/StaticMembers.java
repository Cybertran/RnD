package rnd.java;

public class StaticMembers {
	static int b = getA();
	static int a = 5;
	
	static int getA() {
		return a;
	}
	
	public static void main(String[] args) {
		System.out.printf("a = %d, b = %d\n", a , b);
		
		int test = 2000;
//		println(test);
//		println(~test);
//		println(-test);
//		println(~test - test);
//		println(test - ~test);
//		println(~test + test);
//		println(test + ~test);
//		println( ~(test + ~test));
//		println(1);
		
		oneOrZero(1);
		oneOrZero(2);
		oneOrZero(8);
		oneOrZero(134578741);
		oneOrZero(-1);
		oneOrZero(-8);
		oneOrZero(32);
		oneOrZero(8);
		oneOrZero(64);
		oneOrZero(0);
	}
	
	private static void println(int i) {
		System.out.printf("%32s : %+10d\n",Integer.toBinaryString(i), i);
	}
	
	private static void oneOrZero(int i) {
		int r = 1-(1 >> i); //(i << 0 | ( (1 >> i) << 1 ));
		System.out.printf("%32s : %32s : %+10d : %d\n",Integer.toBinaryString(i), Integer.toBinaryString(r), i, r);
	}
}
