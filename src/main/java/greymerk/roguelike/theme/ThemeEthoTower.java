package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.DyeColor;
import greymerk.roguelike.worldgen.blocks.StairType;
import net.minecraft.block.BlockSandStone;
import net.minecraft.init.Blocks;

public class ThemeEthoTower extends ThemeBase{

	public ThemeEthoTower(){
		
		MetaBlock primaryWall = ColorBlock.get(ColorBlock.CLAY, DyeColor.CYAN);
		
		MetaStair stair = new MetaStair(StairType.SANDSTONE);

		
		MetaBlock secondaryWall = new MetaBlock(Blocks.sandstone);
		secondaryWall.withProperty(BlockSandStone.field_176297_a, BlockSandStone.EnumType.SMOOTH);
		
		this.primary = new BlockSet(primaryWall, primaryWall, stair, primaryWall);
		
		this.secondary = new BlockSet(secondaryWall, secondaryWall, stair, secondaryWall);;
	}

}
