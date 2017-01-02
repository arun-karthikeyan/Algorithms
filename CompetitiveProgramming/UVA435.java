import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Block Voting
 * 
 * @author arun
 *
 */
public class UVA435 {
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

	private static int[] minorityCoalitions;
	private static int[] parties;
	private static int n, majority;
	private static int OK;

	private static void countWinningCoalitions(int sum, int path, int invalidPaths) {
		int vb = (OK & (~path));
		while (vb > 0) {
			int p = vb & (-vb);
			vb -= p;
			int idx = LOOKUP.get(p);
			if ((sum + parties[idx]) > majority) {
				minorityCoalitions[idx]++;
				invalidPaths |= p;
			}
		}
		vb = OK & (~invalidPaths);
		while (vb > 0) {
			int p = vb & (-vb);
			vb -= p;
			countWinningCoalitions(sum + parties[LOOKUP.get(p)], path | p, (p << 1) - 1);
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		// PrintWriter pw = new PrintWriter(
		// new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new
		// File("testop.txt")))));

		int testcases = readInt();

		while (testcases-- > 0) {
			n = readInt();
			OK = (1 << n) - 1;
			parties = new int[n];
			majority = 0;
			minorityCoalitions = new int[n];
			for (int i = 0; i < n; ++i) {
				int val = readInt();
				parties[i] = val;
				majority += val;
			}
			majority /= 2;
			countWinningCoalitions(0, 0, 0);
			for (int i = 0; i < n; ++i) {
				pw.println("party " + (i + 1) + " has power index " + minorityCoalitions[i]);
			}
			pw.println();
		}

		pw.flush();
		pw.close();
	}
}
