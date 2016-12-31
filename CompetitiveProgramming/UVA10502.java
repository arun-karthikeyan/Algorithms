import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Counting Rectangles
 * 
 * @author arun
 *
 */
public class UVA10502 {
	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int n;
		while ((n = Integer.parseInt(br.readLine())) != 0) {
			int m = Integer.parseInt(br.readLine());
			boolean[][] state = new boolean[n][m];
			for (int i = 0; i < n; ++i) {
				String row = br.readLine();
				for (int j = 0; j < m; ++j) {
					state[i][j] = row.charAt(j) == '1';
				}
			}
			int count = 0;
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < m; ++j) {
					for (int k = i, lLen = m; k < n && lLen != 0; ++k) {
						for (int l = j; l < lLen; ++l) {
							if (state[k][l]) {
								count++;
							} else {
								lLen = l;
								break;
							}
						}
					}
				}
			}
			pw.println(count);
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
