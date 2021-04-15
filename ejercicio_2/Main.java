import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int heapSize = in.nextInt();
		int[] sequence = new int[heapSize+1];
		for (int i = 1; i < sequence.length; i++) {
			sequence[i] = in.nextInt();
		}
		System.out.println(isHeapSequence(sequence, 1) ? "1" : "0");
	}

	public static boolean isHeapSequence(int[] sequence, int rootPos) {
		if (rootPos >= sequence.length || rootPos*2 >= sequence.length) return true;
		if (rootPos*2 < sequence.length && rootPos*2+1 > sequence.length) {
			return sequence[rootPos] < sequence[rootPos*2];
		}
		
		if (sequence[rootPos] < sequence[2*rootPos] && sequence[rootPos] < sequence[2*rootPos+1]) {
			return isHeapSequence(sequence, 2*rootPos) && isHeapSequence(sequence, 2*rootPos+1);
		} 	
		return false;
	}
}

