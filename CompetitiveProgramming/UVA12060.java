import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA12060 {
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

	private static final int gcd(int a, int b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}

	public static void main(String[] args) throws Exception {
		stream = System.in;
//		stream = new FileInputStream(new File("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n, i = 1;

		while ((n = readInt()) != 0) {
			int sum = 0;
			for (int j = 0; j < n; ++j) {
				sum += readInt();
			}
			boolean minus = false;

			if (sum < 0) {
				minus = true;
				sum *= -1;
			}
			int gcd = gcd(sum, n);
			n /= gcd;
			sum /= gcd;
			pw.println("Case " + (i++) + ":");
			if (n > sum) {
				if (sum == 0) {
					pw.println(0);
				} else {
					String b = String.valueOf(sum), c = String.valueOf(n);
					for (int j = b.length(), jLen = c.length(); j < jLen; ++j) {
						b = " " + b;
					}
					StringBuilder sb = new StringBuilder();
					for (int j = 0, jLen = c.length(); j < jLen; ++j) {
						sb.append('-');
					}
					String minusPrefix = "";
					if (minus) {
						b = "  " + b;
						minusPrefix = "- ";
						c = "  " + c;
					}
					pw.println(b);
					pw.println(minusPrefix + sb.toString());
					pw.println(c);
				}
			} else {
				if (n == 1) {
					if (minus) {
						pw.print("- ");
					}
					pw.println(sum);
				} else {
					String a = String.valueOf(sum / n);
					String b = String.valueOf(sum % n);
					String c = String.valueOf(n);
					for (int j = b.length(), jLen = c.length(); j < jLen; ++j) {
						b = " " + b;
					}
					StringBuilder sb = new StringBuilder();
					for (int j = 0, jLen = c.length(); j < jLen; ++j) {
						sb.append('-');
					}
					for (int j = 0, jLen = a.length(); j < jLen; ++j) {
						b = " " + b;
						c = " " + c;
					}
					String minusPrefix = "";
					if (minus) {
						minusPrefix = "- ";
						b = "  " + b;
						c = "  " + c;
					}
					pw.println(b);
					pw.println(minusPrefix + a + sb.toString());
					pw.println(c);
				}
			}
		}

		pw.flush();
		pw.close();
	}

}
