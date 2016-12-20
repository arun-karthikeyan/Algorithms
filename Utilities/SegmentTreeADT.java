import java.lang.reflect.Array;

public abstract class SegmentTreeADT<E> {
	private E[] segmentTree;
	private E[] baseArray;
	@SuppressWarnings("unchecked")
	public SegmentTreeADT(Class<E> typeInfo, E[] baseArray, int size){
		segmentTree = (E[])Array.newInstance(typeInfo, 4*size);
		this.baseArray = baseArray; //holding a reference to baseArray for local usage
		build(1, 0, size-1);
	}
	
	private void build(int node, int start, int end){
		if(start==end){
			copySingle(segmentTree[node], this.baseArray[start]);
			return;
		}
		int mid = (start+end)>>1;
		int left = node>>1, right = left+1;
		build(left, start, mid);
		build(right, mid+1, end);
		combine(segmentTree[node], segmentTree[left], segmentTree[right]);
	}
	
	abstract void copySingle(E segTreeNode, E baseTreeNode);
	abstract void combine(E parentNode, E leftChild, E rightChild);
	
}
