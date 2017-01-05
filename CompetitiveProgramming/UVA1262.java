import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Password
 * 
 * @author arun
 *
 */
public class UVA1262 {
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, values = 1; i < 27; ++i, values *= 2) {
			LOOKUP.put(values, i);
		}
	}

	private static int countBits(int val) {
		int count = 0;
		while (val > 0) {
			val -= (val & (-val));
			count++;
		}
		return count;
	}

	private static final String NO = "NO";

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			int[] grid1Cols = new int[5], grid2Cols = new int[5];
			int n = Integer.parseInt(br.readLine());
			for (int i = 0; i < 6; ++i) {
				String row = br.readLine();
				for (int j = 0; j < 5; ++j) {
					grid1Cols[j] |= (1 << (row.charAt(j) - 'A'));
				}
			}
			for (int i = 0; i < 6; ++i) {
				String row = br.readLine();
				for (int j = 0; j < 5; ++j) {
					grid2Cols[j] |= (1 << (row.charAt(j) - 'A'));
				}
			}
			char[][] finalGrid = new char[5][];
			int maxIdx = 1;
			for (int i = 0; i < 5; ++i) {
				int common = grid1Cols[i] & grid2Cols[i];
				int maxBits = countBits(common);
				maxIdx *= maxBits;
				finalGrid[i] = new char[maxBits];
				int idx = 0;
				while (common > 0) {
					int p = common & (-common);
					common -= p;
					finalGrid[i][idx++] = (char) (LOOKUP.get(p) + 'A');
				}

			}

			if (n > maxIdx) {
				pw.println(NO);
			} else {
				n--;
				int[] toPrint = new int[5];
				for (int i = 4; i >= 0 && n > 0; --i) {
					int curLimit = finalGrid[i].length;
					toPrint[i] = n % curLimit;
					n /= curLimit;
				}
				for (int i = 0; i < 5; ++i) {
					pw.print(finalGrid[i][toPrint[i]]);
				}
				pw.println();
			}

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
