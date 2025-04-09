package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
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

import net.minecraft.block.Blocks;

public class ThemeStone extends ThemeBase{
	
	public ThemeStone(){
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.add(MetaBlock.of(Blocks.STONE_BRICKS), 50);
		walls.add(MetaBlock.of(Blocks.CRACKED_STONE_BRICKS), 20);
		walls.add(MetaBlock.of(Blocks.MOSSY_STONE_BRICKS), 10);
		walls.add(MetaBlock.of(Blocks.COBBLESTONE), 6);
		walls.add(MetaBlock.of(Blocks.GRAVEL), 2);
		walls.add(MetaBlock.of(Blocks.MOSSY_COBBLESTONE), 1);
		walls.add(SilverfishBlock.getJumble(), 1);
		
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
