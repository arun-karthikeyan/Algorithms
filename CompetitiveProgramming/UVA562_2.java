import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Dividing coins - Bottom up approach | still a little faster than the top down
 * approach
 * 
 * @author arun
 *
 */
public class UVA562_2 {
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

		int testcases = readInt();

		while (testcases-- > 0) {
			int n = readInt();
			int[] coins = new int[n];
			int total = 0;
			for (int i = 0; i < n; ++i) {
				coins[i] = readInt();
				total += coins[i];
			}
			int[] sum = new int[total + 1];
			int min = total;
			for (int j = 0; j < n; ++j) {
				for (int i = total, coin = coins[j]; i >= coin; --i) {
					if (sum[i - coin] > 0 || i == coin) {
						int oldSum = sum[i];
						int newSum = sum[i - coin] + coin;
						int oldMinDiff = Math.abs(2 * oldSum - total);
						int newMinDiff = Math.abs(2 * newSum - total);
						if (newMinDiff < oldMinDiff) {
							sum[i] = newSum;
							min = Math.min(newMinDiff, min);
						}
					}
				}
			}

			pw.println(min);
		}

		pw.flush();
		pw.close();
	}
}
