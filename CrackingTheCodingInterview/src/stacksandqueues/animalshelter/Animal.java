package stacksandqueues.animalshelter;

public abstract class Animal {
	public static final int CAT=0,DOG=1;
	String typeStr;
	int type;
	int order;
	
	public void setOrder(int time){
		this.order = time;
	}
}
