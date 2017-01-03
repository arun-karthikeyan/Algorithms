import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 23 out of 5
 * 
 * @author arun
 *
 */
public class UVA10344 {

	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		LOOKUP.put(1 << 0, 0);
		LOOKUP.put(1 << 1, 1);
		LOOKUP.put(1 << 2, 2);
		LOOKUP.put(1 << 3, 3);
		LOOKUP.put(1 << 4, 4);
		LOOKUP.put(1 << 5, 5);
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

	private static int nums[] = new int[5];
	private static int OK = (1 << 5) - 1;

	private static int compute(int operation, int a, int b) {
		switch (operation) {
		case 0:
			return a + b;
		case 1:
			return a * b;
		case 2:
			return a - b;
		default:
			return 0;
		}
	}

	private static boolean permuteOperators(int[] vals) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0, iResult = compute(i, vals[0], vals[1]); j < 3; ++j) {
				for (int k = 0, jResult = compute(j, iResult, vals[2]); k < 3; ++k) {
					for (int l = 0, kResult = compute(k, jResult, vals[3]); l < 3; ++l) {
						if (compute(l, kResult, vals[4]) == 23) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean permuteAndCheck(int[] currentPermutation, int path, int col) {
		if (col == 5) {
			return permuteOperators(currentPermutation);
		}
		boolean result = false;
		int vp = (OK & (~path));
		while (vp > 0 && !result) {
			int p = vp & (-vp);
			currentPermutation[col] = nums[LOOKUP.get(p)];
			vp -= p;
			result = permuteAndCheck(currentPermutation, path | p, col + 1);
		}
		return result;
	}

	private static final String POSSIBLE = "Possible";
	private static final String IMPOSSIBLE = "Impossible";

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		while ((nums[0] = readInt()) != 0) {
			nums[1] = readInt();
			nums[2] = readInt();
			nums[3] = readInt();
			nums[4] = readInt();
			if (permuteAndCheck(new int[5], 0, 0)) {
				pw.println(POSSIBLE);
			} else {
				pw.println(IMPOSSIBLE);
			}
		}

		pw.flush();
		pw.close();
	}

}
