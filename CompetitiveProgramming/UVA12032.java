import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * The Monkey and the Oiled Bamboo - Binary Search the Answer Technique
 * 
 * @author arun
 *
 */
public class UVA12032 {
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

	private static int n, rugs[];

	private static boolean possible(int k) {
		int prev = 0;
		for (int i = 0; i < n; ++i) {
			int jump = (rugs[i] - prev);
			if (jump == k) {
				--k;
			} else if (jump > k) {
				return false;
			}
			prev = rugs[i];
		}
		return true;
	}

	private static int findOptimal() {
		int low = 1, high = (int) 1e7, prevMid = low, mid = 0, minK = 0;
		while (prevMid != (mid = ((low + high) >> 1))) {
			if (possible(mid)) {
				high = mid;
				minK = mid;
			} else {
				low = mid;
			}
			prevMid = mid;
		}
		return minK;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		for (int tc = 1, tcLen = readInt(); tc <= tcLen; ++tc) {
			n = readInt();
			rugs = new int[n];
			for (int i = 0; i < n; ++i) {
				rugs[i] = readInt();
			}
			pw.println("Case " + (tc) + ": " + findOptimal());
		}

		pw.flush();
		pw.close();
	}
}
