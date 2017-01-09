import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * The Playboy Chimp
 * 
 * @author arun
 *
 */
public class UVA10661 {
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

	private static int[] ladyChimps;
	private static int n;

	private static int[] baitLadyChimps(int q) {
		int low = 0, high = n - 1;
		while (low <= high) {
			int mid = (high + low) >> 1, val = ladyChimps[mid];
			if (q <= val) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		int v1 = high >= 0 ? ladyChimps[high] : -1;
		low = 0;
		high = n - 1;
		while (low <= high) {
			int mid = (high + low) >> 1, val = ladyChimps[mid];
			if (q >= val) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		int v2 = low < n ? ladyChimps[low] : -1;
		return new int[] { v1, v2 };
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		n = readInt();
		ladyChimps = new int[n];
		for (int i = 0; i < n; ++i) {
			ladyChimps[i] = readInt();
		}
		int q = readInt();
		for (int i = 0; i < q; ++i) {
			int[] result = baitLadyChimps(readInt());
			pw.println((result[0] == -1 ? "X" : result[0]) + " " + (result[1] == -1 ? "X" : result[1]));
		}

		pw.flush();
		pw.close();
	}
}
