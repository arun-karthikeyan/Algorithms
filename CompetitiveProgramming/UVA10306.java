import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * e-coins - Top Down Approach
 * 
 * @author arun
 *
 */
public class UVA10306 {
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

	static class ECoin {
		int cVal;
		int iVal;

		public ECoin(int cVal, int iVal) {
			this.cVal = cVal;
			this.iVal = iVal;
		}
	}

	private static ECoin[] coins;
	private static int MAX, m, s;
	private static int memo[][][] = new int[41][301][301];

	private static int solve(int idx, int cSum, int iSum) {
		int newSum = cSum * cSum + iSum * iSum;
		if (newSum == MAX) {
			return 0;
		}
		if (idx == m) {
			return 301;
		}
		if (memo[idx][cSum][iSum] == -1) {
			int is = coins[idx].iVal, cs = coins[idx].cVal;
			int min = solve(idx + 1, cSum, iSum);
			if (is != 0 || cs != 0) {
				for (int i = 1;; ++i) {
					int currentCs = (i * cs);
					int currentIs = (i * is);
					int overAll = ((cSum + currentCs) * (cSum + currentCs)) + ((iSum + currentIs) * (iSum + currentIs));
					if (overAll <= MAX) {
						min = Math.min(min, i + solve(idx + 1, cSum + currentCs, iSum + currentIs));
					} else {
						break;
					}
				}
			}
			memo[idx][cSum][iSum] = min;
		}
		return memo[idx][cSum][iSum];

	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = readInt();
		String notPossible = "not possible";
		while (testcases-- > 0) {
			m = readInt();
			s = readInt();
			MAX = s * s;
			coins = new ECoin[m];
			for (int i = 0; i < m; ++i) {
				coins[i] = new ECoin(readInt(), readInt());
			}
			for (int i = 0; i < m; ++i) {
				for (int j = 0; j <= s; ++j) {
					for (int k = 0; k <= s; ++k) {
						memo[i][j][k] = -1;
					}
				}
			}
			int result = solve(0, 0, 0);
			if (result > 300) {
				pw.println(notPossible);
			} else {
				pw.println(result);
			}
		}

		pw.flush();
		pw.close();
	}
}
