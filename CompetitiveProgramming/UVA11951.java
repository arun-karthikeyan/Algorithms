import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11951 {
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

		int testcases = readInt(), tc = 1;

		while (testcases-- > 0) {
			int n = readInt(), m = readInt(), p = readInt();
			int[][] prices = new int[n][m];
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < m; ++j) {
					prices[i][j] = readInt();
					if (j > 0)
						prices[i][j] += prices[i][j - 1];
				}
			}
			long finalMinPrice = Long.MAX_VALUE, finalMaxStrip = Long.MIN_VALUE;
			for (int i = 0; i < m; ++i) {
				for (int j = 0; j < m - i; ++j) {
					int l = j, r = j + i, start = 0, maxStripLen = 0;
					long minPrice = Long.MAX_VALUE, currentPrice = 0l;
					for (int k = 0; k < n; ++k) {
						long currentStrip = prices[k][r];
						if (l > 0)
							currentStrip -= prices[k][l - 1];
						if (currentStrip > p) {
							start = k + 1;
							currentPrice = 0;
							continue;
						}
						currentPrice = currentPrice + currentStrip;
						while (currentPrice > p) {
							currentPrice -= prices[start][r];
							if (l > 0)
								currentPrice += prices[start][l - 1];
							start++;
						}
						int stripLen = (k - start + 1) * (r - l + 1);
						if (stripLen > maxStripLen) {
							maxStripLen = stripLen;
							minPrice = currentPrice;
						} else if (stripLen == maxStripLen && currentPrice < minPrice) {
							minPrice = currentPrice;
						}
					}
					if (minPrice <= p) {
						if (maxStripLen > finalMaxStrip) {
							finalMaxStrip = maxStripLen;
							finalMinPrice = minPrice;
						} else if (maxStripLen == finalMaxStrip && minPrice < finalMinPrice) {
							finalMinPrice = minPrice;
						}
					}
				}
			}
			if (finalMaxStrip == Long.MIN_VALUE) {
				pw.println("Case #" + (tc++) + ": 0 0");
			} else {
				pw.println("Case #" + (tc++) + ": " + finalMaxStrip + " " + finalMinPrice);
			}
		}

		pw.flush();
		pw.close();
	}
}
