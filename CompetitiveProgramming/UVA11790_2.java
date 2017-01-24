import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

import org.omg.PortableServer.LifespanPolicyOperations;

/**
 * Murcia's Skyline - Longest Increasing/Decreasing Subsequence O(nlogk)
 * solution
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

	// to get the longest increasing and longest decreasing subsequences
	private static int binarySearchHeight(ArrayList<Integer> array, int key, Comparator<Integer> comp) {
		int low = 0, high = array.size() - 1;
		while (low <= high) {
			int mid = (low + high) >> 1, c = comp.compare(key, array.get(mid));
			if (c < 0) {
				high = mid - 1;
			} else if (c > 0) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		return low;
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	private static final int IDX = 0, WEIGHT = 1;

	// need to get search index of weights
	private static int binarySearchWeightIdx(ArrayList<int[]> array, int key) {
		int low = 0, high = array.size() - 1;
		while (low <= high) {
			int mid = (low + high) >> 1, val = array.get(mid)[WEIGHT];
			if (key < val) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		// low is always greater than 0
		return low - 1;
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

			ArrayList<ArrayList<int[]>> lis = new ArrayList<ArrayList<int[]>>();
			ArrayList<ArrayList<int[]>> lds = new ArrayList<ArrayList<int[]>>();
			ArrayList<Integer> lisGreedy = new ArrayList<Integer>();
			ArrayList<Integer> ldsGreedy = new ArrayList<Integer>();
			Comparator<Integer> ldsComp = Comparator.reverseOrder();
			Comparator<Integer> lisComp = Comparator.naturalOrder();
			int iMax = Integer.MIN_VALUE, dMax = Integer.MIN_VALUE;

			for (int i = 0; i < n; ++i) {
				h[i] = readInt();
			}
			for (int i = 0; i < n; ++i) {
				w[i] = readInt();
			}

			for (int i = 0; i < n; ++i) {
				int currentHeight = h[i], currentWeight = w[i];
				int lisIp = binarySearchHeight(lisGreedy, currentHeight, lisComp);
				if (lisIp == lisGreedy.size()) {
					lisGreedy.add(currentHeight);
					ArrayList<int[]> lisPos = new ArrayList<int[]>();
					if (lisIp > 0) {
						ArrayList<int[]> prevPosition = lis.get(lisIp - 1);
						lisPos.add(new int[] { i, currentWeight + prevPosition.get(prevPosition.size() - 1)[WEIGHT] });
						lis.add(lisPos);
					}
				} else {

				}
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
