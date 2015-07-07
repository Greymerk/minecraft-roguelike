package greymerk.roguelike.dungeon.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import net.minecraft.block.BlockSandStone;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

public class ThemeEthoTower extends ThemeBase{

	public ThemeEthoTower(){
		
		MetaBlock primaryWall = ColorBlock.get(Blocks.stained_hardened_clay, EnumDyeColor.CYAN);
		
		MetaBlock stair = new MetaBlock(Blocks.sandstone_stairs);

		
		MetaBlock secondaryWall = new MetaBlock(Blocks.sandstone);
		secondaryWall.withProperty(BlockSandStone.field_176297_a, BlockSandStone.EnumType.SMOOTH);
		
		this.primary = new BlockSet(primaryWall, primaryWall, stair, primaryWall);
		
		this.secondary = new BlockSet(secondaryWall, secondaryWall, stair, secondaryWall);;
	}

}
