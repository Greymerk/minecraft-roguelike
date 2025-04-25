package com.greymerk.roguelike.editor.blocks;

import java.util.List;
import java.util.Optional;
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
import net.minecraft.util.math.random.Random;

public class BookShelf {

	public static void set(IWorldEditor editor, Random rand, Difficulty diff, Coord origin, Cardinal dir) {
		
		MetaBlock.of(Blocks.CHISELED_BOOKSHELF)
			.with(HorizontalFacingBlock.FACING, Cardinal.facing(Cardinal.reverse(dir)))
			.set(editor, origin);
		
		Optional<BlockEntity> obe = editor.getBlockEntity(origin);
		if(obe.isEmpty()) return;
		BlockEntity be  = obe.get();
		if(!(be instanceof ChiseledBookshelfBlockEntity)) return;
		ChiseledBookshelfBlockEntity shelf = (ChiseledBookshelfBlockEntity)be;
		
		getSlots(rand).forEach(i -> {
			shelf.setStack(i, Enchant.getBook(editor.getInfo().getRegistryManager(), rand, diff));		
		});
		
		shelf.markDirty();
	}
	
	private static List<Integer> getSlots(Random rand){
		return IntStream.rangeClosed(0, 5)
			.boxed()
			.sorted(RandHelper.randomizer(rand))
			.limit(slotCount(rand))
			.toList();
	}
	
	private static int slotCount(Random rand) {
		if(rand.nextInt(10) == 0) return 3;
		if(rand.nextInt(3) == 0) return 2;
		return 1;
	}
	
}
