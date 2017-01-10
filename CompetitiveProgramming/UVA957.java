import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA957 {
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

	private static final int MAX = (int) 1e6;

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		while (true) {
			int y = readInt();
			if (y == 0) {
				break;
			}
			int[] popes = new int[MAX + 1];
			int popeCount = readInt();

			int[] validYears1 = new int[MAX + 1];
			int[] validYears2 = new int[MAX + 1];
			for (int i = 0; i < popeCount; ++i) {
				int val = readInt();
				popes[val]++;
				validYears1[val] = val;
				validYears2[val] = val;
			}

			int sum = 0, maxSum = 0, maxStartYear = -1, maxEndYear = -1;

			for (int i = 0; i < y; ++i) {
				sum += popes[i];
			}

			int lastValidYear = 0;
			for (int i = 1; i <= MAX; ++i) {
				if (validYears1[i] != 0) {
					lastValidYear = i;
				}
				validYears1[i] = lastValidYear;
			}

			lastValidYear = MAX;
			for (int i = MAX - 1; i >= 0; --i) {
				if (validYears2[i] != 0) {
					lastValidYear = i;
				}
				validYears2[i] = lastValidYear;
			}

			for (int i = y; i <= MAX; ++i) {
				int startYear = i - y;
				if (sum > maxSum) {
					maxSum = sum;
					maxStartYear = validYears2[startYear];
					maxEndYear = validYears1[i - 1];
				}
				sum -= popes[startYear];
				sum += popes[i];
			}

			if (sum > maxSum) {
				maxSum = sum;
				maxStartYear = validYears2[MAX + 1 - y];
				maxEndYear = validYears1[MAX];
			}
			pw.println(maxSum + " " + maxStartYear + " " + maxEndYear);
		}
		pw.flush();
		pw.close();
	}
}
