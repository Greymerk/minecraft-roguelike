package com.greymerk.roguelike.editor.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.Wood;
import com.greymerk.roguelike.editor.blocks.WoodBlock;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.StairType;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.editor.theme.BlockSet;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.editor.theme.ThemeBase;

public class ThemeOak extends ThemeBase implements ITheme {

	public ThemeOak() {
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 50);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED), 20);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 5);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 1);
		
		MetaStair stair = new MetaStair(StairType.STONEBRICK);

		MetaBlock logs = Wood.get(WoodBlock.LOG);
		MetaBlock segmentWall = Wood.get(Wood.OAK, WoodBlock.PLANK);
		MetaStair segmentStair = new MetaStair(StairType.OAK);
		
		this.primary = new BlockSet(walls, walls, stair, walls, new Door(DoorType.SPRUCE));
		this.primary.setSlab(Slab.get(Slab.STONEBRICK));
		this.secondary =  new BlockSet(segmentWall, segmentWall, segmentStair, logs, new Door(DoorType.SPRUCE));
		this.secondary.setSlab(Slab.get(Slab.OAK));
	}
	
	@Override
	public String getName() {
		return Theme.OAK.name();
	}
	
}
