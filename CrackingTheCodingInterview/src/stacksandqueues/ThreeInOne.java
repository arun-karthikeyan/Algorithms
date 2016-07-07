package stacksandqueues;

import java.util.EmptyStackException;

//Three stacks in one array
public class ThreeInOne<T> {
int individualCapacity;
T[] allStacks;
int[] top;
private static final int TOTALSTACKS = 3;
private static final int STACKONE = 0, STACKTWO = 1, STACKTHREE = 2, EMPTY=-1;

@SuppressWarnings("unchecked")
public ThreeInOne(int stackSize){
	this.individualCapacity = stackSize;
	allStacks = ((T[])new Object[TOTALSTACKS*stackSize]);
	this.top = new int[TOTALSTACKS];
	this.top[STACKONE] = EMPTY;
	this.top[STACKTWO] = EMPTY;
	this.top[STACKTHREE] = EMPTY;
}

public void push(int stackNo, T data) throws Exception{
	//full stack check
	if(top[stackNo]==(((stackNo+1)*individualCapacity)-1)) {
		throw new Exception("Stack "+stackNo+" is full");
	}
	
	//empty stack check
	if(top[stackNo]==EMPTY){
		top[stackNo] = stackNo*individualCapacity-1;
	}
	
	//data append and top increment
	allStacks[++top[stackNo]] = data;
}

public T pop(int stackNo) throws Exception{
	//empty stack check
	if(top[stackNo]==EMPTY){
		throw new EmptyStackException();
	}
	
	//pop data
	T popData = allStacks[top[stackNo]--];
	
	//check for to be empty stack
	if(top[stackNo]<(this.individualCapacity*stackNo)){
		top[stackNo] = EMPTY;
	}
	
	return popData;
}

public T peek(int stackNo) throws Exception{
	//empty stack check
	if(top[stackNo]==EMPTY){
		throw new EmptyStackException();
	}
	
	//return pop data
	return allStacks[top[stackNo]];
}
private String printStack(int stackNo){
	StringBuilder sb = new StringBuilder();
	if(this.top[stackNo]!=EMPTY){
		sb.append(allStacks[top[stackNo]]);
	}else{
		sb.append("E");
	}
	
	for(int i=this.top[stackNo]-1, iLen=(stackNo*individualCapacity);i>=iLen;--i){
		sb.append(',').append(allStacks[i]);
	}
	return sb.toString();
}
public String toString(){
	StringBuilder sb = new StringBuilder();
	sb.append("Stack 0 : ").append(printStack(0)).append("\n")
		.append("Stack 1 : ").append(printStack(1)).append("\n")
		.append("Stack 2 : ").append(printStack(2));
	
	return sb.toString();
}

public boolean isEmpty(int stackNo){
	return this.top[stackNo]==EMPTY;
}

public static void main(String[] args) throws Exception{
	ThreeInOne<Integer> tio = new ThreeInOne<Integer>(4);
	tio.push(0, 1);
	tio.push(0, 2);
	tio.push(0, 5);
	tio.push(1, 1);
	tio.push(2, 3);
	tio.push(0, 7);
	System.out.println(tio);
	tio.pop(2);
	System.out.println(tio);
	
}
}
