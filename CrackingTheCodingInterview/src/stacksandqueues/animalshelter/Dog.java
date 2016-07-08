package stacksandqueues.animalshelter;

public class Dog extends Animal {
	String name;

	public Dog(String name){
		this.name = name;
		super.type = Animal.DOG;
		super.typeStr = "dog";
	}
	
	public String getName(){
		return this.name;
	}
	
	public String toString(){
		return getName();
	}
}
