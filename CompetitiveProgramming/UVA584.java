import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UVA584 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line = br.readLine();
		while (line.charAt(0) != 'G') {
			ArrayList<Character> scoreDesc = new ArrayList<Character>();
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()) {
				scoreDesc.add(st.nextToken().charAt(0));
			}
			int totalScore = 0;
			int currentFrame = 1;
			for (int i = 0, iLen = scoreDesc.size(); i < iLen; ++i) {
				char ch = scoreDesc.get(i);
				int frameScore = 0;
				if (Character.isDigit(ch)) {
					frameScore = ch - '0';
					++i;
					ch = scoreDesc.get(i);
					if (Character.isDigit(ch)) {
						frameScore += ch - '0';
					} else {// '/' case
						frameScore = 10 + (Character.isDigit(scoreDesc.get(i + 1)) ? scoreDesc.get(i + 1) - '0' : 10);
					}
				} else if (ch == 'X') {
					frameScore = 10;
					frameScore = frameScore
							+ (Character.isDigit(scoreDesc.get(i + 1)) ? (Character.isDigit(scoreDesc.get(i + 2))
									? (scoreDesc.get(i + 1) + scoreDesc.get(i + 2) - 2 * '0') : (10))
									: (Character.isDigit(scoreDesc.get(i + 2)) ? (10 + scoreDesc.get(i + 2) - '0')
											: (20)));
				}
				totalScore += frameScore;
				currentFrame++;
				if (currentFrame > 10) {
					break;
				}
			}
			pw.println(totalScore);
			line = br.readLine();
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
