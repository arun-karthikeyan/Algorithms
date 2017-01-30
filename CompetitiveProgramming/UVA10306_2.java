import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * E-Coins | Bottom Up Approach, a tad bit faster than top down
 * 
 * @author arun
 *
 */
public class UVA10306_2 {
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
		String notPossible = "not possible";
		int testcases = readInt();
		int dp[][] = new int[301][301];
		while (testcases-- > 0) {
			int m = readInt(), s = readInt();
			int[] cCoins = new int[m];
			int[] iCoins = new int[m];
			for (int i = 0; i < m; ++i) {
				cCoins[i] = readInt();
				iCoins[i] = readInt();
			}
			for (int i = 0; i <= s; ++i) {
				Arrays.fill(dp[i], 0, s + 1, 0);
			}
			int min = 301;
			for (int i = 0; i < m; ++i) {
				if (cCoins[i] > 0 || iCoins[i] > 0) {
					for (int j = cCoins[i]; j <= s; ++j) {
						for (int k = iCoins[i]; k <= s; ++k) {
							if ((j == cCoins[i] && k == iCoins[i]) || (dp[j - cCoins[i]][k - iCoins[i]] > 0)) {
								if (dp[j][k] > 0) {
									dp[j][k] = Math.min(dp[j][k], 1 + dp[j - cCoins[i]][k - iCoins[i]]);
								} else {
									dp[j][k] = dp[j - cCoins[i]][k - iCoins[i]] + 1;
								}
								if (j * j + k * k == s * s) {
									min = Math.min(min, dp[j][k]);
								}
							}
						}
					}
				}
			}
			if (min > 300) {
				pw.println(notPossible);
			} else {
				pw.println(min);
			}

		}

		pw.flush();
		pw.close();
	}
}
