package stacksandqueues;

import java.util.EmptyStackException;

public class StackMin<T extends Comparable<T>> extends CustomStack<T> {
	CustomStack<T> minStack;
	public StackMin(){
		super();
		minStack = new CustomStack<T>();
	}
	
	public StackMin(T data){
		super(data);
		minStack = new CustomStack<T>(data);
	}
	
	public void push(T data){
		super.push(data);
		
		if(minStack.isEmpty() || data.compareTo(minStack.peek())<=0){
			minStack.push(data);
		}
	}
	
	public T pop(){
		T popData = super.pop();
		if(popData.compareTo(minStack.peek())==0){
			minStack.pop();
		}
		return popData;
	}
	
	public T min(){
		if(super.isEmpty()){
			throw new EmptyStackException();
		}
		
		return minStack.peek();
	}
	
	public String toString(){
		if(minStack.isEmpty())
		{
			return super.toString()+" | min: "+null;
		}
		
		return super.toString()+" | min: "+minStack.peek();
	}
	
	public static void main(String[] args) throws Exception{
		StackMin<Integer> sm = new StackMin<Integer>();
		sm.push(5);
		sm.push(4);
		sm.push(10);
		sm.push(2);
		System.out.println(sm);
		sm.pop();
		sm.pop();
		sm.pop();
		sm.push(1);
		System.out.println(sm);
	}
}
