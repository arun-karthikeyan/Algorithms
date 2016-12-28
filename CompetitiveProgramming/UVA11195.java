import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Another n-Queens Problem
 * 
 * @author arun
 *
 */
public class UVA11195 {

	private static int[] badSquares;
	private static int finish;

	private static int findSolutions(int rw, int d1, int d2, int col) {
		if (rw == finish) {
			return 1;
		}
		int valid = finish & (~(rw | d1 | d2 | badSquares[col])), sum = 0;
		while (valid > 0) {
			int b = (valid & (-valid));
			valid -= b;
			sum += findSolutions((rw | b), (d1 | b) << 1, (d2 | b) >> 1, col + 1);
		}
		return sum;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int n, tc = 1;
		while ((n = Integer.parseInt(br.readLine())) != 0) {
			badSquares = new int[n + 1];
			finish = (1 << n) - 1;
			for (int i = 0; i < n; ++i) {
				String row = br.readLine();
				for (int j = 0; j < n; ++j) {
					badSquares[j] |= row.charAt(j) == '*' ? 1 << i : 0;
				}
			}
			pw.println("Case " + (tc++) + ": " + findSolutions(0, 0, 0, 0));
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
