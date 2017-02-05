import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Forwarding Emails, just graph traversal
 * A tricky modification of DFS
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

	private static int[] componentLength, adj, cycle;
	private static boolean[] visited;
	private static int n;

	private static int solve(int i) {
		if (componentLength[i] > 0 || visited[i]) {
			if (visited[i]) {
				cycle[i] = i;
				return 0;
			}
			if (cycle[i] != -1) {
				return componentLength[cycle[i]];
			}
			return componentLength[i];
		}
		visited[i] = true;
		componentLength[i] = 1 + solve(adj[i]);
		visited[i] = false;
		if (cycle[adj[i]] != -1 && visited[cycle[adj[i]]]) {
			cycle[i] = cycle[adj[i]];
		}
		return componentLength[i];
	}

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
			componentLength = new int[n];
			adj = new int[n];
			visited = new boolean[n];
			cycle = new int[n];
			Arrays.fill(cycle, -1);
			for (int i = 0; i < n; ++i) {
				int from = readInt() - 1, to = readInt() - 1;
				adj[from] = to;
			}

			int max = 0, maxIdx = -1;
			for (int i = 0; i < n; ++i) {
				// fill max length of component starting at i
				solve(i);
				if (max < componentLength[i]) {
					max = componentLength[i];
					maxIdx = i + 1;
				}
			}
			pw.println("Case " + (tc++) + ": " + maxIdx);
		}

		pw.flush();
		pw.close();
	}
}
