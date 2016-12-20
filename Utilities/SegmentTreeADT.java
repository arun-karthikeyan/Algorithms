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
abstract class SegmentTreeADT<B, S, L extends Lazy> {
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
		createSegTree(4 * size);
		createLazyTree(4 * size);
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
		initSegNode(node);
		initLazyNode(node);
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

		if (this.lazyTree[node].isPendingUpdate()) {
			// take care of the update first
			lazyUpdateSegTree(node);
			if (start != end) { // non-leaf case
				// mark left and right child as lazy
				markChildrenLazy(node);
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
		return merge(p1, p2);
	}

	public void updateRange(int node, int start, int end, int l, int r, B val) {
		if (this.lazyTree[node].isPendingUpdate()) {
			// update node first
			lazyUpdateSegTree(node);
			if(start!=end){//non-leaf case
				//mark left and right child as lazy
				markChildrenLazy(node);
			}
			//mark node as updated
			this.lazyTree[node].setPendingUpdate(false);
		}
		
		if(start>r || end<l){
			return; //completely out of bounds case
		}
		
		if(l>=start && r<=end){
			//completely in bounds case
			updateRangeWithVal(node, start, end, l, r, val);
		}
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
	 * updates segment tree at index idx
	 * 
	 * @param idx
	 */
	abstract void lazyUpdateSegTree(int idx);

	/**
	 * mark the children of this parent node as lazy and pass on any query
	 * parameters to the children as required
	 * 
	 * @param parent
	 *            the parent from which the lazy update has to be pushed to the
	 *            children
	 */
	abstract void markChildrenLazy(int parent);
	
	/**
	 * updates the current range with val
	 * @param node segmentTreeIndex, lazyTreeIndex
	 * @param start start index of this range
	 * @param end end index of this range
	 * @param l start index of this query
	 * @param r end index of this query
	 * @param val value to update the SegmentTree with
	 */
	abstract void updateRangeWithVal(int node, int start, int end, int l, int r, B val);
}

abstract class Lazy {
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