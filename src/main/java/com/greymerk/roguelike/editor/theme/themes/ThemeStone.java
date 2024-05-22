package com.greymerk.roguelike.editor.theme.themes;

import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.SilverfishBlock;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.MetaStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.editor.theme.BlockSet;
import com.greymerk.roguelike.editor.theme.Theme;
import com.greymerk.roguelike.editor.theme.ThemeBase;

public class ThemeStone extends ThemeBase{
	
	public ThemeStone(){
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 50);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED), 20);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 10);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 6);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 2);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 1);
		walls.addBlock(SilverfishBlock.getJumble(), 1);
		
		MetaStair stair = Stair.of(Stair.STONEBRICK);
		
		this.primary = new BlockSet()
				.setWall(walls)
				.setFloor(walls)
				.setStair(stair)
				.setPillar(walls)
				.setDoor(Door.of(DoorType.SPRUCE))
				.setSlab(Slab.get(Slab.STONEBRICK));
		this.secondary = primary;
	}
	
	@Override
	public String getName() {
		return Theme.STONE.name();
	}
}
