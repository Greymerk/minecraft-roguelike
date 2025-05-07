package com.greymerk.roguelike.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



/**
 * The {@code Accordion} class allows the generation of an expandable list of
 * elements of a given type by applying rules based on min/max target values.
 * 
 * @param <T>
 */
public class Accordion<T> {
	
	List<ItemRule<T>> itemRules;
	
	public Accordion(){
		this.itemRules = new ArrayList<ItemRule<T>>();
	}
	
	public Accordion<T> add(T thing){
		return this.add(thing, 1, 1);
	}
	
	public Accordion<T> addExactly(T thing, int count){
		return this.add(thing, count, count);
	}
	
	public Accordion<T> add(T thing, int min, int max){
		this.itemRules.add(ItemRule.of(thing, min, max));
		return this;
	}
	
	public Accordion<T> addAny(T thing){
		return this.add(thing, 0, Integer.MAX_VALUE);
	}
	
	public Accordion<T> addAtLeast(T thing, int min){
		return this.add(thing, min, Integer.MAX_VALUE);
	}
	
	public Accordion<T> addAtMost(T thing, int max){
		return this.add(thing, 0, max);
	}

	public Map<T, Integer> get(int count){
		Map<T, Integer> thingCounter = new LinkedHashMap<T, Integer>();
		
		itemRules.forEach(rule -> {
			thingCounter.put(rule.thing, rule.min);
		});
		
		int i = 0;
		
		while(thingCounter.values().stream().reduce(0, Integer::sum) < count) {
			ItemRule<T> ir = itemRules.get(i);
			int soFar = thingCounter.get(ir.thing);
			if(soFar < ir.max) thingCounter.put(ir.thing, soFar + 1);

			i = i < itemRules.size() - 1 ? i + 1 : 0; 
		}
		
		return thingCounter;
	}
	
	public List<T> list(int count){
		List<T> things = new ArrayList<T>();
		Map<T, Integer> thingCount = this.get(count);
		thingCount.keySet().forEach(key -> {
			for(int i = 0; i < thingCount.get(key); ++i) {
				things.add(key);
			}
		});
		return things;
	}
	
	private record ItemRule<T>(T thing, int min, int max){
		public static <T> ItemRule<T> of(T thing, int min, int max){
			return new ItemRule<T>(thing, min, max);
		}
	};
	
}
