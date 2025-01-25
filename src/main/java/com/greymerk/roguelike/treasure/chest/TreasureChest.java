package com.greymerk.roguelike.treasure.chest;

import java.util.List;
import java.util.Optional;

import com.greymerk.roguelike.config.Config;
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
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.random.Random;

public class TreasureChest implements ITreasureChest{

	private Inventory inventory;
	private Treasure type;
	private LootableContainerBlockEntity chest;
	private Difficulty diff;
	private MetaBlock block;
	
	public static Optional<ITreasureChest> generate(IWorldEditor editor, Random rand, Difficulty diff, Coord pos, Treasure type, ChestType block) {
		Optional<ITreasureChest> chest = new TreasureChest(type, block, diff, pos).set(editor, rand, pos);
		return chest;
	}
	
	public static Optional<ITreasureChest> generate(IWorldEditor editor, Random rand, Difficulty diff, Coord pos, Cardinal dir, Treasure type, ChestType block){
		Optional<ITreasureChest> chest = new TreasureChest(type, block, diff, pos).set(editor, rand, pos, dir);
		return chest;
	}
	
	private Optional<ITreasureChest> set(IWorldEditor editor, Random rand, Coord pos){
		return this.set(editor, rand, pos, this.findOrientation(editor, rand, pos));
	}
	
	private TreasureChest(Treasure type, ChestType block, Difficulty diff, Coord pos){
		this.type = type;
		this.block = ChestType.get(block);
		this.diff = diff;
	}
	
	private Optional<ITreasureChest> set(IWorldEditor editor, Random rand, Coord pos, Cardinal dir) {
		setOrientation(dir);
		if(!block.set(editor, pos)) {
			return Optional.empty();
		}
		
		this.chest = (LootableContainerBlockEntity) editor.getBlockEntity(pos);
		this.inventory = new Inventory(rand, chest);
		
		Optional<RegistryKey<LootTable>> maybeTable = Treasure.getLootTable(type, diff);
		if(maybeTable.isPresent()) this.setLootTable(maybeTable.get(), editor.getSeed(pos));
		
		if(Config.ofBoolean(Config.ROGUELIKE_LOOT)) Loot.fillChest(editor, this, rand);
		return Optional.of(this);
	}
	
	public Cardinal findOrientation(IWorldEditor editor, Random rand, Coord pos) {
		List<Cardinal> dirs = Cardinal.randDirs(rand);
		for(Cardinal dir : dirs) {
			Coord p = pos.copy();
			p.add(dir);
			if(editor.isAir(pos)) {
				return dir;
			}
		}
		return dirs.getFirst();
	}
	
	public void setOrientation(Cardinal dir) {
		Block b = block.getBlock();
		
		if(b == Blocks.CHEST || b == Blocks.TRAPPED_CHEST) {
			if(Cardinal.directions.contains(dir)) {
				block.with(HorizontalFacingBlock.FACING, Cardinal.facing(dir).getOpposite());	
			}
		}
		
		if(b == Blocks.BARREL) {
			block.with(Properties.FACING, Cardinal.facing(Cardinal.UP));
		}
		
		if(b == Blocks.SHULKER_BOX) {
			block.with(FacingBlock.FACING, Cardinal.facing(dir));
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
	public Difficulty getLevel() {
		return this.diff;
	}

	public void setLootTable(RegistryKey<LootTable> key, long seed) {
		this.chest.setLootTable(key, seed);
	}
}
