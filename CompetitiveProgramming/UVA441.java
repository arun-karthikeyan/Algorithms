import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA441 {
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

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n, tc = 0;;

		while ((n = readInt()) != 0) {
			if(tc++!=0){
				pw.println();
			}
			int[] nos = new int[n];
			for (int i = 0; i < n; ++i) {
				nos[i] = readInt();
			}
			for (int a = 0; a < n; ++a) {
				for (int b = a + 1; b < n; ++b) {
					for (int c = b + 1; c < n; ++c) {
						for (int d = c + 1; d < n; ++d) {
							for (int e = d + 1; e < n; ++e) {
								for (int f = e + 1; f < n; ++f) {
									pw.println(nos[a] + " " + nos[b] + " " + nos[c] + " " + nos[d] + " " + nos[e] + " "
											+ nos[f]);
								}
							}
						}
					}
				}
			}
		}

		pw.flush();
		pw.close();
	}
}
