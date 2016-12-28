import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11742 {
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

	private static int[][] constraints;

	private static int countSeating(String prefix, String rest) {
		if (prefix.length() == 0) {
			return satisfyConstraints(rest) ? 1 : 0;
		}
		int sum = 0;
		for (int i = 0, iLen = prefix.length(); i < iLen; ++i) {
			sum += countSeating(prefix.substring(0, i) + prefix.substring(i + 1), rest + prefix.charAt(i));
		}
		return sum;
	}

	private static boolean satisfyConstraints(String seating) {
		for (int i = 0, iLen = constraints.length; i < iLen; ++i) {
			int a = constraints[i][0], b = constraints[i][1];
			int aLoc = -1, bLoc = -1;
			for (int j = 0, jLen = seating.length(); j < jLen; ++j) {
				if ((seating.charAt(j) - '0') == a) {
					aLoc = j;
				}
				if ((seating.charAt(j) - '0') == b) {
					bLoc = j;
				}
			}
			int dist = Math.abs(aLoc - bLoc);
			if (constraints[i][2] < 0) {
				int c = -constraints[i][2];
				if (dist < c) {
					return false;
				}
			} else {
				int c = constraints[i][2];
				if (dist > c) {
					return false;
				}
			}
		}
		return true;
	}

	private static String buildString(int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; ++i) {
			sb.append(i + "");
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n, m;
		while (true) {
			n = readInt();
			m = readInt();
			if (n == 0 && m == 0) {
				break;
			}
			constraints = new int[m][3];
			for (int i = 0; i < m; ++i) {
				constraints[i][0] = readInt();
				constraints[i][1] = readInt();
				constraints[i][2] = readInt();
			}

			pw.println(countSeating(buildString(n), ""));
		}

		pw.flush();
		pw.close();
	}
}
