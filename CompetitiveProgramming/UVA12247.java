import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA12247 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] sis = new int[3], prince = new int[2];

		sis[0] = Integer.parseInt(st.nextToken());
		sis[1] = Integer.parseInt(st.nextToken());
		sis[2] = Integer.parseInt(st.nextToken());
		prince[0] = Integer.parseInt(st.nextToken());
		prince[1] = Integer.parseInt(st.nextToken());
		boolean[] cards = new boolean[53];
		cards[sis[0]] = true;
		cards[sis[1]] = true;
		cards[sis[2]] = true;
		cards[prince[0]] = true;
		cards[prince[1]] = true;
		while (sis[0] != 0 || sis[1] != 0 || sis[2] != 0 || prince[0] != 0
				|| prince[1] != 0) {

			Arrays.sort(sis);
			boolean printed = false;
			if (prince[0] > sis[2] && prince[1] > sis[2]) {
				for (int i = 1; i < 53; ++i) {
					if (!cards[i]) {
						pw.println(i);
						printed = true;
						break;
					}
				}
			} else if (prince[0] > sis[1] && prince[1] > sis[1]) {
				for (int i = sis[1] + 1; i < 53; ++i) {
					if (!cards[i]) {
						pw.println(i);
						printed = true;
						break;
					}
				}
			} else if ((prince[0] > sis[2] || prince[1] > sis[2])) {
				for (int i = sis[2] + 1; i < 53; ++i) {
					if (!cards[i]) {
						pw.println(i);
						printed = true;
						break;
					}
				}
			}
			if (!printed) {
				pw.println(-1);
			}

			st = new StringTokenizer(br.readLine());
			sis[0] = Integer.parseInt(st.nextToken());
			sis[1] = Integer.parseInt(st.nextToken());
			sis[2] = Integer.parseInt(st.nextToken());
			prince[0] = Integer.parseInt(st.nextToken());
			prince[1] = Integer.parseInt(st.nextToken());
			cards = new boolean[53];
			cards[sis[0]] = true;
			cards[sis[1]] = true;
			cards[sis[2]] = true;
			cards[prince[0]] = true;
			cards[prince[1]] = true;
		}

		pw.flush();
		pw.close();
		br.close();
	}

}
