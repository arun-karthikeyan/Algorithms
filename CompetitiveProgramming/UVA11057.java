import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * A Classic Example of the two sum problem
 * 
 * @author arun
 *
 */
public class UVA11057 {
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

	private static int n;
	private static int[] values;
	private static String PRINT = "Peter should buy books whose prices are ";

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		while ((n = readInt()) != 0) {
			values = new int[n];
			for (int i = 0; i < n; ++i) {
				values[i] = readInt();
			}
			Arrays.sort(values);
			int sum = readInt();
			int v1 = -1, v2 = -1, diff = Integer.MAX_VALUE;
			for (int i = 0; i < n; ++i) {
				int a = values[i], b = sum - values[i];
				if (b < a) {
					break;
				}
				int idx = Arrays.binarySearch(values, i + 1, n, b);
				if (idx > 0 && (a - values[idx]) < diff) {
					v1 = a;
					v2 = values[idx];
				}
			}
			pw.println(PRINT + v1 + " and " + v2 + "."+"\n");
		}

		pw.flush();
		pw.close();
	}
}
