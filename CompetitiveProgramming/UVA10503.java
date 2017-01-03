import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA10503 {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];

	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, values = 1; i <= 14; ++i, values *= 2) {
			LOOKUP.put(values, i);
		}
	}

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

	private static int spaces, m, OK, pieces[][], i1, i2, d1, d2;
	private static final String YES = "YES", NO = "NO";

	private static boolean solve(int path, int p1, int col) {
		if (col == spaces) {
			if (p1 == d1) {
				return true;
			}
			return false;
		}
		int vp = (OK & (~path));
		boolean result = false;
		while (vp > 0 && !result) {
			int p = vp & (-vp);
			vp -= p;
			int[] piece = pieces[LOOKUP.get(p)];
			if (piece[0] == p1) {
				result = solve(path | p, piece[1], col + 1);

			} else if (piece[1] == p1) {
				result = solve(path | p, piece[0], col + 1);
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		while ((spaces = readInt()) != 0) {
			m = readInt();
			OK = (1 << m) - 1;
			pieces = new int[m][2];
			i1 = readInt();
			i2 = readInt();
			d1 = readInt();
			d2 = readInt();
			for (int i = 0; i < m; ++i) {
				pieces[i][0] = readInt();
				pieces[i][1] = readInt();
			}
			if (solve(0, i2, 0)) {
				pw.println(YES);
			} else {
				pw.println(NO);
			}
		}

		pw.flush();
		pw.close();
	}
}
