import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA10920 {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];

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

	private static final int UP = 0, RIGHT = 3, MAXTURNS = 4;
	private static final String LINE = "Line", COLUMN = "column";
	private static final int ROW = 0, COL = 1;

	private static void updatePositions(int[] oldPositions, int jumps, int dir) {
		if ((dir % 2) == 0) {
			if (dir == UP) {
				oldPositions[ROW] += jumps;
			} else {
				oldPositions[ROW] -= jumps;
			}

		} else {
			if (dir == RIGHT) {
				oldPositions[COL] += jumps;
			} else {
				oldPositions[COL] -= jumps;
			}
		}
	}

	private static void findPosition(int[] position, long no, long p, int jumps, int dir) {
		long midjump = p - no - jumps;
		if ((dir % 2) == 0) {
			if (dir == UP) {
				position[ROW] += midjump;
			} else {
				position[ROW] -= midjump;
			}

		} else {
			if (dir == RIGHT) {
				position[COL] += midjump;
			} else {
				position[COL] -= midjump;
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

		int size;
		long p;

		while ((size = readInt()) != 0 && (p = readLong()) != 0) {
			int[] position = new int[] { (size + 1) / 2, (size + 1) / 2 };
			long no = 1;
			int jumps = 0, dir = UP, turns = 0, turnLen = (size << 1) - 2;
			while (no < p && turns < turnLen) {
				if (dir % 2 == 0) {
					jumps++;
				}
				updatePositions(position, jumps, dir);
				no += jumps;
				dir = (dir + 1) % MAXTURNS;
				turns++;
			}
			if (no < p) {
				updatePositions(position, jumps, dir);
				no += jumps;
				dir = (dir + 1) % MAXTURNS;
			}
			if (no > p) {
				int oldDirection = (dir - 1) < 0 ? 3 : (dir - 1);
				no -= jumps;
				findPosition(position, no, p, jumps, oldDirection);
			}
			pw.println(LINE + " = " + position[ROW] + ", " + COLUMN + " = " + position[COL] + ".");

		}

		pw.flush();
		pw.close();
	}
}
