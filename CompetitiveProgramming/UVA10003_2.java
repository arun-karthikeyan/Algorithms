import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Cutting Sticks V2 - a bit slower than the previous one
 * Top Down Approach
 * @author arun
 *
 */
public class UVA10003_2 {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];
	private static final HashMap<Long, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Long, Integer>();
		for (int i = 0; i <= 50; ++i) {
			LOOKUP.put(1l << i, i);
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

	private static int cuts[];
	private static HashMap<Cache, Integer> memoi;

	static class Cache {
		int start;
		int end;

		public Cache(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public int hashCode() {
			return this.start << 10 | this.end;
		}

		public boolean equals(Object o) {
			Cache o2 = (Cache) o;
			return this.start == o2.start && this.end == o2.end;
		}
	}

	private static int solve(int start, int end, long cutsbm, long usedCuts) {
		long validCuts = ((OK & (~(usedCuts))) & cutsbm);
		if (validCuts == 0) {
			return 0;
		}
		Cache state = new Cache(start, end);
		if (!memoi.containsKey(state)) {
			int curCutCost = end - start + 1;
			int min = 1000000;
			while (validCuts > 0) {
				long nextCut = (validCuts) & (-validCuts);
				int cutStartPoint = cuts[LOOKUP.get(nextCut)];
				validCuts -= nextCut;
				long leftCutsbm = nextCut - 1;
				long rightCutsbm = validCuts;
				min = Math.min(min, curCutCost + solve(start, cutStartPoint, leftCutsbm, usedCuts | nextCut)
						+ solve(cutStartPoint + 1, end, rightCutsbm, usedCuts | nextCut));
			}
			memoi.put(state, min);
			return min;
		}
		return memoi.get(state);
	}

	private static long OK;

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int l;
		while ((l = readInt()) != 0) {
			int n = readInt();
			cuts = new int[n];
			memoi = new HashMap<Cache, Integer>();
			for (int i = 0; i < n; ++i) {
				cuts[i] = readInt();
			}
			long cutsbm = (1l << n) - 1;
			OK = cutsbm;
			pw.println("The minimum cutting is " + solve(1, l, cutsbm, 0) + ".");
		}

		pw.flush();
		pw.close();
	}
}
