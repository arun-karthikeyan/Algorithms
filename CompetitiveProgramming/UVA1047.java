import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA1047 {
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		LOOKUP.put(1 << 0, 0);
		LOOKUP.put(1 << 1, 1);
		LOOKUP.put(1 << 2, 2);
		LOOKUP.put(1 << 3, 3);
		LOOKUP.put(1 << 4, 4);
		LOOKUP.put(1 << 5, 5);
		LOOKUP.put(1 << 6, 6);
		LOOKUP.put(1 << 7, 7);
		LOOKUP.put(1 << 8, 8);
		LOOKUP.put(1 << 9, 9);
		LOOKUP.put(1 << 10, 10);
		LOOKUP.put(1 << 11, 11);
		LOOKUP.put(1 << 12, 12);
		LOOKUP.put(1 << 13, 13);
		LOOKUP.put(1 << 14, 14);
		LOOKUP.put(1 << 15, 15);
		LOOKUP.put(1 << 16, 16);
		LOOKUP.put(1 << 17, 17);
		LOOKUP.put(1 << 18, 18);
		LOOKUP.put(1 << 19, 19);
		LOOKUP.put(1 << 20, 20);
		LOOKUP.put(1 << 21, 21);
		LOOKUP.put(1 << 22, 22);
		LOOKUP.put(1 << 23, 23);
	}
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

	private static int[] towers;
	private static int[][] isects;
	private static int n, m, isCount, cust, loc;
	private static final int COUNT = 0, BIT = 1, VAL = 2;

	private static int countBits(int val) {
		int count = 0;
		while (val > 0) {
			val -= (val & (-val));
			count++;
		}
		return count;
	}

	private static void solve(int bitCount, int path, int lastBit) {
		if (bitCount == m) {
			int sum = 0;
			int temp = path;
			while (temp > 0) {
				int idx = temp & (-temp);
				temp -= idx;
				sum += towers[LOOKUP.get(idx)];
			}
			for (int i = 0; i < isCount; ++i) {
				int[] currentISect = isects[i];
				int bits = countBits(path & currentISect[BIT]) - 1;
				if (bits > 0) {
					sum -= bits * currentISect[VAL];
				}
			}
			if (sum > cust) {
				cust = sum;
				loc = path;
			}
		}
		for (int i = lastBit; i < n; ++i) {
			solve(bitCount + 1, path | (1 << i), i + 1);
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int tc = 1;
		while ((n = readInt()) != 0) {

			m = readInt();
			if (n == 0 && m == 0) {
				break;
			}
			towers = new int[n];
			cust = loc = -1;
			for (int i = 0; i < n; ++i) {
				towers[i] = readInt();
			}
			isCount = readInt();
			isects = new int[isCount][3];
			for (int i = 0; i < isCount; ++i) {
				int tempCount;
				tempCount = isects[i][COUNT] = readInt();
				for (int j = 0; j < tempCount; ++j) {
					isects[i][BIT] |= (1 << (readInt() - 1));
				}
				isects[i][VAL] = readInt();
			}
			solve(0, 0, 0);
			pw.println("Case Number  " + (tc++));
			pw.println("Number of Customers: " + cust);
			pw.print("Locations recommended:");
			while (loc > 0) {
				int p = loc & (-loc);
				loc -= p;
				pw.print(" " + (LOOKUP.get(p) + 1));
			}
			pw.println("\n");
		}

		pw.flush();
		pw.close();
	}
}
