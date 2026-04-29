package com.greymerk.roguelike.treasure.chest;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootTable;
import com.greymerk.roguelike.config.Config;
import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.treasure.Inventory;
import com.greymerk.roguelike.treasure.Treasure;
import com.greymerk.roguelike.treasure.loot.Loot;

public class TreasureChest implements ITreasureChest, Iterable<ItemStack>{

	private Inventory inventory;
	private Treasure type;
	private RandomizableContainerBlockEntity chest;
	private Difficulty diff;
	private MetaBlock block;
	
	public static Optional<ITreasureChest> generate(IWorldEditor editor, RandomSource rand, Difficulty diff, Coord pos, Treasure type, ChestType block) {
		Optional<ITreasureChest> chest = new TreasureChest(type, block, diff, pos).set(editor, rand, pos);
		return chest;
	}
	
	public static Optional<ITreasureChest> generate(IWorldEditor editor, RandomSource rand, Difficulty diff, Coord pos, Cardinal dir, Treasure type, ChestType block){
		Optional<ITreasureChest> chest = new TreasureChest(type, block, diff, pos).set(editor, rand, pos, dir);
		return chest;
	}
	
	private Optional<ITreasureChest> set(IWorldEditor editor, RandomSource rand, Coord pos){
		return this.set(editor, rand, pos, this.findOrientation(editor, rand, pos));
	}
	
	private TreasureChest(Treasure type, ChestType block, Difficulty diff, Coord pos){
		this.type = type;
		this.block = ChestType.get(block);
		this.diff = diff;
	}
	
	private Optional<ITreasureChest> set(IWorldEditor editor, RandomSource rand, Coord pos, Cardinal dir) {
		setOrientation(dir);
		Optional<RandomizableContainerBlockEntity> container = editor.setBlockEntity(pos, block, RandomizableContainerBlockEntity.class);
		if(container.isEmpty()) return Optional.empty();
		
		this.chest = container.get();
		this.inventory = new Inventory(rand, chest);
		
		Optional<ResourceKey<LootTable>> maybeTable = Treasure.getLootTable(type, diff);
		if(maybeTable.isPresent()) this.setLootTable(maybeTable.get(), editor.getSeed(pos));
		
		if(Config.ofBoolean(Config.ROGUELIKE_LOOT)) Loot.fillChest(editor, this, rand);
		this.forEach(item -> editor.getStatistics().add(item));
		return Optional.of(this);
	}
	
	public Cardinal findOrientation(IWorldEditor editor, RandomSource rand, Coord pos) {
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
				block.with(HorizontalDirectionalBlock.FACING, Cardinal.facing(dir).getOpposite());	
			}
		}
		
		if(b == Blocks.BARREL) {
			block.with(BlockStateProperties.FACING, Cardinal.facing(Cardinal.UP));
		}
		
		if(b == Blocks.SHULKER_BOX) {
			block.with(DirectionalBlock.FACING, Cardinal.facing(dir));
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

	public void setLootTable(ResourceKey<LootTable> key, long seed) {
		this.chest.setLootTable(key, seed);
	}

	@Override
	public Iterator<ItemStack> iterator() {
		return this.chest.iterator();
	}
}
