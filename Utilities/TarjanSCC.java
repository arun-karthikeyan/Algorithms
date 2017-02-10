import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

/**
 * A reference implementation of Tarjan's Strongly Connected Component finding
 * algorithm
 * 
 * @author arun
 *
 */
public class TarjanSCC {
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

	private static Stack<Integer> scc;
	private static HashSet<Integer>[] adjList;
	private static int[] dfsLow, dfsNum;
	private static int[] status;
	private static int counter, sccNum;
	private static final int UNVISITED = 0, VISITED = 1, EXPLORED = 2;

	private static void tarjanSCC(int u) {
		status[u] = EXPLORED;
		dfsLow[u] = dfsNum[u] = counter++;
		scc.push(u);
		HashSet<Integer> adj = adjList[u];
		for (Iterator<Integer> i = adj.iterator(); i.hasNext();) {
			int v = i.next();
			if (status[v] == UNVISITED) {
				tarjanSCC(v);
			}
			if (status[v] == EXPLORED) {
				dfsLow[u] = Math.min(dfsLow[u], dfsLow[v]);
			}
		}
		if (dfsLow[u] == dfsNum[u]) {// head of scc
			System.out.print("SCC " + (++sccNum) + ": ");
			while (true) {
				int c = scc.pop();
				status[c] = VISITED;
				System.out.print(" " + c);
				if (c == u) {
					break;
				}
			}
			System.out.println();
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

		int n = readInt(), m = readInt();
		scc = new Stack<Integer>();
		dfsLow = new int[n];
		dfsNum = new int[n];
		status = new int[n];
		counter = sccNum = 0;
		adjList = new HashSet[n];
		for (int i = 0; i < n; ++i) {
			adjList[i] = new HashSet<Integer>();
		}
		for (int i = 0; i < m; ++i) {
			adjList[readInt()].add(readInt());
		}
		for (int i = 0; i < n; ++i) {
			if (status[i] == UNVISITED) {
				tarjanSCC(i);
			}
		}
		pw.flush();
		pw.close();
	}
}
