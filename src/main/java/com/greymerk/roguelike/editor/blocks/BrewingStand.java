package com.greymerk.roguelike.editor.blocks;

import java.util.List;
import java.util.stream.IntStream;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.util.math.RandHelper;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;

public enum BrewingStand {

	LEFT(0), MIDDLE(1), RIGHT(2), INGREDIENT(3), FUEL(4);
	
	public static final List<BrewingStand> slots = List.of(LEFT, MIDDLE, RIGHT);
	
	private int id;
	BrewingStand(int id){
		this.id = id;
	}
	
	public static void generate(IWorldEditor editor, Random rand, Coord pos, int fuel, List<ItemStack> potions){
		editor.setBlockEntity(pos, MetaBlock.of(Blocks.BREWING_STAND), BrewingStandBlockEntity.class).ifPresent(stand -> {
			List<BrewingStand> shuffledSlots = slots.stream()
				.sorted(RandHelper.randomizer(rand))
				.toList();
			
			IntStream
				.range(0, Math.min(shuffledSlots.size(), potions.size()))
				.forEach(i -> {
					stand.setStack(shuffledSlots.get(i).id, potions.get(i));
				});
			
			if(fuel > 0) stand.setStack(FUEL.id, new ItemStack(Items.BLAZE_POWDER, fuel));
		});
	}
}
