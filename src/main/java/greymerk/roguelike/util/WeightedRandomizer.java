package greymerk.roguelike.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedRandomizer<T> implements IWeighted<T>{

	private int weight;
	private int weightSum;
	private List<IWeighted<T>> items;	

	public WeightedRandomizer(int weight){
		this.weight = weight;
		this.weightSum = 0;
		items = new ArrayList<IWeighted<T>>();
	}
	
	public WeightedRandomizer(WeightedRandomizer<T> toCopy){
		this.weight = toCopy.weight;
		this.weightSum = toCopy.weightSum;
		for(IWeighted<T> e : toCopy.items){
			items.add(e);
		}
	}
	
	public WeightedRandomizer(){
		this(0);
	}
	
	@Override
	public int getWeight() {
		return weight;
	}
	
	public boolean isEmpty(){
		return items.isEmpty();
	}
	
	public void add(IWeighted<T> toAdd){
		this.weightSum += toAdd.getWeight();
		this.items.add(toAdd);
	}
	
	public T get(Random rand){
		if (weightSum == 0) return null;
		if (items.isEmpty()) return null;
		
		int roll = rand.nextInt(weightSum);
		
		for(IWeighted<T> i : this.items){
			roll -= i.getWeight();
			if(roll < 0) return i.get(rand);
		}
		
		return null;
	}
}
