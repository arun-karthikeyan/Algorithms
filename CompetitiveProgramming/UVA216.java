import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Getting in line - A variant of TSP
 * 
 * @author arun
 *
 */

public class UVA216 {
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

	private static double getDistance(int[] p1, int[] p2) {
		int xDiff = p1[X] - p2[X];
		int yDiff = p1[Y] - p2[Y];
		return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
	}

	private static int n, loc[][] = new int[8][2], OK;
	private static final int X = 0, Y = 1;
	private static final String BEGIN = "**********************************************************\nNetwork #";
	private static final String OPSTART = "Cable requirement to connect (";
	private static final String OPEND = "Number of feet of cable required is ";
	private static int[] result;
	private static double minCost;
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, v = 1; i < 9; ++i, v *= 2) {
			LOOKUP.put(v, i);
		}
	}

	private static void solve(int used, double cost, int len, int[] path) {
		int validBits = (OK & (~used));
		if (validBits == 0) {
			if (cost < minCost) {
				result = path.clone();
				minCost = cost;
			}
			return;
		}
		while (validBits > 0) {
			int p = validBits & (-validBits);
			validBits -= p;
			path[len] = LOOKUP.get(p);
			if (len == 0) {
				solve(used | p, 0d, len + 1, path);
			} else {
				solve(used | p, cost + getDistance(loc[path[len]], loc[path[len - 1]]), len + 1, path);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}
		int tc = 1;
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
//		 PrintWriter pw = new PrintWriter("testop.txt");
		while ((n = readInt()) != 0) {
			for (int i = 0; i < n; ++i) {
				loc[i][X] = readInt();
				loc[i][Y] = readInt();
			}
			OK = (1 << n) - 1;
			minCost = Double.MAX_VALUE;
			pw.println(BEGIN + (tc++));
			solve(0, 0d, 0, new int[n]);
			for (int i = 1; i < n; ++i) {
				pw.printf("%s%d,%d) to (%d,%d) is %.2f feet.\n", OPSTART, loc[result[i - 1]][X], loc[result[i - 1]][Y],
						loc[result[i]][X], loc[result[i]][Y], getDistance(loc[result[i - 1]], loc[result[i]])+16);
			}
			pw.printf("%s%.2f.\n", OPEND, (minCost + (n - 1) * 16));
		}
		pw.flush();
		pw.close();
	}
}
