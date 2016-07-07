package stacksandqueues;

public class CustomQueue<T> extends QueueADT<T> {
	private Node<T> first;
	private Node<T> last;
	private Integer size;
	
	public CustomQueue(){
		this.first = null;
		this.last=null;
		this.size=0;
	}
	
	public CustomQueue(T data){
		Node<T> newNode = new Node<T>(data);
		first = newNode;
		last=newNode;
		this.size=1;
		
	}
	
	@Override
	public void add(T data) {
		// TODO Auto-generated method stub
		Node<T> newNode = new Node<T>(data);
		if(this.isEmpty()){ //this.last and this.first have to be null
			this.last = newNode;
			this.first = newNode;
		}else{
			this.last.next = newNode;
			this.last = newNode;
		}
		size++;
		
	}

	@Override
	public T remove() throws Exception{
		// TODO Auto-generated method stub
		if(this.isEmpty()){
			throw new Exception("Empty Queue");
		}
		Node<T> oldFirst = this.first;
		if(this.size==1){
			this.first = null;
			this.last = null;
		}else{
			this.first = this.first.next;
		}
		size--;
		return oldFirst.data;
	}

	@Override
	public T peek() throws Exception{
		// TODO Auto-generated method stub
		if(this.isEmpty()){
			throw new Exception("Empty Queue");
		}
		return this.first.data;
	}
	
	public int size(){
		return this.size;
	}
	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.size==0;
	}
	
	public String toString(){
		return this.first.toString();
	}
	public static void main(String[] args) throws Exception{
		CustomQueue<Integer> q = new CustomQueue<Integer>();
		q.add(1);
		q.add(2);
		q.add(3);
		System.out.println(q);
		System.out.println("removing head : "+q.remove());
		q.add(4);
		q.add(5);
		System.out.println(q);
		System.out.println("peeking current head : "+q.peek());
		System.out.println(q);
		
	}

}
