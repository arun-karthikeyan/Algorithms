import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.BitSet;

/**
 * Sum of different primes - Straightforward DP
 * Top Down Solution - Doesn't pass within time limit in java
 * @author arun
 *
 */
public class UVA1213 {
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

	private static final int MAX = 1120;
	private static int n, k, memoi[][][] = new int[187][1121][15], cache[][] = new int[1121][15];
	private static int[] primes = new int[187];

	private static void runSeive() {
		BitSet seive = new BitSet(MAX + 1);
		for (int i = 2, k = 0; i <= MAX && i >= 0; i = seive.nextClearBit(i + 1)) {
			primes[k++] = i;
			for (int j = 2 * i; j <= MAX; j += i) {
				seive.set(j);
			}
		}
	}

	private static int countWays(int idx, int remSum, int remPrimes) {
		if (remSum == 0 && remPrimes == 0) {
			return 1;
		}
		if (remSum < 0 || remPrimes < 0 || idx == 187) {
			return 0;
		}
		if (memoi[idx][remSum][remPrimes] == -1) {
			int w1 = countWays(idx + 1, remSum, remPrimes);
			int w2 = countWays(idx + 1, remSum - primes[idx], remPrimes - 1);
			memoi[idx][remSum][remPrimes] = w1 + w2;
		}
		return memoi[idx][remSum][remPrimes];
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		runSeive();
		for (int i = 0, iLen = cache.length; i < iLen; ++i) {
			for (int j = 0, jLen = cache[i].length; j < jLen; ++j) {
				cache[i][j] = -1;
			}
		}
		while (true) {
			n = readInt();
			k = readInt();
			if (n == 0 && k == 0) {
				break;
			}
			for (int i = 0; i < 187; ++i) {
				for (int j = 0; j <= n; ++j) {
					for (int l = 0; l <= k; ++l) {
						memoi[i][j][l] = -1;
					}
				}
			}
			if (cache[n][k] == -1) {
				cache[n][k] = countWays(0, n, k);
			}
			pw.println(cache[n][k]);
		}

		pw.flush();
		pw.close();
	}
}
