package com.greymerk.roguelike.treasure.chest;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.treasure.Inventory;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.treasure.loot.Loot;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

public class TreasureChest implements ITreasureChest{

	private Inventory inventory;
	private Treasure type;
	private LootableContainerBlockEntity chest;
	private int level;

	public TreasureChest(Treasure type){
		this.type = type;
		this.level = 0;
	}
	
	public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, ChestType block) throws ChestPlacementException {
		for(Cardinal dir : Cardinal.directions) {
			Coord p = pos.copy();
			p.add(dir);
			if(editor.isAir(pos)) {
				return this.generate(editor, rand, pos, dir, block);
			}
		}
		Cardinal dir = Cardinal.randDirs(rand).get(0);
		return this.generate(editor, rand, pos, dir, block);
	}
	
	public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, Cardinal dir, ChestType type) throws ChestPlacementException {

		this.level = Difficulty.fromY(pos.getY());
		
		MetaBlock block = ChestType.get(type);
		setOrientation(block, dir);
		boolean success = block.set(editor, pos);
		
		if(!success){
			throw new ChestPlacementException("Failed to place chest in world");
		}
		
		this.chest = (LootableContainerBlockEntity) editor.getBlockEntity(pos);
		this.inventory = new Inventory(rand, chest);		
		Loot.fillChest(editor, this, rand);
		return this;
	}
	
	private void setOrientation(MetaBlock block, Cardinal dir) {
		Block b = block.getBlock();
		
		if(b == Blocks.CHEST || b == Blocks.TRAPPED_CHEST) {
			if(Cardinal.directions.contains(dir)) {
				block.withProperty(HorizontalFacingBlock.FACING, Cardinal.facing(dir).getOpposite());	
			}
		}
		
		if(b == Blocks.BARREL) {
			block.withProperty(Properties.FACING, Cardinal.facing(Cardinal.UP));
		}
		
		if(b == Blocks.SHULKER_BOX) {
			block.withProperty(FacingBlock.FACING, Cardinal.facing(dir));
		}
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
