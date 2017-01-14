import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA12210 {
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

		int m, s, tc = 1;
		while (true) {
			m = readInt();
			s = readInt();
			if (m == 0 && s == 0) {
				break;
			}
			int[] men = new int[61], women = new int[61];
			for (int i = 0; i < m; ++i) {
				men[readInt()]++;
			}
			for (int i = 0; i < s; ++i) {
				women[readInt()]++;
			}
			for (int i = 60; i >= 2 && m > 0 && s > 0; --i) {
				while (men[i] > 0 && s > 0) {
					int minDiff = 60, age = -1;
					for (int j = 60; j >= 2; --j) {
						if (women[j] > 0) {
							int diff = Math.abs(i - j);
							if (diff < minDiff) {
								minDiff = diff;
								age = j;
							}
						}
					}
					m--;
					s--;
					women[age]--;
					men[i]--;
				}
			}
			pw.print("Case " + (tc++) + ": " + m);
			if (m > 0) {
				for (int i = 2; i <= 60; ++i) {
					if (men[i] > 0) {
						pw.print(" " + i);
						break;

					}
				}
			}
			pw.println();

		}

		pw.flush();
		pw.close();
	}
}
