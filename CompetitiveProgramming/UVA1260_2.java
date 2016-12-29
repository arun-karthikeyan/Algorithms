import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Sales - Better Approach using Array Inversions from Merge Sort O(nlogn)
 * 
 * @author arun
 *
 */
public class UVA1260_2 {
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

	private static int[] array;

	private static int countInversions(int start, int end) {
		if (start == end) {
			return 0;
		}
		int mid = (start + end) >> 1;
		int c1 = countInversions(start, mid);
		int c2 = countInversions(mid + 1, end);
		int i = start, j = mid + 1, offset = 0;
		int[] tempArray = new int[end - start + 1];
		int c = 0;
		while (i <= mid && j <= end) {
			if (array[j] >= array[i]) {
				tempArray[offset++] = array[j++];
				c += mid - i + 1;
			} else {
				tempArray[offset++] = array[i++];
			}
		}
		while (i <= mid) {
			tempArray[offset++] = array[i++];
		}
		while (j <= end) {
			tempArray[offset++] = array[j++];
		}
		for (i = start, offset = 0; i <= end; ++i, ++offset) {
			array[i] = tempArray[offset];
		}
		return c1 + c2 + c;
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
			array = new int[n];
			for (int i = 0; i < n; ++i) {
				array[i] = readInt();
			}
			pw.println(countInversions(0, n - 1));
		}
		pw.flush();
		pw.close();
	}
}
