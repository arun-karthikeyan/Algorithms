import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
/**
 * Maximum sum on a Torus - O(n^3) solution using Kadane's algorithm and Maximum Circular Sub-Array Sum 
 * @author arun
 *
 */
public class UVA10827_2 {
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

	private static int kadane(int[] array, int n) {
		int currentSum = 0, maxSum = Integer.MIN_VALUE;
		for (int i = 0; i < n; ++i) {
			currentSum += array[i];
			if (currentSum < 0) {
				currentSum = 0;
				maxSum = max(maxSum, array[i]);
			} else {
				maxSum = max(maxSum, currentSum);
			}
		}
		return maxSum;
	}

	private static int min(int a, int b) {
		return a < b ? a : b;
	}

	private static int maxCircularSum(int[] array, int n) {
		int max = kadane(array, n);
		int maxWrap = 0;
		boolean containsZero = false;
		int min = Integer.MAX_VALUE;
		for (int k = 0; k < n; ++k) {
			int val = array[k];
			if (!containsZero && val == 0) {
				containsZero = true;
			}
			min = min(min, val);
			maxWrap += val;
			array[k] = -val;
		}
		int invertedMax = maxWrap + kadane(array, n);
		if (invertedMax == 0 && !containsZero) {
			invertedMax = min;
		}
		return max(max, invertedMax);
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

		int testcases = readInt();

		while (testcases-- > 0) {
			int n = readInt();
			int[][] torus = new int[n][n];
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					torus[i][j] = readInt();
					if (j > 0) {
						torus[i][j] += torus[i][j - 1];
					}
				}
			}
			int overAllMax = -100 * 75 * 75 + 1;
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					int start = j, end = j + i; // => [start,end]
					int[] array = new int[n];
					for (int k = 0; k < n; ++k) {
						int sum;
						if (end < n) {
							sum = torus[k][end];
							if (start > 0) {
								sum -= torus[k][start - 1];
							}
						} else {
							sum = torus[k][n - 1] - torus[k][start - 1] + torus[k][end % n];
						}
						array[k] = sum;
					}
					overAllMax = max(overAllMax, maxCircularSum(array, n));
				}
			}
			pw.println(overAllMax);
		}
		pw.flush();
		pw.close();
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}
}
