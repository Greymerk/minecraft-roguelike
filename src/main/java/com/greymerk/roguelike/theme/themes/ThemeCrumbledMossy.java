package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.SilverfishBlock;
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

public class ThemeCrumbledMossy extends ThemeBase implements ITheme {

	public ThemeCrumbledMossy() {
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 60);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 20);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 10);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED), 10);
		walls.addBlock(SilverfishBlock.getJumble(), 5);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 20);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 15);
		
		BlockWeightedRandom pillar = new BlockWeightedRandom();
		pillar.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 20);
		pillar.addBlock(BlockType.get(BlockType.COBBLESTONE), 5);
		pillar.addBlock(SilverfishBlock.getJumble(), 3);
		pillar.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 5);
		
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 10);
		floor.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 4);
		floor.addBlock(BlockType.get(BlockType.COBBLESTONE), 2);
		floor.addBlock(BlockType.get(BlockType.GRAVEL), 1);
		
		MetaStair stair = Stair.of(Stair.MOSSY_COBBLE);
		
		this.primary = new BlockSet()
				.setWall(walls)
				.setFloor(floor)
				.setStair(stair)
				.setPillar(walls)
				.setDoor(Door.of(DoorType.JUNGLE))
				.setSlab(Slab.of(Slab.MOSSY_COBBLE));
		
		this.secondary = this.primary;
	}
	
	@Override
	public String getName() {
		return Theme.CRUMBLEDMOSSY.name();
	}
}
