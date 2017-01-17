import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Wedding Shopping, garment collection
 * Top Down Approach - Recursive Backtracking with Memo Table
 * 
 * @author arun
 *
 */
public class UVA11450 {
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

	static int max(int a, int b) {
		return a > b ? a : b;
	}

	private static int shop(int garment, int money) {
		if (money < 0) {
			return Integer.MIN_VALUE;
		}
		if (garment >= c) {
			return 0;
		}
		if (memo[garment][money] == -1) {
			int max = Integer.MIN_VALUE;
			for (int i = 0, iLen = clothes[garment].length; i < iLen; ++i) {
				int currentModelCost = clothes[garment][i];
				max = max(max, currentModelCost + shop(garment + 1, money - currentModelCost));
			}
			return memo[garment][money] = max;
		} else {
			return memo[garment][money];
		}
	}

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	private static int m, c, clothes[][], memo[][];

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = readInt();

		while (testcases-- > 0) {
			m = readInt();
			c = readInt();
			clothes = new int[c][];
			memo = new int[c][201];
			for (int i = 0; i < c; ++i) {
				Arrays.fill(memo[i], -1);
				int k = readInt();
				int[] models = new int[k];
				for (int j = 0; j < k; ++j) {
					models[j] = readInt();
				}
				clothes[i] = models;
			}
			int optimalAmount = shop(0, m);
			if (optimalAmount > 0) {
				pw.println(optimalAmount);
			} else {
				pw.println("no solution");
			}
		}

		pw.flush();
		pw.close();
	}
}
