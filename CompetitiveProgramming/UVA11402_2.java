import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Ahoy Pirates V2 - passes well within the time constraints
 * 
 * @author arun
 *
 */
public class UVA11402_2 {
	private static int[] lazyTree;
	private static int[] segmentTree;
	private static int[] baseArray;
	private static int baseArraySize;

	private static final int F = 'F', E = 'E', I = 'I', S = 'S';

	private static boolean isUpdateQuery(int qType) {
		return qType != S;
	}

	private static final int MAX = 1024000;

	private static void buildSegmentTree(int node, int start, int end) {
		if (start == end) {
			segmentTree[node] = baseArray[start];
		} else {
			int mid = (start + end) >> 1, left = node << 1, right = left + 1;
			buildSegmentTree(left, start, mid);
			buildSegmentTree(right, mid + 1, end);
			segmentTree[node] = segmentTree[left] + segmentTree[right];
		}
	}

	private static void updateSegmentTreeNode(int node, int start, int end, int qType) {
		switch (qType) {
		case F:
			segmentTree[node] = end - start + 1;
			break;
		case E:
			segmentTree[node] = 0;
			break;
		case I:
			segmentTree[node] = end - start + 1 - segmentTree[node];
		}
	}

	private static void pushUpdateToChildren(int node, int start, int end, int qType) {
		int left = node << 1, right = left + 1;
		switch (qType) {
		case F:
			lazyTree[left] = F;
			lazyTree[right] = F;
			break;
		case E:
			lazyTree[left] = E;
			lazyTree[right] = E;
			break;
		case I:
			if (lazyTree[left] != 0) {
				switch (lazyTree[left]) {
				case F:
					lazyTree[left] = E;
					break;
				case E:
					lazyTree[left] = F;
					break;
				case I:
					lazyTree[left] = 0;
				}
			} else {
				lazyTree[left] = I;
			}
			if (lazyTree[right] != 0) {
				switch (lazyTree[right]) {
				case F:
					lazyTree[right] = E;
					break;
				case E:
					lazyTree[right] = F;
					break;
				case I:
					lazyTree[right] = 0;
				}
			} else {
				lazyTree[right] = I;
			}
		}
	}

	private static int rangeQuery(int node, int start, int end, int l, int r) {
		if (end < l || start > r) {
			return -1;
		}
		int left = node << 1, right = left + 1, mid = (start + end) >> 1;
		if (lazyTree[node] != 0) {
			updateSegmentTreeNode(node, start, end, lazyTree[node]);
			if (start != end) {
				pushUpdateToChildren(node, start, end, lazyTree[node]);
			}
			lazyTree[node] = 0;
		}
		if (start >= l && end <= r) {
			return segmentTree[node];
		}
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

	private static void updateRange(int node, int start, int end, int l, int r, int qType) {
		if (lazyTree[node] != 0) {
			updateSegmentTreeNode(node, start, end, lazyTree[node]);
			if (start != end) {
				pushUpdateToChildren(node, start, end, lazyTree[node]);
			}
			lazyTree[node] = 0;
		}
		if (end < l || start > r) {
			return;
		}
		int mid = (start + end) >> 1, left = node << 1, right = left + 1;
		if (start >= l && end <= r) {
			updateSegmentTreeNode(node, start, end, qType);
			if (start != end) {
				pushUpdateToChildren(node, start, end, qType);
			}
			return;
		}
		updateRange(left, start, mid, l, r, qType);
		updateRange(right, mid + 1, end, l, r, qType);
		segmentTree[node] = segmentTree[left] + segmentTree[right];
	}

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine()), c = 1;
		while (testcases-- > 0) {
			int m = Integer.parseInt(br.readLine());
			baseArraySize = 0;
			baseArray = new int[MAX];
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < m; ++i) {
				int repeat = Integer.parseInt(br.readLine());
				String pirateInfo = br.readLine();
				for (int j = 0; j < repeat; ++j) {
					for (int k = 0, kLen = pirateInfo.length(); k < kLen; ++k) {
						baseArray[baseArraySize++] = pirateInfo.charAt(k) - '0';
					}
				}
			}
			segmentTree = new int[baseArraySize << 2];
			lazyTree = new int[baseArraySize << 2];
			buildSegmentTree(1, 0, baseArraySize - 1);

			pw.println("Case " + (c++) + ":");
			int queryCount = Integer.parseInt(br.readLine());

			for (int i = 0, q = 0; i < queryCount; ++i) {
				String[] split = br.readLine().split(" ");
				int qType = split[0].charAt(0);
				int l = Integer.parseInt(split[1]);
				int r = Integer.parseInt(split[2]);
				if (isUpdateQuery(qType)) {
					updateRange(1, 0, baseArraySize - 1, l, r, qType);
				} else {
					pw.println("Q" + (++q) + ": " + rangeQuery(1, 0, baseArraySize - 1, l, r));
				}
			}

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
