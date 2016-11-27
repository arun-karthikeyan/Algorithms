import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA489 {
	private static final String WIN = "You win.";
	private static final String LOSE = "You lose.";
	private static final String CHICKEN = "You chickened out.";

	private static void fillChoices(int[] hangman, String choice) {
		for (int i = 0, iLen = choice.length(); i < iLen; ++i) {
			hangman[choice.charAt(i) - 'a']++;
		}
	}

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new
		 InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line = "";
		while (!"-1".equals(line = br.readLine())) {
			int roundNo = Integer.parseInt(line);
			int[] hangman = new int[26];
			boolean[] choices = new boolean[26];
			String hangmanstr = br.readLine();
			String choicestr = br.readLine();
			fillChoices(hangman, hangmanstr);
			int hangMan = 0;
			int count = 0;
			boolean win = false;
			for (int i = 0, iLen = choicestr.length(), hangmanLen = hangmanstr.length(); i < iLen; ++i) {
				int chIdx = choicestr.charAt(i) - 'a';
				if (!choices[chIdx]) {
					if (hangman[chIdx] > 0) {
						count += hangman[chIdx];
						hangman[chIdx] = 0;
						if (count == hangmanLen) {
							win = true;
							break;
						}
					} else {
						hangMan++;
						if(hangMan>=7){
							break;
						}
					}
				}
				choices[chIdx] = true;
			}
			pw.println("Round " + roundNo);
			if (win) {
				pw.println(WIN);
			} else if (hangMan < 7) {
				pw.println(CHICKEN);
			} else {
				pw.println(LOSE);
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
