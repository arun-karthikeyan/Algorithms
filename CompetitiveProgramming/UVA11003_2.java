import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Boxes - Bottom Solution
 * 
 * @author arun
 *
 */
public class UVA11003_2 {
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

		int n, groundCapacity = 6001;
		int[] dp = new int[groundCapacity];
		int[] weight = new int[1000];
		int[] capacity = new int[1000];
		while ((n = readInt()) != 0) {
			Arrays.fill(dp, 0);
			for (int i = 0; i < n; ++i) {
				weight[i] = readInt();
				capacity[i] = readInt();
			}
			int max = 0;
			for (int i = n - 1; i >= 0; --i) {
				for (int j = weight[i]+capacity[i], jLimit = weight[i]; j >= jLimit; --j) {
					dp[j] = Math.max(dp[j], 1 + dp[Math.min(capacity[i], j - weight[i])]);
					max = Math.max(max, dp[j]);
				}
			}
			pw.println(max);
		}

		pw.flush();
		pw.close();
	}
}
