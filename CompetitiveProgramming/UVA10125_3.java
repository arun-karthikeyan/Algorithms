import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Sumsets Optimized Implementation ;) - Best Solution
 * 
 * @author arun
 *
 */
public class UVA10125_3 {
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

	static class Pair {
		int i, j, sum;

		public Pair(int i, int j, int sum) {
			this.i = i;
			this.j = j;
			this.sum = sum;
		}
	}

	public static int binarySearch(int start, int end, int key) {
		int low = start, high = end;
		while (low <= high) {
			int mid = (low + high) >> 1;
			int midElement = contributors[mid].sum;
			if (key <= midElement) {
				high = mid - 1;
			} else if (key > midElement) {
				low = mid + 1;
			}
		}
		return low <= end && contributors[low].sum == key ? low : -1;
	}

	private static final String NOSOLUTION = "no solution";
	private static Pair[] contributors;

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n;
		// long start = System.currentTimeMillis();
		while ((n = readInt()) != 0) {
			int[] vals = new int[n];
			for (int i = 0; i < n; ++i) {
				vals[i] = readInt();
			}
			Arrays.sort(vals);
			int aPlusBLen = (n * (n - 1)) / 2;
			contributors = new Pair[aPlusBLen];
			for (int i = 0, k = 0; i < n; ++i) {
				for (int j = i + 1; j < n; ++j) {
					contributors[k++] = new Pair(i, j, vals[i] + vals[j]);
				}
			}
			Arrays.sort(contributors, new java.util.Comparator<Pair>() {
				@Override
				public int compare(Pair arg0, Pair arg1) {
					// TODO Auto-generated method stub
					return arg0.sum - arg1.sum;
				}
			});
			int maxD = -1;
			boolean dFound = false;
			for (int i = n - 1; i >= 0 && !dFound; --i) {
				for (int j = 0; j < n && !dFound; ++j) {
					if (i == j) {
						continue;
					}
					int dMinusC = vals[i] - vals[j];
					int resultIdx = binarySearch(0, aPlusBLen - 1, dMinusC);
					if (resultIdx != -1) {
						Pair temp;
						int limit = min(resultIdx + 4, aPlusBLen);
						while (resultIdx < limit && ((temp = contributors[resultIdx]).sum == dMinusC)) {
							int tempI = temp.i, tempJ = temp.j;
							if (i != tempI && i != tempJ && j != tempI && j != tempJ) {
								dFound = true;
								maxD = vals[i];
								break;
							}
							resultIdx++;
						}
					}

				}
			}
			if (dFound) {
				pw.println(maxD);
			} else {
				pw.println(NOSOLUTION);
			}
		}
		// long end = System.currentTimeMillis();
		// pw.println("Time Taken : " + (end - start) / 1000d);
		pw.flush();
		pw.close();
	}

	private static int min(int a, int b) {
		return a < b ? a : b;
	}
}
