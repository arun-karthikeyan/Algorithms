import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Garbage Heap, can reduce by atleast degree of n using kadane's algorithm
 * 
 * @author arun
 *
 */
public class UVA10755 {
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

	private static long readLong() {
		int number = readByte();

		while (eolchar(number))
			number = readByte();

		int sign = 1;
		long val = 0;

		if (number == '-') {
			sign = -1;
			number = readByte();
		}

		do {
			if ((number < '0') || (number > '9')) {
				// return sign*val;
				return 0;
			}
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

		long[][][] pp = new long[21][21][21];
		while (testcases-- > 0) {
			int d1 = readInt() + 1, d2 = readInt() + 1, d3 = readInt() + 1;
			for (int i = 1; i < d1; ++i) {
				for (int j = 1; j < d2; ++j) {
					for (int k = 1; k < d3; ++k) {
						pp[i][j][k] = readLong() + pp[i][j][k - 1];
					}
				}
			}
			for (int i = 1; i < d1; ++i) {
				for (int j = 1; j < d2; ++j) {
					for (int k = 1; k < d3; ++k) {
						pp[i][j][k] += pp[i][j - 1][k];
					}
				}
			}
			for (int i = 1; i < d1; ++i) {
				for (int j = 1; j < d2; ++j) {
					for (int k = 1; k < d3; ++k) {
						pp[i][j][k] += pp[i - 1][j][k];
					}
				}
			}
			long max = Long.MIN_VALUE;
			for (int i = 1; i < d1; ++i) {
				for (int j = 1; j < d2; ++j) {
					for (int k = 1; k < d3; ++k) {
						for (int l = i; l < d1; ++l) {
							for (int m = j; m < d2; ++m) {
								for (int n = k; n < d3; ++n) {
									long val = pp[l][m][n] - pp[l][m][k - 1] - pp[i - 1][m][n] + pp[i - 1][m][k - 1]
											- pp[l][j - 1][n] + pp[l][j - 1][k - 1] + pp[i - 1][j - 1][n]
											- pp[i - 1][j - 1][k - 1];
									max = max(val, max);
								}
							}
						}
					}
				}
			}
			pw.println(max);
			if (testcases != 0) {
				pw.println();
			}
		}

		pw.flush();
		pw.close();
	}

	private static long max(long a, long b) {
		return a > b ? a : b;
	}
}
