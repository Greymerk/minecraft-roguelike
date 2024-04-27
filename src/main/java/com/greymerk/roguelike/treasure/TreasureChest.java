package com.greymerk.roguelike.treasure;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

public class TreasureChest implements ITreasureChest{

	private Inventory inventory;
	private Treasure type;
	private ChestBlockEntity chest;
	private int level;

	public TreasureChest(Treasure type){
		this.type = type;
		this.level = 0;
	}
	
	public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped) throws ChestPlacementException {
		for(Cardinal dir : Cardinal.directions) {
			Coord p = pos.copy();
			p.add(dir);
			if(editor.isAir(pos)) {
				return this.generate(editor, rand, pos, dir, level, trapped);
			}
		}
		Cardinal dir = Cardinal.randDirs(rand).get(0);
		return this.generate(editor, rand, pos, dir, level, trapped);
	}
	
	public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, Cardinal dir, int level, boolean trapped) throws ChestPlacementException {

		this.level = level;
		
		MetaBlock block = new MetaBlock(trapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST);
		
		boolean success = block.set(editor, pos);
		
		if(!success){
			throw new ChestPlacementException("Failed to place chest in world");
		}
		
		this.chest = (ChestBlockEntity) editor.getBlockEntity(pos);
		this.inventory = new Inventory(rand, chest);		
		editor.fillChest(this, rand);
		return this;
	}
	
	@Override
	public boolean setSlot(int slot, ItemStack item){
		return this.inventory.setInventorySlot(slot, item);
	}
	
	@Override
	public boolean setRandomEmptySlot(ItemStack item){
		return this.inventory.setRandomEmptySlot(item);
	}
	
	@Override
	public boolean isEmptySlot(int slot){
		return this.inventory.isEmptySlot(slot);
	}

	@Override
	public Treasure getType(){
		return this.type;
	}
	
	@Override
	public int getSize(){
		return this.inventory.getInventorySize();
	}

	@Override
	public int getLevel() {
		if(level < 0) return 0;
		if(level > 4) return 4;
		return this.level;
	}

	@Override
	public void setLootTable(Identifier table) {
		//this.chest.setLootTable(table, (long)Objects.hash(pos.hashCode(), editor.getSeed()));
	}
}
