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
 * Claw Decomposition
 * 
 * @author arun
 *
 */
public class UVA11396 {
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

	private static final int C1 = 1;
	private static final String YES = "YES", NO = "NO";
	private static final int VERTEX = 0, COLOR = 1;

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n;
		while ((n = readInt()) != 0) {
			@SuppressWarnings("unchecked")
			ArrayList<Integer>[] adjList = new ArrayList[n];
			for (int i = 0; i < n; ++i) {
				adjList[i] = new ArrayList<Integer>();
			}
			int v1, v2;
			while (true) {
				v1 = readInt();
				v2 = readInt();
				if (v1 == 0 && v2 == 0) {
					break;
				}
				v1--;
				v2--;
				adjList[v1].add(v2);
				adjList[v2].add(v1);
			}
			boolean result = true;
			int[] color = new int[n];
			boolean[] visited = new boolean[n];

			for (int i = 0; i < n && result; ++i) {
				if (!visited[i]) {
					Queue<int[]> bfs = new LinkedList<int[]>();
					bfs.add(new int[] { i, C1 });
					int cc = 0, tc = 0;
					while (!bfs.isEmpty()) {
						int[] cv = bfs.remove();
						int adjC = cv[COLOR] ^ 3;
						if (!visited[cv[VERTEX]]) {
							color[cv[VERTEX]] = cv[COLOR];
							tc++;
							cc += (cv[COLOR] & 1);
							visited[cv[VERTEX]] = true;
							ArrayList<Integer> adj = adjList[cv[VERTEX]];
							for (int j = 0, jLen = adj.size(); j < jLen; ++j) {
								int av = adj.get(j);
								bfs.add(new int[] { av, adjC });
							}
						} else {
							if (color[cv[VERTEX]] != cv[COLOR]) {
								result = false;
								break;
							}
						}
					}
					if (tc < 6 || (tc >> 1) != cc) {
						result = false;
						break;
					}
				}
			}
			if (result) {
				pw.println(YES);
			} else {
				pw.println(NO);
			}
		}

		pw.flush();
		pw.close();
	}
}
