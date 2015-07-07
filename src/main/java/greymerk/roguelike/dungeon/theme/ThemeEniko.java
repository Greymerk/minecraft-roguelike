package greymerk.roguelike.dungeon.theme;

import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

public class ThemeEniko extends ThemeBase{

	public ThemeEniko(){
		
		BlockStripes floor = new BlockStripes();
		floor.addBlock(ColorBlock.get(Blocks.stained_hardened_clay, EnumDyeColor.LIGHT_BLUE));
		floor.addBlock(ColorBlock.get(Blocks.stained_hardened_clay, EnumDyeColor.WHITE));
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.stonebrick), 100);
		MetaBlock cracked = new MetaBlock(Blocks.stonebrick);
		cracked.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
		walls.addBlock(cracked, 1);
		MetaBlock mossy = new MetaBlock(Blocks.stonebrick);
		mossy.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
		walls.addBlock(new MetaBlock(mossy), 5);
		
		
		
		MetaBlock stair = new MetaBlock(Blocks.stone_brick_stairs);
		MetaBlock pillar = new MetaBlock(Blocks.double_stone_slab);
		pillar.withProperty(BlockStoneSlab.SEAMLESS_PROP, true);
		pillar.withProperty(BlockStoneSlab.VARIANT_PROP, BlockStoneSlab.EnumType.STONE);
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		
		this.secondary =  primary;
	}
}
