import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class CoinChange {
	/**
	 * Top Down Approach to count the distinct possible ways to make change
	 * 
	 * @param coins
	 * @param memoi
	 * @param idx
	 * @param len
	 * @param rem
	 * @return
	 */
	private static long countPossibleWaysToMakeChange(int[] coins, long[][] memoi, int idx, int len, int rem) {
		if (rem == 0) {
			return 1;
		}
		if (idx == len) {
			return 0;
		}
		if (memoi[idx][rem] == -1) {
			int currentDen = coins[idx];
			long sum = 0;
			for (int i = 0; i <= rem; i += currentDen) {
				sum += countPossibleWaysToMakeChange(coins, memoi, idx + 1, len, rem - i);
			}
			return memoi[idx][rem] = sum;
		}
		return memoi[idx][rem];
	}

	/**
	 * Bottom Up Approach to count the distinct possible ways to make change
	 * 
	 * @param coins
	 * @param n
	 * @return
	 */
	private static long countPossibleWaysBottomUp(int[] coins, int n) {
		long[] dp = new long[n + 1];
		for (int i = 0, iLen = coins.length; i < iLen; ++i) {
			int currentVal = coins[i];
			if (currentVal <= n) {
				dp[currentVal]++;
			}
			for (int j = currentVal + 1; j <= n; ++j) {
				dp[j] += dp[j - currentVal];
			}
		}
		return dp[n];
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		String[] info = br.readLine().split("\\s+");
		int n = Integer.parseInt(info[0]), m = Integer.parseInt(info[1]);
		int[] coins = new int[m];
		info = br.readLine().split("\\s+");
		long[][] memoi = new long[m][n + 1];
		for (int i = 0; i < m; ++i) {
			coins[i] = Integer.parseInt(info[i]);
			Arrays.fill(memoi[i], -1);
		}
		pw.println("Top Down Approach : " + countPossibleWaysToMakeChange(coins, memoi, 0, coins.length, n));
		pw.println("Bottom Up Approach: " + countPossibleWaysBottomUp(coins, n));
		// pw.println(countPossibleWaysBottomUp(coins, n));
		pw.flush();
		pw.close();
		br.close();
	}
}