package com.greymerk.roguelike.treasure;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import com.greymerk.roguelike.util.math.RandHelper;

public class Inventory {
	private RandomizableContainerBlockEntity chest;
	List<Integer> shuffledSlots;
	
	public Inventory(RandomSource rand, RandomizableContainerBlockEntity chest){
		this.chest = chest;
		this.shuffledSlots = new ArrayList<Integer>();
		for(int i = 0; i < this.getInventorySize(); ++i){
			shuffledSlots.add(i);
		}
		
		RandHelper.shuffle(shuffledSlots, rand);
	}
	
	public boolean setRandomEmptySlot(ItemStack item){
		int slot = this.getRandomEmptySlot();
		if(slot < 0) return false;
		return setInventorySlot(slot, item);
	}
	
	private int getRandomEmptySlot(){
		for(int slot : this.shuffledSlots){
			if(isEmptySlot(slot)) return slot;
		}
		return -1;
	}
	
	public boolean isEmptySlot(int slot){		
		try{
			ItemStack item = chest.getItem(slot);
			return item.isEmpty();
		} catch(NullPointerException e){
			return false;
		}
	}
	
	public boolean setInventorySlot(int slot, ItemStack item){
		try{
			chest.setItem(slot, item);
			return true;
		} catch(NullPointerException e){
			return false;
		}
	}
	
	public int getInventorySize(){
		
		if(chest == null){
			return 0;
		}
		
		try{
			return chest.getContainerSize();
		} catch(NullPointerException e){
			return 0;
		}
	}
}
