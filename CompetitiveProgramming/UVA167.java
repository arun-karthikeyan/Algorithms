import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA167 {
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

	private static int[][] board = new int[8][8];
	private static boolean[] ld = new boolean[15], rd = new boolean[15], r = new boolean[8];

	private static int maxSum(int currentPos) {
		if (currentPos == 8) {
			return 0;
		}
		int maxSum = 0;
		for (int i = 0; i < 8; ++i) {
			int ldIdx = i + currentPos, rdIdx = i - currentPos + 7;
			if (!r[i] && !ld[ldIdx] && !rd[rdIdx]) {
				r[i] = true;
				ld[ldIdx] = true;
				rd[rdIdx] = true;
				maxSum = max(maxSum, maxSum(currentPos + 1) + board[i][currentPos]);
				r[i] = false;
				ld[ldIdx] = false;
				rd[rdIdx] = false;
			}
		}
		return maxSum;
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
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
			for (int i = 0; i < 8; ++i) {
				for (int j = 0; j < 8; ++j) {
					board[i][j] = readInt();
				}
			}
			pw.printf("%5d\n", maxSum(0));
		}

		pw.flush();
		pw.close();
	}
}
