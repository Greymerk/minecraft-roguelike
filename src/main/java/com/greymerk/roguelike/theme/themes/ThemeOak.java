package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
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

import net.minecraft.block.Blocks;

public class ThemeOak extends ThemeBase implements ITheme {

	public ThemeOak() {
		BlockWeightedRandom walls = new BlockWeightedRandom()
			.add(MetaBlock.of(Blocks.STONE_BRICKS), 50)
			.add(MetaBlock.of(Blocks.CRACKED_STONE_BRICKS), 10)
			.add(MetaBlock.of(Blocks.COBBLESTONE), 5)
			.add(MetaBlock.of(Blocks.MOSSY_STONE_BRICKS), 1);
		
		RandomStair stair = new RandomStair()
				.add(Stair.of(Stair.STONEBRICK), 5)
				.add(Stair.of(Stair.MOSSY_STONEBRICK), 1);
		
		this.primary = BlockSet.builder()
				.walls(walls)
				.floor(walls)
				.stair(stair)
				.pillar(Wood.get(WoodBlock.LOG))
				.slab(Slab.get(Slab.STONEBRICK))
				.door(Door.of(DoorType.SPRUCE))
				.build();
		
		this.secondary = BlockSet.builder()
				.walls(Wood.get(Wood.OAK, WoodBlock.PLANK))
				.floor(Wood.get(Wood.OAK, WoodBlock.PLANK))
				.stair(Stair.of(Stair.OAK))
				.pillar(Wood.get(WoodBlock.LOG))
				.slab(Slab.of(Slab.OAK))
				.door(Door.of(DoorType.SPRUCE))
				.build();
	}
	
	@Override
	public String getName() {
		return Theme.OAK.name();
	}
	
}
