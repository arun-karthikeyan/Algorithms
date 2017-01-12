import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Station Balance - Greedy Algorithm 
 * Output formatting is so messed up :|
 * 
 * @author arun
 *
 */
public class UVA410 {
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

		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		// PrintWriter pw = new PrintWriter("testop.txt");

		int c, s, tc = 1;
		while (true) {
			c = readInt();
			s = readInt();
			if (c == 0 && s == 0) {
				break;
			}

			int[] specimens = new int[s];
			double average = 0.0;
			for (int i = 0; i < s; ++i) {
				int val = readInt();
				specimens[i] = val;
				average += val;
			}
			average /= c;
			Arrays.sort(specimens);
			@SuppressWarnings("unchecked")
			ArrayList<Integer>[] chambers = new ArrayList[c];
			for (int i = 0; i < c; ++i) {
				chambers[i] = new ArrayList<Integer>();
			}
			int idx = s - 1;
			for (int i = 0; i < c && idx >= 0; ++i, --idx) {
				chambers[i].add(specimens[idx]);
			}
			for (int i = c - 1; c >= 0 && idx >= 0; --i, --idx) {
				chambers[i].add(specimens[idx]);
			}
			pw.println("Set #" + (tc++));
			double imbalance = 0;
			for (int i = 0; i < c; ++i) {
				pw.printf(" %d:", i);
				double currentSum = 0d;
				for (int j = 0, jLen = chambers[i].size(); j < jLen; ++j) {
					int val = chambers[i].get(j);
					pw.print(" " + val);
					currentSum += val;

				}
				pw.println();
				imbalance = imbalance + Math.abs(currentSum - average);
			}
			pw.printf("IMBALANCE = %.5f\n\n", imbalance);
		}

		pw.flush();
		pw.close();
	}
}
