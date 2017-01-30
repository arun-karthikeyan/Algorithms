import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11517 {
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

		int testcases = readInt();

		while (testcases-- > 0) {
			int m = readInt();
			int c = readInt();
			int[] coins = new int[c];
			for (int i = 0; i < c; ++i) {
				coins[i] = readInt();
			}
			int[] coinCount = new int[10001];
			for (int i = 0; i < c; ++i) {
				int currentCoin = coins[i];
				for (int j = 10000; j >= currentCoin; --j) {
					if (coinCount[j - currentCoin] > 0 || j == currentCoin) {
						if (coinCount[j] > 0) {
							coinCount[j] = Math.min(coinCount[j], coinCount[j - currentCoin] + 1);
						} else {
							coinCount[j] = coinCount[j - currentCoin] + 1;
						}
					}
				}
			}
			for (int i = m; i <= 10000; ++i) {
				if (coinCount[i] > 0) {
					pw.println(i + " " + coinCount[i]);
					break;
				}
			}
		}

		pw.flush();
		pw.close();
	}
}
