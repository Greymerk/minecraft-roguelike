package com.greymerk.roguelike.editor.theme.themes;

import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.door.IDoor;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.StairType;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.editor.theme.BlockSet;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.editor.theme.ThemeBase;

import net.minecraft.block.Blocks;

public class ThemeNether extends ThemeBase implements ITheme {

	public ThemeNether() {
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.NETHERBRICK), 200);
		walls.addBlock(BlockType.get(BlockType.NETHERRACK), 20);
		walls.addBlock(BlockType.get(BlockType.ORE_QUARTZ), 20);
		walls.addBlock(BlockType.get(BlockType.SOUL_SAND), 15);
		walls.addBlock(BlockType.get(BlockType.NETHER_WART_BLOCK), 10);
		walls.addBlock(BlockType.get(BlockType.COAL_BLOCK), 5);

		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(walls, 1500);
		floor.addBlock(BlockType.get(BlockType.NETHERBRICK), 1000);
		floor.addBlock(new MetaBlock(Blocks.CRIMSON_NYLIUM), 100);
		floor.addBlock(BlockType.get(BlockType.REDSTONE_BLOCK), 10);
		floor.addBlock(BlockType.get(BlockType.GOLD_BLOCK), 2);
		floor.addBlock(BlockType.get(BlockType.DIAMOND_BLOCK), 1);
		
		MetaStair stair = new MetaStair(StairType.NETHERBRICK);
		MetaBlock obsidian = BlockType.get(BlockType.OBSIDIAN);
		MetaBlock redBrick = BlockType.get(BlockType.RED_NETHERBRICK);
		
		IDoor door = new Door(DoorType.IRON);
		IBlockFactory lightstone = BlockType.get(BlockType.GLOWSTONE);
		IBlockFactory liquid = BlockType.get(BlockType.LAVA_FLOWING);
		
		this.primary = new BlockSet(floor, walls, obsidian, stair, Slab.get(Slab.NETHER_BRICK), door, lightstone, liquid);
		this.secondary = new BlockSet(floor, redBrick, obsidian, stair, Slab.get(Slab.RED_NETHER_BRICK),	door, lightstone, liquid);
	}
	
	@Override
	public String getName() {
		return Theme.NETHER.name();
	}
	
}
