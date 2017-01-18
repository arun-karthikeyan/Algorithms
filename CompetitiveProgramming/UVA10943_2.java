import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * How do you add ? - Top Down Approach
 * 
 * @author arun
 *
 */
public class UVA10943_2 {
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

	private static int countWays(int n, int k, int[][] memoi) {
		if (n < 0 || k < 0) {
			return 0;
		}
		if (k == 1) {
			return 1;
		}
		if (memoi[n][k] == -1) {
			int sum = 0;
			for (int i = 0; i <= n; ++i) {
				sum = (sum + countWays(n - i, k - 1, memoi)) % 1000000;
			}
			return memoi[n][k] = sum;
		}
		return memoi[n][k];
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n, k;
		while (true) {
			n = readInt();
			k = readInt();
			if (n == 0 && k == 0) {
				break;
			}
			int[][] memoi = new int[n + 1][k + 1];
			for(int i=0;i<=n;++i) Arrays.fill(memoi[i], -1);
			pw.println(countWays(n, k, memoi));
		}

		pw.flush();
		pw.close();
	}
}
