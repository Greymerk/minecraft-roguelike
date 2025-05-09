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
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

import net.minecraft.block.Blocks;

public class ThemeCrumbledStone extends ThemeBase implements ITheme {

	public ThemeCrumbledStone() {
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.add(MetaBlock.of(Blocks.STONE_BRICKS), 20);
		walls.add(MetaBlock.of(Blocks.CRACKED_STONE_BRICKS), 10);
		walls.add(MetaBlock.of(Blocks.MOSSY_STONE_BRICKS), 10);
		walls.add(MetaBlock.of(Blocks.COBBLESTONE), 10);
		walls.add(MetaBlock.of(Blocks.GRAVEL), 5);
		walls.add(MetaBlock.of(Blocks.MOSSY_COBBLESTONE), 2);
		walls.add(SilverfishBlock.getJumble(), 2);
		
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
