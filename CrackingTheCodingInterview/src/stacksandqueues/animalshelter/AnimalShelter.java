package stacksandqueues.animalshelter;

import java.util.LinkedList;

public class AnimalShelter {
	
	LinkedList<Animal> catList;
	LinkedList<Animal> dogList;
	Integer order;
	
	public AnimalShelter(){
		this.catList = new LinkedList<Animal>();
		this.dogList = new LinkedList<Animal>();
		this.order = 0;
	}
	
	public void enqueue(Animal animal) throws Exception{
		animal.setOrder(++this.order);
		switch(animal.type){
		case Animal.CAT: 
			catList.add(animal);
			break;
		case Animal.DOG: 
			dogList.add(animal); 
			break;
		default: 
			this.order--;
			throw new Exception("Invalid shelter animal");
		}
	}
	
	public Animal dequeueAny() throws Exception{
		if(catList.isEmpty() && dogList.isEmpty()){
			throw new Exception("No more animals left in the shelter");
		}
		int catOrder = catList.isEmpty()?Integer.MAX_VALUE:catList.peek().order;
		int dogOrder = dogList.isEmpty()?Integer.MAX_VALUE:dogList.peek().order;
		
		if(catOrder<dogOrder){
			return catList.poll();
		}else{
			return dogList.poll();
		}
	}
	
	public Cat dequeueCat() throws Exception{
		if(catList.isEmpty()){
			throw new Exception("No more cats in the shelter");
		}
		
		return (Cat)catList.poll();
		
	}
	
	public Dog dequeueDog() throws Exception{
		if(dogList.isEmpty()){
			throw new Exception("No more dogs in the shelter");
		}
		
		return (Dog)dogList.poll();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Dogs : ").append(this.dogList).append('\n')
		.append("Cats : ").append(this.catList).append('\n');
		
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception{
		AnimalShelter as = new AnimalShelter();
		as.enqueue(new Cat("cat1"));
		as.enqueue(new Dog("dog1"));
		as.enqueue(new Dog("dog2"));
		as.enqueue(new Dog("dog3"));
		as.enqueue(new Cat("cat2"));
		
		System.out.println(as);
		System.out.println(as.dequeueAny());
		System.out.println(as);
		System.out.println(as.dequeueAny());
		System.out.println(as);
		System.out.println(as.dequeueCat());
		System.out.println(as);
		System.out.println(as.dequeueAny());
		System.out.println(as);
	}
}
