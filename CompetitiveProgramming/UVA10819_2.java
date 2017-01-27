import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * UVA 10819 - Trouble of 13-Dots | Bottom-Up approach - Much faster than the
 * top down approach with the space reduction trick. A great way to solve it,
 * got the hint from github:ajahuang. One of my favorite solutions
 * 
 * @author arun
 *
 */
public class UVA10819_2 {
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

	private static final int COST = 0, FAV = 1;

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n, m;

		while (true) {
			m = readInt();
			n = readInt();
			if (n == -1 && m == -1) {
				break;
			}
			int[][] base = new int[n][2];
			for (int i = 0; i < n; ++i) {
				base[i][COST] = readInt();
				base[i][FAV] = readInt();
			}
			int UB = m > 1800 ? m + 200 : m;
			int[] dp = new int[UB + 1];
			for (int i = 0; i < n; ++i) {
				for (int j = UB; j >= base[i][COST]; --j) {
					if (dp[j - base[i][COST]] > 0 || j == base[i][COST]) {
						dp[j] = Math.max(dp[j], dp[j - base[i][COST]] + base[i][FAV]);
					}
				}
			}
			int max = -1;
			// cool technique, doesn't exactly carry the result forward but
			// keeps it in place.
			// so we have to perform one final iteration over the dp array to
			// get the max value.
			if (m >= 1801 && m <= 2000) {
				for (int i = 0; i <= m; ++i) {
					max = Math.max(max, dp[i]);
				}
				// this is to avoid counting any fav. values that cost greater
				// than my budget 'm' but can't make it past the 2000 mark
				for (int i = 2001; i <= UB; ++i) {
					max = Math.max(max, dp[i]);
				}
			} else {
				for (int i = 0; i <= UB; ++i) {
					max = Math.max(max, dp[i]);
				}
			}
			pw.println(max);
		}

		pw.flush();
		pw.close();
	}
}
