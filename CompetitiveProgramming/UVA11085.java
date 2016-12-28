import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11085 {
	private static boolean[] ld = new boolean[15], rd = new boolean[15], r = new boolean[8];
	private static String origQueens;

	private static int minMoves(int currentPos) {
		if (currentPos == 8) {
			return 0;
		}
		int min = 8;
		for (int i = 0; i < 8; ++i) {
			int ldIdx = i + currentPos, rdIdx = i - currentPos + 7;
			if (!r[i] && !ld[ldIdx] && !rd[rdIdx]) {
				r[i] = true;
				ld[ldIdx] = true;
				rd[rdIdx] = true;
				min = min(min, minMoves(currentPos + 1) + ((origQueens.charAt(currentPos<<1) - '1') == i ? 0 : 1));
				r[i] = false;
				ld[ldIdx] = false;
				rd[rdIdx] = false;
			}
		}
		return min;
	}

	private static int min(int a, int b) {
		return a < b ? a : b;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int tc = 1;
		while ((origQueens = br.readLine()) != null) {
			pw.println("Case "+(tc++)+": "+minMoves(0));
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
