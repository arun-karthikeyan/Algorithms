import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Trip, 2007 *Note - Only one bag can be packed inside another bag
 * 
 * @author arun
 *
 */
public class UVA11100 {
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

		int n, tc = 1;
		while ((n = readInt()) != 0) {
			if (tc++ != 1) {
				pw.println();
			}
			int[] bags = new int[n];
			for (int i = 0; i < n; ++i) {
				bags[i] = readInt();
			}
			Arrays.sort(bags);
			int maxLen = 1, occ = 1;
			for (int i = 1; i < n; ++i) {
				if (bags[i] == bags[i - 1]) {
					occ++;
				} else {
					maxLen = Math.max(maxLen, occ);
					occ = 1;
				}
			}
			maxLen = Math.max(maxLen, occ);
			@SuppressWarnings("unchecked")
			ArrayList<Integer>[] results = new ArrayList[maxLen];
			for (int i = 0; i < maxLen; ++i) {
				results[i] = new ArrayList<Integer>();
			}
			for (int i = 0; i < n; ++i) {
				results[i % maxLen].add(bags[i]);
			}
			pw.println(maxLen);
			for (int i = 0; i < maxLen; ++i) {
				ArrayList<Integer> currentPacking = results[i];
				int size = currentPacking.size();
				if (size > 0) {
					pw.print(currentPacking.get(0));
				}
				for (int j = 1; j < size; ++j) {
					pw.print(" " + currentPacking.get(j));
				}
				pw.println();
			}
		}

		pw.flush();
		pw.close();
	}
}
