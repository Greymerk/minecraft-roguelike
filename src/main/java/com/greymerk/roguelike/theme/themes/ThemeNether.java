package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.MetaBlock;
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

import net.minecraft.block.Blocks;

public class ThemeNether extends ThemeBase implements ITheme {

	public ThemeNether() {
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.add(MetaBlock.of(Blocks.NETHER_BRICKS), 200);
		walls.add(MetaBlock.of(Blocks.NETHERRACK), 20);
		walls.add(MetaBlock.of(Blocks.NETHER_QUARTZ_ORE), 20);
		walls.add(MetaBlock.of(Blocks.SOUL_SAND), 15);
		walls.add(MetaBlock.of(Blocks.NETHER_WART_BLOCK), 10);
		walls.add(MetaBlock.of(Blocks.COAL_BLOCK), 5);

		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.add(walls, 1500);
		floor.add(MetaBlock.of(Blocks.NETHERRACK), 1000);
		floor.add(MetaBlock.of(Blocks.CRIMSON_NYLIUM), 100);
		floor.add(MetaBlock.of(Blocks.REDSTONE_BLOCK), 10);
		floor.add(MetaBlock.of(Blocks.GOLD_BLOCK), 2);
		floor.add(MetaBlock.of(Blocks.DIAMOND_BLOCK), 1);
		
		MetaStair stair = Stair.of(Stair.NETHERBRICK);
		MetaBlock obsidian = MetaBlock.of(Blocks.OBSIDIAN);
		MetaBlock redBrick = MetaBlock.of(Blocks.RED_NETHER_BRICKS);
		
		IBlockFactory lightstone = MetaBlock.of(Blocks.GLOWSTONE);
		IBlockFactory liquid = MetaBlock.of(Blocks.LAVA);
		
		this.primary = BlockSet.builder()
				.walls(walls)
				.floor(floor)
				.pillar(obsidian)
				.stair(stair)
				.slab(Slab.of(Slab.NETHER_BRICK))
				.door(Door.of(DoorType.CRIMSON))
				.lightblock(lightstone)
				.liquid(liquid)
				.build();
		
		this.secondary = BlockSet.builder()
				.walls(redBrick)
				.floor(floor)
				.pillar(obsidian)
				.stair(stair)
				.slab(Slab.of(Slab.RED_NETHER_BRICK))
				.door(Door.of(DoorType.CRIMSON))
				.lightblock(lightstone)
				.liquid(liquid)
				.build();
				
	}
	
	@Override
	public String getName() {
		return Theme.NETHER.name();
	}
	
}
