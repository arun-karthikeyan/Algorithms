import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Cutting Sticks - Bottom Up Approach
 * 
 * @author arun
 *
 */
public class UVA10003_3 {
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
				return 0;
			val *= 10;
			val += (number - '0');
			number = readByte();
		} while (!eolchar(number));

		return sign * val;
	}

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int len;
		int[][] dp = new int[1001][1001];
		while ((len = readInt()) != 0) {
			int m = readInt();
			int[] cuts = new int[m + 2];
			cuts[m + 1] = len;
			for (int i = 1; i <= m; ++i) {
				cuts[i] = readInt();
			}
			for (int i = 1, iLen = cuts.length, prev = cuts[0]; i < iLen; ++i) {
				dp[prev][prev = cuts[i]] = 0;
			}
			for (int i = 2, iLen = m + 2; i < iLen; ++i) {
				for (int j = 0, jLen = iLen - i, l = i; j < jLen; ++j, ++l) {
					int c1 = cuts[j], c2 = cuts[l], cost = c2 - c1;
					dp[c1][c2] = Integer.MAX_VALUE;
					for (int k = j + 1; k < l; ++k) {
						dp[c1][c2] = Math.min(dp[c1][c2], dp[c1][cuts[k]] + dp[cuts[k]][c2] + cost);
					}
				}
			}
			pw.println("The minimum cutting is " + dp[0][len] + ".");
		}

		pw.flush();
		pw.close();
	}
}
