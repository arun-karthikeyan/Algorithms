import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA12086_2 {
	private static int[] baseArray;
	private static int n;
	private static int[] segmentTree;

	private static void buildSegmentTree(int node, int start, int end) {
		if (start == end) {
			segmentTree[node] = baseArray[start];
			return;
		}
		int mid = (start + end) >> 1, left = node << 1, right = left + 1;
		buildSegmentTree(left, start, mid);
		buildSegmentTree(right, mid + 1, end);
		segmentTree[node] = segmentTree[left] + segmentTree[right];
	}

	private static int rangeQuery(int node, int start, int end, int l, int r) {
		if (end < l || start > r) {
			return -1;
		}
		if (start >= l && end <= r) {
			return segmentTree[node];
		}
		int mid = (start + end) >> 1, left = node << 1, right = left + 1;
		int p1 = rangeQuery(left, start, mid, l, r);
		int p2 = rangeQuery(right, mid + 1, end, l, r);
		if (p1 == -1) {
			return p2;
		}
		if (p2 == -1) {
			return p1;
		}
		return p1 + p2;
	}

	// segment tree point update
	private static void update(int node, int start, int end, int idx, int val) {
		if (end < idx || start > idx) {
			return;
		}
		if (start == end) {
			if (start == idx) {
				segmentTree[node] = val;
			}
			return;
		}
		int mid = (start + end) >> 1, left = node << 1, right = left + 1;
		update(left, start, mid, idx, val);
		update(right, mid + 1, end, idx, val);
		segmentTree[node] = segmentTree[left] + segmentTree[right];
	}

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
//		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("testop.txt"))));
		int tc = 1;
		while ((n = Integer.parseInt(br.readLine())) != 0) {
			if (tc > 1) {
				pw.println();
			}
			baseArray = new int[n];
			segmentTree = new int[4 * n];
			for (int i = 0; i < n; ++i) {
				baseArray[i] = Integer.parseInt(br.readLine());
			}
			buildSegmentTree(1, 0, n - 1);
			String query;
			pw.println("Case " + (tc++) + ":");
			while ((query = br.readLine()).charAt(0) != 'E') {
				String[] q = query.split(" ");
				if (q[0].charAt(0) == 'M') {
					pw.println(rangeQuery(1, 0, n - 1, Integer.parseInt(q[1]) - 1, Integer.parseInt(q[2]) - 1));
				} else {
					update(1, 0, n - 1, Integer.parseInt(q[1]) - 1, Integer.parseInt(q[2]));
				}
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
