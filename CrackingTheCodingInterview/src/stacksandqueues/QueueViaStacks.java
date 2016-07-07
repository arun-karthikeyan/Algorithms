package stacksandqueues;

import java.util.Stack;

//implement a queue using two stacks
public class QueueViaStacks<T> {
	Stack<T> lifoStack, fifoStack;
	
	public QueueViaStacks(){
		lifoStack = new Stack<T>();
		fifoStack = new Stack<T>();
	}
	
	public void add(T data){
		//add always has to happen into lifo stack
		lifoStack.push(data);
	}
	private void makeItAQueue() throws Exception{
		if(fifoStack.isEmpty()){
			if(lifoStack.isEmpty()){
				throw new Exception("Empty Queue Exception");
			}
			//add all elements from lifo stack to fifo stack
			while(!lifoStack.isEmpty()){
				fifoStack.push(lifoStack.pop());
			}
		}
	}
	public T remove() throws Exception{
		makeItAQueue();
		return fifoStack.pop();
	}
	public T peek() throws Exception{
		makeItAQueue();
		return fifoStack.peek();
	}
	
	public static void main(String[] args) throws Exception{
		QueueViaStacks<Integer> qvs = new QueueViaStacks<Integer>();
		qvs.add(1);
		qvs.add(2);
		qvs.add(3);
		System.out.println("Removing 1st pos: "+qvs.remove());
		System.out.println("Removing 2nd pos: "+qvs.remove());
		qvs.add(4);
		System.out.println("Peeking 3rd pos: "+qvs.peek());
		qvs.add(5);
		System.out.println("Removing 3rd pos: "+qvs.remove());
		System.out.println("Removing 4th pos: "+qvs.remove());
		System.out.println("Removing 5th pos: "+qvs.remove());
	}
}
