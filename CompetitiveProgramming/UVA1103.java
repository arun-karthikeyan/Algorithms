import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Ancient Messages | A good flood-fill problem, Connected Components
 * sub-category
 * 
 * @author arun
 *
 */
public class UVA1103 {
	private static final HashMap<Character, String> LOOKUP;
	static {
		LOOKUP = new HashMap<Character, String>();
		LOOKUP.put('0', "0000");
		LOOKUP.put('1', "0001");
		LOOKUP.put('2', "0010");
		LOOKUP.put('3', "0011");
		LOOKUP.put('4', "0100");
		LOOKUP.put('5', "0101");
		LOOKUP.put('6', "0110");
		LOOKUP.put('7', "0111");
		LOOKUP.put('8', "1000");
		LOOKUP.put('9', "1001");
		LOOKUP.put('a', "1010");
		LOOKUP.put('b', "1011");
		LOOKUP.put('c', "1100");
		LOOKUP.put('d', "1101");
		LOOKUP.put('e', "1110");
		LOOKUP.put('f', "1111");

	}
	private static final int ROW = 0, COL = 1;
	private static final int BLACK = 1;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int tc = 1;
		while (true) {
			String[] info = br.readLine().split(" ");
			int n = Integer.parseInt(info[0]);
			int m = Integer.parseInt(info[1]);
			if (n == 0 && m == 0) {
				break;
			}
			int[][] image = new int[n][m << 2];
			for (int i = 0; i < n; ++i) {
				String row = br.readLine();
				for (int j = 0; j < m; ++j) {
					String bv = LOOKUP.get(row.charAt(j));
					for (int k = 0; k < 4; ++k) {
						image[i][(j << 2) + k] = bv.charAt(k) - '0';
					}
				}
			}
			int[][] decodedImage = new int[n][m << 2];
			boolean[][] visited = new boolean[n][m << 2];
			int heiroglyphsCount = 1;
			for (int i = 0; i < n; ++i) {
				for (int j = 0, jLen = m << 2; j < jLen; ++j) {
					if (image[i][j] == BLACK && !visited[i][j]) {
						Queue<int[]> bfs = new LinkedList<int[]>();
						bfs.add(new int[] { i, j });
						while (!bfs.isEmpty()) {
							int[] currentPair = bfs.remove();
							int r = currentPair[ROW];
							int c = currentPair[COL];
							if (!visited[r][c]) {
								visited[r][c] = true;
								decodedImage[r][c] = heiroglyphsCount;
								if (r > 0 && image[r - 1][c] == 1) {
									bfs.add(new int[] { r - 1, c });
								}
								if (r + 1 < n && image[r + 1][c] == 1) {
									bfs.add(new int[] { r + 1, c });
								}
								if (c > 0 && image[r][c - 1] == 1) {
									bfs.add(new int[] { r, c - 1 });
								}
								if (c + 1 < jLen && image[r][c + 1] == 1) {
									bfs.add(new int[] { r, c + 1 });
								}
							}
						}
						heiroglyphsCount++;
					}
				}
			}
			int[] heiroGlyphsLabel = new int[heiroglyphsCount];
			for (int i = 0; i < n; ++i) {
				for (int j = 0, jLen = m << 2; j < jLen; ++j) {
					if (!visited[i][j]) {
						// only unvisited white components come in
						Queue<int[]> bfs = new LinkedList<int[]>();
						boolean[] enclosingHeiroglyph = new boolean[heiroglyphsCount];
						bfs.add(new int[] { i, j });
						boolean touchesBorder = false;
						while (!bfs.isEmpty()) {
							int[] currentPair = bfs.remove();
							int r = currentPair[ROW];
							int c = currentPair[COL];
							int code = decodedImage[r][c];
							if (code > 0 && !enclosingHeiroglyph[code]) {
								enclosingHeiroglyph[code] = true;
							}
							if (!visited[r][c]) {
								visited[r][c] = true;
								if (r > 0) {
									bfs.add(new int[] { r - 1, c });
								} else if (!touchesBorder) {
									touchesBorder = true;
								}
								if (r + 1 < n) {
									bfs.add(new int[] { r + 1, c });
								} else if (!touchesBorder) {
									touchesBorder = true;
								}
								if (c > 0) {
									bfs.add(new int[] { r, c - 1 });
								} else if (!touchesBorder) {
									touchesBorder = true;
								}
								if (c + 1 < jLen) {
									bfs.add(new int[] { r, c + 1 });
								} else if (!touchesBorder) {
									touchesBorder = true;
								}
							}
						}
						if (!touchesBorder) {
							for (int k = 1; k < heiroglyphsCount; ++k) {
								if (enclosingHeiroglyph[k]) {
									heiroGlyphsLabel[k]++;
								}
							}
						}

					}
				}
			}
			char[] result = new char[heiroglyphsCount];
			for (int i = 1; i < heiroglyphsCount; ++i) {
				switch (heiroGlyphsLabel[i]) {
				case 1:
					result[i] = 'A';
					break;
				case 3:
					result[i] = 'J';
					break;
				case 5:
					result[i] = 'D';
					break;
				case 4:
					result[i] = 'S';
					break;
				case 2:
					result[i] = 'K';
					break;
				case 0:
					result[i] = 'W';
				}
			}
			Arrays.sort(result, 1, heiroglyphsCount);
			pw.print("Case " + (tc++) + ": ");
			for (int i = 1; i < heiroglyphsCount; ++i) {
				pw.print(result[i]);
			}
			pw.println();
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
