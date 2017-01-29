import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Basic coin change
 * Straight forward DP
 * @author arun
 *
 */
public class UVA357 {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];

	private static int readByte() {
		if (totalchars < 0)
			return 0;
		if (offset >= totalchars) {
			offset = 0;
			try {
				totalchars = stream.read(buffer);
			} catch (IOException e) {
				return 0;
			}
			if (totalchars <= 0)
				return -1;
		}
		return buffer[offset++];
	}

	private static int readInt() {
		int number = readByte();

		while (eolchar(number))
			number = readByte();

		int sign = 1;
		int val = 0;

		if (number == '-') {
			sign = -1;
			number = readByte();
		}

		do {
			if ((number < '0') || (number > '9'))
				return -1;
			val *= 10;
			val += (number - '0');
			number = readByte();
		} while (!eolchar(number));

		return sign * val;
	}

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	private static final int[] denominations = new int[] { 1, 5, 10, 25, 50 };

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		// precompute all answers
		long[] dp = new long[30001];
		for (int i = 0; i < 5; ++i) {
			int currentDenomination = denominations[i];
			dp[currentDenomination]++;
			for (int j = currentDenomination + 1; j < 30001; ++j) {
				dp[j] += dp[j - currentDenomination];
			}
		}
		String p1 = "There is only 1 way to produce ", p2 = " cents change.";
		String p3 = "There are ", p4 = " ways to produce ", p5 = " cents change.";
		int n;
		while ((n = readInt()) != -1) {
			// O(1) query
			if (dp[n] <= 1) {
				pw.println(p1 + n + p2);
			} else {
				pw.println(p3 + dp[n] + p4 + n + p5);
			}
		}

		pw.flush();
		pw.close();
	}
}
