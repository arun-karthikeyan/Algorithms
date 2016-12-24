import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UVA11991 {
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

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n;

		while ((n = readInt()) != 0) {
			int q = readInt();
			ArrayList<Integer>[] adjList = new ArrayList[(int)1e6];
			for (int i = 0; i < n; ++i) {
				int no = readInt();
				if (adjList[no - 1] == null) {
					adjList[no - 1] = new ArrayList<Integer>();
				}
				adjList[no - 1].add(i + 1);
			}
			for (int i = 0; i < q; ++i) {
				int k = readInt() - 1;
				int no = readInt() - 1;
				if (k < adjList[no].size()) {
					pw.println(adjList[no].get(k));
				} else {
					pw.println("0");
				}
			}

		}

		pw.flush();
		pw.close();
	}
}
