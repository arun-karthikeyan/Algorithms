import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Pay the Price - Bottom up, similar to sum of primes from the zbokp section
 * 
 * @author arun
 *
 */
public class UVA10313 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		// 300*300*1000 pre-computation and O(1) result
		long[][] dp = new long[301][1001];
		dp[0][0] = 1;
		for (int i = 1; i < 301; ++i) {
			for (int j = 1; j < 301; ++j) {
				for (int k = i; k < 1001; ++k) {
					dp[j][k] += dp[j - 1][k - i];
				}
			}
		}
		for (int i = 1; i < 301; ++i) {
			for (int j = 0; j < 1001; ++j) {
				dp[i][j] += dp[i - 1][j];
			}
		}
		String line;
		while ((line = br.readLine()) != null) {
			String[] info = line.split(" ");
			int size = info.length;
			int amount = Integer.parseInt(info[0]), l = 0, r = 300;
			if (size == 2) {
				r = Math.min(Integer.parseInt(info[1]), 300);
			} else if (size == 3) {
				l = Integer.parseInt(info[1]);
				r = Math.min(Integer.parseInt(info[2]), 300);
			}
			if (l > 300) {
				pw.println(0);
			} else {
				long result = dp[r][amount];
				if (l > 0) {
					result -= dp[l - 1][amount];
				}
				pw.println(result);
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
