import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Prime Ring Problem
 * 
 * @author arun
 *
 */
public class UVA524 {

	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];

	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, values = 1; i <= 16; ++i, values *= 2) {
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

	private static int n, OK;

	private static boolean isPrime(int val) {
		for (int i = 2; i * i <= val; ++i) {
			if ((val % i) == 0) {
				return false;
			}
		}
		return true;
	}

	private static ArrayList<int[]> result;

	private static void solve(int path, int[] nos, int prev, int col) {
		if (path == OK) {
			if (isPrime(prev + 1)) {
				result.add(nos.clone());
			}
			return;
		}
		int vp = (OK & ~path);
		while (vp > 0) {
			int p = vp & (-vp);
			vp -= p;
			int no = LOOKUP.get(p) + 1;
			if (isPrime(prev + no)) {
				nos[col] = no;
				solve(path | p, nos, no, col + 1);
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
		// PrintWriter pw = new PrintWriter(new FileWriter("testop.txt"));
		int tc = 1;
		while ((n = readInt()) != 0) {
			if (tc != 1) {
				pw.println();
			}
			result = new ArrayList<int[]>();
			OK = (1 << n) - 1;
			int[] permutations = new int[n];
			permutations[0] = 1;
			solve(1, permutations, 1, 1);
			pw.println("Case " + (tc++) + ":");
			int resultSize = result.size();
			for (int i = 0; i < resultSize; ++i) {
				int[] currentResult = result.get(i);
				pw.print(currentResult[0]);
				for (int j = 1, jLen = currentResult.length; j < jLen; ++j) {
					pw.print(" " + currentResult[j]);
				}
				pw.println();
			}
		}

		pw.flush();
		pw.close();
	}

}
