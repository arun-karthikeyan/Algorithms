import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA129 {
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

	private static int n, l, count;
	private static String result;

	private static void solve(StringBuilder chs, int chsLen) {
		if (count == n) {
			result = chs.toString();
			return;
		}

		for (int i = 0; i < l && count < n; ++i) {
			boolean hard = true;
			char ch = (char) ('A' + i);
			for (int j = chsLen - 1, jLow = (chsLen / 2); j >= jLow; --j) {
				if (chs.charAt(j) == ch) {
					int jump = chsLen - j, k = j + 1;
					for (; k < chsLen; ++k) {
						if (chs.charAt(k) != chs.charAt(k - jump)) {
							break;
						}
					}
					if (k == chsLen) {
						hard = false;
						break;
					}
				}
			}
			if (hard) {
				count++;
				chs.append(ch);
				solve(chs, chsLen + 1);
				chs.deleteCharAt(chsLen);
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
		StringBuilder temp = new StringBuilder();
		while (true) {
			n = readInt();
			l = readInt();
			result = "";
			count = 0;
			if (n == 0 && l == 0) {
				break;
			}
			solve(temp, 0);
			int len = result.length();
			if (len > 0) {
				pw.print(result.charAt(0));
			}
			for (int i = 1; i < len; ++i) {
				if (i % 64 == 0) {
					pw.println();
				} else if (i % 4 == 0) {
					pw.print(" ");
				}
				pw.print(result.charAt(i));
			}
			pw.println("\n" + len);
		}

		pw.flush();
		pw.close();
	}
}
