import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA10038 {
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

	private static final String JOLLY = "Jolly", NOTJOLLY = "Not jolly";

	public static void main(String[] args) throws Exception {
		 stream = System.in;
//		stream = new FileInputStream(new File("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int val;
		while ((val = readInt()) != 0) {
			boolean check = true;
			boolean[] vals = new boolean[val];
			int prevNo = readInt();
			if (val > 1) {
				for (int i = 1; i < val; ++i) {
					int currentNo = readInt();
					int idx = Math.abs(prevNo - currentNo);
					if (idx < val) {
						vals[Math.abs(prevNo - currentNo)] = true;
					}
					prevNo = currentNo;
				}
				for (int i = 1; i < val; ++i) {
					if (!vals[i]) {
						check = false;
						break;
					}
				}
			}
			if (check) {
				pw.println(JOLLY);
			} else {
				pw.println(NOTJOLLY);
			}
		}

		pw.flush();
		pw.close();
	}
}
