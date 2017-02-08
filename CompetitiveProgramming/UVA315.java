import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Network - Simply identify all the articulation points Tarjan's algorithm for
 * finding articulation points
 * 
 * @author arun
 *
 */
public class UVA315 {

	private static boolean adjMat[][] = new boolean[100][100], visited[] = new boolean[100];
	private static int dfsParent[] = new int[100], dfsLow[] = new int[100], dfsNum[] = new int[100];
	private static HashSet<Integer> result;
	private static int n, dfsRoot, rootChildren, counter;

	private static void findArticulationPoint(int v) {
		for (int i = 0; i < n; ++i) {
			if (i != v) {
				if (adjMat[v][i]) {
					if (!visited[i]) {
						if (v == dfsRoot) {
							rootChildren++;
						}
						dfsParent[i] = v;
						dfsNum[i] = dfsLow[i] = counter++;
						visited[i] = true;
						findArticulationPoint(i);
						dfsLow[v] = Math.min(dfsLow[i], dfsLow[v]); // update
																	// lowest
																	// ancestor
																	// reachable
						if (v != dfsRoot && dfsLow[i] >= dfsNum[v]) { // articulation
																		// point
																		// condition
							result.add(v + 1);
						}
					} else {
						if (dfsParent[v] != i) {// backedge (not a bidrectional
												// edge), update dfsLow[v]
							dfsLow[v] = Math.min(dfsLow[v], dfsNum[i]);
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		while (true) {
			n = Integer.parseInt(br.readLine());
			if (n == 0) {
				break;
			}
			Arrays.fill(visited, 0, n, false);
			for (int i = 0; i < n; ++i) {
				Arrays.fill(adjMat[i], 0, n, false);
			}
			while (true) {
				String info[] = br.readLine().split(" ");
				int from = Integer.parseInt(info[0]);
				if (from == 0) {
					break;
				}
				for (int i = 1, iLen = info.length; i < iLen; ++i) {
					int to = Integer.parseInt(info[i]);
					adjMat[from - 1][to - 1] = true;
					adjMat[to - 1][from - 1] = true;
				}
			}
			result = new HashSet<Integer>();
			for (int i = 0; i < n; ++i) {
				if (!visited[i]) {
					dfsRoot = i;
					rootChildren = counter = 0;
					dfsParent[i] = i;
					dfsNum[i] = dfsLow[i] = counter++;
					visited[i] = true;
					findArticulationPoint(i);
					if (rootChildren > 1) {
						result.add(i + 1);
					}
				}
			}
			pw.println(result.size());
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
