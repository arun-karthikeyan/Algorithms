import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class HelperMethod {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[65536];

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

	private static int[][] DP;
	private static int[] treats;
	private static int n;

	public static void main(String[] args) throws Exception {
		stream = System.in;
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		n = readInt();

		treats = new int[n];

		DP = new int[n][n];

		for (int i = 0, j = n - 1; i < n; ++i, --j) {
			treats[i] = readInt();
			DP[i][j] = n * treats[i];
		}

		for (int k = 2; k <= n; ++k) {
			for (int i = n - k, j = 0; i >= 0; --i, ++j) {
				
				DP[i][j] = max((DP[i + 1][j] + (i + j + 1) * treats[i]),
						(DP[i][j + 1] + (i + j + 1) * treats[n - j - 1]));
			}
		}

		pw.println(DP[0][0]);
		pw.flush();
		pw.close();
	}
}