package stacksandqueues;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

public class StackOfPlates<T> {
	int threshold;
	LinkedList<Stack<T>> stackList;
	public StackOfPlates(int threshold) throws Exception{
		if(threshold<1){
			throw new Exception("insufficient threshold");
		}
		this.threshold = threshold;
		stackList = new LinkedList<Stack<T>>();
	}
	//according to the implementation, no empty stack shall reside in the stackList at any time
	public void push(T data){
		//check if stackList is empty
		if(stackList.isEmpty()){
			//append a new Empty Stack
			stackList.add(new Stack<T>());
		}
		//new data has to be pushed in the tail stack
		Stack<T> tailStack = stackList.peekLast();
		if(tailStack.size()<threshold){
			tailStack.push(data);
		}else{
			tailStack = new Stack<T>();
			tailStack.push(data);
			stackList.add(tailStack);
		}
	}
	public T pop(){
		if(stackList.isEmpty()){
			throw new EmptyStackException();
		}
		Stack<T> tailStack = stackList.peekLast();
		if(tailStack.size()==1){
			stackList.removeLast();
		}
		return tailStack.pop();
	}
	
	public T peek(){
		if(stackList.isEmpty()){
			throw new EmptyStackException();
		}
		return stackList.peekLast().peek();
	}
	
	//make sure all stacks except the tailStack are always full
	public T popAt(int idx){
		if(stackList.isEmpty()){
			throw new EmptyStackException();
		}
		
		if(idx<1 || idx>stackList.size()){
			throw new NoSuchElementException();
		}
		
		Stack<T> idxStack = stackList.get(idx-1);
		T dataToReturn = idxStack.pop();
		
		if(idxStack.size()==0){
			stackList.remove(idxStack);
		}else{
			//shifting elements from consequent stacks to make sure the all stacks (maybe except tailStack) stay full
			Stack<T> prevStack=null,currentStack=null;
			for(int i=idx, iLen=stackList.size(); i<iLen; ++i){
				prevStack = stackList.get(idx-1);
				currentStack = stackList.get(idx);
				Stack<T> tempStack = new Stack<T>();
				while(!currentStack.isEmpty()){
					tempStack.push(currentStack.pop());
				}
				prevStack.push(tempStack.pop());
				while(!tempStack.isEmpty()){
					currentStack.push(tempStack.pop());
				}
			}
			//the last stack has to be removed if it is empty
			if(currentStack.isEmpty()){
				stackList.remove(currentStack);
			}
		}
		return dataToReturn;
	}
	
	public T peekAt(int idx){
		if(stackList.isEmpty()){
			throw new EmptyStackException();
		}
		if(idx>stackList.size()){
			throw new NoSuchElementException();
		}
		return stackList.get(idx-1).peek();
	}
	
	public String toString(){
		Iterator<Stack<T>> iter = stackList.iterator();
		StringBuilder sb = new StringBuilder();
		int idx = 0;
		while(iter.hasNext()){
			idx++;
			sb.append("Stack ").append(idx).append(" : ").append(iter.next().toString()).append('\n');
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception{
		StackOfPlates<Integer> sop = new StackOfPlates<Integer>(4);
		sop.push(1);
		sop.push(2);
		sop.push(3);
		sop.push(4);
		System.out.println(sop);
		sop.push(5);
		System.out.println(sop);
		System.out.println(sop.peek());
		System.out.println(sop.pop());
		System.out.println(sop);
		sop.push(5);
		sop.push(6);
		System.out.println(sop);
		System.out.println(sop.popAt(1));
		System.out.println(sop);
		System.out.println(sop.popAt(1));
		System.out.println(sop);
		
	}
}
