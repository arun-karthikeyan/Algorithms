package stacksandqueues;

public abstract class StackADT<T> {
	public abstract void push(T data);
	public abstract T pop();
	public abstract T peek();
	public abstract boolean isEmpty();
}
