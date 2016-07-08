package stacksandqueues.animalshelter;

public class Cat extends Animal {
	String name;

	public Cat(String name){
		this.name = name;
		super.type = Animal.CAT;
		super.typeStr = "cat";
		
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return getName();
	}
}
