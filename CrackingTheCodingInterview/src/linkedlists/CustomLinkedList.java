package linkedlists;

import java.util.HashSet;

public class CustomLinkedList<T extends Number & Comparable<T>> {

	Node<T> Head;
	Integer size;
	
	class Node<E extends Number & Comparable<E>>{
		Node<E> next;
		E data;
	}
	
	public CustomLinkedList(){
		this.Head = null;
		this.size = 0;
	}
	
	public CustomLinkedList(T data){
		Node<T> newNode = new Node<T>();
		newNode.data = data;
		newNode.next = null;
		this.Head = newNode;
		this.size = 1;
	}
	
	public void append(T data){
		Node<T> newNode = new Node<T>();
		newNode.data = data;
		newNode.next = null;
		
		if(this.isEmpty()){
			this.Head = newNode;
		}
		else{
		Node<T> tempHead = this.Head;
		while(tempHead.next!=null){
			tempHead = tempHead.next;
		}
		tempHead.next = newNode;
		}
		
		this.size++;
	}
	
	public void appendToHead(T data){
		Node<T> newNode = new Node<T>();
		newNode.data = data;
		newNode.next = this.Head;
		this.Head = newNode;
		this.size++;
	}
	
	public T removeFirst() throws Exception {
		if(this.isEmpty()){
			throw new Exception("Empty List");
		}
		T oldHead = this.Head.data;
		this.Head = this.Head.next;
		this.size--;
		return oldHead;
		
	}
	
	public T removeLast() throws Exception{
		if(this.size<=1){
			return removeFirst();
		}else{
			Node<T> tempHead = this.Head;
			while(tempHead.next.next!=null){
				tempHead = tempHead.next;
			}
			T removedData = tempHead.next.data;
			tempHead.next = null;
			this.size--;
			return removedData;
		}
		
	}
	
	public boolean isEmpty(){
		return size==0;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if(size!=0){
			Node<T> tempHead = this.Head;
			sb.append(tempHead.data);
			tempHead = tempHead.next;
			while(tempHead!=null){
				sb.append(", ").append(tempHead.data);
				tempHead = tempHead.next;
			}
		}
		sb.append("]");
		return sb.toString();
	}
	public Node<T> getHead(){
		return this.Head;
	}
	public void removeDupsHashSet(){
		if(this.isEmpty() || this.size==1){
			return;
		}
		
		Node<T> tempHead = this.getHead();
		HashSet<T> uniqueElements = new HashSet<T>();
		uniqueElements.add(tempHead.data);
		while(tempHead.next!=null){
			if(!uniqueElements.add(tempHead.next.data)){
				tempHead.next = tempHead.next.next;
			}else{
			tempHead = tempHead.next;
			}
		}
	}
	public void removeDupsNoBuffer(){
		if(this.isEmpty() || this.size==1){
			return;
		}
		
		Node<T> tempHead = this.getHead();
		while(tempHead!=null){
			Node<T> searchNode = tempHead;
			while(searchNode.next!=null){
				if(searchNode.next.data==tempHead.data){
					searchNode.next = searchNode.next.next;
				}
				else{
					searchNode = searchNode.next;
				}
			}
			tempHead = tempHead.next;
		}
	}
	
	public T kToLast(int k) throws Exception{
		if(this.size<k){
			throw new Exception("List has <=K elements");
		}
		k++;
		Node<T> ptr1 = this.getHead();
		Node<T> ptr2 = this.getHead();
		while(k-->0){
			ptr1 = ptr1.next;
		}
		while(ptr1!=null){
			ptr1 = ptr1.next;
			ptr2 = ptr2.next;
		}
		return ptr2.data;
	}
	
	public T kToLastRecursive(int k) throws Exception{
		if(this.size<k){
			throw new Exception("List has <=K elements");
		}
		k++;
		Node<T> kToLast = new Node<T>();
		getKToLast(this.getHead(), k, kToLast);
		return kToLast.data; 
	}
	
	private int getKToLast(Node<T> head,int k,Node<T> kToLast){
		if(head==null){
			return 0;
		}
		
		int idx = 1+getKToLast(head.next, k, kToLast);
		if(idx==k){
			kToLast.next = head.next;
			kToLast.data = head.data;
		}
		return idx;
	}
	
	public T removeMiddle(T key) throws Exception{
		Node<T> tempHead = this.getHead();
		if(this.size<3){
			if(this.size==0){
				throw new Exception("Empty List");
			}
			else{
				throw new Exception("No Middle");
			}
		}
		Node<T> nodeToRemove = null;
		while(tempHead.next!=null){
			if(tempHead.next.data==key && tempHead.next.next!=null){
				nodeToRemove = tempHead.next;
				tempHead.next = tempHead.next.next;
				break;
			}
			tempHead = tempHead.next;
		}
		return nodeToRemove!=null?nodeToRemove.data:null;
	}
	
	public void partition(T key) throws Exception{
		if(this.isEmpty()){
			throw new Exception("Empty List");
		}
		
		Node<T> tempHead = this.getHead();
		CustomLinkedList<T> newList = new CustomLinkedList<T>();
		
		while(tempHead!=null && tempHead.data.compareTo(key)<0){
			newList.append(tempHead.data);
			tempHead = tempHead.next;
		}
		this.Head = tempHead;
		if(tempHead!=null)
		{
			while(tempHead.next!=null){
				if(tempHead.next.data.compareTo(key)<0){
					newList.append(tempHead.next.data);
					tempHead.next = tempHead.next.next;
				}else{
					tempHead = tempHead.next;
				}
			}
		}
		newList.appendAll(this);
		this.Head = newList.Head;
	}
	
	public void appendAll(CustomLinkedList<T> ll){
		if(this.isEmpty()){
			this.Head = ll.getHead();
			return;
		}
		if(ll.isEmpty()){
			return;
		}
		
		Node<T> tempHead = this.getHead();
		
		while(tempHead.next!=null){
			tempHead=tempHead.next;
		}
		
		tempHead.next = ll.getHead();
	}
	//assuming Integers
	public int sumListsReverse(CustomLinkedList<T> ll){
		int exponent = 1, result=0;
		
		Node<T> h1 = this.getHead();
		Node<T> h2 = ll.getHead();
		while(h1!=null && h2!=null){
			result = result + ((h1.data.intValue()+h2.data.intValue())*exponent);
			exponent*=10;
			h1 = h1.next;
			h2 = h2.next;
		}
		while(h1!=null){
			result = result + (h1.data.intValue()*exponent);
			exponent*=10;
			h1 = h1.next;
		}
		while(h2!=null){
			result = result + (h2.data.intValue()*exponent);
			exponent*=10;
			h2 = h2.next;
		}
		
		return result;
	}
	
	//assuming Integers
	public int sumListsForward(CustomLinkedList<T> ll){
		int result1 = 0, result2 = 0;
		Node<T> h1 = this.getHead();
		Node<T> h2 = ll.getHead();
		while(h1!=null){
			result1 = (result1*10)+h1.data.intValue();
			h1 = h1.next;
		}
		while(h2!=null){
			result2 = (result2*10)+h2.data.intValue();
			h2 = h2.next;
		}
		return result1+result2;
		
	}
	
	//linked list palindrome check - 1 pass recursive
	private static final int boolIdx = 1, nodeIdx = 0;
	public boolean isPalindromeOnePass(){
		Node<T> h1 = this.getHead();
		Node<T> h2 = this.getHead();
		Object[] result = palCheck(h1, h2);
		return (Boolean)result[boolIdx];
	}
	private Object[] palCheck(Node<T> h1, Node<T> h2){
		if(h1==null){
			return new Object[]{h2, new Boolean(true)};
		}
		Object[] result = palCheck(h1.next, h2);
		result[boolIdx] = ((Boolean)result[boolIdx]) && ((h1.data.compareTo(((Node<T>)result[nodeIdx]).data))==0);
		result[nodeIdx] = ((Node<T>)result[nodeIdx]).next;
		return result;
	}
	//linked list palindrome check - two pass
	public boolean isPalindromeTwoPass(){
		int size = 0; //assuming size is not given
		CustomLinkedList ll = new CustomLinkedList();
		Node<T> tempHead = this.getHead();
		while(tempHead!=null){
			ll.appendToHead(tempHead.data);
			tempHead = tempHead.next;
			size++;
		}
		size>>=1;
		tempHead = this.getHead();
		Node<T> tempHead2 = ll.getHead();
		while(size-->0){
			if(tempHead.data.compareTo(tempHead2.data)!=0){
				return false;
			}
			tempHead = tempHead.next;
			tempHead2 = tempHead2.next;
		}
		return true;
	}
	public static void main(String[] args) throws Exception{
		CustomLinkedList<Integer> ll = new CustomLinkedList<Integer>();
		ll.append(1);
		ll.append(2);
		ll.append(3);
		ll.append(2);
		ll.append(1);
		System.out.println(ll.isPalindromeTwoPass());
	}
}
