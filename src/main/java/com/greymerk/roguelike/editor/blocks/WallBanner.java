package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.treasure.loot.items.Banner;

import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class WallBanner {
	
	public static void generate(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		WallBanner.generate(editor, Banner.get(editor.getInfo().getRegistryManager(), rand), origin, dir);
	}
	
	public static void generate(IWorldEditor editor, ItemStack banner, Coord origin, Cardinal dir) {
		editor.setBlockEntity(origin,
			MetaBlock.of(Blocks.BLACK_WALL_BANNER)
				.with(HorizontalFacingBlock.FACING, Cardinal.facing(dir)), 
			BannerBlockEntity.class).ifPresent(bannerEntity -> {
				bannerEntity.readComponents(banner);
				bannerEntity.markDirty();	
			});
	}	
}
