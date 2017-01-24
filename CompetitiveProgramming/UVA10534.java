import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UVA10534 {
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

	private static int binarySearchInsertPos(ArrayList<Integer> ls, int key) {
		int low = 0, high = ls.size() - 1;
		while (low <= high) {
			int mid = (low + high) >> 1, val = ls.get(mid);
			if (key < val) {
				high = mid - 1;
			} else if (key > val) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		return low;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n;
		while ((n = readInt()) != 0) {
			int[] val = new int[n];
			for (int i = 0; i < n; ++i) {
				val[i] = readInt();
			}
			ArrayList<Integer> lis = new ArrayList<Integer>();
			int[] lisDp = new int[n];
			for (int i = 0; i < n; ++i) {
				int cno = val[i];
				int ip = binarySearchInsertPos(lis, cno);
				int size = lis.size();
				if (ip < size) {
					lis.set(ip, cno);
					lisDp[i] = ip + 1;
				} else {
					lis.add(cno);
					lisDp[i] = ip+1;
				}
			}
			int max = 1;
			lis = new ArrayList<Integer>();
			for (int i = n - 1; i >= 0; --i) {
				int cno = val[i];
				int ip = binarySearchInsertPos(lis, cno);
				int size = lis.size();
				max = Math.max(max, Math.min(ip+1, lisDp[i])*2-1);
				if (ip < size) {
					lis.set(ip, cno);
				} else {
					lis.add(cno);
				}
			}
			pw.println(max);
		}

		pw.flush();
		pw.close();
	}
}
