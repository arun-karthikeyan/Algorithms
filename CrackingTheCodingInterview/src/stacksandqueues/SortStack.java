package stacksandqueues;

import java.util.Stack;

public class SortStack<T extends Comparable<T>> extends Stack<T> {
	/**
	 * A stack which always has its elements sorted such that the smallest elements are in the top
	 */
	private static final long serialVersionUID = 1L;
	Stack<T> tempStack;
	
	public SortStack(){
		super();
		tempStack = new Stack<T>();
	}
	
	public T push(T data){
		while(!super.isEmpty() && super.peek().compareTo(data)<0){
			tempStack.push(super.pop());
		}
		T returnData = super.push(data);
		while(!tempStack.isEmpty()){
			super.push(tempStack.pop());
		}
		return returnData;
	}
	
	public static void main(String[] args) throws Exception{
		SortStack<Integer> ss = new SortStack<Integer>();
		ss.push(10);
		ss.push(11);
		ss.push(5);
		ss.push(12);
		ss.push(18);
		ss.push(0);
		System.out.println(ss);
		System.out.println(ss.pop());
	}
}
