import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Grapevine
 * 
 * @author arun
 *
 */
public class UVA12192 {
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

	private static int binarySearchRowL(int r, int start, int end, int key) {
		int low = start, high = end;
		while (low <= high) {
			int mid = (low + high) >> 1, val = vals[r][mid];
			if (key > val) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		return low <= end ? low : -1;
	}

	private static int n, m, q, queryL, queryR, vals[][];

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		while (true) {
			n = readInt();
			m = readInt();
			if (n == 0 && m == 0) {
				break;
			}
			vals = new int[n][m];
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < m; ++j) {
					vals[i][j] = readInt();
				}
			}
			q = readInt();
			while (q-- > 0) {
				queryL = readInt();
				queryR = readInt();
				int max = 0;
				for (int i = 0; i < min(n, (n - max + 1)); ++i) {
					int c1 = binarySearchRowL(i, 0, m - 1, queryL);
					if (c1 == -1) {
						continue;
					}
					int rowEnd = i + max, colEnd = c1 + max;
					while (rowEnd < n && colEnd<m && vals[rowEnd++][colEnd++] <= queryR) {
						max++;
					}
				}
				pw.println(max);
			}
			pw.println("-");
		}

		pw.flush();
		pw.close();
	}

	private static int min(int a, int b) {
		return a < b ? a : b;
	}
}
