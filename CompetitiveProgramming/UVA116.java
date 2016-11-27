import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UVA116 {
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

	private static final int SRC = 0, DST = 1, COST = 2;
	private static final long INF = Long.MAX_VALUE;
	private static ArrayList<int[]> edgeList;
	private static int m, n;

	private static void addThreeSources(int node, int cost) {
		if ((node % n) != 0) {
			int max = m * n;
			int src1 = (node - n + 1) < 1 ? (node - n + 1 + max) : (node - n + 1);
			int src2 = node + 1;
			int src3 = (node + n + 1) > max ? (node + n + 1 - max) : (node + n + 1);
			edgeList.add(new int[] { src1, node, cost });
			edgeList.add(new int[] { src2, node, cost });
			edgeList.add(new int[] { src3, node, cost });
		} else {
			edgeList.add(new int[] { 0, node, cost });
		}
	}

	private static int getRow(int node) {
		return ((node - 1) / n) + 1;
	}

	static class BF {
		public long cost;
		public int parent;

		public BF() {
			this.cost = INF;
			this.parent = -1;
		}
	}

	public static void main(String[] args) throws Exception {
		 stream = System.in;
//		stream = new FileInputStream(new File("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		m = readInt();
		n = readInt();
		while (m != 0 || n != 0) {
			edgeList = new ArrayList<int[]>(3 * m * n);
			for (int i = 1, node = 1; i <= m; ++i) {
				for (int j = 1; j <= n; ++j, node++) {
					addThreeSources(node, readInt());
				}
			}
			// long[][] bellmanFordDP = new long[2][m * n + 1];
			BF[] bellmanFordDP = new BF[m * n + 1];
			for (int i = 0, iLen = bellmanFordDP.length; i < iLen; ++i) {
				bellmanFordDP[i] = new BF();
			}

			bellmanFordDP[0].cost = 0;

			for (int i = 0, max = m * n; i < max; ++i) {
				boolean change = false;
				for (int j = 0, jLen = edgeList.size(); j < jLen; ++j) {
					int[] currentEdge = edgeList.get(j);
					if ((bellmanFordDP[currentEdge[SRC]].cost != INF)
							&& (bellmanFordDP[currentEdge[DST]].cost > (bellmanFordDP[currentEdge[SRC]].cost
									+ currentEdge[COST]))
							|| ((bellmanFordDP[currentEdge[DST]].cost == (bellmanFordDP[currentEdge[SRC]].cost
									+ currentEdge[COST]))
									&& (bellmanFordDP[currentEdge[DST]].parent > currentEdge[SRC]))) {
						bellmanFordDP[currentEdge[DST]].cost = bellmanFordDP[currentEdge[SRC]].cost + currentEdge[COST];
						bellmanFordDP[currentEdge[DST]].parent = currentEdge[SRC];
						change = true;
					}
				}
				if (!change) {
					break;
				}
			}

			long min = Long.MAX_VALUE;
			int minIndex = -1;
			for (int i = 1, max = m * n; i <= max; i += n) {
				if (min > bellmanFordDP[i].cost) {
					min = bellmanFordDP[i].cost;
					minIndex = i;
				}
			}

			pw.print(getRow(minIndex));
			minIndex = bellmanFordDP[minIndex].parent;
			while (minIndex != 0) {
				pw.print(" " + getRow(minIndex));
				minIndex = bellmanFordDP[minIndex].parent;
			}
			pw.println();
			pw.println(min);
			m = readInt();
			n = readInt();
		}
		pw.flush();
		pw.close();
	}
}
