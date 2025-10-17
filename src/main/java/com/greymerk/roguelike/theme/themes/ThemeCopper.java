package com.greymerk.roguelike.theme.themes;

import java.util.List;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.door.Door;
import com.greymerk.roguelike.editor.blocks.door.DoorType;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.RandomStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockJumble;
import com.greymerk.roguelike.editor.factories.BlockLayers;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

import net.minecraft.block.Blocks;

public class ThemeCopper extends ThemeBase implements ITheme {

	public ThemeCopper() {
		

		
		BlockWeightedRandom base = new BlockWeightedRandom();
		base.add(MetaBlock.of(Blocks.STONE_BRICKS), 5);
		base.add(MetaBlock.of(Blocks.MOSSY_STONE_BRICKS), 1);
		base.add(MetaBlock.of(Blocks.CRACKED_STONE_BRICKS), 1);
		
		BlockJumble tuff = BlockJumble.of(List.of(
				MetaBlock.of(Blocks.TUFF_BRICKS),
				MetaBlock.of(Blocks.POLISHED_TUFF),
				base
				));
		
		BlockJumble copper = BlockJumble.of(List.of(
				MetaBlock.of(Blocks.CHISELED_COPPER),
				MetaBlock.of(Blocks.CUT_COPPER)
				));

		BlockLayers walls = new BlockLayers(base);
		walls.add(0, tuff);
		walls.add(1, copper);
		walls.add(2, tuff);
		walls.add(3, copper);
		
		RandomStair stair = new RandomStair()
				.add(Stair.of(Stair.MOSSY_STONEBRICK), 1)
				.add(Stair.of(Stair.STONEBRICK), 3);
		
		BlockWeightedRandom pillar = new BlockWeightedRandom();
		pillar.add(walls, 3);
		pillar.add(MetaBlock.of(Blocks.CHISELED_TUFF_BRICKS), 1);
		pillar.add(MetaBlock.of(Blocks.CHISELED_TUFF), 1);
		
		this.primary = BlockSet.builder()
				.walls(walls)
				.floor(base)
				.stair(stair)
				.pillar(pillar)
				.liquid(MetaBlock.of(Blocks.WATER))
				.slab(Slab.of(Slab.STONEBRICK))
				.door(Door.of(DoorType.COPPER))
				.build();
		this.secondary = this.primary;
	}
	
	@Override
	public String getName() {
		return Theme.COPPER.name();
	}

}
