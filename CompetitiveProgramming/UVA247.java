import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

/**
 * Calling Circles - Finding Strongly Connected Components Have used TarjanSCC
 * 
 * @author arun
 *
 */
public class UVA247 {
	private static int n, m;
	private static HashSet<Integer> adjList[];
	private static int[] dfsNum, dfsLow;
	private static int[] status;
	private static int counter;
	private static Stack<Integer> scc;
	private static ArrayList<ArrayList<String>> result;
	private static HashMap<Integer, String> rvMap;
	private static HashMap<String, Integer> vMap;
	private static final int UNVISITED = 0, VISITED = 1, EXPLORED = 2;

	private static void tarjanSCC(int u) {
		status[u] = EXPLORED;
		dfsNum[u] = dfsLow[u] = counter++;
		scc.add(u);
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
		if (dfsLow[u] == dfsNum[u]) {
			ArrayList<String> currentScc = new ArrayList<String>();
			// reached head
			while (true) {
				int c = scc.pop();
				status[c] = VISITED;
				String name = rvMap.get(c);
				if (name != null) {
					currentScc.add(name);
				}
				if (c == u) {
					break;
				}
			}
			result.add(currentScc);
		}
	}

	private static final String OP = "Calling circles for data set ";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int tc = 0;
		while (true) {
			String[] info = br.readLine().trim().split("\\s+");
			n = Integer.parseInt(info[0]);
			m = Integer.parseInt(info[1]);
			if (n == 0 && m == 0) {
				break;
			}
			if (tc != 0) {
				pw.println();
			}
			pw.println(OP + (++tc) + ":");
			vMap = new HashMap<String, Integer>();
			rvMap = new HashMap<Integer, String>();
			adjList = new HashSet[n];
			dfsNum = new int[n];
			dfsLow = new int[n];
			status = new int[n];
			result = new ArrayList<ArrayList<String>>();
			scc = new Stack<Integer>();

			for (int i = 0; i < n; ++i) {
				adjList[i] = new HashSet<Integer>();
			}
			for (int i = 0; i < m; ++i) {
				info = br.readLine().trim().split("\\s+");
				int from = -1, to = -1;
				if (vMap.containsKey(info[0])) {
					from = vMap.get(info[0]);
				} else {
					from = vMap.size();
					vMap.put(info[0], from);
					rvMap.put(from, info[0]);
				}
				if (vMap.containsKey(info[1])) {
					to = vMap.get(info[1]);
				} else {
					to = vMap.size();
					vMap.put(info[1], to);
					rvMap.put(to, info[1]);
				}
				adjList[from].add(to);
			}
			for (int i = 0; i < n; ++i) {
				if (status[i] == UNVISITED) {
					counter = 0;
					tarjanSCC(i);
				}
			}
			for (int i = 0, iLen = result.size(); i < iLen; ++i) {
				ArrayList<String> currentSCC = result.get(i);
				int sz = currentSCC.size();
				if (sz > 0) {
					pw.print(currentSCC.get(0));
					for (int j = 1; j < sz; ++j) {
						pw.print(", " + currentSCC.get(j));
					}
					pw.println();
				}

			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
