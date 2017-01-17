import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Wedding Shopping - Bottom Up Approach, with reduced space trick
 * 
 * @author arun
 *
 */
public class UVA11450_2 {
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

	private static int m, c, clothes[][];

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = readInt();
		boolean[][] dp = new boolean[2][201];

		while (testcases-- > 0) {
			m = readInt();
			c = readInt();
			clothes = new int[c][];
			for (int i = 0; i < c; ++i) {
				int k = readInt();
				int[] models = new int[k];
				for (int j = 0; j < k; ++j) {
					models[j] = readInt();
				}
				clothes[i] = models;
			}
			Arrays.fill(dp[0], false);
			for (int i = 0, iLen = clothes[0].length; i < iLen; ++i) {
				int remainingMoney = m - clothes[0][i];
				if (remainingMoney >= 0) {
					dp[0][remainingMoney] = true;
				}
			}
			for (int i = 1, prevIdx = 0, curIdx = 1; i < c; ++i, prevIdx = (prevIdx + 1) % 2, curIdx = (curIdx + 1)
					% 2) {
				Arrays.fill(dp[curIdx], false);
				for (int j = 0; j <= 200; ++j) {
					if (dp[prevIdx][j]) {
						int[] currentModels = clothes[i];
						for (int k = 0, kLen = currentModels.length; k < kLen; ++k) {
							int remainingMoney = j - currentModels[k];
							if (remainingMoney >= 0) {
								dp[curIdx][remainingMoney] = true;
							}
						}
					}
				}
			}

			int optimal = -1;
			for (int i = 0, idx = ((c + 1) % 2); i <= 200; ++i) {
				if (dp[idx][i]) {
					optimal = m - i;
					break;
				}
			}
			if (optimal != -1) {
				pw.println(optimal);
			} else {
				pw.println("no solution");
			}
		}

		pw.flush();
		pw.close();
	}
}
