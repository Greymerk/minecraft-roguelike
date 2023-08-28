package com.greymerk.roguelike.treasure;

import java.util.ArrayList;
import java.util.List;

import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class Inventory {
	private ChestBlockEntity chest;
	List<Integer> shuffledSlots;
	
	public Inventory(Random rand, ChestBlockEntity chest){
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
			ItemStack item = chest.getStack(slot);
			return item.isEmpty();
		} catch(NullPointerException e){
			return false;
		}
	}
	
	public boolean setInventorySlot(int slot, ItemStack item){
		try{
			chest.setStack(slot, item);
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
			return chest.size();
		} catch(NullPointerException e){
			return 0;
		}
	}
}
