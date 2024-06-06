package com.greymerk.roguelike.editor.blocks;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public class BookShelf {

	public static void set(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		
		MetaBlock.of(Blocks.CHISELED_BOOKSHELF)
			.with(HorizontalFacingBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)))
			.set(editor, origin);
		
		BlockEntity be = editor.getBlockEntity(origin);
		
		if(be == null) return;
		if(!(be instanceof ChiseledBookshelfBlockEntity)) return;
		
		ChiseledBookshelfBlockEntity shelf = (ChiseledBookshelfBlockEntity)be;
		
		getSlots(rand).forEach(i -> {
			shelf.setStack(i, createBook(editor, rand, Difficulty.fromY(origin.getY())));		
		});
		
		shelf.markDirty();
	}
	
	private static ItemStack createBook(IWorldEditor editor, Random rand, Difficulty diff) {
		
		if(rand.nextInt(6) == 0) return Enchant.getBook(Enchant.MENDING, rand);
		return Enchant.getBook(editor.getFeatureSet(), rand, diff);
	}
	
	private static List<Integer> getSlots(Random rand){
		List<Integer> slots = IntStream.rangeClosed(0, 5).boxed().collect(Collectors.toList());
		RandHelper.shuffle(slots, rand);
		int count = rand.nextBetween(1, 3);
		return slots.subList(0, count);
	}
	
}
