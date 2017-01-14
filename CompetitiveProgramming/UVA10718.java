import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA10718 {
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
				return -1;
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

	private static long getOKBits(long n) {
		for (long val = 1; val < n; val *= 2) {
			n |= val;
		}
		return n;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		long n, l, u;
		while (true) {
			n = readLong();
			l = readLong();
			u = readLong();
			if (n == -1 && u == -1 && l == -1) {
				break;
			}
			long ok2 = getOKBits(u);
			long bitsToChange = (ok2 & (((1l << 32) - 1) & ~n));
			String bitsToChangeStr = Long.toBinaryString(bitsToChange);
			long m = l;
			for (int i = 0, iLen = bitsToChangeStr.length(); i < iLen; ++i) {
				if (bitsToChangeStr.charAt(i) == '1') {
					long bitToSet = (1l << (iLen - i - 1));
					long temp = m | bitToSet;
					if (temp <= u) {
						m = temp;
					} else {
						temp = (temp & (~(bitToSet - 1)));
						if (temp <= u) {
							m = temp;
						}
					}
				} else {
					long bitToUnSet = (1l << (iLen - i - 1));
					long temp = (m & (~bitToUnSet));
					if (temp >= l) {
						m = temp;
					}
				}
			}
			pw.println(m);

		}

		pw.flush();
		pw.close();
	}
}
