import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Basic 0/1 Knapsack DP O(nk) pseudo polynomial algorithm
 *
 **/
public class ZBOKnapsack {
	public static int bottomUp(int[] array, int k) {
		int[] dp = new int[k + 1];
		for (int i = 0, iLen = array.length; i < iLen; ++i) {
			int start = array[i];
			for (int j = start; j <= k; ++j) {
				dp[j] = Math.max(dp[j], dp[j - start] + start);
				if (j > 0)
					dp[j] = Math.max(dp[j], dp[j - 1]);
			}
		}
		return dp[k];
	}

	public static int topDown(int[] array, int k) {
		return k - runTopDownKnapsack(array, 0, k);
	}

	public static int runTopDownKnapsack(int[] array, int idx, int remainingWeight) {
		if (idx == array.length) {
			return remainingWeight;
		}

		int currentWeight = array[idx];
		int m1 = Integer.MAX_VALUE;
		if (currentWeight <= remainingWeight) {
			// can pick this weight one or more times
			m1 = runTopDownKnapsack(array, idx, remainingWeight - currentWeight);

		}
		// can ignore it
		return Math.min(m1, runTopDownKnapsack(array, idx + 1, remainingWeight));
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int t = Integer.parseInt(br.readLine());
		while (t-- > 0) {
			String[] info = br.readLine().split(" ");
			int n = Integer.parseInt(info[0]);
			int k = Integer.parseInt(info[1]);
			int[] array = new int[n];
			info = br.readLine().split(" ");
			for (int i = 0; i < n; ++i) {
				array[i] = Integer.parseInt(info[i]);
			}
			pw.println(bottomUp(array, k));
		}

		br.close();
		pw.flush();
		pw.close();
	}
}