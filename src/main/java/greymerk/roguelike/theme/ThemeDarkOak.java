package greymerk.roguelike.theme;

import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.Wood;
import greymerk.roguelike.worldgen.blocks.WoodBlock;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;

public class ThemeDarkOak extends ThemeBase{

	public ThemeDarkOak(){
		
		MetaBlock walls = new MetaBlock(Blocks.planks);
		walls.withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.DARK_OAK);
		MetaStair stair = new MetaStair(StairType.DARKOAK);
		MetaBlock pillar = Log.getLog(Wood.DARKOAK);
		
		this.primary = new BlockSet(walls, stair, pillar);
		MetaBlock secondaryWalls = Wood.get(Wood.DARKOAK, WoodBlock.PLANK);
		this.secondary = new BlockSet(secondaryWalls, stair, pillar);

	}
}
