import java.util.Arrays;
/**
 * Longest Increasing Subsequence - Top Down DP
 * @author arun
 *
 */
public class LIS {
	int[] array;
	int[] memo;

	public LIS(int[] array) {
		this.array = array;
		this.memo = new int[array.length];
		Arrays.fill(memo, -1);
	}

	public int findLIS(int idx) {
		if (memo[idx] == -1) {
			int max = 1;
			for (int i = idx + 1, iLen = array.length; i < iLen; ++i) {
				if (array[idx] > array[i]) {
					max = Math.max(max, 1 + findLIS(i));
				} else {
					max = Math.max(max, findLIS(i));
				}
			}
			return memo[idx] = max;
		} else {
			return memo[idx];
		}
	}

	public static void main(String[] args) throws Exception {
		LIS obj = new LIS(new int[] { -7, 10, 9, 2, 3, 8, 8, 1 });
		System.out.println("Longest Increasing sub-sequence : " + obj.findLIS(0));
	}
}
