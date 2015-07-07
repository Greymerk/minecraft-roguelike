package greymerk.roguelike.dungeon.theme;

import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.MetaBlock;
import net.minecraft.block.BlockSandStone;
import net.minecraft.init.Blocks;

public class ThemePyramid  extends ThemeBase{

	public ThemePyramid(){
		
		MetaBlock smooth = new MetaBlock(Blocks.sandstone);
		smooth.withProperty(BlockSandStone.field_176297_a, BlockSandStone.EnumType.SMOOTH);
		MetaBlock chisel = new MetaBlock(Blocks.sandstone);
		smooth.withProperty(BlockSandStone.field_176297_a, BlockSandStone.EnumType.CHISELED);
		
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(smooth, 100);
		walls.addBlock(new MetaBlock(Blocks.sandstone), 10);
		walls.addBlock(chisel, 5);
		walls.addBlock(new MetaBlock(Blocks.sand), 5);
		
		MetaBlock stair = new MetaBlock(Blocks.sandstone_stairs);
		
		MetaBlock pillar = smooth;
		
		this.primary = new BlockSet(walls, stair, pillar);
		
		MetaBlock SegmentWall = chisel;
		
		this.secondary =  new BlockSet(SegmentWall, stair, pillar);

	}
	
}
