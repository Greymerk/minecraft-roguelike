package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.MetaBlock;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.item.EnumDyeColor;

public class ColorBlock {

	public static MetaBlock get(Block type, EnumDyeColor color){
		MetaBlock block = new MetaBlock(type);
		block.withProperty(BlockColored.COLOR, color);
		return block;
	}
	
	public static MetaBlock get(Block type, Random rand){
		EnumDyeColor[] colors = EnumDyeColor.values();
		EnumDyeColor choice = colors[rand.nextInt(colors.length)];
		return get(type, choice);
	}
	
}
