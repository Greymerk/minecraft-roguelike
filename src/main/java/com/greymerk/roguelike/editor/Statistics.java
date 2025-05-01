package com.greymerk.roguelike.editor;

import java.util.HashMap;
import java.util.Map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.dynamic.Codecs;

public class Statistics {

	public static final Codec<Item> ITEM_CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				Codec.STRING.fieldOf("item").forGetter(item -> item.getName().getString()),
				Codec.INT.fieldOf("id").forGetter(item -> Item.getRawId(item))
			).apply(instance, (name, id) -> Item.byRawId(id))
		);
	
	public static final Codec<Map<Integer, Integer>> ITEM_BY_ID_CODEC = Codec.unboundedMap(Codecs.POSITIVE_INT, Codecs.POSITIVE_INT);
	public static final Codec<Map<String, Integer>> ITEM_BY_NAME_CODEC = Codec.unboundedMap(Codecs.NON_EMPTY_STRING, Codecs.POSITIVE_INT);
	
	public static final Codec<Statistics> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				ITEM_BY_ID_CODEC.fieldOf("items").forGetter(stats -> stats.getIdMap())
			).apply(instance, (itemIdMap) -> Statistics.of(itemIdMap))
		);
	
	public static final Codec<Statistics> LOG_CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				ITEM_BY_NAME_CODEC.fieldOf("items").forGetter(stats -> stats.getStrMap())
			).apply(instance, (itemMap) -> new Statistics())
		); 
	
	
	Map<Item, Integer> items;
	
	private static Statistics of(Map<Integer, Integer> itemMap) {
		Statistics stats = new Statistics();
		itemMap.forEach((k, v) -> {
			stats.items.put(Item.byRawId(k), v);
		});
		return stats;
	}
	
	public Statistics() {
		this.items = new HashMap<Item, Integer>();
		
		Items.STICK.getName().getString();
		Item.byRawId(Item.getRawId(Items.STICK));
	}
	
	public void add(ItemStack toAdd) {
		if(toAdd.getItem().equals(Items.AIR)) return;
		items.merge(toAdd.getItem(), toAdd.getCount(), Integer::sum);
	}
	
	public void merge(Statistics other) {
		other.items.forEach((k, v) -> {
			this.items.merge(k, v, Integer::sum);
		});
	}
	
	private Map<Integer, Integer> getIdMap(){
		Map<Integer, Integer> idMap = new HashMap<Integer, Integer>();
		this.items.forEach((k, v) -> {
			idMap.put(Item.getRawId(k), v);
		});
		return idMap;
	}
	
	private Map<String, Integer> getStrMap(){
		Map<String, Integer> strMap = new HashMap<String, Integer>();
		this.items.forEach((k, v) -> {
			strMap.put(k.getName().getString(), v);
		});
		return strMap;
	}
	
	@Override
	public String toString() {
		return items.toString();
	}
}
