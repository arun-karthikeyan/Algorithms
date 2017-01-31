import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * bar codes - Top Down Approach
 * 
 * @author arun
 *
 */
public class UVA10721 {
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

	private static int n, k, m;
	private static long[][][] memo = new long[51][51][51];

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	private static long solve(int idx, int bars, int cLen) {
		if (bars < 0 || cLen < 0) {
			return 0;
		}
		if (idx == 0) {
			if (bars == 0) {
				return 1;
			}
			return 0;
		}
		if (memo[idx][bars][cLen] == -1) {
			long m1 = solve(idx - 1, bars, cLen - 1);
			long m2 = solve(idx - 1, bars - 1, m - 1);
			memo[idx][bars][cLen] = m1 + m2;
		}
		return memo[idx][bars][cLen];
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		while (true) {
			n = readInt();
			if (n == 0) {
				break;
			}
			k = readInt();
			m = readInt();
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < k; ++j) {
					Arrays.fill(memo[i][j], 0, m, -1);
				}
			}
			pw.println(solve(n - 1, k - 1, m - 1));
		}

		pw.flush();
		pw.close();
	}
}
