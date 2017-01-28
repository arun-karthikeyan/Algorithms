import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Struggled to find the state, still weak with DP. A good practice problem Top
 * Down solution
 * 
 * @author arun
 *
 */
public class UVA11003 {
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

	private static final int GROUNDCAPACITY = (int) (2 * 3e3), MAXBOXES = (int) 1e3;
	private static int n, memoi[][] = new int[MAXBOXES + 1][GROUNDCAPACITY + 1], weight[] = new int[MAXBOXES + 1],
			capacity[] = new int[MAXBOXES + 1];

	private static int solve(int idx, int remCapacity) {
		if (remCapacity < 0) {
			return Integer.MIN_VALUE;
		}
		if (idx == n) {
			return 0;
		}
		if (memoi[idx][remCapacity] == -1) {
			memoi[idx][remCapacity] = Math.max(solve(idx + 1, remCapacity),
					1 + solve(idx + 1, Math.min(capacity[idx], remCapacity - weight[idx])));
		}
		return memoi[idx][remCapacity];
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		while ((n = readInt()) != 0) {
			int max = 0;
			for (int i = 0; i < n; ++i) {
				weight[i] = readInt();
				capacity[i] = readInt();
				max = Math.max(max, weight[i] + capacity[i]);
				Arrays.fill(memoi[i], -1);
			}
			pw.println(solve(0, max));
		}

		pw.flush();
		pw.close();
	}
}
