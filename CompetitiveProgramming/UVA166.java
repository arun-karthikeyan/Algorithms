import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA166 {
	private static final int[] den = new int[] { 5, 10, 20, 50, 100, 200 };

	private static int readDouble(String d) {
		int val = 0;
		for (int i = d.length() - 1, mul = 1; i >= 0; --i) {
			char c = d.charAt(i);
			if (c != '.') {
				val += ((c - '0') * mul);
				mul *= 10;
			}
		}
		return val;
	}

	public static void main(String[] args) throws Exception {
//		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int[] minCoinSK = new int[10001];
		for (int i = 0; i < 6; ++i) {
			int cd = den[i];
			minCoinSK[cd] = 1;
			for (int j = den[i] + 1; j <= 10000; ++j) {
				if (minCoinSK[j - cd] > 0) {
					if (minCoinSK[j] > 0) {
						minCoinSK[j] = Math.min(minCoinSK[j], minCoinSK[j - cd] + 1);
					} else {
						minCoinSK[j] = minCoinSK[j - cd] + 1;
					}
				}
			}
		}

		String line;
		while ((line = br.readLine()) != null) {
			int[] coins = new int[6];
			String[] info = line.split(" ");
			int max = 0;
			for (int i = 0; i < 6; ++i) {
				coins[i] = Integer.parseInt(info[i]);
				max = max + (coins[i] * den[i]);
			}
			if (max == 0) {
				break;
			}
			int change = readDouble(info[6]);
			int[] minCoinCust = new int[max + 1];
			for (int i = 0; i < 6; ++i) {
				int ccount = coins[i];
				int coin = den[i];
				for (int j = 0; j < ccount; ++j) {
					for (int k = max; k >= coin; --k) {
						if (minCoinCust[k - coin] > 0 || k == coin) {
							if (minCoinCust[k] > 0) {
								minCoinCust[k] = Math.min(minCoinCust[k], minCoinCust[k - coin] + 1);
							} else {
								minCoinCust[k] = minCoinCust[k - coin] + 1;
							}
						}
					}
				}
			}
			int minexchange = minCoinCust[change] > 0 ? minCoinCust[change] : 101;
			for (int i = change + 5; i <= max; ++i) {
				if (minCoinCust[i] > 0) {
					minexchange = Math.min(minexchange, minCoinCust[i] + minCoinSK[i - change]);
				}
			}
			pw.printf("%3d\n", minexchange);
		}
		br.close();
		pw.flush();
		pw.close();
	}
}
