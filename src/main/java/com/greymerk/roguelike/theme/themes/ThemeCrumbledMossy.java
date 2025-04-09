package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.SilverfishBlock;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.RandomStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockLayers;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

import net.minecraft.block.Blocks;

public class ThemeCrumbledMossy extends ThemeBase implements ITheme {

	public ThemeCrumbledMossy() {
		BlockWeightedRandom mossy = new BlockWeightedRandom();
		mossy.add(MetaBlock.of(Blocks.COBBLESTONE), 60);
		mossy.add(MetaBlock.of(Blocks.MOSSY_STONE_BRICKS), 20);
		mossy.add(MetaBlock.of(Blocks.STONE_BRICKS), 10);
		mossy.add(MetaBlock.of(Blocks.CRACKED_STONE_BRICKS), 10);
		mossy.add(SilverfishBlock.getJumble(), 5);
		mossy.add(MetaBlock.of(Blocks.MOSSY_COBBLESTONE), 20);
		mossy.add(MetaBlock.of(Blocks.GRAVEL), 15);
		
		BlockWeightedRandom tuff = new BlockWeightedRandom();
		tuff.add(mossy, 4);
		tuff.add(MetaBlock.of(Blocks.CHISELED_TUFF_BRICKS), 3);
		tuff.add(MetaBlock.of(Blocks.CHISELED_TUFF), 1);
		
		BlockLayers layered = new BlockLayers(mossy);
		layered.add(1, tuff);
		layered.add(3, tuff);
		
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
				.add(Stair.of(Stair.MOSSY_COBBLE), 10)
				.add(Stair.of(Stair.COBBLE), 5)
				.add(Stair.of(Stair.MOSSY_STONEBRICK), 3)
				.add(Stair.of(Stair.STONEBRICK), 1);
		
		this.primary = BlockSet.builder()
				.walls(layered)
				.floor(floor)
				.stair(stair)
				.pillar(mossy)
				.door(Door.of(DoorType.JUNGLE))
				.slab(Slab.of(Slab.MOSSY_COBBLE))
				.build();
		
		this.secondary = this.primary;
	}
	
	@Override
	public String getName() {
		return Theme.CRUMBLEDMOSSY.name();
	}
}
