import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11413 {
	/**
	 * Fill the containers - Binary Search the Answer technique | very
	 * interesting
	 */
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

	private static int n, m, vessels[];

	private static boolean packingPossible(int maxCapacity) {

		int containerCount = 1, containerCapacity = 0;
		for (int i = 0; i < n && containerCount <= m; ++i) {
			int currentCapacity = vessels[i];
			if ((containerCapacity + currentCapacity) <= maxCapacity) {
				containerCapacity += currentCapacity;
			} else if (currentCapacity <= maxCapacity) {
				containerCount++;
				containerCapacity = currentCapacity;
			} else {
				return false;
			}
		}
		return containerCount <= m;
	}

	private static int findOptimalCapacity(int low, int high) {
		int requiredCapacity = high, prevMid = high, mid = 0;
		while (prevMid != (mid = ((high + low) >> 1))) {
			if (packingPossible(mid)) {
				high = mid;
				requiredCapacity = mid;
			} else {
				low = mid;
			}
			prevMid = mid;
		}
		return requiredCapacity;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		while (true) {
			n = readInt();
			m = readInt();
			if (n == 0 && m == 0) {
				break;
			}
			vessels = new int[n];
			for (int i = 0; i < n; ++i) {
				vessels[i] = readInt();
			}
			int minCapacity = vessels[0], maxCapacity = vessels[0];
			for (int i = 1; i < n; ++i) {
				int currentCapacity = vessels[i];
				minCapacity = max(minCapacity, currentCapacity);
				maxCapacity += currentCapacity;
			}
			pw.println(findOptimalCapacity(minCapacity, maxCapacity));

		}

		pw.flush();
		pw.close();
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}
}
