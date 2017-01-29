import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Luggage - Need not go with knapsack, an easy way would be to iterate through
 * all possibilities to figure out if there is a matching subset. You only need
 * to check halfway through because of symmetry
 * 
 * @author arun
 *
 */
public class UVA10664 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			String[] info = br.readLine().split("\\s+");
			int size = info.length;
			int[] luggages = new int[size];
			for (int i = 0; i < size; ++i) {
				luggages[i] = Integer.parseInt(info[i]);
			}
			boolean result = false;
			for (int i = 1, jLen = 1 << size, iLen = jLen >> 1; i < iLen; ++i) {
				int b1 = 0, b2 = 0;
				for (int j = 1, k = 0; j < jLen; j <<= 1, ++k) {
					if ((i & j) > 0) {
						b1 += luggages[k];
					} else {
						b2 += luggages[k];
					}
				}
				if (b1 == b2) {
					result = true;
					break;
				}
			}
			if (result) {
				pw.println("YES");
			} else {
				pw.println("NO");
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}

}
