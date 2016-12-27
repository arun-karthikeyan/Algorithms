import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class UVA11565 {
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

		int testcases = readInt();

		while (testcases-- > 0) {
			int a = readInt(), b = readInt(), c = readInt();
			boolean resultPrint = false;
			int limit = ((int) (Math.pow(b, (1d / 3)))) + 1;
			for (int x = -limit; x < limit && !resultPrint; ++x) {
				int xSq = x * x;
				for (int y = -100; y <= 100; ++y) {
					if (x == y) {
						continue;
					}
					int ySq = y * y;
					int zSq = c - (xSq + ySq);
					int z = (int) Math.sqrt(zSq);
					if (x == z || y == z) {
						continue;
					}
					int[] vals = new int[] { x, y, z };
					Arrays.sort(vals);
					if (((z * z) == zSq) && ((x * y * z) == b) && ((x + y + z) == a)) {
						pw.println(vals[0] + " " + vals[1] + " " + vals[2]);
						resultPrint = true;
						break;
					}
				}
			}
			if (!resultPrint) {
				pw.println("No solution.");
			}
		}

		pw.flush();
		pw.close();
	}
}
