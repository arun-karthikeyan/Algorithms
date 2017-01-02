import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;

public class UVA11205 {
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

	private static int n, m, OK, minBits;
	private static int[] symbols;

	private static void solve(int path, int invalidPaths, int bits) {
		HashSet<Integer> combs = new HashSet<Integer>();
		for (int i = 0; i < m; ++i) {
			if (!combs.add(path & symbols[i])) {
				break;
			}
		}
		if (combs.size() == m) {
			if (minBits > bits) {
				minBits = bits;
			}
		} else {
			int vb = OK & (~(path | invalidPaths));
			while (vb > 0) {
				int p = vb & (-vb);
				vb -= p;
				solve(path | p, (p << 1) - 1, bits + 1);
			}
		}
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
			n = readInt();
			m = readInt();
			OK = (1 << n) - 1;
			minBits = n;
			symbols = new int[m];
			for (int i = 0; i < m; ++i) {
				for (int j = 0; j < n; ++j) {
					symbols[i] <<= 1;
					symbols[i] += readInt();
				}
			}
			solve(0, 0, 0);
			pw.println(minBits);

		}

		pw.flush();
		pw.close();
	}
}
