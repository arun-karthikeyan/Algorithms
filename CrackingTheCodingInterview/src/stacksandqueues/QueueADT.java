package stacksandqueues;

public abstract class QueueADT<T> {
	public abstract void add(T data);
	public abstract T remove() throws Exception;
	public abstract T peek() throws Exception;
	public abstract boolean isEmpty();
}
