package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.treasure.loot.Banner;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class WallBanner {
	
	public static void generate(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		WallBanner.generate(editor, Banner.get(rand), origin, dir);
	}
	
	public static void generate(IWorldEditor editor, ItemStack banner, Coord origin, Cardinal dir) {
		Block b = Blocks.BLACK_WALL_BANNER;
		MetaBlock block = new MetaBlock(b);
		block.withProperty(HorizontalFacingBlock.FACING, Cardinal.facing(dir));
		block.set(editor, origin);
		
		BlockEntity be = editor.getBlockEntity(origin);
		if(be == null) return;
		if(!(be instanceof BannerBlockEntity)) return;
		BannerBlockEntity bannerEntity = (BannerBlockEntity)be;
		bannerEntity.readFrom(banner);
		bannerEntity.markDirty();
	}	
}
