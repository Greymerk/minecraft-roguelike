package com.greymerk.roguelike.theme.themes;

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
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

public class ThemeOak extends ThemeBase implements ITheme {

	public ThemeOak() {
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 50);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED), 10);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 5);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 1);
		
		MetaStair stair = new MetaStair(Stair.STONEBRICK);

		MetaBlock logs = Wood.get(WoodBlock.LOG);
		MetaBlock segmentWall = Wood.get(Wood.OAK, WoodBlock.PLANK);
		MetaStair segmentStair = Stair.of(Stair.OAK);
		
		this.primary = new BlockSet()
				.setWall(walls)
				.setFloor(walls)
				.setStair(stair)
				.setPillar(logs)
				.setSlab(Slab.get(Slab.STONEBRICK))
				.setDoor(Door.of(DoorType.SPRUCE));
		
		this.secondary = new BlockSet()
				.setWall(segmentWall)
				.setFloor(segmentWall)
				.setStair(segmentStair)
				.setPillar(logs)
				.setSlab(Slab.of(Slab.OAK))
				.setDoor(Door.of(DoorType.SPRUCE));

		
	}
	
	@Override
	public String getName() {
		return Theme.OAK.name();
	}
	
}
