import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Waking up brain
 * 
 * @author arun
 *
 */
public class UVA10507_2 {
	private static final int YEAR = 0, NODE = 1;

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String N;
		while ((N = br.readLine()) != null) {
			int n = Integer.parseInt(N.trim());
			int m = Integer.parseInt(br.readLine().trim());
			String wakeup = br.readLine().trim().toUpperCase();
			int awakeCount = 3;
			int[] awakeYear = new int[n];
			Arrays.fill(awakeYear, -1);
			HashMap<Character, Integer> vertexMap = new HashMap<Character, Integer>();
			@SuppressWarnings("unchecked")
			HashSet<Integer>[] adjList = new HashSet[n];
			int years = 0;
			for (int i = 0; i < n; ++i) {
				adjList[i] = new HashSet<Integer>();
			}
			for (int i = 0; i < m; ++i) {
				String currentEdge = br.readLine().trim().toUpperCase();
				char from = currentEdge.charAt(0);
				char to = currentEdge.charAt(1);
				if (!vertexMap.containsKey(from)) {
					vertexMap.put(from, vertexMap.size());
				}
				if (!vertexMap.containsKey(to)) {
					vertexMap.put(to, vertexMap.size());
				}
				int fromInt = vertexMap.get(from), toInt = vertexMap.get(to);
				adjList[fromInt].add(toInt);
				adjList[toInt].add(fromInt);
			}
			br.readLine();
			Queue<int[]> bfs = new LinkedList<int[]>();
			for (int i = 0; i < 3; ++i) {
				if (!vertexMap.containsKey(wakeup.charAt(i))) {
					vertexMap.put(wakeup.charAt(i), vertexMap.size());
				}
				int awakeNode = vertexMap.get(wakeup.charAt(i));
				awakeYear[awakeNode] = 0;
				bfs.add(new int[] { 0, awakeNode });
			}
			while (!bfs.isEmpty()) {
				int[] currentNodeInfo = bfs.remove();
				Iterator<Integer> neighbors = adjList[currentNodeInfo[NODE]].iterator();
				years = currentNodeInfo[YEAR];
				while (neighbors.hasNext()) {
					int currentNeighbor = neighbors.next();
					if (awakeYear[currentNeighbor] == -1) {
						Iterator<Integer> fof = adjList[currentNeighbor].iterator();
						int awakeNeighbors = 0;
						int[] fofYears = new int[26]; int fofIdx = 0;
						while (fof.hasNext()) {
							int currentFof = fof.next();
							if (awakeYear[currentFof] != -1) {
								awakeNeighbors++;
								fofYears[fofIdx++] = awakeYear[currentFof];
							}
						}
						if (awakeNeighbors > 2) {
							Arrays.sort(fofYears, 0, fofIdx);
							bfs.add(new int[] { fofYears[2] + 1, currentNeighbor });
							awakeYear[currentNeighbor] = fofYears[2] + 1;
							awakeCount++;
						}
					}
				}
			}

			if (awakeCount == n) {
				pw.println("WAKE UP IN, " + years + ", YEARS");
			} else {
				pw.println("THIS BRAIN NEVER WAKES UP");
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}
}
