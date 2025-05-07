package com.greymerk.roguelike.treasure.loot.trim;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.math.random.Random;

public class Trim {

	public static ItemStack set(DynamicRegistryManager reg, ItemStack item, TrimPattern pattern, TrimMaterial material) {
		NbtCompound nbt = item.getNbt();

        if (nbt == null){
            nbt = new NbtCompound();
            item.setNbt(nbt);
        }
        
        Trim.setNbt(nbt, pattern, material);
        return item;
	}
	
	public static void setNbt(NbtCompound nbt, TrimPattern pattern, TrimMaterial material) {
		
		NbtCompound trim = nbt.getCompound("Trim");
		if(!nbt.contains("Trim")) {
			nbt.put("Trim", trim);
		}
		
		NbtString m = TrimMaterial.getNbt(material);
		trim.put("material", m);
		NbtString p = TrimPattern.getNbt(pattern);
		trim.put("pattern", p);
		nbt.put("Trim", trim);
	}
	
	public static ItemStack addRandom(DynamicRegistryManager reg, ItemStack item, Random rand) {
		TrimMaterial m = TrimMaterial.getRandom(rand);
		TrimPattern p = TrimPattern.getRandom(rand);
		return Trim.set(reg, item, p, m);
	}
}