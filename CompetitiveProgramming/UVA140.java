import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class UVA140 {
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, val = 1; i <= 26; ++i, val *= 2) {
			LOOKUP.put(val, i);
		}
	}
	private static int n, OK;
	private static HashMap<Character, Integer> vertexMap;
	private static HashMap<Integer, Character> reverseVertexMap;
	private static ArrayList<Integer> adjList[];
	private static int minBandwidth;
	private static String op;

	private static int computeBandwidth(String graph) {
		int[] vals = new int[n];
		int maxBandWidth = -1;
		for (int i = 0, iLen = graph.length(); i < iLen; ++i) {
			vals[vertexMap.get(graph.charAt(i))] = i;
		}
		for (int i = 0; i < n; ++i) {
			ArrayList<Integer> currentAdj = adjList[i];
			for (int j = 0, jLen = currentAdj.size(); j < jLen; ++j) {
				maxBandWidth = max(maxBandWidth, Math.abs(vals[i] - vals[currentAdj.get(j)]));
			}
		}
		return maxBandWidth;
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	private static void solve(int used, int col, String graph) {
		if (col == n) {
			int currentBandWidth = computeBandwidth(graph);
			if (currentBandWidth < minBandwidth) {
				minBandwidth = currentBandWidth;
				op = graph;
			}
		}
		int valid = (OK & (~used));
		while (valid > 0) {
			int p = valid & (-valid);
			valid -= p;
			solve(used | p, col + 1, graph + reverseVertexMap.get(LOOKUP.get(p)));
		}
	}

	@SuppressWarnings("unchecked")
	private static void initMaps(String line) {
		int vertices = 0;
		vertexMap = new HashMap<Character, Integer>();
		reverseVertexMap = new HashMap<Integer, Character>();
		for (int i = 0, iLen = line.length(); i < iLen; ++i) {
			char currentChar = line.charAt(i);
			if (currentChar >= 'A' && currentChar <= 'Z') {
				vertices |= (1 << (currentChar - 'A'));
			}
		}
		while (vertices > 0) {
			int p = vertices & (-vertices);
			vertices -= p;
			char pChar = (char) (LOOKUP.get(p) + 'A');
			vertexMap.put(pChar, vertexMap.size());
			reverseVertexMap.put(reverseVertexMap.size(), pChar);
		}
		// other init stuff
		minBandwidth = 10;
		n = vertexMap.size();
		adjList = new ArrayList[n];
		for (int i = 0; i < n; ++i) {
			adjList[i] = new ArrayList<Integer>();
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line;
		while (!"#".equals(line = br.readLine())) {
			String[] adjInfo = line.split(";");
			initMaps(line);
			for (int i = 0, iLen = adjInfo.length; i < iLen; ++i) {
				String[] edgeInfo = adjInfo[i].split(":");
				int from = vertexMap.get(edgeInfo[0].charAt(0));
				for (int j = 0, jLen = edgeInfo[1].length(); j < jLen; ++j) {
					char to = edgeInfo[1].charAt(j);
					int adjIdx = vertexMap.get(to);
					adjList[from].add(adjIdx);
					adjList[adjIdx].add(from);
				}
			}
			OK = (1 << n) - 1;
			solve(0, 0, "");
			if (op.length() > 0) {
				for (int i = 0; i < n; ++i) {
					pw.print(op.charAt(i) + " ");
				}
				pw.println("-> " + minBandwidth);
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
