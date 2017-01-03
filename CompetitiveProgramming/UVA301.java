import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA301 {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, values = 1; i <= 22; ++i, values *= 2) {
			LOOKUP.put(values, i);
		}
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

	private static int n, b, m, OK, maxSum, tickets[][];
	private static final int START = 0, END = 1, PASSENGERS = 2;

	private static void solve(int invalid, int sum, int[] pc) {
		int vp = OK & (~invalid);
		while (vp > 0) {
			int p = vp & (-vp);
			vp -= p;
			int idx = LOOKUP.get(p);
			int s = tickets[idx][START], e = tickets[idx][END], pass = tickets[idx][PASSENGERS];
			pc[s] += pass;
			pc[e] -= pass;
			boolean can = true;
			for (int i = 0, maxPassengers = 0; i <= b; ++i) {
				maxPassengers += pc[i];
				if (maxPassengers > n) {
					can = false;
					break;
				}
			}
			if (can) {
				int ticketCost = (e - s) * pass;
				solve((p << 1) - 1, sum + ticketCost, pc);
			}
			pc[s] -= pass;
			pc[e] += pass;
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		while (true) {
			n = readInt();
			b = readInt();
			m = readInt();
			if (n == 0 && b == 0 && m == 0) {
				break;
			}
			OK = (1 << m) - 1;
			tickets = new int[m][3];
			maxSum = 0;
			for (int i = 0; i < m; ++i) {
				tickets[i][START] = readInt();
				tickets[i][END] = readInt();
				tickets[i][PASSENGERS] = readInt();
			}
			solve(0, 0, new int[b + 1]);
			pw.println(maxSum);

		}

		pw.flush();
		pw.close();
	}
}
