import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Maximum 2D Sum - n^3 solution inspired by Kadane's algorithm
 * 
 * @author arun
 *
 */
public class UVA108_2 {
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
		int n = readInt();
		int[][] sum = new int[n][n + 1];
		for (int i = 0; i < n; ++i) {
			for (int j = 1; j <= n; ++j) {
				sum[i][j] = readInt();
				sum[i][j] += sum[i][j - 1];
			}
		}
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < n; ++i) {
			// 0, 1, 2, or 3 size
			for (int j = i + 1; j <= n; ++j) {
				// how many starts of this size exist
				int subMax = Integer.MIN_VALUE;
				for (int k = 0, currentMax = 0; k < n; ++k) {
					// col major sum
					int cms = sum[k][j] - sum[k][j - i - 1];
					currentMax += cms;
					if (currentMax < 0) {
						subMax = Math.max(subMax, cms);
						currentMax = 0;
					} else {
						subMax = Math.max(subMax, currentMax);
					}
				}
				max = Math.max(max, subMax);
			}
		}
		pw.println(max);
		pw.flush();
		pw.close();
	}
}
