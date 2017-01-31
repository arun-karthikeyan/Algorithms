import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA10496 {
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

	private static int X = 0, Y = 1;

	private static int getDistance(int[] p1, int[] p2) {
		return Math.abs(p1[X] - p2[X]) + Math.abs(p1[Y] - p2[Y]);
	}

	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, v = 1; i < 12; ++i, v *= 2) {
			LOOKUP.put(v, i);
		}
	}
	private static int[] world = new int[2], beepers[];
	private static int n, OK;
	private static final String OP = "The shortest path has length ";

	private static int min(int a, int b) {
		return a < b ? a : b;
	}

	private static int solve(int used, int prev) {
		int valid = (OK & (~used));
		if (valid == 0) {
			return getDistance(beepers[prev], beepers[0]);
		}
		int minDist = Integer.MAX_VALUE;
		while (valid > 0) {
			int p = valid & (-valid);
			valid -= p;
			int next = LOOKUP.get(p);
			minDist = min(minDist, getDistance(beepers[next], beepers[prev]) + solve(used | p, next));
		}
		return minDist;
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
			world[X] = readInt();
			world[Y] = readInt();
			int startX = readInt();
			int startY = readInt();
			n = readInt();
			beepers = new int[n + 1][2];
			beepers[0][X] = startX;
			beepers[0][Y] = startY;
			for (int i = 1; i <= n; ++i) {
				beepers[i][X] = readInt();
				beepers[i][Y] = readInt();
			}
			OK = (2 << n) - 1;
			pw.println(OP + solve(1, 0));
		}

		pw.flush();
		pw.close();
	}
}
