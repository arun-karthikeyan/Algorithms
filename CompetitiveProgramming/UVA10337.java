import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA10337 {
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

	private static int min(int a, int b, int c) {
		if (a < b) {
			return a < c ? a : c;
		}
		return b < c ? b : c;
	}

	private static int x, windResistance[][] = new int[10][1000], m, memo[][] = new int[10][1000];
	private static final int CLIMB = 60, HOLD = 30, SINK = 20;

	private static int solve(int idx, int alt) {
		if (alt > 9 || alt < 0) {
			return 61001;
		}
		if (idx == m) {
			if (alt == 0) {
				return 0;
			}
			return 61001;
		}
		if (memo[alt][idx] == -1) {
			int wr = windResistance[alt][idx];
			int m1 = 61001, m2 = 61001, m3 = 61001;
			// maintain altitude
			m1 = HOLD - wr + solve(idx + 1, alt);
			// climb
			m2 = CLIMB - wr + solve(idx + 1, alt + 1);
			// sink
			m3 = SINK - wr + solve(idx + 1, alt - 1);
			memo[alt][idx] = min(m1, m2, m3);
		}
		return memo[alt][idx];
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
			x = readInt();
			m = x / 100;
			for (int i = 9; i >= 0; --i) {
				for (int j = 0; j < m; ++j) {
					windResistance[i][j] = readInt();
					memo[i][j] = -1;
				}
			}
			pw.println(solve(0, 0) + "\n");
		}

		pw.flush();
		pw.close();
	}
}
