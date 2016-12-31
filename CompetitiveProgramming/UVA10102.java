import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The path in the color field
 * 
 * @author arun
 *
 */
public class UVA10102 {
	static class Pair {
		int i, j, level;

		public Pair(int i, int j, int level) {
			this.i = i;
			this.j = j;
			this.level = level;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line;
		while ((line = br.readLine()) != null) {
			int m = Integer.parseInt(line);
			int[][] vals = new int[m][m];
			for (int i = 0; i < m; ++i) {
				String row = br.readLine();
				for (int j = 0; j < m; ++j) {
					vals[i][j] = row.charAt(j) - '0';
				}
			}
			int max = -1;
			for (int i = 0; i < m; ++i) {
				for (int j = 0; j < m; ++j) {
					if (vals[i][j] == 1) {
						Queue<Pair> bfs = new LinkedList<Pair>();
						bfs.add(new Pair(i, j, 0));
						boolean[][] visited = new boolean[m][m];
						while (!bfs.isEmpty()) {
							Pair currentVertex = bfs.remove();
							if (vals[currentVertex.i][currentVertex.j] == 3) {
								max = max(max, currentVertex.level);
								break;
							}
							if (!visited[currentVertex.i][currentVertex.j]) {
								visited[currentVertex.i][currentVertex.j] = true;
								int childLevel = currentVertex.level + 1;
								if (currentVertex.i > 0) {
									bfs.add(new Pair(currentVertex.i - 1, currentVertex.j, childLevel));
								}
								if (currentVertex.j > 0) {
									bfs.add(new Pair(currentVertex.i, currentVertex.j - 1, childLevel));
								}
								if (currentVertex.i < (m - 1)) {
									bfs.add(new Pair(currentVertex.i + 1, currentVertex.j, childLevel));
								}
								if (currentVertex.j < (m - 1)) {
									bfs.add(new Pair(currentVertex.i, currentVertex.j + 1, childLevel));
								}
							}
						}
					}
				}
			}
			pw.println(max);
		}
		br.close();
		pw.flush();
		pw.close();
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}
}
