import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * LIS involving multidimensional sort -> greedy + dp
 * 
 * @author arun
 *
 */
public class UVA1196 {
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

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	private static final int L = 0, R = 1;

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		Comparator<int[]> blocksL = new java.util.Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				int c = o1[L] - o2[L];
				if (c == 0) {
					return o1[R] - o2[R];
				}
				return c;
			}
		};
		int n;
		while ((n = readInt()) != 0) {
			int[][] blocks = new int[n][2];
			for (int i = 0; i < n; ++i) {
				blocks[i][L] = readInt();
				blocks[i][R] = readInt();
			}
			Arrays.sort(blocks, blocksL);
			int[] dp = new int[n];
			dp[0] = 1;
			int finalMax = 1;
			for (int i = 1; i < n; ++i) {
				int max = 1;
				int currentR = blocks[i][R];
				for (int j = 0; j < i; ++j) {
					if (blocks[j][R] <= currentR) {
						max = max(max, dp[j] + 1);
					}
				}
				dp[i] = max;
				finalMax = max(finalMax, max);
			}
			pw.println(finalMax);
		}
		pw.println("*");
		pw.flush();
		pw.close();
	}
}
