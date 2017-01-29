import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Best Coalitions - Bottom Up approach (directly tried, was pretty straight
 * forward :) ), the key is to keep everything as integer to avoid floating
 * point errors
 * 
 * @author arun
 *
 */
public class UVA11658 {
	private static int readDouble(String d) {
		int result = 0;
		for (int i = d.length() - 1, j = 1; i >= 0; --i) {
			char c = d.charAt(i);
			if (c != '.') {
				result = result + ((c - '0') * j);
				j*=10;
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		while (true) {
			String[] info = br.readLine().split("\\s+");
			int n = Integer.parseInt(info[0]);
			int x = Integer.parseInt(info[1]);
			if (n == 0 && x == 0) {
				break;
			}
			x--;
			int[] shares = new int[n];
			for (int i = 0; i < n; ++i) {
				shares[i] = readDouble(br.readLine());
			}
			int xCoalition = shares[x];
			if (xCoalition > 5000) {
				pw.println("100.00");
			} else {
				boolean[] coalitions = new boolean[10001];
				for (int i = 0; i < n; ++i) {
					if (i != x) {
						for (int j = 10000, jLow = shares[i]; j >= jLow; --j) {
							if (coalitions[j - jLow] || j == jLow) {
								coalitions[j] = true;
							}
						}
					}
				}
				double maxShare = 0;
				for (int i = 5001 - xCoalition; i <= 10000; ++i) {
					if (coalitions[i]) {
						maxShare = Math.max(maxShare, (xCoalition * 100d) / (i + xCoalition));
					}
				}
				pw.printf("%.2f\n", maxShare);
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
