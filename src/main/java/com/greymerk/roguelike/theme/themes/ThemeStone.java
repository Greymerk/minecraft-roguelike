package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.blocks.BlockType;
import com.greymerk.roguelike.editor.blocks.SilverfishBlock;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.RandomStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

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
		
		RandomStair stair = new RandomStair()
				.add(Stair.of(Stair.STONEBRICK), 20)
				.add(Stair.of(Stair.MOSSY_STONEBRICK), 5)
				.add(Stair.of(Stair.COBBLE), 5)
				.add(Stair.of(Stair.MOSSY_COBBLE), 1);
		
		this.primary = BlockSet.builder()
				.walls(walls)
				.floor(walls)
				.stair(stair)
				.pillar(walls)
				.door(Door.of(DoorType.SPRUCE))
				.slab(Slab.get(Slab.STONEBRICK))
				.build();
		this.secondary = primary;
	}
	
	@Override
	public String getName() {
		return Theme.STONE.name();
	}
}
