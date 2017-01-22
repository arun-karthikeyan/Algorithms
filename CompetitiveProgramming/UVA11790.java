import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Murcia's Skyline - Longest Increasing/Decreasing Subsequence
 * 
 * @author arun
 *
 */
public class UVA11790 {
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
			int[] w = new int[n], h = new int[n];
			int[] lis = new int[n], lds = new int[n];
			for (int i = 0; i < n; ++i) {
				h[i] = readInt();
			}
			for (int i = 0; i < n; ++i) {
				w[i] = readInt();
			}
			lds[0] = lis[0] = w[0];
			for (int i = 1; i < n; ++i) {
				int iMax = 0, dMax = 0;
				int curHeight = h[i];
				for (int j = 0; j < i; ++j) {
					if (curHeight > h[j] && iMax < lis[j]) {
						iMax = lis[j];
					}
					if (curHeight < h[j] && dMax < lds[j]) {
						dMax = lds[j];
					}
				}
				lis[i] = w[i] + iMax;
				lds[i] = w[i] + dMax;
			}
			int iMax = Integer.MIN_VALUE, dMax = Integer.MIN_VALUE;
			for (int i = 0; i < n; ++i) {
				iMax = Math.max(iMax, lis[i]);
				dMax = Math.max(dMax, lds[i]);
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
