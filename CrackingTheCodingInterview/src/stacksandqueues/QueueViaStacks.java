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
	
	public T remove() throws Exception{
		
		if(fifoStack.isEmpty()){
			if(lifoStack.isEmpty()){
				throw new Exception("Empty Queue Exception");
			}
			//add all elements from lifo stack to fifo stack
			while(!lifoStack.isEmpty()){
				fifoStack.push(lifoStack.pop());
			}
		}
		return fifoStack.pop();
	}
	
	public static void main(String[] args) throws Exception{
		QueueViaStacks<Integer> qvs = new QueueViaStacks<Integer>();
		qvs.add(1);
		qvs.add(2);
		qvs.add(3);
		System.out.println(qvs.remove());
		System.out.println(qvs.remove());
		qvs.add(4);
		qvs.add(5);
		System.out.println(qvs.remove());
		System.out.println(qvs.remove());
		System.out.println(qvs.remove());
	}
}
