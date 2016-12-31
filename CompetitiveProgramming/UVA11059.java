import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Maximum Product
 * 
 * @author arun
 *
 */
public class UVA11059 {
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

		int n, tc = 1;

		while ((n = readInt()) != 0) {
			int[] sequence = new int[n];
			for (int i = 0; i < n; ++i) {
				sequence[i] = readInt();
			}
			long max = 0;
			for (int i = 0; i < n; ++i) {
				long subMax = sequence[i];
				if (subMax > 0) {
					max = max(max, subMax);
				}
				for (int j = i + 1; j < n; ++j) {
					subMax *= sequence[j];
					if (subMax > 0) {
						max = max(max, subMax);
					}
				}
			}
			pw.println("Case #" + (tc++) + ": The maximum product is " + max + ".\n");
		}

		pw.flush();
		pw.close();
	}

	private static long max(long a, long b) {
		return a > b ? a : b;
	}
}
