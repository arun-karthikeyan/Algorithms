package stacksandqueues;

import java.util.Stack;

public class SortStackII {
 //algorithm to sort an unsorted stack
	public static Stack<Integer> sortStack(Stack<Integer> stack) throws Exception{
		
		Stack<Integer> tempStack = new Stack<Integer>();
		while(!stack.isEmpty()){
			Integer currentTop = stack.pop();
			if(tempStack.isEmpty() || tempStack.peek()>=currentTop){
				tempStack.push(currentTop);
			}else{
				//here tempStack is not empty and it has a top lesser than the currentTop
				while(!tempStack.isEmpty() && tempStack.peek()<currentTop){
					stack.push(tempStack.pop());
				}
				tempStack.push(currentTop);
			}
		}
		
		return tempStack;
		
	}
	public static void main(String[] args) throws Exception{
	 Stack<Integer> s = new Stack<Integer>();
	 s.push(5);
	 s.push(2);
	 s.push(1);
	 s.push(3);
	 s.push(8);
	 s.push(9);
	 s.push(0);
	 System.out.println(s);
	 s = sortStack(s);
	 System.out.println(s);
 }
}
