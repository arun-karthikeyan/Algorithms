import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Ordering - Recursive backtracking to produce all topo orderings
 * 
 * @author arun
 *
 */
public class UVA872 {
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, j = 1; i < 21; ++i, j <<= 1) {
			LOOKUP.put(j, i);
		}
	}
	private static ArrayList<int[]> result;
	private static int[] in;
	private static ArrayList<Integer>[] adjList;
	private static int OK;

	// core logic to print all topo-orderings
	private static void solve(int used, int[] subResult, int sz) {
		int vp = (OK & (~used));
		if (vp == 0) {
			result.add(subResult.clone());
		}
		while (vp > 0) {
			int p = vp & (-vp);
			vp -= p;
			int idx = LOOKUP.get(p);
			if (in[idx] == 0) {
				ArrayList<Integer> adj = adjList[idx];
				for (int i = 0, iLen = adj.size(); i < iLen; ++i) {
					int cv = adj.get(i);
					in[cv]--;
				}
				subResult[sz] = idx;
				solve(used | p, subResult, sz + 1);
				for (int i = 0, iLen = adj.size(); i < iLen; ++i) {
					int cv = adj.get(i);
					in[cv]++;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		int[] temp = new int[21];
		while (testcases-- > 0) {
			br.readLine();
			String[] info = br.readLine().split(" ");
			int n = info.length;
			OK = (1 << n) - 1;
			Arrays.sort(info);
			result = new ArrayList<int[]>();

			// BEGIN --input and mapping--
			HashMap<Character, Integer> vMap = new HashMap<Character, Integer>();
			HashMap<Integer, Character> rvMap = new HashMap<Integer, Character>();
			for (int i = 0; i < n; ++i) {
				char ch = info[i].charAt(0);
				vMap.put(ch, i);
				rvMap.put(i, ch);
			}
			info = br.readLine().split(" ");
			adjList = new ArrayList[n];
			for (int i = 0; i < n; ++i) {
				adjList[i] = new ArrayList<Integer>();
			}
			in = new int[n];
			for (int i = 0, iLen = info.length; i < iLen; ++i) {
				int dpdnt = vMap.get(info[i].charAt(0));
				int dpnde = vMap.get(info[i].charAt(2));
				adjList[dpdnt].add(dpnde);
				in[dpnde]++;
			}
			// END --input and mapping--

			// actual logic
			solve(0, temp, 0);

			// BEGIN - output
			if (result.size() == 0) {
				pw.println("NO");
			} else {
				for (int i = 0, iLen = result.size(); i < iLen; ++i) {
					int[] currentResult = result.get(i);
					pw.print(rvMap.get(currentResult[0]));
					for (int j = 1; j < n; ++j) {
						pw.print(" " + rvMap.get(currentResult[j]));
					}
					pw.println();
				}
			}
			if (testcases != 0) {
				pw.println();
			}
			// END - output
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
