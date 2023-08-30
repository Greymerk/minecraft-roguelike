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

public class ThemeDarkOak extends ThemeBase implements ITheme {

	public ThemeDarkOak() {
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 60);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED), 20);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 5);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 3);
		
		MetaStair stair = new MetaStair(StairType.STONEBRICK);

		
		MetaBlock pillar = Wood.get(Wood.DARKOAK, WoodBlock.LOG);
		MetaBlock segmentWall = Wood.get(Wood.DARKOAK, WoodBlock.PLANK);
		MetaStair segmentStair = new MetaStair(StairType.DARKOAK);
		
		this.primary = new BlockSet(walls, walls, stair, pillar, new Door(DoorType.SPRUCE));
		this.secondary =  new BlockSet(segmentWall, segmentWall, segmentStair, pillar, new Door(DoorType.SPRUCE));
	}
	
	@Override
	public String getName() {
		return Theme.DARKOAK.name();
	}
	
}
