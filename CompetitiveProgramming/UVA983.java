import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA983 {
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
//		PrintWriter pw = new PrintWriter("testop.txt");
		int tc = 0;
		while (true) {
			int n = readInt(), m = readInt();
			if (n == 0 && m == 0) {
				break;
			}
			if (tc++ != 0) {
				pw.println();
			}
			long[][] vals = new long[n][n];
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					vals[i][j] = readInt();
					if (i > 0)
						vals[i][j] += vals[i - 1][j];
					if (j > 0)
						vals[i][j] += vals[i][j - 1];
					if (i > 0 && j > 0)
						vals[i][j] -= vals[i - 1][j - 1];
				}
			}
			int k = n - m + 1;
			long sum = 0l;
			for (int i = 0; i < k; ++i) {
				for (int j = 0; j < k; ++j) {
					int i2 = i + n - k, j2 = j + n - k;
					long result = vals[i2][j2];
					if (i > 0)
						result -= vals[i - 1][j2];
					if (j > 0)
						result -= vals[i2][j - 1];
					if (i > 0 && j > 0)
						result += vals[i - 1][j - 1];
					pw.println(result);
					sum += result;
				}
			}
			pw.println(sum);
		}

		pw.flush();
		pw.close();
	}
}
