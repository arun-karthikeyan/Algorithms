import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Bottom up approach - new technique re-using from 13dots
 * 
 * @author arun
 *
 */
public class UVA10130_2 {
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

	private static final int V = 0, W = 1;
	private static int dp[] = new int[31], items[][] = new int[1000][2];

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = readInt();

		while (testcases-- > 0) {
			int n = readInt();
			items = new int[n][2];
			for (int i = 0; i < n; ++i) {
				items[i][V] = readInt();
				items[i][W] = readInt();
			}
			int g = readInt(), total = 0;
			Arrays.fill(dp, 0);
			for (int i = 0; i < n; ++i) {
				for (int j = 30, jLow = items[i][W]; j >= jLow; --j) {
					if (j == jLow || dp[j - items[i][W]] > 0) {
						dp[j] = Math.max(dp[j], items[i][V] + dp[j - items[i][W]]);
					}
				}
			}
			int[] weights = new int[g];
			for (int i = 0; i < g; ++i) {
				weights[i] = readInt();
			}
			Arrays.sort(weights);
			int maxTillNow = 0;
			for (int i = 0, j = 0; i <= 30; ++i) {
				maxTillNow = Math.max(maxTillNow, dp[i]);
				while (j < g && weights[j] == i) {
					total += maxTillNow;
					j++;
				}
			}
			pw.println(total);
		}

		pw.flush();
		pw.close();
	}
}
