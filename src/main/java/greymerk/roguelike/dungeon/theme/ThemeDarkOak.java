package greymerk.roguelike.dungeon.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.blocks.Log;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;

public class ThemeDarkOak extends ThemeBase{

	public ThemeDarkOak(){
		
		MetaBlock walls = new MetaBlock(Blocks.planks);
		walls.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.DARK_OAK);
		MetaBlock stair = new MetaBlock(Blocks.dark_oak_stairs);
		MetaBlock pillar = Log.getLog(Log.DARKOAK);
		
		this.primary = new BlockSet(walls, stair, pillar);
		
		MetaBlock secondaryWalls = new MetaBlock(Blocks.planks);
		secondaryWalls.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.OAK);
		
		this.secondary = new BlockSet(secondaryWalls, stair, pillar);

	}
}
