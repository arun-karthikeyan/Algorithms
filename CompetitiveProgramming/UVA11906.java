import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Knight in a War Grid, basic graph traversal
 * --incomplete--
 * @author arun
 *
 */
public class UVA11906 {
	private static int getSum(int[][] water, int i1, int j1, int i2, int j2) {
		int sum = water[i2][j2];
		if (i1 > 0) {
			sum -= water[i1 - 1][j2];
		}
		if (j1 > 0) {
			sum -= water[i2][j1 - 1];
		}
		if (i1 > 0 && j1 > 0) {
			sum += water[i1 - 1][j1 - 1];
		}
		return sum;
	}

	private static void updateVisits(int[][] visits, int[][] water, int i, int j, int m, int n, int r, int c) {
		if (i + m < r && j + n < c) { // bottom right
			int p1 = getSum(water, i, j, i + m, j) + getSum(water, i + m, j, i + m, j + n);
			int p2 = getSum(water, i, j, i, j + n) + getSum(water, i, j + n, i + m, j + n);
			if (p1 == 0 || p2 == 0) {
				visits[i + m][j + n]++;
			}
		}
		if (i - m >= 0 && j + n < c) { // top right
			int p1 = getSum(water, i - m, j, i, j) + getSum(water, i - m, j, i - m, j + n);
			int p2 = getSum(water, i, j, i, j + n) + getSum(water, i - m, j + n, i, j + n);
			if (p1 == 0 || p2 == 0) {
				visits[i - m][j + n]++;
			}
		}
		if (i - m >= 0 && j - n >= 0) { // top left
			int p1 = getSum(water, i - m, j, i, j) + getSum(water, i - m, j - n, i - m, j);
			int p2 = getSum(water, i, j - n, i, j) + getSum(water, i - m, j - n, i, j - n);
			if (p1 == 0 || p2 == 0) {
				visits[i - m][j - n]++;
			}
		}
		if (i + m < r && j - n >= 0) { // bottom left
			int p1 = getSum(water, i, j - n, i, j) + getSum(water, i, j - n, i + m, j - n);
			int p2 = getSum(water, i, j, i + m, j) + getSum(water, i + m, j - n, i + m, j);
			if (p1 == 0 || p2 == 0) {
				visits[i + m][j - n]++;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine()), tc = 0;
		while (tc++ < testcases) {
			String[] info = br.readLine().split(" ");
			int r = Integer.parseInt(info[0]), c = Integer.parseInt(info[1]);
			int m = Integer.parseInt(info[2]), n = Integer.parseInt(info[3]);
			int q = Integer.parseInt(br.readLine());
			int[][] water = new int[r][c];
			for (int i = 0; i < q; ++i) {
				info = br.readLine().split(" ");
				water[Integer.parseInt(info[0])][Integer.parseInt(info[1])] = 1;
			}
			for (int i = 0; i < r; ++i) {
				for (int j = 0; j < c; ++j) {
					if (i > 0) {
						water[i][j] += water[i - 1][j];
					}
					if (j > 0) {
						water[i][j] += water[i][j - 1];
					}
					if (i > 0 && j > 0) {
						water[i][j] -= water[i - 1][j - 1];
					}
				}
			}
			int[][] visits = new int[r][c];
			for (int i = 0; i < r; ++i) {
				for (int j = 0; j < c; ++j) {
					updateVisits(visits, water, i, j, m, n, r, c);
					if (m != n) {
						updateVisits(visits, water, i, j, n, m, r, c);
					}
				}
			}
			int odd = 0, even = 0;
			for (int i = 0; i < r; ++i) {
				for (int j = 0; j < c; ++j) {
					if (visits[i][j] > 0) {
						if ((visits[i][j] & 1) == 0) {
							even++;
						} else {
							odd++;
						}
					}
				}
			}
			pw.println("Case " + tc + ": " + even + " " + odd);
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
