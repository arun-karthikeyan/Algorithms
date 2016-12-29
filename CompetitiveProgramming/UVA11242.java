import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class UVA11242 {
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

		int f, r;
		while (true) {
			f = readInt();
			if (f == 0) {
				break;
			}
			r = readInt();
			int[] frontTeeth = new int[f], rearTeeth = new int[r];
			for (int i = 0; i < f; ++i) {
				frontTeeth[i] = readInt();
			}
			for (int i = 0; i < r; ++i) {
				rearTeeth[i] = readInt();
			}
			double[] driveRatio = new double[f * r];
			for (int i = 0, k = 0; i < r; ++i) {
				double rt = rearTeeth[i];
				for (int j = 0; j < f; ++j, ++k) {
					driveRatio[k] = rt / frontTeeth[j];
				}
			}
			Arrays.sort(driveRatio);
			double maxDriveRatio = -1;
			for (int i = 1, iLen = driveRatio.length; i < iLen; ++i) {
				maxDriveRatio = max(maxDriveRatio, driveRatio[i] / driveRatio[i - 1]);
			}
			pw.printf("%.2f\n", maxDriveRatio);
		}

		pw.flush();
		pw.close();
	}

	private static double max(double a, double b) {
		return a > b ? a : b;
	}
}
