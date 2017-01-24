import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * UVA 231 - Testing the CATCHER A straightforward implementation of LDS (but
 * not strictly decreasing)
 * 
 * @author arun
 *
 */
public class UVA231 {
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

	private static int binarySearchInsertPos(ArrayList<Integer> lis, int key) {
		int low = 0, high = lis.size() - 1;
		while (low <= high) {
			int mid = (low + high) >> 1, val = lis.get(mid);
			if (key <= val) {
				low = mid + 1;
			} else if (key > val) {
				high = mid - 1;
			}
		}
		return low;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n, tc = 1;
		while ((n = readInt()) != -1) {
			if (tc != 1) {
				pw.println();
			}
			ArrayList<Integer> lis = new ArrayList<Integer>();
			do {
				int ip = binarySearchInsertPos(lis, n);
				if (ip < lis.size()) {
					lis.set(ip, n);
				} else {
					lis.add(n);
				}
			} while ((n = readInt()) != -1);
			pw.println("Test #" + (tc++) + ":\n" + "  maximum possible interceptions: " + lis.size());
		}

		pw.flush();
		pw.close();
	}
}
