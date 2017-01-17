import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * A classical example of MAX 1D Sub-Array Sum
 * 
 * @author arun
 *
 */
public class UVA507 {

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

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out), true);

		int testcases = readInt(), tc = 1;

		while (testcases-- > 0) {
			int stops = readInt();
			int max = 0, st = -1, en = -1, maxLen = 0;
			for (int i = 0, iLen = stops - 1, currentMax = 0, cst = 0; i < iLen; ++i) {
				int currentVal = readInt();
				currentMax += currentVal;
				if (currentMax >= max) {
					int len = i - cst + 1;
					if (currentMax > max || len > maxLen) {
						maxLen = len;
						st = cst;
						en = i;
						max = currentMax;
					}
				}
				if (currentMax < 0) {
					currentMax = 0;
					cst = i+1;
				}
			}
			if (max > 0) {
				pw.println("The nicest part of route " + tc + " is between stops " + (st + 1) + " and " + (en + 2));
			} else {
				pw.println("Route " + tc + " has no nice parts");
			}
			tc++;
		}

		pw.flush();
		pw.close();
	}

}
