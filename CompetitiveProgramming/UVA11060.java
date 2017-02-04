import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Bevereages - Topological sort using Kahn's algorithm
 * 
 * @author arun
 *
 */
public class UVA11060 {
	private static final String OP = "Dilbert should drink beverages in this order:";

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line;
		int tc = 1;
		while ((line = br.readLine()) != null) {
			int n = Integer.parseInt(line);
			HashMap<String, Integer> vertexMap = new HashMap<String, Integer>();
			HashMap<Integer, String> reverseVertexMap = new HashMap<Integer, String>();
			@SuppressWarnings("unchecked")
			ArrayList<Integer>[] adjList = new ArrayList[n];// adjList
			for (int i = 0; i < n; ++i) {
				String vertexName = br.readLine();
				vertexMap.put(vertexName, i);
				reverseVertexMap.put(i, vertexName);
				adjList[i] = new ArrayList<Integer>();
			}
			int m = Integer.parseInt(br.readLine());
			int[] in = new int[n];
			for (int i = 0; i < m; ++i) {
				String[] info = br.readLine().split(" ");
				int from = vertexMap.get(info[0]);
				int to = vertexMap.get(info[1]);
				adjList[from].add(to);
				in[to]++;
			}
			boolean[] visited = new boolean[n];
			PriorityQueue<Integer> kahn = new PriorityQueue<Integer>();
			for (int i = 0; i < n; ++i) {
				if (in[i] == 0) {
					kahn.add(i);
				}
			}
			ArrayList<String> result = new ArrayList<String>();
			while (!kahn.isEmpty()) {
				int cv = kahn.remove();
				if (!visited[cv]) {
					visited[cv] = true;
					result.add(reverseVertexMap.get(cv));
					ArrayList<Integer> cAdj = adjList[cv];
					for (int i = 0, iLen = cAdj.size(); i < iLen; ++i) {
						int av = cAdj.get(i);
						in[av]--;
						if (in[av] >= 0 && in[av] == 0) {
							kahn.add(av);
						}
					}
				}
			}
			pw.print("Case #" + (tc++) + ": " + OP);
			for (int i = 0, iLen = result.size(); i < iLen; ++i) {
				pw.print(" " + result.get(i));
			}
			pw.println(".\n");
			br.readLine();
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
