package com.greymerk.roguelike.editor.blocks;

import java.util.List;
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
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public class BookShelf {

	public static void set(IWorldEditor editor, Random rand, Difficulty diff, Coord origin, Cardinal dir) {
		BookShelf.set(editor, rand, diff, origin, dir, rand.nextInt(5) + 1);
	}
	
	public static void set(IWorldEditor editor, Random rand, Difficulty diff, Coord origin, Cardinal dir, int bookCount) {
		
		editor.setBlockEntity(origin, 
				MetaBlock.of(Blocks.CHISELED_BOOKSHELF)
					.with(HorizontalFacingBlock.FACING, Cardinal.facing(Cardinal.reverse(dir))),
				ChiseledBookshelfBlockEntity.class).ifPresent(shelf -> {
			getSlots(rand, bookCount).forEach(i -> {
				ItemStack book = rand.nextBoolean()
						? Enchant.getBook(editor.getInfo().getRegistryManager(), rand, diff)
						: Items.BOOK.getDefaultStack();
				shelf.setStack(i, book);		
			});
			
			shelf.markDirty();	
		});
	}
	
	private static List<Integer> getSlots(Random rand, int slotCount){
		return IntStream.rangeClosed(0, 5)
			.boxed()
			.sorted(RandHelper.randomizer(rand))
			.limit(Math.min(slotCount, 6))
			.toList();
	}
}
