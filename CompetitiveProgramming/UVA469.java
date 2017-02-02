import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Wetlands of Florida, BFS is faster than DFS here
 * 
 * @author arun
 *
 */
public class UVA469 {
	private static final int ROW = 0, COL = 1;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		br.readLine();
		while (testcases-- > 0) {
			ArrayList<String> grid = new ArrayList<String>();
			String line;
			while (true) {
				line = br.readLine();
				if (Character.isDigit(line.charAt(0))) {
					break;
				}
				grid.add(line);
			}
			// precomputing takes O(8*n) time
			int rows = grid.size(), cols = grid.get(0).length();
			boolean[][] visited = new boolean[rows][cols];
			int[][] wetLands = new int[rows][cols];
			for (int i = 0; i < rows; ++i) {
				String currentRow = grid.get(i);
				for (int j = 0; j < cols; ++j) {
					char ch = currentRow.charAt(j);
					if (!visited[i][j]) {
						if (ch == 'L') {
							visited[i][j] = true;
						} else {
							Queue<int[]> dfs = new LinkedList<int[]>();
							ArrayList<int[]> currentWetLand = new ArrayList<int[]>();
							int[] pair = new int[] { i, j };
							dfs.add(pair);

							while (!dfs.isEmpty()) {
								int[] currentNode = dfs.remove();
								int r = currentNode[ROW], c = currentNode[COL];
								if (!visited[r][c]) {
									visited[r][c] = true;
									currentWetLand.add(currentNode);
									// add all 8 possible adjacents
									if (r > 0 && grid.get(r - 1).charAt(c) == 'W') {
										dfs.add(new int[] { r - 1, c });
									}
									if (r > 0 && c > 0 && grid.get(r - 1).charAt(c - 1) == 'W') {
										dfs.add(new int[] { r - 1, c - 1 });
									}
									if (c > 0 && grid.get(r).charAt(c - 1) == 'W') {
										dfs.add(new int[] { r, c - 1 });
									}
									if (r + 1 < rows && c > 0 && grid.get(r + 1).charAt(c - 1) == 'W') {
										dfs.add(new int[] { r + 1, c - 1 });
									}
									if (r + 1 < rows && grid.get(r + 1).charAt(c) == 'W') {
										dfs.add(new int[] { r + 1, c });
									}
									if (r + 1 < rows && c + 1 < cols && grid.get(r + 1).charAt(c + 1) == 'W') {
										dfs.add(new int[] { r + 1, c + 1 });
									}
									if (c + 1 < cols && grid.get(r).charAt(c + 1) == 'W') {
										dfs.add(new int[] { r, c + 1 });
									}
									if (r > 0 && c + 1 < cols && grid.get(r - 1).charAt(c + 1) == 'W') {
										dfs.add(new int[] { r - 1, c + 1 });
									}
								}
							}
							for (int k = 0, kLen = currentWetLand.size(); k < kLen; ++k) {
								int[] currentPair = currentWetLand.get(k);
								int r = currentPair[ROW], c = currentPair[COL];
								wetLands[r][c] = kLen;
							}
						}
					}
				}
			}
			do {
				String[] info = line.split("\\s+");
				pw.println(wetLands[Integer.parseInt(info[0]) - 1][Integer.parseInt(info[1]) - 1]);
			} while ((line = br.readLine()) != null && line.length() > 0);
			if (testcases != 0) {
				pw.println();
			}

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
