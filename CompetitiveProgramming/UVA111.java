import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * History Grading - Plain LIS (had to read the question twice to understand
 * what they were really looking for)
 * 
 * @author arun
 *
 */
public class UVA111 {
	private static int binarySearchInsertPos(ArrayList<Integer> lis, int key) {
		int low = 0, high = lis.size() - 1;
		while (low <= high) {
			int mid = (low + high) >> 1, val = lis.get(mid);
			if (key < val) {
				high = mid - 1;
			} else if (key > val) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		return low;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line = br.readLine().trim();
		while (true) {
			if (line == null) {
				break;
			}
			int n = Integer.parseInt(line);
			String[] ranking = br.readLine().trim().split("\\s+");
			HashMap<Integer, Integer> properRanking = new HashMap<Integer, Integer>();
			for (int i = 0; i < n; ++i) {
				properRanking.put(i + 1, Integer.parseInt(ranking[i]));
			}
			while ((line = br.readLine()) != null) {
				ranking = line.trim().split("\\s+");
				if (ranking.length < 2) {
					break;
				}
				ArrayList<Integer> lds = new ArrayList<Integer>();
				int[] studentRanking = new int[n];
				for (int i = 0; i < n; ++i) {
					studentRanking[Integer.parseInt(ranking[i]) - 1] = properRanking.get(i + 1);
				}
				for (int i = 0; i < n; ++i) {
					int val = studentRanking[i];
					int ip = binarySearchInsertPos(lds, val);
					if (ip < lds.size()) {
						lds.set(ip, val);
					} else {
						lds.add(val);
					}
				}
				pw.println(lds.size());
			}

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
