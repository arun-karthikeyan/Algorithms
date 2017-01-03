import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class UVA574 {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, values = 1; i <= 12; ++i, values *= 2) {
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

	private static int t = 0, n = 0, OK = 0, nos[];
	private static ArrayList<Integer> result;

	private static void solve(int path, int invalid, int sum) {
		if (path != 0 && sum == t) {
			result.add(path);
			return;
		}
		int vp = OK & ~invalid;
		HashSet<Integer> visited = new HashSet<Integer>();
		while (vp > 0) {
			int p = vp & (-vp);
			vp -= p;
			int val = nos[LOOKUP.get(p)];
			if (visited.add(val)) {
				solve(path | p, (p << 1) - 1, sum + val);
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

		while (true) {
			t = readInt();
			n = readInt();
			if (t == 0 && n == 0) {
				break;
			}
			nos = new int[n];
			for (int i = 0; i < n; ++i) {
				nos[i] = readInt();
			}
			OK = (1 << n) - 1;
			result = new ArrayList<Integer>();
			solve(0, 0, 0);
			pw.println("Sums of " + t + ":");
			if (result.size() == 0) {
				pw.println("NONE");
			} else {
				for (int i = 0, iLen = result.size(); i < iLen; ++i) {
					int currentPath = result.get(i);
					if (currentPath > 0) {
						int p = currentPath & (-currentPath);
						currentPath -= p;
						pw.print(nos[LOOKUP.get(p)]);
					}
					while (currentPath > 0) {
						int p = currentPath & (-currentPath);
						currentPath -= p;
						pw.print("+" + nos[LOOKUP.get(p)]);
					}
					pw.println();
				}
			}

		}

		pw.flush();
		pw.close();
	}
}
