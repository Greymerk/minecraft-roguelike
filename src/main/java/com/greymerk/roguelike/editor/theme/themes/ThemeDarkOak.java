package com.greymerk.roguelike.editor.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.Wood;
import com.greymerk.roguelike.editor.blocks.WoodBlock;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.editor.theme.BlockSet;
import com.greymerk.roguelike.editor.theme.ITheme;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.editor.theme.ThemeBase;

public class ThemeDarkOak extends ThemeBase implements ITheme {

	public ThemeDarkOak() {
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 50);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED), 30);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 10);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 5);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 1);
		
		MetaStair stair = new MetaStair(Stair.STONEBRICK);

		
		MetaBlock logs = Wood.get(Wood.DARKOAK, WoodBlock.LOG);
		MetaBlock segmentWall = Wood.get(Wood.DARKOAK, WoodBlock.PLANK);
		MetaStair segmentStair = new MetaStair(Stair.DARKOAK);
		
		this.primary = new BlockSet()
				.setWall(walls)
				.setFloor(walls)
				.setStair(stair)
				.setPillar(logs)
				.setDoor(Door.of(DoorType.DARKOAK))
				.setSlab(Slab.get(Slab.STONEBRICK));
		this.secondary = new BlockSet()
				.setWall(segmentWall)
				.setFloor(segmentWall)
				.setStair(segmentStair)
				.setPillar(logs)
				.setDoor(Door.of(DoorType.DARKOAK))
				.setSlab(Slab.get(Slab.SPRUCE));
	}
	
	@Override
	public String getName() {
		return Theme.DARKOAK.name();
	}
	
}
