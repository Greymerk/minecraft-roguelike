package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.Wood;
import com.greymerk.roguelike.editor.blocks.WoodBlock;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.RandomStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

public class ThemeOak extends ThemeBase implements ITheme {

	public ThemeOak() {
		BlockWeightedRandom walls = new BlockWeightedRandom()
			.addBlock(BlockType.get(BlockType.STONE_BRICK), 50)
			.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED), 10)
			.addBlock(BlockType.get(BlockType.COBBLESTONE), 5)
			.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 1);
		
		RandomStair stair = new RandomStair()
				.add(Stair.of(Stair.STONEBRICK), 5)
				.add(Stair.of(Stair.MOSSY_STONEBRICK), 1);
		
		this.primary = new BlockSet()
				.setWall(walls)
				.setFloor(walls)
				.setStair(stair)
				.setPillar(Wood.get(WoodBlock.LOG))
				.setSlab(Slab.get(Slab.STONEBRICK))
				.setDoor(Door.of(DoorType.SPRUCE));
		
		this.secondary = new BlockSet()
				.setWall(Wood.get(Wood.OAK, WoodBlock.PLANK))
				.setFloor(Wood.get(Wood.OAK, WoodBlock.PLANK))
				.setStair(Stair.of(Stair.OAK))
				.setPillar(Wood.get(WoodBlock.LOG))
				.setSlab(Slab.of(Slab.OAK))
				.setDoor(Door.of(DoorType.SPRUCE));
	}
	
	@Override
	public String getName() {
		return Theme.OAK.name();
	}
	
}
