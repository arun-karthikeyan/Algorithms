package treesandgraphs;

import java.util.ArrayList;
//max-heap
public class BinaryMaxHeap<T extends Comparable<T>> {
	ArrayList<HeapNode<T>> heap;
	
	class HeapNode<E extends Comparable<E>>{
		E value;
		public HeapNode(E data){
			this.value = data;
		}
		public String toString(){
			return value.toString();
		}
	}
	
	public BinaryMaxHeap(){
		this.heap = new ArrayList<HeapNode<T>>();
		this.heap.add(null); //1 indexed
	}
	public BinaryMaxHeap(int size){
		this.heap = new ArrayList<HeapNode<T>>(size+1);
		this.heap.add(null); //1 indexed
	}

	public BinaryMaxHeap(ArrayList<T> array){
		this(array.size()+1);
		for(int i=0,iLen=array.size();i<iLen;++i){
			this.heap.add(new HeapNode<T>(array.get(i)));
		}
		//heapify
		for(int i=this.heap.size(), j=(i-1)/2; j>=1; --j){
			bubbleDown(this.heap.get(j).value, j, i);
		}
	}
	
	public void insert(T data){
		int idx = this.heap.size();
		this.heap.add(new HeapNode<T>(data));
//		bubble up until it comes to root
		while(((idx/2)>0) && (this.heap.get(idx/2).value.compareTo(data)<0)){
			this.heap.get(idx).value = this.heap.get(idx/2).value;
			this.heap.get(idx/2).value = data;
			idx>>=1;
		}
	}
	
	public T remove() throws Exception{
		if(isEmpty()){
			throw new Exception("Empty Heap Exception");
		}
		int idx = 1, lastIdx = this.heap.size()-1;
		T dataToReturn = this.heap.get(idx).value;
		//shift, bubble down and remove
		this.heap.get(idx).value = this.heap.get(lastIdx).value;
		bubbleDown(this.heap.get(lastIdx).value,idx,lastIdx);
		this.heap.remove(lastIdx);
		return dataToReturn;
	}
	
	private void bubbleDown(T value, int from, int to){
		while(2*from<to){
			int oldIdx = from;
			from<<=1;
			HeapNode<T> child = this.heap.get(from);
			if((from+1)<to && child.value.compareTo(this.heap.get(from+1).value)<0){
				child = this.heap.get(++from);
			}
			if(this.heap.get(oldIdx).value.compareTo(child.value)<0){
				this.heap.get(oldIdx).value = child.value;
				child.value = value;
			}else{
				break;
			}
		}
	}
	public boolean isEmpty(){
		return this.heap.size()==1;
	}
	
	public String toString(){
		return this.heap.toString();
	}
	
	public static void main(String[] args) throws Exception{
		BinaryMaxHeap<Integer> bmh = new BinaryMaxHeap<Integer>();
		bmh.insert(104);
		bmh.insert(71);
		bmh.insert(24);
		bmh.insert(66);
		bmh.insert(27);
		bmh.insert(23);
		bmh.insert(8);
		bmh.insert(5);
		bmh.insert(32);
		bmh.insert(25);
		bmh.insert(18);
		bmh.insert(22);
		System.out.println(bmh);
		bmh.insert(50);
//		System.out.println("Extracting Max : "+bmh.remove());
		System.out.println(bmh);
		ArrayList<Integer> value = new ArrayList<Integer>();
		value.add(1);
		value.add(2);
		value.add(3);
		value.add(4);
		value.add(5);
		BinaryMaxHeap<Integer> heapify = new BinaryMaxHeap<Integer>(value);
		System.out.println(heapify);
		
	}
	
}
