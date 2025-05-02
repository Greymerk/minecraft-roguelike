package com.greymerk.roguelike.editor;

import java.util.HashMap;
import java.util.Map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.dynamic.Codecs;

public class Statistics {

	private static final Codec<Map<RegistryEntry<Item>, Integer>> ENTRY_MAP_CODEC = Codec.unboundedMap(Item.ENTRY_CODEC, Codecs.POSITIVE_INT);
	
	public static final Codec<Statistics> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				ENTRY_MAP_CODEC.fieldOf("items").forGetter(stats -> stats.items)
			).apply(instance, (itemMap) -> Statistics.of(itemMap))
		);

	
	Map<RegistryEntry<Item>, Integer> items;
	
	private static Statistics of(Map<RegistryEntry<Item>, Integer> itemMap) {
		Statistics stats = new Statistics();
		stats.items = itemMap;
		return stats;
	}
	
	public Statistics() {
		this.items = new HashMap<RegistryEntry<Item>, Integer>();
	}
	
	public void add(ItemStack toAdd) {
		if(toAdd.getItem().equals(Items.AIR)) return;
		items.merge(Registries.ITEM.getEntry(toAdd.getItem()), toAdd.getCount(), Integer::sum);
	}
	
	public void merge(Statistics other) {
		other.items.forEach((k, v) -> {
			this.items.merge(k, v, Integer::sum);
		});
	}
	
	@Override
	public String toString() {
		return items.toString();
	}
}
