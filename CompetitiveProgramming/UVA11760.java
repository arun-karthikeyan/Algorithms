import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11760 {
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

	private static final int MAXGRID = 10000;
	private static final String ESCAPE = " Escaped again! More 2D grid problems!";
	private static final String CAUGHT = " Party time! Let's find a restaurant!";
	private static final String CASE = "Case ";

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int r, c, n, t = 1;
		while ((r = readInt()) != 0 && (c = readInt()) != 0 && (n = readInt()) != 0) {
//			BitSet rows = new BitSet(MAXGRID), cols = new BitSet(MAXGRID);
			boolean[] rows = new boolean[MAXGRID], cols = new boolean[MAXGRID]; //boolean has better performance for this problem
			for (int i = 0; i < n; ++i) {
//				rows.set(readInt());
//				cols.set(readInt());
				rows[readInt()] = true;
				cols[readInt()] = true;
			}
			int atifR = readInt(), atifC = readInt();
//			if (((atifR + 1) < r && !rows.get(atifR + 1) && !cols.get(atifC))
//					|| ((atifR - 1) >= 0 && !rows.get(atifR - 1) && !cols.get(atifC))
//					|| ((atifC - 1) >= 0 && !rows.get(atifR) && !cols.get(atifC - 1))
//					|| ((atifC + 1) < c && !rows.get(atifR) && !cols.get(atifC + 1))
//					|| (!rows.get(atifR) && !cols.get(atifC))) {
			if (((atifR + 1) < r && !rows[atifR + 1] && !cols[atifC])
					|| ((atifR - 1) >= 0 && !rows[atifR - 1] && !cols[atifC])
					|| ((atifC - 1) >= 0 && !rows[atifR] && !cols[atifC - 1])
					|| ((atifC + 1) < c && !rows[atifR] && !cols[atifC + 1])
					|| (!rows[atifR] && !cols[atifC])) {
				pw.println(CASE + t++ + ":" + ESCAPE);
			} else {
				pw.println(CASE + t++ + ":" + CAUGHT);
			}
		}

		pw.flush();
		pw.close();
	}
}
