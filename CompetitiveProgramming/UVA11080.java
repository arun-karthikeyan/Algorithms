import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Place the guards, bipartite check
 * 
 * @author arun
 *
 */

public class UVA11080 {
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

	private static final int C1 = 0;
	private static final int V = 0, C = 1;

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = readInt();

		while (testcases-- > 0) {
			int n = readInt(), m = readInt();
			@SuppressWarnings("unchecked")
			ArrayList<Integer> adjList[] = new ArrayList[n];
			for (int i = 0; i < n; ++i) {
				adjList[i] = new ArrayList<Integer>();
			}
			for (int i = 0; i < m; ++i) {
				int from = readInt(), to = readInt();
				adjList[from].add(to);
				adjList[to].add(from);
			}
			boolean[] visited = new boolean[n];
			int[] colors = new int[n];
			int minSoldiers = 0;
			boolean possible = true;
			for (int i = 0; i < n && possible; ++i) {
				if (!visited[i]) {
					Queue<int[]> bfs = new LinkedList<int[]>();
					int totalCount = 0, c1Count = 0;
					bfs.add(new int[] { i, C1 });
					while (!bfs.isEmpty()) {
						int[] cv = bfs.remove();
						int adjC = (cv[C] + 1) & 1;
						if (!visited[cv[V]]) {
							visited[cv[V]] = true;
							colors[cv[V]] = cv[C];
							totalCount++;
							c1Count += (cv[C] == C1 ? 1 : 0);
							ArrayList<Integer> adj = adjList[cv[V]];
							for (int j = 0; j < adj.size(); ++j) {
								int av = adj.get(j);
								bfs.add(new int[] { av, adjC });
							}
						} else {
							if (colors[cv[V]] != cv[C]) {
								possible = false;
								break;
							}
						}
					}
					if (c1Count == totalCount) {
						minSoldiers += c1Count;
					} else {
						minSoldiers += Math.min(c1Count, totalCount - c1Count);
					}
				}
			}
			if (possible) {
				pw.println(minSoldiers);
			} else {
				pw.println(-1);
			}
		}

		pw.flush();
		pw.close();
	}
}
