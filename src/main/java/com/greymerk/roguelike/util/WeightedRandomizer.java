package com.greymerk.roguelike.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.math.random.Random;

public class WeightedRandomizer<T> implements IWeighted<T>{

	private int weight;
	private int weightSum;
	private List<IWeighted<T>> items;	

	public WeightedRandomizer(int weight){
		assert(weight > 0);
		this.weight = weight;
		this.weightSum = 0;
		items = new ArrayList<IWeighted<T>>();
	}
	
	public WeightedRandomizer(){
		this(1);
	}
	
	@Override
	public int getWeight() {
		return weight;
	}
	
	public boolean isEmpty(){
		return items.isEmpty();
	}
	
	public WeightedRandomizer<T> add(IWeighted<T> toAdd){
		this.weightSum += toAdd.getWeight();
		this.items.add(toAdd);
		return this;
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

	public void merge(WeightedRandomizer<T> toMerge) {
		for(IWeighted<T> item : toMerge.items){
			this.add(item);
		}
	}
}
