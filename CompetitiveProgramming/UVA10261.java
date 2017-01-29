import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Ferry Loading - Top Down Approach. Also learned from this problem that array
 * accesses are costly, and we must skip them with an if else check whenever
 * possible
 * 
 * @author arun
 *
 */
public class UVA10261 {

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

	private static int n, len, memo[][] = new int[200][10001], path[][] = new int[200][10001];
	private static ArrayList<Integer> cars;
	private static final String PORTSTR = "port", STARSTR = "starboard";
	private static final int STAR = 0, PORT = 1;

	private static int solve(int idx, int portSum, int starSum) {
		if (idx == n) {
			return 0;
		}

		if (memo[idx][portSum] == -1) {
			int currentLen = cars.get(idx);
			int m1 = 0, m2 = 0;
			if (portSum >= currentLen) {
				m1 = 1 + solve(idx + 1, portSum - cars.get(idx), starSum);
			}
			if (starSum >= currentLen) {
				m2 = 1 + solve(idx + 1, portSum, starSum - cars.get(idx));
			}
			if (m1 > m2) {
				if (m1 > 0) { //skipping unnecessary array access
					path[idx][portSum] = PORT;
				}
				memo[idx][portSum] = m1;
			} else {
				if (m2 > 0) { //skipping unnecessary array access
					path[idx][portSum] = STAR;
				}
				memo[idx][portSum] = m2;
			}
		}
		return memo[idx][portSum];
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
			len = readInt() * 100; // ferry len in cms
			cars = new ArrayList<Integer>();
			int size = 0;
			while ((n = readInt()) != 0) {
				if (size < 200) {
					cars.add(n);
					Arrays.fill(memo[size++], -1);
				}
			}
			n = size;
			int maxCars = solve(0, len, len);
			pw.println(maxCars);
			for (int i = 0, j = len; i < maxCars; ++i) {
				if (path[i][j] == PORT) {
					pw.println(PORTSTR);
					j -= cars.get(i);
				} else {
					pw.println(STARSTR);
				}
			}
			if (testcases != 0) {
				pw.println();
			}
		}
		pw.flush();
		pw.close();
	}

}
