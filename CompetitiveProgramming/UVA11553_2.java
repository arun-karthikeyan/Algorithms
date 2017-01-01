import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA11553_2 {
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
		LOOKUP.put(1 << 6, 6);
		LOOKUP.put(1 << 7, 7);
		LOOKUP.put(1 << 8, 8);
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

	private static int n;
	private static int[][] board;
	private static int OK;

	private static int optimalScore(int ur, int col) {
		if (ur == OK) {
			return 0;
		}
		int vr = (OK & (~ur));
		int min = 1001;
		while (vr > 0) {
			int p = vr & (-vr);
			vr -= p;
			min = min(min, board[LOOKUP.get(p)][col] + optimalScore(ur | p, col + 1));
		}
		return min;
	}

	private static int min(int a, int b) {
		return a < b ? a : b;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = readInt();

		while (testcases-- > 0) {
			n = readInt();
			OK = (1 << n) - 1;
			board = new int[n][n];
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					board[i][j] = readInt();
				}
			}
			pw.println(optimalScore(0, 0));
		}

		pw.flush();
		pw.close();
	}
}
