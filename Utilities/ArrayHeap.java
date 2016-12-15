import java.util.ArrayList;
import java.util.Comparator;

public class ArrayHeap<E extends Comparable<E>> {
	ArrayList<E> heap;
	Comparator<E> comp;
	private static final int MINHEAP = 0;
	private static final int MAXHEAP = 1;

	private final Comparator<E> ascendingComparator = new java.util.Comparator<E>() {
		@Override
		public int compare(E o1, E o2) {
			return o1.compareTo(o2);
		}
	};

	public ArrayHeap(int heapType, int initialCapacity) {
		this.heap = new ArrayList<E>(initialCapacity);
		this.comp = heapType == ArrayHeap.MINHEAP ? this.ascendingComparator : this.ascendingComparator.reversed();
	}

	public ArrayHeap(int heapType) {
		this(heapType, 10);
	}

	public ArrayHeap(int initialCapacity, Comparator<E> customComparator) {
		this.heap = new ArrayList<E>(initialCapacity);
		this.comp = customComparator;
	}

	public ArrayHeap(Comparator<E> customComparator) {
		this(10, customComparator);
	}

	public E peek() {
		if (!heap.isEmpty()) {
			return heap.get(0);
		}
		return null;
	}

	public E remove() {
		if (!heap.isEmpty()) {
			E toReturn = peek();
			if (heap.size() > 1) {
				heap.set(0, heap.remove(heap.size() - 1));
				siftDown();
			} else {
				heap.remove(0);
			}
			return toReturn;
		}
		return null;
	}

	public void add(E val) {
		heap.add(val);
		bubbleUp();
	}

	private void bubbleUp() {
		int idx = heap.size() - 1;
		while (idx > 0) {
			int parent = ((idx - 1) / 2);
			int compareWithParent = this.comp.compare(heap.get(idx), heap.get(parent));
			if (compareWithParent < 0) {
				swap(idx, parent);
				idx = parent;
			} else {
				break;
			}
		}
	}

	private void swap(int idx1, int idx2) {
		E temp = heap.get(idx1);
		heap.set(idx1, heap.get(idx2));
		heap.set(idx2, temp);
	}

	private void siftDown() {
		int idx = 0, size = heap.size(), lastParent = (heap.size()-1)/2;
		while (idx<lastParent) {
			int lChild = idx * 2 + 1;
			int rChild = lChild + 1;
			E idxElement = heap.get(idx), lChildElement = heap.get(lChild);
			if (rChild < size) {
				E rChildElement = heap.get(rChild);
				int leftRightCompare = this.comp.compare(lChildElement, rChildElement);
				if (leftRightCompare < 0) {
					int leftCompare = this.comp.compare(idxElement, lChildElement);
					if (leftCompare > 0) {
						swap(idx, lChild);
						idx = lChild;
					} else {
						break;
					}
				} else {
					int rightCompare = this.comp.compare(idxElement, rChildElement);
					if (rightCompare > 0) {
						swap(idx, rChild);
						idx = rChild;
					} else {
						break;
					}
				}
			} else {
				int leftCompare = this.comp.compare(idxElement, lChildElement);
				if (leftCompare > 0) {
					swap(idx, lChild);
					idx = lChild;
				} else {
					break;
				}
			}
		}
	}
	
	public int size(){
		return this.heap.size();
	}
	public String toString(){
		return this.heap.toString();
	}
	public boolean isEmpty(){
		return this.heap.isEmpty();
	}
	public static void main(String[] args){
		ArrayHeap<Integer> minHeap = new ArrayHeap<>(ArrayHeap.MAXHEAP);
		int N = (int)1e6;
		int MAX = N*100;
		int elements[] = new int[N];
		for(int i=0; i<N; ++i){
//			elements[i] = i;
			elements[i] = (int)(Math.random()*MAX);
		}
		long start = System.currentTimeMillis();
		for(int i=0; i<N; ++i){
			minHeap.add(elements[i]);
		}
		long end = System.currentTimeMillis();
//		System.out.println("Heap : "+minHeap);
		System.out.println("Heap size : "+minHeap.size());
		System.out.println("Total time to add : "+((end-start)/1000d));
		start = System.currentTimeMillis();
		while(!minHeap.isEmpty()){
//			System.out.print(minHeap.remove()+" ");
			minHeap.remove();
		}
		end = System.currentTimeMillis();
		System.out.println("\nTotal time to remove : "+((end-start)/1000d));
	}
}
