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
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

public class ThemeCrumbledStone extends ThemeBase implements ITheme {

	public ThemeCrumbledStone() {
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK), 20);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_CRACKED), 10);
		walls.addBlock(BlockType.get(BlockType.STONE_BRICK_MOSSY), 10);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE), 10);
		walls.addBlock(BlockType.get(BlockType.GRAVEL), 5);
		walls.addBlock(BlockType.get(BlockType.COBBLESTONE_MOSSY), 2);
		walls.addBlock(SilverfishBlock.getJumble(), 2);
		
		RandomStair stair = new RandomStair()
				.add(Stair.of(Stair.STONEBRICK), 15)
				.add(Stair.of(Stair.MOSSY_STONEBRICK), 5)
				.add(Stair.of(Stair.COBBLE), 2)
				.add(Stair.of(Stair.MOSSY_COBBLE), 1);
		
		this.primary = BlockSet.builder()
				.walls(walls)
				.floor(walls)
				.stair(stair)
				.pillar(walls)
				.door(Door.of(DoorType.SPRUCE))
				.slab(Slab.of(Slab.MOSSY_STONEBRICK))
				.build();
		this.secondary = primary;
	}
	
	@Override
	public String getName() {
		return Theme.CRUMBLEDSTONE.name();
	}
}
