package com.greymerk.roguelike.treasure;

import java.util.Objects;
import net.minecraft.util.math.random.Random;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class TreasureChest implements ITreasureChest{

	protected Inventory inventory;
	protected Treasure type;
	protected Random rand;
	private long seed;
	private ChestBlockEntity chest;
	private int level;

	public TreasureChest(Treasure type){
		this.type = type;
		this.level = 0;
	}
	
	public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped) throws ChestPlacementException {

		this.rand = rand;
		this.level = level;
		
		MetaBlock chestType = new MetaBlock(trapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST);
		
		boolean success = chestType.set(editor, pos);
		
		if(!success){
			throw new ChestPlacementException("Failed to place chest in world");
		}
		
		this.chest = (ChestBlockEntity) editor.getBlockEntity(pos);
		this.inventory = new Inventory(rand, chest);
		this.seed = (long)Objects.hash(pos.hashCode(), editor.getSeed());
		
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
		this.chest.setLootTable(table, seed);
	}
}
