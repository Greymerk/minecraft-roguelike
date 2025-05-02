package com.greymerk.roguelike.theme.themes;

import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.editor.blocks.Deepslate;
import com.greymerk.roguelike.editor.blocks.slab.Slab;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.blocks.stair.Stair;
import com.greymerk.roguelike.editor.factories.BlockWeightedRandom;
import com.greymerk.roguelike.theme.BlockSet;
import com.greymerk.roguelike.theme.ITheme;
import com.greymerk.roguelike.theme.Theme;
import com.greymerk.roguelike.theme.ThemeBase;

import net.minecraft.block.Blocks;

public class ThemeTiledSlate extends ThemeBase implements ITheme {

	public ThemeTiledSlate() {
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.add(Deepslate.get(Deepslate.COBBLED), 400);
		floor.add(Deepslate.get(Deepslate.COAL), 4);
		floor.add(Deepslate.get(Deepslate.REDSTONE), 3);
		floor.add(Deepslate.get(Deepslate.IRON), 3);
		floor.add(Deepslate.get(Deepslate.LAPIS), 3);
		floor.add(Deepslate.get(Deepslate.GOLD), 2);
		floor.add(Deepslate.get(Deepslate.EMERALD), 2);
		floor.add(Deepslate.get(Deepslate.DIAMOND), 1);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.add(Deepslate.get(Deepslate.TILE), 30);
		walls.add(Deepslate.get(Deepslate.CRACKED_TILE), 5);
		walls.add(Deepslate.get(Deepslate.COBBLED), 1);
		
		
		IStair stair = Stair.of(Stair.TILED_DEEPSLATE);
		
		BlockWeightedRandom pillar = new BlockWeightedRandom();
		pillar.add(Deepslate.get(Deepslate.TILE), 20);
		pillar.add(Deepslate.get(Deepslate.CRACKED_TILE), 4);
		pillar.add(Deepslate.get(Deepslate.CHISELED), 1);
		
		this.primary = BlockSet.builder()
				.walls(walls)
				.floor(floor)
				.stair(stair)
				.pillar(pillar)
				.slab(Slab.get(Slab.TILED_SLATE))
				.liquid(MetaBlock.of(Blocks.LAVA))
				.naturalFire(false)
				.build();
		this.secondary = primary;
	}
	
	@Override
	public String getName() {
		return Theme.TILEDSLATE.name();
	}
	
}
