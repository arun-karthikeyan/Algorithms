import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Safe Breaker
 * 
 * @author arun
 *
 */
public class UVA296 {
	private static final int MAX = (int) 1e4;

	private static int getMisplacedDigitsCount(String orig, String guess) {
		int count = 0;
		int[] origCount = new int[10];
		for (int i = 0, iLen = orig.length(); i < iLen; ++i) {
			origCount[orig.charAt(i) - '0']++;
		}
		for (int i = 0, iLen = guess.length(); i < iLen; ++i) {
			int idx = guess.charAt(i) - '0';
			if (origCount[idx] > 0) {
				count++;
				origCount[idx]--;
			}
		}
		return count;
	}

	private static int[] getClue(String orig, String guess) {
		int count = 0;
		StringBuilder incorrectGuess = new StringBuilder();
		StringBuilder incorrectOrig = new StringBuilder();
		for (int i = 0; i < 4; ++i) {
			if (orig.charAt(i) == guess.charAt(i)) {
				count++;
			} else {
				incorrectGuess.append(guess.charAt(i));
				incorrectOrig.append(orig.charAt(i));
			}
		}
		return new int[] { count, getMisplacedDigitsCount(incorrectOrig.toString(), incorrectGuess.toString()) };
	}

	private static String getAppendedString(int val) {
		String strVal = String.valueOf(val);
		StringBuilder sb = new StringBuilder();
		for (int i = strVal.length(); i < 4; ++i) {
			sb.append('0');
		}
		sb.append(strVal);
		return sb.toString();
	}

	private static final String INDETERMINATE = "indeterminate";
	private static final String IMPOSSIBLE = "impossible";

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			String secretCode = "";
			int g = Integer.parseInt(br.readLine());
			boolean resultPrinted = false;
			String[] guess = new String[g];
			int[] r1 = new int[g];
			int[] r2 = new int[g];
			for (int i = 0; i < g; ++i) {
				String[] info = br.readLine().split(" ");
				guess[i] = info[0];
				r1[i] = info[1].charAt(0) - '0';
				r2[i] = info[1].charAt(2) - '0';
			}

			for (int i = 0; i < MAX; ++i) {
				String strVal = getAppendedString(i);
				boolean guessCompliant = true;

				for (int j = 0; j < g; ++j) {
					int[] clue = getClue(strVal, guess[j]);
					if (clue[0] != r1[j] || clue[1] != r2[j]) {
						guessCompliant = false;
						break;
					}
				}
				if (guessCompliant) {
					if ("".equals(secretCode)) {
						secretCode = strVal;
					} else {
						pw.println(INDETERMINATE);
						resultPrinted = true;
						break;
					}
				}
			}
			if (!resultPrinted) {
				if ("".equals(secretCode)) {
					pw.println(IMPOSSIBLE);
				} else {
					pw.println(secretCode);
				}
			}

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
