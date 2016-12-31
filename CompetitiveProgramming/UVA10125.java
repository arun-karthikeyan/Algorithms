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
 * 
 * @author arun
 *
 */
public class UVA10125 {
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

	static class APLUSB {
		int val;
		ArrayList<int[]> source;

		public APLUSB(int val, int i, int j) {
			this.val = val;
			source = new ArrayList<int[]>();
			addSourceIndex(i, j);
		}

		public void addSourceIndex(int i, int j) {
			if (source.size() < 4)
				source.add(new int[] { i, j });
		}
	}

	private static APLUSB[] aPlusB;

	public static APLUSB binarySearch(int start, int end, int key) {
		int low = start, high = end;
		while (low <= high) {
			int mid = (low + high) >> 1;
			APLUSB midElement = aPlusB[mid];
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
		// long start = System.currentTimeMillis();
		while ((n = readInt()) != 0) {
			int[] vals = new int[n];
			for (int i = 0; i < n; ++i) {
				vals[i] = readInt();
			}
			Arrays.sort(vals);
			int aPlusBLen = (n * (n - 1)) / 2;
			Pair[] contributors = new Pair[aPlusBLen];
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
			aPlusB = new APLUSB[aPlusBLen];
			int newAPlusBLen = 1;
			aPlusB[0] = new APLUSB(contributors[0].sum, contributors[0].i, contributors[0].j);
			for (int i = 1; i < aPlusBLen; ++i) {
				Pair temp = contributors[i];
				if (aPlusB[newAPlusBLen - 1].val == temp.sum) {
					aPlusB[newAPlusBLen - 1].addSourceIndex(temp.i, temp.j);
				} else {
					aPlusB[newAPlusBLen++] = new APLUSB(temp.sum, temp.i, temp.j);
				}
			}
			int maxD = -1;
			boolean dFound = false;
			for (int i = n - 1; i >= 0 && !dFound; --i) {
				for (int j = 0; j < n && !dFound; ++j) {
					if (i == j) {
						continue;
					}
					int dMinusC = vals[i] - vals[j];
					APLUSB result = binarySearch(0, newAPlusBLen - 1, dMinusC);
					if (result != null) {
						ArrayList<int[]> src = result.source;
						for (int k = 0, kLen = src.size(); k < kLen; ++k) {
							int[] currentSrc = src.get(k);
							if (i != currentSrc[0] && j != currentSrc[0] && i != currentSrc[1] && j != currentSrc[1]) {
								// maxD = max(maxD, vals[i]);
								maxD = vals[i];
								dFound = true;
								break;
							}
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

	// private static int max(int a, int b) {
	// return a > b ? a : b;
	// }
}
