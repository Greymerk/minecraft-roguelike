package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.treasure.loot.items.Banner;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;

public class WallBanner {
	
	public static void generate(IWorldEditor editor, RandomSource rand, Coord origin, Cardinal dir) {
		WallBanner.generate(editor, Banner.get(editor.getInfo().getRegistryManager(), rand), origin, dir);
	}
	
	public static void generate(IWorldEditor editor, ItemStack banner, Coord origin, Cardinal dir) {
		editor.setBlockEntity(origin,
			MetaBlock.of(Blocks.WALL_BANNER.black())
				.with(HorizontalDirectionalBlock.FACING, Cardinal.facing(dir)), 
			BannerBlockEntity.class).ifPresent(bannerEntity -> {
				bannerEntity.applyComponentsFromItemStack(banner);
				bannerEntity.setChanged();	
			});
	}	
}
