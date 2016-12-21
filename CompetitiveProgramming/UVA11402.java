import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Ahoy, Pirates! - less optimized version, doesn't pass within the time
 * constraints, same approach without all the OOProgramming works
 * 
 * @author arun
 *
 */
public class UVA11402 {
	private static final char BUCCANEER = '1';
	private static final char BARBARY = '0';
	private static final int F = 'F', E = 'E', I = 'I', S = 'S';

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine()), c = 1;
		;
		while (testcases-- > 0) {
			int m = Integer.parseInt(br.readLine());
			int totalSize = 0;
			int[] repeats = new int[m];
			String[] pirateInfo = new String[m];
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < m; ++i) {
				repeats[i] = Integer.parseInt(br.readLine());
				pirateInfo[i] = br.readLine();
				for (int j = 0; j < repeats[i]; ++j) {
					sb.append(pirateInfo[i]);
				}
				totalSize += (repeats[i] * pirateInfo[i].length());
			}

			pw.println("Case " + (c++) + ":");
			SegmentTree segmentTree = new SegmentTree(sb, totalSize);
			int queryCount = Integer.parseInt(br.readLine());

			for (int i = 0, q = 0; i < queryCount; ++i) {
				// StringTokenizer st = new StringTokenizer(br.readLine());
				String[] split = br.readLine().split(" ");
				int qType = split[0].charAt(0);
				int l = Integer.parseInt(split[1]);
				int r = Integer.parseInt(split[2]);
				if (isUpdateQuery(qType)) {
					segmentTree.updateRange(1, qType, 0, totalSize - 1, l, r);
				} else {
					pw.println("Q" + (++q) + ": " + segmentTree.rangeQuery(l, r).sum);
				}
			}

		}

		br.close();
		pw.flush();
		pw.close();
	}

	private static boolean isUpdateQuery(int qType) {
		return qType != S;
	}

	static class SegNode {
		int sum;

		public SegNode() {
			this.sum = 0;
		}

		public SegNode(int sum) {
			this.sum = sum;
		}

		public String toString() {
			return String.valueOf(this.sum);
		}
	}

	static class LazyNode extends Lazy {
		int qType;
	}

	static class SegmentTree extends SegmentTreeADT<StringBuilder, SegNode, LazyNode> {

		protected SegmentTree(StringBuilder baseArray, int size) {
			super(baseArray, size);
			// TODO Auto-generated constructor stub
		}

		@Override
		void make(int segTreeIdx, int baseArrayIdx) {
			// TODO Auto-generated method stub
			segmentTree[segTreeIdx].sum = baseArray.charAt(baseArrayIdx) - '0';
		}

		@Override
		void createSegTree(int size) {
			// TODO Auto-generated method stub
			segmentTree = new SegNode[size];
		}

		@Override
		void createLazyTree(int size) {
			// TODO Auto-generated method stub
			lazyTree = new LazyNode[size];

		}

		@Override
		void initSegNode(int idx) {
			// TODO Auto-generated method stub
			segmentTree[idx] = new SegNode();

		}

		@Override
		void initLazyNode(int idx) {
			// TODO Auto-generated method stub
			lazyTree[idx] = new LazyNode();

		}

		@Override
		void finishPendingUpdate(int idx, int start, int end, int l, int r) {
			// TODO Auto-generated method stub
			switch (lazyTree[idx].qType) {
			case F:
				segmentTree[idx].sum = end - start + 1;
				break;
			case E:
				segmentTree[idx].sum = 0;
				break;
			case I:
				segmentTree[idx].sum = end - start + 1 - segmentTree[idx].sum;
				break;
			}

		}

		@Override
		void moveLazyUpdateToChildren(int node, int start, int end, int l, int r) {
			// TODO Auto-generated method stub
			int left = node << 1, right = left + 1;
			if (lazyTree[left].isPendingUpdate() && lazyTree[node].qType == I) {
				switch (lazyTree[left].qType) {
				case F:
					lazyTree[left].qType = E;
					break;
				case E:
					lazyTree[left].qType = F;
					break;
				case I:
					lazyTree[left].setPendingUpdate(false);
				}
			} else {
				lazyTree[left].qType = lazyTree[node].qType;
				lazyTree[left].setPendingUpdate(true);
			}

			if (lazyTree[right].isPendingUpdate() && lazyTree[node].qType == I) {
				switch (lazyTree[right].qType) {
				case F:
					lazyTree[right].qType = E;
					break;
				case E:
					lazyTree[right].qType = F;
					break;
				case I:
					lazyTree[right].setPendingUpdate(false);
				}
			} else {
				lazyTree[right].qType = lazyTree[node].qType;
				lazyTree[right].setPendingUpdate(true);
			}
		}

		@Override
		SegNode mergeValue(SegNode p1, SegNode p2) {
			// TODO Auto-generated method stub
			return new SegNode(p1.sum + p2.sum);
		}

		@Override
		void mergeSegmentTreeNode(int node, int left, int right) {
			// TODO Auto-generated method stub
			segmentTree[node].sum = segmentTree[left].sum + segmentTree[right].sum;
		}

		@Override
		void updateRangeWithVal(int node, int qType, int start, int end, int l, int r) {
			// TODO Auto-generated method stub
			switch (qType) {
			case F:
				segmentTree[node].sum = (end - start + 1);
				break;
			case E:
				segmentTree[node].sum = 0;
				break;
			case I:
				segmentTree[node].sum = end - start + 1 - segmentTree[node].sum;
				break;
			}

		}

		@Override
		protected void moveUpdateToChildren(int node, int qType, int start, int end, int l, int r) {
			// TODO Auto-generated method stub
			int left = node << 1, right = left + 1;

			if (lazyTree[left].isPendingUpdate() && qType == I) {
				switch (lazyTree[left].qType) {
				case F:
					lazyTree[left].qType = E;
					break;
				case E:
					lazyTree[left].qType = F;
					break;
				case I:
					lazyTree[left].setPendingUpdate(false);
				}
			} else {
				lazyTree[left].qType = qType;
				lazyTree[left].setPendingUpdate(true);
			}

			if (lazyTree[right].isPendingUpdate() && qType == I) {
				switch (lazyTree[right].qType) {
				case F:
					lazyTree[right].qType = E;
					break;
				case E:
					lazyTree[right].qType = F;
					break;
				case I:
					lazyTree[right].setPendingUpdate(false);
				}
			} else {
				lazyTree[right].qType = qType;
				lazyTree[right].setPendingUpdate(true);
			}

		}

	}

	static abstract class SegmentTreeADT<B, S, L extends Lazy> {
		protected S[] segmentTree;
		protected B baseArray;
		protected L[] lazyTree;
		protected int baseArraySize;

		/**
		 * Initializes and Builds a segment tree with the given baseArray
		 * 
		 * @param segNodeInfo
		 *            Class type of SegmentTree Node
		 * @param baseArray
		 *            The Array on top of which the segment tree has to be built
		 * @param size
		 *            specifies the size of the base array
		 */
		protected SegmentTreeADT(B baseArray, int size) {
			createSegTree(4 * size);
			createLazyTree(4 * size);
			this.baseArray = baseArray;
			this.baseArraySize = size;// holding a reference to baseArray for
										// local usage
			build(1, 0, size - 1);
		}

		/**
		 * The build method constructs a segment tree with base array
		 * 
		 * @param node
		 * @param start
		 * @param end
		 */
		private void build(int node, int start, int end) {
			if (start == end) {
				initSegNode(node);
				initLazyNode(node);
				make(node, start);
				return;
			}
			int mid = (start + end) >> 1;
			int left = node << 1, right = left + 1;
			build(left, start, mid);
			build(right, mid + 1, end);
			initSegNode(node);
			initLazyNode(node);
			mergeSegmentTreeNode(node, left, right);
		}

		/**
		 * returns the result of the range query in the range [l,r]
		 * 
		 * @param l
		 *            left most index of the range (inclusive)
		 * @param r
		 *            right most index of the range (inclusive)
		 * @return the result of the range query
		 */
		public S rangeQuery(int l, int r) {
			return rangeQuery(1, 0, this.baseArraySize - 1, l, r);
		}

		private S rangeQuery(int node, int start, int end, int l, int r) {
			if (end < l || start > r) {
				// completely out of bounds case
				return null;
			}

			if (this.lazyTree[node].isPendingUpdate()) {
				// take care of the update first
				finishPendingUpdate(node, start, end, l, r);
				if (start != end) { // non-leaf case
					// mark left and right child as lazy
					moveLazyUpdateToChildren(node, start, end, l, r);
				}
				// mark node as updated
				this.lazyTree[node].setPendingUpdate(false);
			}

			if (start >= l && end <= r) {
				// completely in bounds case
				return this.segmentTree[node];
			}
			// partially in bounds case
			int left = node << 1, right = left + 1, mid = (start + end) >> 1;
			S p1 = rangeQuery(left, start, mid, l, r);
			S p2 = rangeQuery(right, mid + 1, end, l, r);
			if (p1 == null) {
				return p2;
			}
			if (p2 == null) {
				return p1;
			}
			return mergeValue(p1, p2);
		}

		/**
		 * Range lazy-update segment tree
		 * 
		 * @param node
		 *            node to be updated
		 * @param start
		 *            start index of the node
		 * @param end
		 *            end index of the node
		 * @param l
		 *            start index of the query
		 * @param r
		 *            end index of the query
		 * @param val
		 *            update value
		 */

		public void updateRange(int node, int qType, int start, int end, int l, int r) {
			if (this.lazyTree[node].isPendingUpdate()) {
				// update node first
				finishPendingUpdate(node, start, end, l, r);
				if (start != end) {// non-leaf case
					// mark left and right child as lazy
					moveLazyUpdateToChildren(node, start, end, l, r);
					// this.lazyTree[node << 1].setPendingUpdate(true);
					// this.lazyTree[(node << 1) + 1].setPendingUpdate(true);
				}
				// mark node as updated
				this.lazyTree[node].setPendingUpdate(false);
			}

			if (start > r || end < l) {
				return; // completely out of bounds case
			}

			if (start >= l && end <= r) {
				// completely in bounds case
				updateRangeWithVal(node, qType, start, end, l, r);
				if (start != end) {// non-leaf case
					// lazily push update of children to a later time
					moveUpdateToChildren(node, qType, start, end, l, r);
				}
				return;
			}

			int mid = (start + end) >> 1, left = node << 1, right = left + 1;
			updateRange(left, qType, start, mid, l, r);
			updateRange(right, qType, mid + 1, end, l, r);
			mergeSegmentTreeNode(node, left, right);
		}

		/**
		 * method to make a segmentTreeNode indexed at segTreeIdx from baseArray
		 * indexed at baseArrayIdx
		 * 
		 * @param segTreeIdx
		 *            segmentTree Index
		 * @param baseArrayIdx
		 *            baseArray Index
		 */
		abstract void make(int segTreeIdx, int baseArrayIdx);

		/**
		 * merges the value of p1 and p2 and returns the result
		 * 
		 * @param p1
		 * @param p2
		 * @return
		 */
		abstract S mergeValue(S p1, S p2);

		/**
		 * merges the value of the segmentTreeNode at index left and right and
		 * saves the result in node
		 * 
		 * @param node
		 *            destination of merge
		 * @param left
		 *            left child
		 * @param right
		 *            right child
		 */
		abstract void mergeSegmentTreeNode(int node, int left, int right);

		/**
		 * defines the segmentTree array
		 * 
		 * @param size
		 */
		abstract void createSegTree(int size);

		/**
		 * defines the lazyTree array
		 * 
		 * @param size
		 */
		abstract void createLazyTree(int size);

		/**
		 * initializes the segmentTree at position idx
		 * 
		 * @param idx
		 *            index of the segmentTree array to be initialized
		 */
		abstract void initSegNode(int idx);

		/**
		 * initializes the lazyTree at position idx
		 * 
		 * @param idx
		 *            index of the lazyTree array to be initialized
		 */
		abstract void initLazyNode(int idx);

		/**
		 * updates segment tree at index node
		 * 
		 * @param node
		 */
		abstract void finishPendingUpdate(int node, int start, int end, int l, int r);

		/**
		 * mark the children of this parent node as lazy and pass on any query
		 * parameters to the children as required
		 * 
		 * @param node
		 *            the parent from which the lazy update has to be pushed to
		 *            the children
		 */
		abstract void moveLazyUpdateToChildren(int node, int start, int end, int l, int r);

		/**
		 * updates the current range with val
		 * 
		 * @param node
		 *            segmentTreeIndex, lazyTreeIndex
		 * @param qType
		 *            query type
		 * @param start
		 *            start index of this range
		 * @param end
		 *            end index of this range
		 * @param l
		 *            start index of this query
		 * @param r
		 *            end index of this query
		 * @param val
		 *            value to update the SegmentTree with
		 */
		abstract void updateRangeWithVal(int node, int qType, int start, int end, int l, int r);

		/**
		 * moves the query update to children so that the children can be
		 * updated lazily at a later time
		 * 
		 * @param node
		 *            the parent (segmentTree index & lazyTree index) whose
		 *            children have to be marked for lazy update
		 * @param qType
		 *            query type
		 * @param start
		 *            start index of this range
		 * @param end
		 *            end index of this range
		 * @param l
		 *            start index of this query
		 * @param r
		 *            end index of this query
		 * @param val
		 *            value to mark the children for lazy update with
		 */
		protected abstract void moveUpdateToChildren(int node, int qtype, int start, int end, int l, int r);
	}

	static abstract class Lazy {
		private boolean pendingUpdate;

		protected Lazy() {
			this.setPendingUpdate(false);
		}

		public boolean isPendingUpdate() {
			return pendingUpdate;
		}

		public void setPendingUpdate(boolean pendingUpdate) {
			this.pendingUpdate = pendingUpdate;
		}
	}
}
