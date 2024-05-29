package com.greymerk.roguelike.treasure.loot;

import java.util.HashMap;
import java.util.Map;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.util.IWeighted;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class LootSettings {

	private Map<Loot, IWeighted<ItemStack>> loot;
	
	public LootSettings(Difficulty diff, IWorldEditor editor){
		loot = new HashMap<Loot, IWeighted<ItemStack>>();
		for(Loot type : Loot.values()){
			loot.put(type, Loot.getProvider(type, diff, editor));
		}
	}
	
	public LootSettings(LootSettings toCopy){
		this.loot = new HashMap<Loot, IWeighted<ItemStack>>();
		this.loot.putAll(toCopy.loot);
	}
	
	public LootSettings(LootSettings base, LootSettings override){
		this.loot = new HashMap<Loot, IWeighted<ItemStack>>();
		if(base != null) this.loot.putAll(base.loot);
		if(override != null) this.loot.putAll(override.loot);
	}
	
	public ItemStack get(Loot type, Random rand){
		IWeighted<ItemStack> provider = loot.get(type);
		return provider.get(rand);
	}
	
	public IWeighted<ItemStack> get(Loot type){
		return this.loot.get(type);
	}
	
	public void set(Loot type, IWeighted<ItemStack> provider){
		this.loot.put(type, provider);
	}
}