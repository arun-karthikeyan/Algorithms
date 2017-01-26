import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Starting with DP - wasn't able to get the perfect solution on my own, But got
 * close to it.| Divisible Group Sums - UVA 10616
 * 
 * @author arun
 *
 */
public class UVA10616 {
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

	private static int[] array;
	private static long memoi[][][];
	private static int n, m, d;

	private static long countWays(int idx, int m, int sum) {
		if (m == 0) {
			if (sum == 0) {
				return 1;
			} else {
				return 0;
			}
		}
		if (idx == n) {
			return 0;
		}
		if (memoi[idx][m][sum] == -1) {
			long ways = 0l;
			long newSum = sum + array[idx];
			if (newSum < 0) {
				newSum += ((long) d * Integer.MAX_VALUE);
			}
			newSum = newSum % d;
			ways = ways + countWays(idx + 1, m - 1, (int) newSum) + countWays(idx + 1, m, sum);
			memoi[idx][m][sum] = ways;
		}
		return memoi[idx][m][sum];
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int tc = 1;
		while (true) {
			n = readInt();
			int q = readInt();
			if (n == 0 && q == 0) {
				break;
			}
			pw.println("SET " + (tc++) + ":");
			array = new int[n];
			for (int i = 0; i < n; ++i) {
				array[i] = readInt();
			}
			for (int i = 0; i < q; ++i) {
				d = readInt();
				m = readInt();
				memoi = new long[n][m + 1][d];
				for (int j = 0; j < n; ++j)
					for (int k = 0; k <= m; ++k)
						Arrays.fill(memoi[j][k], -1);
				pw.println("QUERY " + (i + 1) + ": " + countWays(0, m, 0));
			}
		}

		pw.flush();
		pw.close();
	}

}
