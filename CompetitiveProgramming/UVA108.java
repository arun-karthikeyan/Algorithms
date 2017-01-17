import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
/**
 * 2D Max Sub Array Sum - based on cumulative storage, can be done better with 2D bit/2D segment tree
 * @author arun
 *
 */
public class UVA108 {
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

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n = readInt();
		int[][] array = new int[n + 1][n + 1];
		for (int i = 1; i <= n; ++i) {
			for (int j = 1; j <= n; ++j) {
				array[i][j] = readInt();
				array[i][j] += array[i][j - 1];
				array[i][j] += array[i - 1][j];
				array[i][j] -= array[i - 1][j - 1];
			}
		}
		int max = Integer.MIN_VALUE;
		for (int i = 1; i <= n; ++i) {
			for (int j = 1; j <= n; ++j) {
				for (int k = i; k <= n; ++k) {
					for (int l = j; l <= n; ++l) {
						max = max(max, array[k][l] - array[i - 1][l] - array[k][j - 1] + array[i - 1][j - 1]);
					}
				}
			}
		}
		pw.println(max);
		pw.flush();
		pw.close();
	}
}
