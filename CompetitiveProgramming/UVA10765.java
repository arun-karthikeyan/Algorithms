import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Doves and Bombs
 * 
 * @author arun
 *
 */
public class UVA10765 {
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

	private static int n;
	private static int dfsRoot, counter, rootChildren;
	private static int[] dfsNum, dfsLow, dfsParent;
	private static boolean[] visited;
	private static int[][] result;
	private static HashSet<Integer> adjList[];
	private static final int IDX = 0, PIGEONS = 1;

	private static void solve(int u) {
		HashSet<Integer> adj = adjList[u];
		for (Iterator<Integer> i = adj.iterator(); i.hasNext();) {
			int v = i.next();
			if (!visited[v]) {
				visited[v] = true;
				dfsNum[v] = dfsLow[v] = counter++;
				dfsParent[v] = u;
				if (dfsRoot == u) {
					rootChildren++;
				}
				solve(v);
				if (u != dfsRoot && dfsLow[v] >= dfsNum[u]) {
					result[u][PIGEONS]++;
				}
				dfsLow[u] = Math.min(dfsLow[u], dfsLow[v]);
			} else {
				if (dfsParent[u] != v) {
					dfsLow[u] = Math.min(dfsLow[u], dfsLow[v]);
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
//		PrintWriter pw = new PrintWriter("testop.txt");

		while (true) {
			n = readInt();
			int m = readInt();
			if (n == 0 && m == 0) {
				break;
			}
			adjList = new HashSet[n];
			for (int i = 0; i < n; ++i) {
				adjList[i] = new HashSet<Integer>();
			}
			while (true) {
				int from = readInt(), to = readInt();
				if (from == -1 && to == -1) {
					break;
				}
				adjList[from].add(to);
				adjList[to].add(from);
			}
			dfsNum = new int[n];
			dfsLow = new int[n];
			dfsParent = new int[n];
			visited = new boolean[n];
			result = new int[n][2];
			for (int i = 0; i < n; ++i) {
				result[i][IDX] = i;
				result[i][PIGEONS] = 1;
			}
			for (int i = 0; i < n; ++i) {
				if (!visited[i]) {
					visited[i] = true;
					dfsRoot = i;
					counter = rootChildren = 0;
					dfsParent[i] = i;
					dfsNum[i] = dfsLow[i] = counter++;
					solve(i);
					result[dfsRoot][PIGEONS] = rootChildren;
				}
			}
			Arrays.sort(result, new java.util.Comparator<int[]>() {
				@Override
				public int compare(int[] arg0, int[] arg1) {
					// TODO Auto-generated method stub
					int c1 = arg1[PIGEONS] - arg0[PIGEONS];
					if (c1 == 0) {
						return arg0[IDX] - arg1[IDX];
					}
					return c1;
				}
			});
			for (int i = 0; i < m; ++i) {
				pw.println(result[i][IDX] + " " + result[i][PIGEONS]);
			}
			pw.println();
		}

		pw.flush();
		pw.close();
	}
}
