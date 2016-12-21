import java.io.OutputStreamWriter;
import java.io.PrintWriter;


public class HelperMethod {
	/**
	 * Abstract Class API for the Segment Tree Data Structure with Lazy Propagation
	 * 
	 * @author arun
	 *
	 * @param <S>
	 *            SegmentTree Node
	 * @param <L>
	 *            Lazy Node
	 */
	static abstract class SegmentTreeADT<B, S, L> {
		protected S[] segmentTree;
		protected B[] baseArray;
		protected L[] lazyTree;

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
		protected SegmentTreeADT(B[] baseArray, int size) {
			createSegTree(4*size);
			createLazyTree(4*size);
			this.baseArray = baseArray; // holding a reference to baseArray for
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
			initSegNode(node); initLazyNode(node);
			this.segmentTree[node] = merge(this.segmentTree[left], this.segmentTree[right]);
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
			return rangeQuery(1, 0, this.baseArray.length - 1, l, r);
		}

		private S rangeQuery(int node, int start, int end, int l, int r) {
			if (end < l || start > r) {
				// completely out of bounds case
				return null;
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
			return merge(p1, p2);
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
		abstract S merge(S p1, S p2);
		
		abstract void createSegTree(int size);
		abstract void createLazyTree(int size);
		abstract void initSegNode(int idx);
		abstract void initLazyNode(int idx);
	}

	static class SegNode implements Comparable<SegNode> {
		int val;

		public SegNode() {
			this.val = 0;
		}

		public SegNode(int val) {
			this.val = val;
		}
		
		public String toString(){
			return this.val+"";
		}

		@Override
		public int compareTo(SegNode arg0) {
			// TODO Auto-generated method stub
			return this.val-arg0.val;
		}
	}

	static class LazyNode {
		int l, r, val;

		public LazyNode() {
			this.val = 0;
			this.l = this.r = -1;
		}

		public LazyNode(int l, int r, int val) {
			this.val = val;
			this.l = l;
			this.r = r;
		}
	}

	static class SegmentTree extends SegmentTreeADT<Integer, SegNode, LazyNode> {

		public SegmentTree(Integer[] baseArray, int size) {
			super(baseArray, size);
			// TODO Auto-generated constructor stub
		}

		@Override
		void make(int segTreeIdx, int baseArrayIdx) {
			segmentTree[segTreeIdx].val = baseArray[baseArrayIdx];

		}

		@Override
		SegNode merge(SegNode p1, SegNode p2) {
			// TODO Auto-generated method stub
			return p1.compareTo(p2)<=0?p1:p2;
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
	}

	public static void main(String[] args) throws Exception {
		
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out), true);
		SegmentTree st = new SegmentTree(new Integer[] { 18, 17, 13, 19, 15, 11, 20 }, 7);
		pw.println("RMQ of 1-3 : " + (st.rangeQuery(1, 3)));
		pw.println("RMQ of 4-6 : " + (st.rangeQuery(4, 6)));
		pw.println("RMQ of 3-4 : " + (st.rangeQuery(3, 4)));

		pw.flush();
		pw.close();
	}
}
