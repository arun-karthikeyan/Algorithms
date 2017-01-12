import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA677 {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, values = 1; i <= 10; ++i, values *= 2) {
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

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	private static int n, m, OK;
	private static int[] paths;
	private static boolean walkPrinted;
	private static final String OPEN = "(", CLOSE = ")";

	private static void printAllPaths(PrintWriter pw, int used, int lastNode, int len, StringBuilder path) {
		if (len == m) {
			pw.println(path + CLOSE);
			walkPrinted = true;
			return;
		}
		int vp = (OK & (~used)) & (paths[lastNode]);
		while (vp > 0) {
			int p = vp & (-vp);
			vp -= p;
			int node = LOOKUP.get(p);
			path.append(",").append(node + 1);
			printAllPaths(pw, used | p, node, len + 1, path);
			int strLen = path.length();
			path.delete(strLen - 2, strLen);
		}
	}

	private static final String NOWALK = "no walk of length ";

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		while (true) {
			n = readInt();
			m = readInt();
			if (n == 0 && m == 0) {
				break;
			}
			OK = (1 << n) - 1;
			paths = new int[n];
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					paths[i] |= (readInt() << j);
				}
			}
			walkPrinted = false;
			StringBuilder sb = new StringBuilder();
			sb.append(OPEN).append(1);
			printAllPaths(pw, 1, 0, 0, sb);
			if (!walkPrinted) {
				pw.println(NOWALK + m);
			}
			if (readInt() == -9999) {
				pw.println();
			}
		}

		pw.flush();
		pw.close();
	}
}
