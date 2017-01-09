import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Number Sequence
 * 
 * @author arun
 *
 */
public class UVA10706 {
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

	private static int getDigits(int val) {
		int count = 0;
		while (val > 0) {
			count++;
			val /= 10;
		}
		return count;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int max = 31268;// ~upper bound
		long[] sk = new long[max];
		for (int i = 1; i < max; ++i) {
			sk[i] = sk[i - 1] + getDigits(i);
		}
		for (int i = 1; i < max; ++i) {
			sk[i] += sk[i - 1];
		}

		int testcases = readInt();
		while (testcases-- > 0) {
			long digitIdx = readInt();
			int idx = Arrays.binarySearch(sk, digitIdx);
			if (idx < 0) {
				idx *= -1;
				idx--;
			}
			idx--;
			digitIdx = digitIdx - sk[idx];
			int i = -1, j = -1, digits = 0;
			for (i = 0, j = 1; i < idx && (digitIdx - (digits = getDigits(j))) > 0; j++) {
				digitIdx -= digits;
			}
			digitIdx--;
			String strVal = String.valueOf(j);
			pw.println(strVal.charAt((int)digitIdx));
		}

		pw.flush();
		pw.close();
	}
}
