import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Simple Equations Extreme ! - Still TLE :(
 * 
 * @author arun
 *
 */
public class UVA11571 {
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

	private static long sqrt(long val) {
		long sq = (long) Math.sqrt(val);
		if ((sq * sq) == val) {
			return sq;
		}
		return -1;

	}

	private static long validate(long x, long y, long newA, long newB, long newC) {
		long z = sqrt(newC - y * y);
		if (z == -1) {
			return -1;
		}
		boolean result1 = (y + z) == newA, result2 = (y * z) == newB, result3 = ((y * y) + (z * z)) == newC;
		if (result1 && result2 && result3 && x != y && x != z && y != z) {
			return z;
		} else {
			return -1;
		}
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
			long a = readLong(), b = readLong(), c = readLong();
			boolean resultPrinted = false;
			long limit = (long) Math.ceil(Math.pow(b, 1d / 3));
			for (long x = -limit; x < limit; x++) {
				if ((x == 0) || (b % x) == 0) {
					long newA = a - x, newB = x == 0 ? 0 : (b / x), newC = c - (x * x);
					long newASq = newA * newA;
					long dLong = sqrt((newASq) - (newB * 4));
					if (dLong == -1) {
						continue;
					}
					long y1 = (newA + dLong) / 2, y2 = (newA - dLong) / 2;
					long z1 = validate(x, y1, newA, newB, newC), z2 = validate(x, y2, newA, newB, newC);
					if (z1 != -1) {
						long[] vals = new long[] { x, y1, z1 };
						Arrays.sort(vals);
						pw.println(vals[0] + " " + vals[1] + " " + vals[2]);
						resultPrinted = true;
						break;
					}
					if (z2 != -1) {
						long[] vals = new long[] { x, y2, z2 };
						Arrays.sort(vals);
						pw.println(vals[0] + " " + vals[1] + " " + vals[2]);
						resultPrinted = true;
						break;
					}

				}
			}
			if (!resultPrinted) {
				pw.println("No solution.");
			}
		}

		pw.flush();
		pw.close();
	}
}
