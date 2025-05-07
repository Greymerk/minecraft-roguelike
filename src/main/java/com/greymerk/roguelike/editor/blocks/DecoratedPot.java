package com.greymerk.roguelike.editor.blocks;

import java.util.Optional;

import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.MetaBlock;
import com.greymerk.roguelike.util.IWeighted;
import com.greymerk.roguelike.util.WeightedChoice;
import com.greymerk.roguelike.util.WeightedRandomizer;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.block.entity.DecoratedPotBlockEntity.Sherds;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;

public class DecoratedPot {
	
	public static void set(IWorldEditor editor, Random rand, Coord origin) {
		
		MetaBlock pot = MetaBlock.of(Blocks.DECORATED_POT);

		boolean success = pot.set(editor, origin);
		
		if(!success) return;
		
		Optional<BlockEntity> obe = editor.getBlockEntity(origin);
		
		if(!obe.isPresent()) return;
		BlockEntity be = obe.get();
		if(!(be instanceof DecoratedPotBlockEntity)) return;
		
		DecoratedPotBlockEntity potEntity = (DecoratedPotBlockEntity)be;
		
		
		IWeighted<Item> faceroll = getFaceRoller();
		
		Sherds sherds = new Sherds(faceroll.get(rand), faceroll.get(rand), faceroll.get(rand), faceroll.get(rand));
		NbtCompound nbt = sherds.toNbt(new NbtCompound());
		
		potEntity.readNbt(nbt);
		potEntity.markDirty();
	}
	
	private static IWeighted<Item> getFaceRoller(){
		
		WeightedRandomizer<Item> faceroll = new WeightedRandomizer<Item>();
		faceroll.add(new WeightedChoice<Item>(Items.BRICK, 50));
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
