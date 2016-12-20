import java.lang.reflect.Array;

abstract class SegmentTreeADT<E> {
	protected E[] segmentTree;
	protected E[] baseArray;

	/**
	 * Initializes and Builds a segment tree with the given baseArray
	 * 
	 * @param typeInfo
	 *            Class type of SegmentTree Node
	 * @param baseArray
	 *            The Array on top of which the segment tree has to be built
	 * @param size
	 *            specifies the size of the base array
	 */
	@SuppressWarnings("unchecked")
	public SegmentTreeADT(Class<E> typeInfo, E[] baseArray, int size) {
		segmentTree = (E[]) Array.newInstance(typeInfo, 4 * size);
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
			copy(node, start);
			return;
		}
		int mid = (start + end) >> 1;
		int left = node << 1, right = left + 1;
		build(left, start, mid);
		build(right, mid + 1, end);
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
	public E rangeQuery(int l, int r) {
		return rangeQuery(1, 0, this.baseArray.length - 1, l, r);
	}

	private E rangeQuery(int node, int start, int end, int l, int r) {
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
		E p1 = rangeQuery(left, start, mid, l, r);
		E p2 = rangeQuery(right, mid + 1, end, l, r);
		if (p1 == null) {
			return p2;
		}
		if (p2 == null) {
			return p1;
		}
		return merge(p1, p2);
	}

	/**
	 * method to deep copy the contents of super.baseArray[baseArrayIdx] to
	 * super.segmentTree[segTreeIdx]
	 * 
	 * @param segTreeIdx
	 *            segmentTree Index to which the values have to be copied into
	 * @param baseArrayIdx
	 *            baseArray Index from which the values have to be copied from
	 */
	abstract void copy(int segTreeIdx, int baseArrayIdx);

	/**
	 * merges the value of p1 and p2 and returns the result
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	abstract E merge(E p1, E p2);
}