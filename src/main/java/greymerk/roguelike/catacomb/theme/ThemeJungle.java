package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.Log;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;

public class ThemeJungle extends ThemeBase{

	public ThemeJungle(){
		MetaBlock cracked = new MetaBlock(Blocks.stonebrick);
		cracked.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
		MetaBlock mossy = new MetaBlock(Blocks.stonebrick);
		mossy.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.MOSSY);
		MetaBlock chisel = new MetaBlock(Blocks.stonebrick);
		chisel.withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CHISELED);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.mossy_cobblestone), 50);
		walls.addBlock(mossy, 30);
		walls.addBlock(cracked, 20);
		walls.addBlock(chisel, 15);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_stairs);
		
		MetaBlock pillar = chisel;
		MetaBlock pillar2 = Log.getLog(Log.JUNGLE);
		
		BlockJumble stairJumble = new BlockJumble();
		stairJumble.addBlock(new MetaBlock(Blocks.stone_stairs.getDefaultState().withProperty(BlockStairs.FACING, Cardinal.getFacing(Cardinal.NORTH))));
		stairJumble.addBlock(new MetaBlock(Blocks.stone_stairs.getDefaultState().withProperty(BlockStairs.FACING, Cardinal.getFacing(Cardinal.SOUTH))));
		stairJumble.addBlock(new MetaBlock(Blocks.stone_stairs.getDefaultState().withProperty(BlockStairs.FACING, Cardinal.getFacing(Cardinal.EAST))));
		stairJumble.addBlock(new MetaBlock(Blocks.stone_stairs.getDefaultState().withProperty(BlockStairs.FACING, Cardinal.getFacing(Cardinal.WEST))));
		
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(stairJumble, 1);
		floor.addBlock(walls, 3);
		
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		this.secondary = new BlockSet(chisel, stair, pillar2);
	}
}
