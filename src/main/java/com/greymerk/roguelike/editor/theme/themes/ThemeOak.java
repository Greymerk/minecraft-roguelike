package com.greymerk.roguelike.editor.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.Wood;
import com.greymerk.roguelike.editor.blocks.WoodBlock;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
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
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 30);
		MetaBlock cracked = BlockType.get(BlockType.STONE_BRICK_CRACKED);
		walls.addBlock(cracked, 20);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 5);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 1);
		
		MetaStair stair = new MetaStair(StairType.STONEBRICK);

		this.primary = new BlockSet(walls, walls, stair, walls, new Door(DoorType.SPRUCE));
		
		MetaBlock pillar = Wood.get(WoodBlock.LOG);
		MetaBlock segmentWall = Wood.get(Wood.OAK, WoodBlock.PLANK);
		MetaStair segmentStair = new MetaStair(StairType.OAK);
		
		this.secondary =  new BlockSet(segmentWall, segmentWall, segmentStair, pillar, new Door(DoorType.SPRUCE));
	}
	
	@Override
	public String getName() {
		return Theme.OAK.name();
	}
	
}