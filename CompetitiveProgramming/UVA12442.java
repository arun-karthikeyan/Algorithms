import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Forwarding Emails, just graph traversal
 * --incomplete--
 * @author arun
 *
 */
public class UVA12442 {
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

	private static ArrayList<Integer> adjList[];
	private static boolean[] visited;
	private static int[][] componentLength;
	private static final int LEN = 0, SV = 1;
	private static int n;

	private static int solve(int sv, int cv) {
		if (!visited[cv]) {
			ArrayList<Integer> adj = adjList[cv];
			int maxLen = 1;
			visited[cv] = true;
			componentLength[cv][SV] = sv;
			for (int i = 0, iLen = adj.size(); i < iLen; ++i) {
				int av = adj.get(i);
				if (av == cv) {
					continue;
				}
				maxLen = maxLen + solve(sv, av);
			}
			componentLength[cv][LEN] = maxLen;
			return maxLen;
		}
		if (componentLength[cv][SV] != sv) {
			return componentLength[cv][LEN];
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = readInt(), tc = 1;

		while (testcases-- > 0) {
			n = readInt();
			componentLength = new int[n][2];
			visited = new boolean[n];
			adjList = new ArrayList[n];

			for (int i = 0; i < n; ++i) {
				adjList[i] = new ArrayList<Integer>();
			}

			for (int i = 0; i < n; ++i) {
				int from = readInt() - 1, to = readInt() - 1;
				adjList[from].add(to);
			}
			for (int i = 0; i < n; ++i) {
				// fill max length of component starting at i
				solve(i, i);
			}
			int max = componentLength[0][LEN], maxIdx = 1;
			for (int i = 1; i < n; ++i) {
				if (max < componentLength[i][LEN]) {
					max = componentLength[i][LEN];
					maxIdx = i + 1;
				}
			}
			pw.println("Case " + (tc++) + ": " + maxIdx);
		}

		pw.flush();
		pw.close();
	}
}
