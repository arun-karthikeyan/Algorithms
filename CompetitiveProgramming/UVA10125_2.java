import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Sumsets
 * Another way to solve the problem
 * @author arun
 *
 */
public class UVA10125_2 {
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

	static class DMINUSC {
		int val;
		ArrayList<int[]> source;

		public DMINUSC(int val, int i, int j) {
			this.val = val;
			source = new ArrayList<int[]>();
			addSourceIndex(i, j);
		}

		public void addSourceIndex(int i, int j) {
			if (source.size() < 7)
				source.add(new int[] { i, j });
		}
	}

	private static DMINUSC[] dMinusC;

	public static DMINUSC binarySearch(int start, int end, int key) {
		int low = start, high = end;
		while (low <= high) {
			int mid = (low + high) >> 1;
			DMINUSC midElement = dMinusC[mid];
			if (key < midElement.val) {
				high = mid - 1;
			} else if (key > midElement.val) {
				low = mid + 1;
			} else {
				return midElement;
			}
		}
		return null;
	}

	private static final String NOSOLUTION = "no solution";

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n;
//		long start = System.currentTimeMillis();
		while ((n = readInt()) != 0) {
			int[] vals = new int[n];
			for (int i = 0; i < n; ++i) {
				vals[i] = readInt();
			}
			int dMinusCLen = n * n - n;
			Pair[] contributors = new Pair[dMinusCLen];
			for (int i = 0, k = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					if (i == j)
						continue;

					contributors[k++] = new Pair(i, j, vals[i] - vals[j]);
				}
			}
			Arrays.sort(contributors, new java.util.Comparator<Pair>() {
				@Override
				public int compare(Pair arg0, Pair arg1) {
					// TODO Auto-generated method stub
					return arg0.sum - arg1.sum;
				}
			});
			dMinusC = new DMINUSC[dMinusCLen];
			int newDMinusCLen = 1;
			dMinusC[0] = new DMINUSC(contributors[0].sum, contributors[0].i, contributors[0].j);
			for (int i = 1; i < dMinusCLen; ++i) {
				Pair temp = contributors[i];
				if (dMinusC[newDMinusCLen - 1].val == temp.sum) {
					dMinusC[newDMinusCLen - 1].addSourceIndex(temp.i, temp.j);
				} else {
					dMinusC[newDMinusCLen++] = new DMINUSC(temp.sum, temp.i, temp.j);
				}
			}
			int maxD = Integer.MIN_VALUE;
			for (int i = 0; i < n; ++i) {
				for (int j = i + 1; j < n; ++j) {
					int aPlusB = vals[i] + vals[j];
					DMINUSC result = binarySearch(0, newDMinusCLen - 1, aPlusB);
					if (result != null) {
						ArrayList<int[]> src = result.source;
						for (int k = 0, kLen = src.size(); k < kLen; ++k) {
							int[] currentSrc = src.get(k);
							if (i != currentSrc[0] && j != currentSrc[0] && i != currentSrc[1] && j != currentSrc[1]) {
								maxD = max(maxD, vals[currentSrc[0]]);
								break;
							}
						}
					}

				}
			}
			if (maxD == Integer.MIN_VALUE) {
				pw.println(NOSOLUTION);
			} else {
				pw.println(maxD);
			}
		}
//		long end = System.currentTimeMillis();
//		pw.println("Time Taken : " + (end - start) / 1000d);
		pw.flush();
		pw.close();
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}
}
