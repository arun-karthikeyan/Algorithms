import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA12346 {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, val = 1; i <= 20; ++i, val *= 2) {
			LOOKUP.put(val, i);
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

	private static int n, OK;
	private static int[][] gates;
	private static final int FLOW = 0, COST = 1;
	private static int maxHours;
	private static long maxFlow;

	private static long min(long a, long b) {
		return a < b ? a : b;
	}

	private static long computeMinCost(int path, int invalid, long currentFlow, long cost) {
		if ((currentFlow * maxHours) >= maxFlow) {
			return cost;
		}
		int vb = OK & (~invalid);
		long min = Long.MAX_VALUE;
		while (vb > 0) {
			int p = (vb & (-vb));
			vb -= p;
			int idx = LOOKUP.get(p);
			min = min(min,
					computeMinCost(path | p, (p << 1) - 1, currentFlow + gates[idx][FLOW], cost + gates[idx][COST]));
		}
		return min;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		n = readInt();
		OK = (1 << n) - 1;
		gates = new int[n][2];
		for (int i = 0; i < n; ++i) {
			gates[i][FLOW] = readInt();
			gates[i][COST] = readInt();
		}
		int m = readInt();
		for (int i = 1; i <= m; ++i) {
			maxFlow = readInt();
			maxHours = readInt();
			long minFlow = computeMinCost(0, 0, 0l, 0l);
			if (minFlow == Long.MAX_VALUE) {
				pw.println("Case " + i + ": IMPOSSIBLE");
			} else {
				pw.println("Case " + i + ": " + minFlow);
			}
		}
		pw.flush();
		pw.close();
	}
}
