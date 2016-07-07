package stacksandqueues;

public class Node<T> {
	T data;
	Node<T> next;
	
	public Node(){
		this.data = null;
		this.next = null;
	}
	
	public Node(T data){
		this.data = data;
		this.next = null;
	}
	
	public String toString(){
		return data+" -> "+next;
	}
}
