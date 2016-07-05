package linkedlists;

import java.util.HashSet;

public class CustomLinkedList<T> {

	Node<T> Head;
	Integer size;
	
	class Node<E>{
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
	public static void main(String[] args) throws Exception{
		CustomLinkedList<Integer> ll = new CustomLinkedList<Integer>();
		ll.append(1);
		ll.append(1);
		ll.append(1);
		System.out.println(ll);
	}
}
