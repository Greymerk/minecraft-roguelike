package com.greymerk.roguelike.editor.blocks;

import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.treasure.loot.Enchant;
import com.greymerk.roguelike.util.math.RandHelper;

public class BookShelf {

	public static void set(IWorldEditor editor, RandomSource rand, Difficulty diff, Coord origin, Cardinal dir) {
		BookShelf.set(editor, rand, diff, origin, dir, rand.nextInt(3) + 1);
	}
	
	public static void set(IWorldEditor editor, RandomSource rand, Difficulty diff, Coord origin, Cardinal dir, int bookCount) {
		
		editor.setBlockEntity(origin, 
				MetaBlock.of(Blocks.CHISELED_BOOKSHELF)
					.with(HorizontalDirectionalBlock.FACING, Cardinal.facing(Cardinal.reverse(dir))),
				ChiseledBookShelfBlockEntity.class).ifPresent(shelf -> {
			List<Integer> slots = getSlots(rand, bookCount);
			
			int firstSlot = slots.getFirst();
			shelf.setItem(firstSlot, Enchant.getBook(editor.getInfo().getRegistryManager(), rand, diff));
			
			if(slots.size() > 1) {
				slots.subList(1, slots.size()).forEach(i -> {
					shelf.setItem(i, Items.BOOK.getDefaultInstance());
				});
			}
			
			shelf.setChanged();	
		});
	}
	
	private static List<Integer> getSlots(RandomSource rand, int slotCount){
		return IntStream.rangeClosed(0, 5)
			.boxed()
			.sorted(RandHelper.randomizer(rand))
			.limit(Math.min(slotCount, 6))
			.toList();
	}
}
