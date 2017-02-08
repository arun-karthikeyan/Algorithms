import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Critical Links - Identifying bridges, I've used Tarjan's algorithm.
 * O(V + E) solution, with modified dfs.
 * @author arun
 *
 */
public class UVA796 {
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
				return -1;
			val *= 10;
			val += (number - '0');
			number = readByte();
		} while (!eolchar(number));

		return sign * val;
	}

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t' || c == '(' || c == ')';
	}

	private static int n, dfsLow[], dfsParent[], dfsNum[];
	private static ArrayList<int[]> bridges;
	private static boolean visited[];
	private static HashSet<Integer>[] adjList;
	private static int counter;

	private static void solve(int u) {
		HashSet<Integer> adj = adjList[u];
		for (Iterator<Integer> v = adj.iterator(); v.hasNext();) {
			int av = v.next();
			if (!visited[av]) {
				visited[av] = true;
				dfsLow[av] = dfsNum[av] = counter++;
				dfsParent[av] = u;
				solve(av);
				dfsLow[u] = Math.min(dfsLow[u], dfsLow[av]);
				if (dfsLow[av] > dfsNum[u]) {
					if (u < av) {
						bridges.add(new int[] { u, av });
					} else {
						bridges.add(new int[] { av, u });
					}
				}
			} else {
				if (dfsParent[u] != av) {
					dfsLow[u] = Math.min(dfsLow[av], dfsLow[u]);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		while ((n = readInt()) != -1) {
			adjList = new HashSet[n];
			for (int i = 0; i < n; ++i) {
				adjList[i] = new HashSet<Integer>();
			}
			for (int i = 0; i < n; ++i) {
				int from = readInt();
				int toLen = readInt();
				for (int j = 0; j < toLen; ++j) {
					int to = readInt();
					adjList[from].add(to);
					adjList[to].add(from);
				}
			}
			dfsLow = new int[n];
			dfsParent = new int[n];
			dfsNum = new int[n];
			visited = new boolean[n];
			bridges = new ArrayList<int[]>();
			for (int i = 0; i < n; ++i) {
				if (!visited[i]) {
					counter = 0;
					visited[i] = true;
					dfsNum[i] = dfsLow[i] = counter++;
					dfsParent[i] = i;
					solve(i);
				}
			}
			int resultSize = bridges.size();
			Collections.sort(bridges, new java.util.Comparator<int[]>() {

				@Override
				public int compare(int[] arg0, int[] arg1) {
					// TODO Auto-generated method stub
					int c1 = arg0[0] - arg1[0];
					if (c1 == 0) {
						return arg0[1] - arg1[1];
					}
					return c1;
				}

			});
			pw.println(resultSize + " critical links");

			for (int i = 0; i < resultSize; ++i) {
				int[] currentLink = bridges.get(i);
				pw.println(currentLink[0] + " - " + currentLink[1]);
			}
			pw.println();
		}

		pw.flush();
		pw.close();
	}
}
