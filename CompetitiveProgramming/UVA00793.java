import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA00793 {
	private static int[] id, sz;
	private static int n;

	private static void init() {
		id = new int[n];
		sz = new int[n];
		for (int i = 0; i < n; ++i) {
			id[i] = i;
			sz[i] = 1;
		}
	}

	private static int find(int x) {
		if (id[x] != x) {
			return id[x] = find(id[x]);
		}
		return x;
	}

	private static void union(int x, int y) {
		int p1 = find(x), p2 = find(y);
		if (p1 == p2) {
			return;
		}
		if (sz[p1] < sz[p2]) {
			id[p1] = p2;
			sz[p2] += sz[p1];
		} else {
			id[p2] = p1;
			sz[p1] += sz[p2];
		}
	}

	private static boolean connected(int x, int y) {
		return find(x) == find(y);
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		br.readLine();
		while (testcases-- > 0) {
			n = Integer.parseInt(br.readLine());
			init();
			String query;
			int success = 0, fails = 0;
			while ((query = br.readLine()) != null && query.length() > 0) {
				String[] q = query.split(" ");
				if (q[0].charAt(0) == 'c') {
					union(Integer.parseInt(q[1]) - 1, Integer.parseInt(q[2]) - 1);
				} else {
					if (connected(Integer.parseInt(q[1]) - 1, Integer.parseInt(q[2]) - 1)) {
						success++;
					} else {
						fails++;
					}
				}
			}
			pw.println(success + "," + fails);
			if (testcases != 0) {
				pw.println();
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
