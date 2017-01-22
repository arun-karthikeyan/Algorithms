import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
/**
 * Sucky n^4 solution, but with no extra space
 * @author arun
 *
 */
public class UVA10827 {
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

	private static int[][] torus;

	private static int getSum(int i, int j, int i2, int j2) {
		int sum = torus[i2][j2];
		if (i > 0)
			sum -= torus[i - 1][j2];
		if (j > 0)
			sum -= torus[i2][j - 1];
		if (i > 0 && j > 0)
			sum += torus[i - 1][j - 1];
		return sum;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = readInt();

		torus = new int[76][76];
		while (testcases-- > 0) {
			int n = readInt();
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					torus[i][j] = readInt();
					if (j > 0)
						torus[i][j] += torus[i][j - 1];
					if (i > 0)
						torus[i][j] += torus[i - 1][j];
					if (i > 0 && j > 0)
						torus[i][j] -= torus[i - 1][j - 1];
				}
			}
			int max = -562500;
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					for (int k = 0; k < n; ++k) {
						int newI = i + k;
						for (int l = 0; l < n; ++l) {
							int newJ = j + l;
							int sum = 0;
							// case 1 both newI and newJ are within bounds
							if (newI < n && newJ < n) {
								sum = getSum(i, j, newI, newJ);
							} else if (newI < n) {
								// case 2 only newI is within bounds
								newJ %= n;
								sum = getSum(i, j, newI, n - 1) + getSum(i, 0, newI, newJ);
							} else if (newJ < n) {
								// case 2 only newJ is within bounds
								sum = getSum(i, j, n - 1, newJ) + getSum(0, j, newI % n, newJ);
							} else {
								// case 3 both are out of bounds
								sum = getSum(i, j, n - 1, n - 1) + getSum(0, 0, newI % n, newJ % n)
										+ getSum(0, j, newI % n, n - 1) + getSum(i, 0, n - 1, newJ % n);
							}
							if(sum==15){
								System.out.println("Comes here");
							}
							max = max(max, sum);
						}
					}
				}
			}
			pw.println(max);
		}

		pw.flush();
		pw.close();

	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}
}
