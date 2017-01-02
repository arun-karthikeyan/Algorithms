import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA624 {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, vals = 1; i <= 20; ++i, vals *= 2) {
			LOOKUP.put(vals, i);
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

	private static int n, m, maxPath, maxSum, OK, tracks[];

	private static void solve(int path, int invalid, int sum) {
		if (sum <= n) {
			if (maxSum < sum) {
				maxPath = path;
				maxSum = sum;
			}
			int vp = OK & (~invalid);
			while (vp > 0) {
				int p = vp & (-vp);
				vp -= p;
				int idx = LOOKUP.get(p);
				solve(path | p, (p << 1) - 1, sum + tracks[idx]);
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

		while ((n = readInt()) != 0) {
			m = readInt();
			maxPath = 0;
			maxSum = 0;
			OK = (1 << m) - 1;
			tracks = new int[m];
			for (int i = 0; i < m; ++i) {
				tracks[i] = readInt();
			}
			solve(0, 0, 0);
			while (maxPath > 0) {
				int p = maxPath & (-maxPath);
				maxPath -= p;
				pw.print(tracks[LOOKUP.get(p)] + " ");
			}
			pw.println("sum:" + maxSum);
		}

		pw.flush();
		pw.close();
	}
}
