import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Murcia's Skyline - Longest Increasing/Decreasing Subsequence O(nlogk)
 * solution - incomplete
 * 
 * @author arun
 *
 */
public class UVA11790_2 {
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

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = readInt(), tc = 1;

		while (testcases-- > 0) {
			int n = readInt();
			int[] h = new int[n], w = new int[n];
			int iMax = Integer.MIN_VALUE, dMax = Integer.MIN_VALUE;

			for (int i = 0; i < n; ++i) {
				h[i] = readInt();
			}
			for (int i = 0; i < n; ++i) {
				w[i] = readInt();
			}
			TreeMap<Integer, Integer> weightedLIS = new TreeMap<Integer, Integer>();
			TreeMap<Integer, Integer> weightedLDS = new TreeMap<Integer, Integer>();
			for (int i = 0; i < n; ++i) {
				int currentHeight = h[i], currentWeight = w[i];
				Entry<Integer, Integer> lis = weightedLIS.lowerEntry(currentHeight);
				Entry<Integer, Integer> lds = weightedLDS.higherEntry(currentHeight);
				int newWeightLIS, newWeightLDS;
				if (lis == null) {
					Integer oldVal = weightedLIS.get(currentHeight);
					newWeightLIS = currentWeight;
					if (oldVal != null) {
						newWeightLIS = max(newWeightLIS, oldVal);
					}
				} else {
					Integer oldVal = weightedLIS.get(currentHeight);
					newWeightLIS = lis.getValue() + currentWeight;
					if (oldVal != null) {
						newWeightLIS = max(oldVal, newWeightLIS);
					}
				}
				
				if (lds == null) {
					Integer oldVal = weightedLDS.get(currentHeight);
					newWeightLDS = currentWeight;
					if (oldVal != null) {
						newWeightLDS = max(newWeightLDS, oldVal);
					}
				} else {
					Integer oldVal = weightedLDS.get(currentHeight);
					newWeightLDS = lds.getValue() + currentWeight;
					if (oldVal != null) {
						newWeightLDS = max(oldVal, newWeightLDS);
					}
				}
				weightedLIS.put(currentHeight, newWeightLIS);
				weightedLDS.put(currentHeight, newWeightLDS);
				iMax = max(iMax, newWeightLIS);
				dMax = max(dMax, newWeightLDS);
			}

			if (iMax >= dMax) {
				pw.println("Case " + (tc++) + ". Increasing (" + iMax + "). Decreasing (" + dMax + ").");
			} else {
				pw.println("Case " + (tc++) + ". Decreasing (" + dMax + "). Increasing (" + iMax + ").");
			}
		}

		pw.flush();
		pw.close();
	}
}
