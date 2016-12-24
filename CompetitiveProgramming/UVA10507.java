import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Waking up brain
 * 
 * @author arun
 *
 */
public class UVA10507 {
	public static void main(String[] args) throws Exception {
//		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String N;
		while ((N = br.readLine()) != null) {
			int n = Integer.parseInt(N.trim());
			int m = Integer.parseInt(br.readLine().trim());
			String wakeup = br.readLine().trim().toUpperCase();
			boolean[] awake = new boolean[n];
			int awakeCount = 3;
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
			for (int i = 0; i < 3; ++i) {
				if (!vertexMap.containsKey(wakeup.charAt(i))) {
					vertexMap.put(wakeup.charAt(i), vertexMap.size());
				}
				awake[vertexMap.get(wakeup.charAt(i))] = true;
			}
			while (true) {
				boolean change = false;
				boolean[] newAwake = new boolean[n];
				for (int i = 0; i < n; ++i) {
					if (!awake[i]) {
						int awakeNeighbors = 0;
						Iterator<Integer> neighbors = adjList[i].iterator();
						while (neighbors.hasNext()) {
							awakeNeighbors = awake[neighbors.next()] ? awakeNeighbors + 1 : awakeNeighbors;
						}
						if (awakeNeighbors >= 3) {
							newAwake[i] = true;
							change = true;
							awakeCount++;
						}
					}
				}
				if (change) {
					years++;
					for (int i = 0; i < n; ++i) {
						if (newAwake[i]) {
							awake[i] = true;
						}
					}
				}
				if (!change || awakeCount == n) {
					break;
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
}
