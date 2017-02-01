import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Shopping Trip A cool problem, requires floyd warshall pre-proessing and DP
 * TSP, runs in O(n*2^n).
 * 
 * @author arun
 *
 */
public class UVA11284 {
	private static int dist[][] = new int[51][51];
	private static int memo[][] = new int[51][4097];
	private static int[][] operas;
	private static int OK;
	private static final int OP = 0, COST = 1, HOME = 0;
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, vals = 1; i < 13; ++i, vals <<= 1) {
			LOOKUP.put(vals, i);
		}
	}

	private static int readDouble(String s) {
		int result = 0;
		for (int d = s.length() - 1, val = 1; d >= 0; --d) {
			char ch = s.charAt(d);
			if (ch != '.') {
				result += ((ch - '0') * val);
				val *= 10;
			}
		}
		return result;
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	private static int min(int a, int b) {
		return a < b ? a : b;
	}

	private static int solve(int valid, int prev) {
		if (memo[prev][valid] == -1) {
			int maxSavings = 0 - dist[prev][HOME];
			int temp = valid;
			while (temp > 0) {
				int p = temp & (-temp);
				temp -= p;
				int idx = LOOKUP.get(p);
				int nextOp = operas[idx][OP];
				int nextOpSave = operas[idx][COST];
				int nextDist = dist[prev][nextOp];
				if (nextDist != Integer.MAX_VALUE) {
					maxSavings = max(maxSavings, nextOpSave - dist[prev][nextOp] + solve(valid & (~p), nextOp));
				}
			}
			memo[prev][valid] = maxSavings;
		}
		return memo[prev][valid];
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			String line = br.readLine();
			String[] info;
			if (line.length() == 0) {
				info = br.readLine().split(" ");
			} else {
				info = line.split(" ");
			}
			int n = Integer.parseInt(info[0]);
			int m = Integer.parseInt(info[1]);
			for (int i = 0; i <= n; ++i) {
				for (int j = 0; j <= n; ++j) {
					dist[i][j] = Integer.MAX_VALUE;
				}
			}
			for (int i = 0; i < m; ++i) {
				info = br.readLine().split(" ");
				int from = Integer.parseInt(info[0]);
				int to = Integer.parseInt(info[1]);
				int cost = readDouble(info[2]);

				dist[from][to] = dist[to][from] = min(dist[from][to], cost);
			}

			int opCount = Integer.parseInt(br.readLine());
			// handles the - multiple operas available in the same store case
			HashMap<Integer, Integer> operasInit = new HashMap<Integer, Integer>();
			for (int i = 0; i < opCount; ++i) {
				info = br.readLine().split(" ");
				int op = Integer.parseInt(info[0]);
				int save = readDouble(info[1]);
				if (operasInit.containsKey(op)) {
					operasInit.put(op, save + operasInit.get(op));
				} else {
					operasInit.put(op, save);
				}
			}
			opCount = operasInit.size();
			OK = (1 << opCount) - 1;
			operas = new int[opCount][2];
			Iterator<Integer> opIter = operasInit.keySet().iterator();
			// computing the unique opera stores
			for (int i = 0; i < opCount; ++i) {
				int op = opIter.next();
				int cost = operasInit.get(op);
				operas[i][OP] = op;
				operas[i][COST] = cost;
			}
			for (int i = 0; i <= n; ++i) {
				Arrays.fill(memo[i], 0, OK + 1, -1);
			}
			for (int k = 0; k <= n; ++k) {
				for (int i = 0; i <= n; ++i) {
					for (int j = 0; j <= n; ++j) {
						if (i != j) {
							int d1 = dist[i][k], d2 = dist[k][j];
							if (d1 != Integer.MAX_VALUE && d2 != Integer.MAX_VALUE) {
								dist[i][j] = min(dist[i][j], d1 + d2);
							}
						}
					}
				}
			}
			int savings = solve(OK, HOME);
			if (savings <= 0) {
				pw.println("Don't leave the house");
			} else {
				pw.printf("Daniel can save $%d.%02d\n", savings / 100, savings % 100);
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
