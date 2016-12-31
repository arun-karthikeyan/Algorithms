import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Citizen attention offices
 * 
 * @author arun
 *
 */
public class UVA10660 {
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

	private static int minOfficeLoc(int[] offices, int[] vals) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < 5; ++i) {
			int r = offices[i] / 5, c = offices[i] % 5;
			int dist = Math.abs(r - vals[0]) + Math.abs(c - vals[1]);
			if (min > dist) {
				min = dist;
			}
		}
		return min;
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
			int n = readInt();
			int[][] vals = new int[n][3];

			for (int i = 0; i < n; ++i) {
				vals[i][0] = readInt();
				vals[i][1] = readInt();
				vals[i][2] = readInt();
			}
			int minDist = Integer.MAX_VALUE;
			int[] minOfficeLoc = new int[5];
			for (int i = 0; i < 25; ++i) {
				for (int j = i + 1; j < 25; ++j) {
					for (int k = j + 1; k < 25; ++k) {
						for (int l = k + 1; l < 25; ++l) {
							for (int m = l + 1; m < 25; ++m) {
								int sum = 0;
								for (int a = 0; a < n; ++a) {
									int currentMinDistance = minOfficeLoc(new int[] { i, j, k, l, m }, vals[a]);
									sum += (currentMinDistance * vals[a][2]);
								}
								if (minDist > sum) {
									minDist = sum;
									minOfficeLoc[0] = i;
									minOfficeLoc[1] = j;
									minOfficeLoc[2] = k;
									minOfficeLoc[3] = l;
									minOfficeLoc[4] = m;
								}
							}
						}
					}
				}
			}
			pw.println(minOfficeLoc[0] + " " + minOfficeLoc[1] + " " + minOfficeLoc[2] + " " + minOfficeLoc[3] + " "
					+ minOfficeLoc[4]);
		}

		pw.flush();
		pw.close();
	}
}
