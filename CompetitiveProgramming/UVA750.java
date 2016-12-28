import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UVA750 {
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

	private static ArrayList<int[]> solutions;

	private static void get8QueenSols(int[] queenPositions, int currentPos, int sCol, int sRow, boolean[] ld,
			boolean[] rd, boolean[] r) {
		if (currentPos == 8) {
			if (queenPositions[sCol] == sRow) {
				solutions.add(queenPositions.clone());
			}
			return;
		}
		for (int i = 0; i < 8; ++i) {
			int rIdx = i, ldIdx = i + currentPos, rdIdx = i - currentPos + 7;
			if (!r[rIdx] && !ld[ldIdx] && !rd[rdIdx]) {
				r[rIdx] = true;
				ld[ldIdx] = true;
				rd[rdIdx] = true;
				queenPositions[currentPos] = i;
				get8QueenSols(queenPositions, currentPos + 1, sCol, sRow, ld, rd, r);
				r[rIdx] = false;
				ld[ldIdx] = false;
				rd[rdIdx] = false;
			}
		}

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
			pw.println("SOLN       COLUMN");
			pw.println(" #      1 2 3 4 5 6 7 8\n");
			int qr = readInt(), qc = readInt();
			solutions = new ArrayList<int[]>();
			get8QueenSols(new int[8], 0, qc - 1, qr - 1, new boolean[15], new boolean[15], new boolean[8]);
			for (int i = 0, iLen = solutions.size(); i < iLen; ++i) {
				int[] currentSol = solutions.get(i);
				pw.printf("%2d      %d", (i + 1), (currentSol[0] + 1));
				for (int j = 1; j < 8; ++j) {
					pw.print(" " + (currentSol[j] + 1));
				}
				pw.println();
			}
			if (testcases != 0) {
				pw.println();
			}
		}

		pw.flush();
		pw.close();
	}
}
