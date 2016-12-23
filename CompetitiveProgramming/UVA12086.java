import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA12086 {
	private static int[] fenwickTree;
	private static int[] baseArray;
	private static int n;

	private static void buildFenwickTree() {
		int[] cumulative = new int[n + 1];
		cumulative[1] = baseArray[0];
		for (int i = 2; i <= n; ++i) {
			cumulative[i] = baseArray[i - 1] + cumulative[i - 1];
		}
		for (int i = 1; i <= n; ++i) {
			fenwickTree[i] = cumulative[i] - cumulative[i - lsOne(i)];
		}
	}

	private static int rangeQuery(int l, int r) {
		return rangeQuery(r) - rangeQuery(l - 1);
	}

	private static int rangeQuery(int r) {
		int sum = 0;
		while (r > 0) {
			sum += fenwickTree[r];
			r -= lsOne(r);
		}
		return sum;
	}

	private static void update(int idx, int val) {
		int valueToAdd = val - baseArray[idx - 1];
		baseArray[idx - 1] = val;
		while (idx <= n) {
			fenwickTree[idx] += valueToAdd;
			idx += lsOne(idx);
		}
	}

	private static int lsOne(int a) {
		return (a & (-a));
	}

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
//		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("testop.txt"))));
		 PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int tc = 1;
		while ((n = Integer.parseInt(br.readLine())) != 0) {
			if (tc > 1) {
				pw.println();
			}
			baseArray = new int[n];
			fenwickTree = new int[n + 1];
			for (int i = 0; i < n; ++i) {
				baseArray[i] = Integer.parseInt(br.readLine());
			}
			buildFenwickTree();
			String query;
			pw.println("Case " + (tc++) + ":");
			while ((query = br.readLine()).charAt(0) != 'E') {
				String[] q = query.split(" ");
				if (q[0].charAt(0) == 'M') {
					pw.println(rangeQuery(Integer.parseInt(q[1]), Integer.parseInt(q[2])));
				} else {
					update(Integer.parseInt(q[1]), Integer.parseInt(q[2]));
				}
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
