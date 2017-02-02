import java.io.BufferedWriter;
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
 * Dominator - Basic Graph Traversal
 * 
 * @author arun
 *
 */
public class UVA11902 {

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

	private static String rowBreak(int n) {
		StringBuilder sb = new StringBuilder();
		sb.append('+');
		int len = 2 * n - 1;
		for (int i = 0; i < len; ++i) {
			sb.append('-');
		}
		sb.append('+');
		return sb.toString();
	}

	private static final String CASE = "Case ";

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		// PrintWriter pw = new PrintWriter("testop.txt");

		int testcases = readInt(), tc = 1;

		while (testcases-- > 0) {
			int n = readInt();
			String rowBreak = rowBreak(n);
			// boolean[][] adjMat = new boolean[n][n];
			@SuppressWarnings("unchecked")
			ArrayList<Integer>[] adjList = new ArrayList[n];
			for (int i = 0; i < n; ++i) {
				adjList[i] = new ArrayList<Integer>();
			}
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					// adjMat[i][j] = readInt() == 1;
					if (readInt() == 1) {
						adjList[i].add(j);
					}
				}
			}
			boolean[][] result = new boolean[n][n];
			// all nodes reachable from start-node (including the startNode)
			// dominate of themselves and are also dominated by the startNode
			Queue<Integer> dfs = new LinkedList<Integer>();
			dfs.add(0);
			boolean[] reachableFromStartNode = new boolean[n];
			while (!dfs.isEmpty()) {
				int currentNode = dfs.remove();
				if (!reachableFromStartNode[currentNode]) {
					ArrayList<Integer> neighbors = adjList[currentNode];
					for (int i = 0, iLen = neighbors.size(); i < iLen; ++i) {
						dfs.add(neighbors.get(i));
					}
					reachableFromStartNode[currentNode] = true;
				}
			}
			for (int i = 0; i < n; ++i) {
				if (reachableFromStartNode[i]) {
					result[i][i] = true;
					result[0][i] = true;
				}
			}
			// now mark all other nodes
			for (int i = 1; i < n; ++i) {
				dfs.add(0);
				boolean[] notDominatedByI = new boolean[n];
				while (!dfs.isEmpty()) {
					int currentNode = dfs.remove();
					if (!notDominatedByI[currentNode] && currentNode != i) {
						ArrayList<Integer> neighbors = adjList[currentNode];
						for (int j = 0, jLen = neighbors.size(); j < jLen; ++j) {
							dfs.add(neighbors.get(j));
						}
						notDominatedByI[currentNode] = true;
					}
				}
				for (int j = 1; j < n; ++j) {
					if (j == i)
						continue;
					result[i][j] = reachableFromStartNode[j] && !notDominatedByI[j];
				}
			}
			// print result
			pw.println(CASE + (tc++) + ":\n" + rowBreak);
			for (int i = 0; i < n; ++i) {
				pw.print('|');
				for (int j = 0; j < n; ++j) {
					if (result[i][j]) {
						pw.print('Y');
					} else {
						pw.print('N');
					}
					pw.print('|');
				}
				pw.println();
				pw.println(rowBreak);
			}

		}

		pw.flush();
		pw.close();
	}

}
