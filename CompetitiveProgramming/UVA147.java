import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA147 {
	private static int readInt(String d) {
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

	private static String rightJustify(String line) {
		StringBuilder sb = new StringBuilder();
		for (int i = line.length(); i < 6; ++i) {
			sb.append(" ");
		}
		sb.append(line);
		return sb.toString();
	}

	private static final int den[] = new int[] { 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		// preCompute
		long[] dp = new long[30001];
		dp[0] = 1;
		for (int i = 0; i < 11; ++i) {
			for (int j = den[i]; j <= 30000; ++j) {
				dp[j] += dp[j - den[i]];
			}
		}
		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			int change = readInt(line);
			if (change == 0) {
				break;
			}
			long result = dp[change];
			pw.printf("%s%17d\n", rightJustify(line), result);

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
