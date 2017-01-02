import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
/**
 * Can be solved in O(1), 2^n search not required.
 * @author arun
 *
 */
public class UVA10576 {
	private static final String DEFICIT = "Deficit";
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

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		long s;
		while ((s = readLong()) != 0) {
			long d = readLong();
			if (d > 4 * s) {
				long totalSurplus = 10 * s - 2 * d;
				if (totalSurplus > 0) {
					pw.println(totalSurplus);
				} else {
					pw.println(DEFICIT);
				}
			} else if (2 * d > 3 * s) {
				long totalSurplus = 8 * s - 4 * d;
				if (totalSurplus > 0) {
					pw.println(totalSurplus);
				} else {
					pw.println(DEFICIT);
				}
			} else if (3 * d > 2 * s) {
				long totalSurplus = 6 * s - 6 * d;
				if (totalSurplus > 0) {
					pw.println(totalSurplus);
				} else {
					pw.println(DEFICIT);
				}
			} else if (4 * d > s) {
				long totalSurplus = 3 * s - 9 * d;
				if (totalSurplus > 0) {
					pw.println(totalSurplus);
				} else {
					pw.println(DEFICIT);
				}
			} else {
				pw.println(DEFICIT);
			}
		}

		pw.flush();
		pw.close();
	}
}
