import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CHEFSET {
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

	private static long readLong() {
		int number = readByte();

		while (eolchar(number))
			number = readByte();

		int sign = 1;
		long val = 0;

		if (number == '-') {
			sign = -1;
			number = readByte();
		}

		do {
			if ((number < '0') || (number > '9')) {
				// return sign*val;
				return 0;
			}
			val *= 10;
			val += (number - '0');
			number = readByte();
		} while (!eolchar(number));

		return sign * val;
	}

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	private static ArrayList<Integer> primeFactors;
	private static final boolean PRIME = false, NOTPRIME = true;
	private static final int MAX = (int) 1e6;
	private static final int PRIMEFACTORLIMIT = (int) Math.ceil(Math.sqrt(MAX));

	private static void generatePrimeList() {
		primeFactors = new ArrayList<Integer>();
		boolean[] seive = new boolean[MAX + 1];
		for (int i = 2; i <= PRIMEFACTORLIMIT; ++i) {
			if (seive[i] == PRIME) {
				for (int j = 2 * i; j <= MAX; j += i) {
					seive[j] = NOTPRIME;
				}
			}
		}
		for (int i = 2; i <= PRIMEFACTORLIMIT; ++i) {
			if (seive[i] == PRIME) {
				primeFactors.add(i);
			}
		}
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
		generatePrimeList();
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = readInt();

		while (testcases-- > 0) {
			int n = readInt();
			int result = 0;
			HashMap<Integer, Integer> primeVsIdx = new HashMap<Integer, Integer>(n);
			for (int i = 0; i < n; ++i) {
				int currentNo = readInt();
					for (int j = 0, jLen = primeFactors.size(); j < jLen && currentNo > 1; ++j) {
						int currentPrime = primeFactors.get(j);
						if ((currentNo % currentPrime) == 0) {
							int count = 0;
							if (!primeVsIdx.containsKey(currentPrime)) {
								primeVsIdx.put(currentPrime, 0);
							}
							while ((currentNo % currentPrime) == 0) {
								currentNo /= currentPrime;
								count++;
							}
							if ((count % 2) == 1) {
								primeVsIdx.put(currentPrime, primeVsIdx.get(currentPrime) + 1);
							}
						}
					}
					if (currentNo > 1) {
						if (!primeVsIdx.containsKey(currentNo)) {
							primeVsIdx.put(currentNo, 0);
						}
						primeVsIdx.put(currentNo, primeVsIdx.get(currentNo) + 1);
					}
			}
			Iterator<Integer> primeFactorCount = primeVsIdx.keySet().iterator();
			while (primeFactorCount.hasNext()) {
				int currentCount = primeVsIdx.get(primeFactorCount.next());
				result += min(currentCount, n - currentCount);
			}
			pw.println(result);
		}

		pw.flush();
		pw.close();
	}
}
