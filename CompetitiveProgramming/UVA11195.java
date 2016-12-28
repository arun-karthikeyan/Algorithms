import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11195 {
	private static int countQueenSol(int[] queens, int currentPos, boolean[][] badSquares, boolean[] ld, boolean[] rd,
			boolean[] r) {
		if (currentPos == queens.length) {
			return 1;
		}

		int sols = 0;
		for (int i = 0, iLen = queens.length; i < iLen; ++i) {
			int rowIdx = i, ldIdx = i + currentPos, rdIdx = i - currentPos + iLen - 1;
			if (!badSquares[i][currentPos] && !r[rowIdx] && !ld[ldIdx] && !rd[rdIdx]) {
				queens[currentPos] = i;
				r[rowIdx] = true;
				ld[ldIdx] = true;
				rd[rdIdx] = true;
				sols += countQueenSol(queens, currentPos + 1, badSquares, ld, rd, r);
				r[rowIdx] = false;
				ld[ldIdx] = false;
				rd[rdIdx] = false;
			}
		}
		return sols;
	}

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int n, tc = 1;
		while ((n = Integer.parseInt(br.readLine())) != 0) {
			boolean[][] badSquares = new boolean[n][n];
			for (int i = 0; i < n; ++i) {
				String row = br.readLine();
				for (int j = 0; j < n; ++j) {
					badSquares[i][j] = row.charAt(j) == '*';
				}
			}
			pw.println("Case " + (tc++) + ": " + countQueenSol(new int[n], 0, badSquares, new boolean[2 * n - 1],
					new boolean[2 * n - 1], new boolean[n]));
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
