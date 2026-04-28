package com.greymerk.roguelike.editor.blocks;

import com.greymerk.roguelike.dungeon.Difficulty;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.treasure.loot.Loot;
import com.greymerk.roguelike.util.IWeighted;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;

public class DecoratedPot {

	public static void set(IWorldEditor editor, RandomSource rand, Difficulty diff, Coord origin) {
		set(editor, rand, diff, origin, Loot.PRECIOUS);
	}
	
	public static void set(IWorldEditor editor, RandomSource rand, Difficulty diff, Coord origin, Loot type) {
		editor.setBlockEntity(origin, MetaBlock.of(Blocks.DECORATED_POT), DecoratedPotBlockEntity.class).ifPresent(potEntity -> {
			IWeighted<Item> faceroll = getFaceRoller();
			
			PotDecorations sherds = new PotDecorations(faceroll.get(rand), faceroll.get(rand), faceroll.get(rand), faceroll.get(rand));
			
			DataComponentPatch.Builder changes = DataComponentPatch.builder();
			changes.set(DataComponents.POT_DECORATIONS, sherds);
			
			potEntity.applyComponents(potEntity.components(), changes.build());		

			ItemStack loot = Loot.getLootItem(editor, type, rand, diff);
			potEntity.setTheItem(loot);
			
			potEntity.setChanged();	
		});
	}
	
	private static IWeighted<Item> getFaceRoller(){
		
		WeightedRandomizer<Item> faceroll = new WeightedRandomizer<Item>();
		faceroll.add(new WeightedChoice<Item>(Items.BRICK, 100));
		faceroll.add(new WeightedChoice<Item>(Items.ANGLER_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.ARCHER_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.ARMS_UP_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.BLADE_POTTERY_SHERD, 3));
		faceroll.add(new WeightedChoice<Item>(Items.BREWER_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.BURN_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.DANGER_POTTERY_SHERD, 3));
		faceroll.add(new WeightedChoice<Item>(Items.EXPLORER_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.FRIEND_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.HEART_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.HEARTBREAK_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.HOWL_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.MINER_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.MOURNER_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.PLENTY_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.PRIZE_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.SHEAF_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.SHELTER_POTTERY_SHERD, 1));
		faceroll.add(new WeightedChoice<Item>(Items.SKULL_POTTERY_SHERD, 5));
		faceroll.add(new WeightedChoice<Item>(Items.SNORT_POTTERY_SHERD, 1));
		
		return faceroll;
	}
	
}
