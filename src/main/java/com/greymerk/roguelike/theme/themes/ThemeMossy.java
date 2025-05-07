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

public class ThemeMossy extends ThemeBase implements ITheme {

	public ThemeMossy(){
				
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.add(MetaBlock.of(Blocks.COBBLESTONE), 60);
		walls.add(MetaBlock.of(Blocks.MOSSY_STONE_BRICKS), 20);
		walls.add(MetaBlock.of(Blocks.STONE_BRICKS), 10);
		walls.add(SilverfishBlock.getJumble(), 5);
		walls.add(MetaBlock.of(Blocks.MOSSY_COBBLESTONE), 10);
		walls.add(MetaBlock.of(Blocks.GRAVEL), 15);
		
		BlockWeightedRandom pillar = new BlockWeightedRandom();
		pillar.add(MetaBlock.of(Blocks.MOSSY_STONE_BRICKS), 20);
		pillar.add(MetaBlock.of(Blocks.COBBLESTONE), 5);
		pillar.add(SilverfishBlock.getJumble(), 3);
		pillar.add(MetaBlock.of(Blocks.MOSSY_COBBLESTONE), 5);
		
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.add(MetaBlock.of(Blocks.MOSSY_COBBLESTONE), 10);
		floor.add(MetaBlock.of(Blocks.MOSSY_STONE_BRICKS), 4);
		floor.add(MetaBlock.of(Blocks.COBBLESTONE), 2);
		floor.add(MetaBlock.of(Blocks.GRAVEL), 1);
		
		RandomStair stair = new RandomStair()
				.add(Stair.of(Stair.STONEBRICK), 3)
				.add(Stair.of(Stair.MOSSY_STONEBRICK), 1)
				.add(Stair.of(Stair.COBBLE), 2)
				.add(Stair.of(Stair.MOSSY_COBBLE), 1);
		
		this.primary = BlockSet.builder()
				.walls(walls)
				.floor(floor)
				.stair(stair)
				.pillar(pillar)
				.door(Door.of(DoorType.JUNGLE))
				.slab(Slab.of(Slab.MOSSY_COBBLE))
				.build();
		this.secondary = this.primary;
	}

	@Override
	public String getName() {
		return Theme.MOSSY.name();
	}
}
