package stacksandqueues;

import java.util.EmptyStackException;

public class CustomStack<T> extends StackADT<T> {

	Node<T> top;
	Integer size;
	
	public CustomStack(){
		this.top = null;
		this.size = 0;
	}
	
	public CustomStack(T data){
		this.top = new Node<T>(data);
		this.size=1;
	}
	
	@Override
	public void push(T data) {
		Node<T> newTop = new Node<T>(data);
		newTop.next = this.top;
		this.top = newTop;
		this.size++;
	}

	@Override
	public T pop() {
		if(this.isEmpty()){
			throw new EmptyStackException();
		}
		Node<T> oldTop = this.top;
		this.top = top.next;
		this.size--;
		return oldTop.data;
	}

	@Override
	public T peek() {
		if(this.isEmpty()){
			throw new EmptyStackException();
		}
		return this.top.data;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size==0;
	}
	public String toString(){
		return top.toString();
	}
	public int size(){
		return this.size;
	}
	
	public static void main(String[] args) throws Exception{
		CustomStack<Integer> q = new CustomStack<Integer>();
		q.push(1);
		q.push(2);
		q.push(3);
		System.out.println(q);
		System.out.println("removing top : "+q.pop());
		q.push(4);
		q.push(5);
		System.out.println(q);
		System.out.println("peeking current top : "+q.peek());
		System.out.println(q);
		
	}

}
