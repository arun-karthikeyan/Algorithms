import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class UVA208 {
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, values = 1; i < 21; ++i, values *= 2) {
			LOOKUP.put(values, i);
		}
	}
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

	private static final int MAX = 21;
	private static int n, valid;
	private static int adjList[] = new int[MAX];
	private static ArrayList<String> result;

	private static void solve(int used, ArrayList<Integer> path, int lastNode) {
		if (lastNode == n) {
			StringBuilder sb = new StringBuilder();
			sb.append((path.get(0) + 1));
			for (int i = 1, iLen = path.size(); i < iLen; ++i) {
				sb.append(" " + (path.get(i) + 1));
			}
			result.add(sb.toString());
			return;
		}
		int adj = adjList[lastNode];
		int len = path.size();
		while (adj > 0) {
			int b = adj & (-adj);
			adj -= b;
			if (((valid & b) > 0) && ((used & b) == 0)) {
				int cAdj = LOOKUP.get(b);
				path.add(cAdj);
				solve(used | b, path, cAdj);
				path.remove(len);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

//		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		PrintWriter pw = new PrintWriter("testop.txt");
		int tc = 1;
		ArrayList<Integer> temp = new ArrayList<Integer>(20);
		temp.add(0);
		while ((n = readInt()) != 0) {
			n--;
			pw.println("CASE " + (tc++) + ":");
			Arrays.fill(adjList, 0);
			int visited = 1 << n;
			while (true) {
				int from = readInt(), to = readInt();
				if (from == 0 && to == 0) {
					break;
				}
				from--;
				to--;
				adjList[from] |= (1 << to);
				adjList[to] |= (1 << from);
			}

			for (int i = 0, values = 1; i < MAX; ++i, values *= 2) {
				if ((visited & values) == 0) {
					int valid = 0;
					Stack<Integer> dfs = new Stack<Integer>();
					dfs.add(i);
					while (!dfs.isEmpty()) {
						int node = dfs.pop();
						if (node == n) {
							visited |= valid;
							break;
						}
						int b = 1 << node;
						if ((valid & b) == 0) {
							valid |= b;
							int adj = adjList[node];
							while (adj > 0) {
								int p = adj & (-adj);
								adj -= p;
								dfs.push(LOOKUP.get(p));
							}
						}
					}
				}
			}
			valid = visited;

			result = new ArrayList<String>();
			solve(1, temp, 0);
			int resultSize = result.size();
			for (int i = 0; i < resultSize; ++i) {
				pw.println(result.get(i));
			}

			pw.println("There are " + resultSize + " routes from the firestation to streetcorner " + (n + 1) + ".");
		}

		pw.flush();
		pw.close();
	}
}
