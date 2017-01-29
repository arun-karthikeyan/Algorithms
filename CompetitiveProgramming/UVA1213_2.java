import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.BitSet;

/**
 * Sum of different primes bottom up approach - O(187*1121*15) precomputation
 * and O(1) query
 * 
 * @author arun
 *
 */
public class UVA1213_2 {
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

	private static final int MAX = 1120;
	private static int[] primes = new int[187], result[] = new int[15][1121];

	private static void runSeive() {
		BitSet seive = new BitSet(MAX + 1);
		for (int i = 2, k = 0; i <= MAX && i >= 0; i = seive.nextClearBit(i + 1)) {
			primes[k++] = i;
			for (int j = 2 * i; j <= MAX; j += i) {
				seive.set(j);
			}
		}
	}

	private static void preCompute() {
		result[0][0] = 1;
		for (int i = 0; i < 187; ++i) {
			for (int k = 14; k > 0; --k) {
				for (int j = 1120, jLimit = primes[i]; j >= jLimit; --j) {
					result[k][j] += result[k - 1][j - jLimit];
				}
			}
		}
	}

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		runSeive();
		preCompute();
		while (true) {
			int n = readInt();
			int k = readInt();
			if (n == 0 && k == 0) {
				break;
			}
			pw.println(result[k][n]);
		}

		pw.flush();
		pw.close();
	}
}
