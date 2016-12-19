import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CHEFARRB {
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

	private static int[] getBinaryArray(int val) {
		int idx = 30;
		int[] result = new int[30];
		while (val > 0) {
			result[--idx] = val & 1;
			val >>= 1;
		}
		return result;
	}

	private static int compare(int[] result1, int[] result2) {
		for (int i = 0; i < 30; ++i) {
			if (result1[i] > 0 && result2[i] == 0) {
				return 1;
			} else if (result2[i] > 0 && result1[i] == 0) {
				return -1;
			}
		}
		return 0;
	}

	private static void add(int[] a1, int[] a2, int[] a3) {
		for (int i = 0; i < 30; ++i) {
			a1[i] = a2[i] + a3[i];
		}
	}

	private static int[] subtract(int[] a1, int[] a2) {
		int[] result = new int[30];
		for (int i = 0; i < 30; ++i) {
			result[i] = a1[i] - a2[i];
		}
		return result;
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	private static int[] getCumulativeK(int[][] cumulative, int a, int b) {
		if (a == 0) {
			return cumulative[b];
		}
		return subtract(cumulative[b], cumulative[a - 1]);

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
			int n = readInt(), k = readInt();
			int[] kBinary = getBinaryArray(k);
			int[][] vals = new int[n][30];
			int[][] cumulative = new int[n][30];
			int[] intVal = new int[n];

			for (int i = 0; i < n; ++i) {
				intVal[i] = readInt();
				vals[i] = getBinaryArray(intVal[i]);
			}
			if (n > 0) {
				for (int i = 0; i < 30; ++i) {
					cumulative[0][i] = vals[0][i];
				}
			}
			for (int i = 1; i < n; ++i) {
				add(cumulative[i], cumulative[i - 1], vals[i]);
			}

			long subArrayCount = 0l;
			for (int l = 0, i = 0; i < n; ++i) {
				if (intVal[i] >= k) {
					subArrayCount += (n - i);
					continue;
				}
				for (int j = max(i + 1, l); j < n; ++j) {
					if (compare(getCumulativeK(cumulative, i, j), kBinary) >= 0) {
						subArrayCount += (n - j);
						break;
					}
					l = j;
				}
			}
			pw.println(subArrayCount);
		}

		pw.flush();
		pw.close();
	}
}
