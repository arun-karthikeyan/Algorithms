import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
/**
 * Grid Game, A Second Player Advantage game
 * @author arun
 *
 */
public class UVA11553 {
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

	private static int[][] board;
	private static int min;
	private static int n;

	private static String constructPermutationString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; ++i) {
			sb.append(i);
		}
		return sb.toString();
	}

	private static int min(int a, int b) {
		return a < b ? a : b;
	}

	private static int computeOptimalScore(String permutation) {
		int score = 0;
		for (int i = 0; i < n; ++i) {
			score += board[permutation.charAt(i) - '0'][i];
		}
		return score;
	}

	private static void findSolution(String prefix, String rest) {
		if (rest.length() == 0) {
			min = min(min, computeOptimalScore(prefix));
		}
		for (int i = 0, iLen = rest.length(); i < iLen; ++i) {
			findSolution(prefix + rest.charAt(i), rest.substring(0, i) + rest.substring(i + 1));
		}
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
			min = Integer.MAX_VALUE;
			board = new int[n][n];
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					board[i][j] = readInt();
				}
			}
			findSolution("", constructPermutationString());
			pw.println(min);
		}

		pw.flush();
		pw.close();
	}
}
