package com.greymerk.roguelike.editor;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Statistics {

	private static final Codec<Map<Holder<Item>, Integer>> ENTRY_MAP_CODEC = Codec.unboundedMap(Item.CODEC, ExtraCodecs.POSITIVE_INT);
	
	public static final Codec<Statistics> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				ENTRY_MAP_CODEC.fieldOf("items").forGetter(stats -> stats.items)
			).apply(instance, (itemMap) -> Statistics.of(itemMap))
		);

	
	Map<Holder<Item>, Integer> items;
	
	private static Statistics of(Map<Holder<Item>, Integer> itemMap) {
		Statistics stats = new Statistics();
		stats.items = itemMap;
		return stats;
	}
	
	public Statistics() {
		this.items = new HashMap<Holder<Item>, Integer>();
	}
	
	public void add(ItemStack toAdd) {
		if(toAdd.getItem().equals(Items.AIR)) return;
		items.merge(BuiltInRegistries.ITEM.wrapAsHolder(toAdd.getItem()), toAdd.getCount(), Integer::sum);
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
