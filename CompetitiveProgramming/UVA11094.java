import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Continents - Flood fill
 * 
 * @author arun
 *
 */
public class UVA11094 {
	private static char land;
	private static final int ROW = 0, COL = 1;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line;
		while ((line = br.readLine()) != null) {
			String[] info = line.split(" ");
			int n = Integer.parseInt(info[0]), m = Integer.parseInt(info[1]);
			char[][] map = new char[n][m];
			for (int i = 0; i < n; ++i) {
				String row = br.readLine();
				for (int j = 0; j < m; ++j) {
					map[i][j] = row.charAt(j);
				}
			}
			boolean[][] visited = new boolean[n][m];
			info = br.readLine().split(" ");
			Queue<int[]> bfs = new LinkedList<int[]>();
			int kingR = Integer.parseInt(info[0]), kingC = Integer.parseInt(info[1]);
			land = map[kingR][kingC];
			bfs.add(new int[] { kingR, kingC });
			while (!bfs.isEmpty()) {
				int[] cv = bfs.remove();
				int r = cv[ROW], c = cv[COL];
				if (!visited[r][c]) {
					visited[r][c] = true;
					if (r > 0 && map[r - 1][c] == land) {
						bfs.add(new int[] { r - 1, c });
					}
					if (r + 1 < n && map[r + 1][c] == land) {
						bfs.add(new int[] { r + 1, c });
					}
					int c1 = ((c - 1) + m) % m, c2 = (c + 1) % m;
					if (map[r][c1] == land) {
						bfs.add(new int[] { r, c1 });
					}
					if (map[r][c2] == land) {
						bfs.add(new int[] { r, c2 });
					}
				}
			}
			int result = 0;
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < m; ++j) {
					if (map[i][j] == land && !visited[i][j]) {
						int count = 0;
						bfs.add(new int[] { i, j });
						while (!bfs.isEmpty()) {
							int[] cv = bfs.remove();
							int r = cv[ROW], c = cv[COL];
							if (!visited[r][c]) {
								count++;
								visited[r][c] = true;
								if (r > 0 && map[r - 1][c] == land) {
									bfs.add(new int[] { r - 1, c });
								}
								if (r + 1 < n && map[r + 1][c] == land) {
									bfs.add(new int[] { r + 1, c });
								}
								int c1 = ((c - 1) + m) % m, c2 = (c + 1) % m;
								if (map[r][c1] == land) {
									bfs.add(new int[] { r, c1 });
								}
								if (map[r][c2] == land) {
									bfs.add(new int[] { r, c2 });
								}
							}
						}
						result = Math.max(result, count);
					}
				}
			}
			pw.println(result);
			br.readLine();
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
