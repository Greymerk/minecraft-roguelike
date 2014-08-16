package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.Catacomb;
import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsFire extends DungeonBase {

	
	
	@Override
	public boolean generate(World inWorld, Random inRandom, CatacombLevelSettings settings, Cardinal[] entrances, int inOriginX, int inOriginY, int inOriginZ) {
		
		ITheme theme = settings.getTheme();

		IBlockFactory fillBlocks = theme.getPrimaryWall();
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// clear air
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 6, inOriginY, inOriginZ - 6, inOriginX + 6, inOriginY + 3, inOriginZ + 6, air);
		
		// floor
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 7, inOriginY - 1, inOriginZ - 7, inOriginX + 7, inOriginY - 1, inOriginZ + 7, fillBlocks);
		
		// fill door walls
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 7, inOriginY - 1, inOriginZ - 4, inOriginX - 7, inOriginY + 5, inOriginZ + 4, fillBlocks, false, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 7, inOriginY - 1, inOriginZ - 4, inOriginX + 7, inOriginY + 5, inOriginZ + 4, fillBlocks, false, true);
		
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 4, inOriginY - 1, inOriginZ - 7, inOriginX + 4, inOriginY + 5, inOriginZ - 7, fillBlocks, false, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 4, inOriginY - 1, inOriginZ + 7, inOriginX + 4, inOriginY + 5, inOriginZ + 7, fillBlocks, false, true);
		
		// columns
		
		// column 1
		WorldGenPrimitive.fillRectHollow(inWorld, inRandom, inOriginX - 6, inOriginY, inOriginZ - 6, inOriginX - 4, inOriginY + 6, inOriginZ - 4, fillBlocks, true, true);
		WorldGenPrimitive.setBlock(inWorld, inOriginX - 4, inOriginY + 1, inOriginZ - 5, Blocks.iron_bars);
		WorldGenPrimitive.setBlock(inWorld, inOriginX - 4, inOriginY + 2, inOriginZ - 5, Blocks.iron_bars);
		
		WorldGenPrimitive.setBlock(inWorld, inOriginX - 5, inOriginY + 1, inOriginZ - 4, Blocks.iron_bars);
		WorldGenPrimitive.setBlock(inWorld, inOriginX - 5, inOriginY + 2, inOriginZ - 4, Blocks.iron_bars);
		
		WorldGenPrimitive.setBlock(inWorld, inOriginX - 5, inOriginY, inOriginZ - 5, Blocks.netherrack);
		WorldGenPrimitive.setBlock(inWorld, inOriginX - 5, inOriginY + 1, inOriginZ - 5, Blocks.fire);
		
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 3, inOriginY + 3, inOriginZ - 6, inOriginX - 3, inOriginY + 3, inOriginZ - 5, fillBlocks);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 6, inOriginY + 3, inOriginZ - 3, inOriginX - 5, inOriginY + 3, inOriginZ - 3, fillBlocks);
		
		// column 2
		WorldGenPrimitive.fillRectHollow(inWorld, inRandom, inOriginX - 6, inOriginY, inOriginZ + 4, inOriginX - 4, inOriginY + 6, inOriginZ + 6, fillBlocks, true, true);
		WorldGenPrimitive.setBlock(inWorld, inOriginX - 4, inOriginY + 1, inOriginZ + 5, Blocks.iron_bars);
		WorldGenPrimitive.setBlock(inWorld, inOriginX - 4, inOriginY + 2, inOriginZ + 5, Blocks.iron_bars);
		
		WorldGenPrimitive.setBlock(inWorld, inOriginX - 5, inOriginY + 1, inOriginZ + 4, Blocks.iron_bars);
		WorldGenPrimitive.setBlock(inWorld, inOriginX - 5, inOriginY + 2, inOriginZ + 4, Blocks.iron_bars);
		
		WorldGenPrimitive.setBlock(inWorld, inOriginX - 5, inOriginY, inOriginZ + 5, Blocks.netherrack);
		WorldGenPrimitive.setBlock(inWorld, inOriginX - 5, inOriginY + 1, inOriginZ + 5, Blocks.fire);
		
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 3, inOriginY + 3, inOriginZ + 5, inOriginX - 3, inOriginY + 3, inOriginZ + 6, fillBlocks);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 6, inOriginY + 3, inOriginZ + 3, inOriginX - 5, inOriginY + 3, inOriginZ + 3, fillBlocks);
		
		//column 3
		WorldGenPrimitive.fillRectHollow(inWorld, inRandom, inOriginX + 4, inOriginY, inOriginZ - 6, inOriginX + 6, inOriginY + 6, inOriginZ - 4, fillBlocks, true, true);
		WorldGenPrimitive.setBlock(inWorld, inOriginX + 4, inOriginY + 1, inOriginZ - 5, Blocks.iron_bars);
		WorldGenPrimitive.setBlock(inWorld, inOriginX + 4, inOriginY + 2, inOriginZ - 5, Blocks.iron_bars);
		
		WorldGenPrimitive.setBlock(inWorld, inOriginX + 5, inOriginY + 1, inOriginZ - 4, Blocks.iron_bars);
		WorldGenPrimitive.setBlock(inWorld, inOriginX + 5, inOriginY + 2, inOriginZ - 4, Blocks.iron_bars);
		
		WorldGenPrimitive.setBlock(inWorld, inOriginX + 5, inOriginY, inOriginZ - 5, Blocks.netherrack);
		WorldGenPrimitive.setBlock(inWorld, inOriginX + 5, inOriginY + 1, inOriginZ - 5, Blocks.fire);
		
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 3, inOriginY + 3, inOriginZ - 6, inOriginX + 3, inOriginY + 3, inOriginZ - 5, fillBlocks);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 5, inOriginY + 3, inOriginZ - 3, inOriginX + 6, inOriginY + 3, inOriginZ - 3, fillBlocks);
		
		// column 4
		WorldGenPrimitive.fillRectHollow(inWorld, inRandom, inOriginX + 4, inOriginY, inOriginZ + 4, inOriginX + 6, inOriginY + 6, inOriginZ + 6, fillBlocks, true, true);
		WorldGenPrimitive.setBlock(inWorld, inOriginX + 4, inOriginY + 1, inOriginZ + 5, Blocks.iron_bars);
		WorldGenPrimitive.setBlock(inWorld, inOriginX + 4, inOriginY + 2, inOriginZ + 5, Blocks.iron_bars);
		
		WorldGenPrimitive.setBlock(inWorld, inOriginX + 5, inOriginY + 1, inOriginZ + 4, Blocks.iron_bars);
		WorldGenPrimitive.setBlock(inWorld, inOriginX + 5, inOriginY + 2, inOriginZ + 4, Blocks.iron_bars);
		
		WorldGenPrimitive.setBlock(inWorld, inOriginX + 5, inOriginY, inOriginZ + 5, Blocks.netherrack);
		WorldGenPrimitive.setBlock(inWorld, inOriginX + 5, inOriginY + 1, inOriginZ + 5, Blocks.fire);
		
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 3, inOriginY + 3, inOriginZ + 5, inOriginX + 3, inOriginY + 3, inOriginZ + 6, fillBlocks);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 5, inOriginY + 3, inOriginZ + 3, inOriginX + 6, inOriginY + 3, inOriginZ + 3, fillBlocks);
		
		// mid ceiling
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 6, inOriginY + 4, inOriginZ - 6, inOriginX + 6, inOriginY + 4, inOriginZ + 6, fillBlocks);
		
		// upper ceiling
		WorldGenPrimitive.fillRectHollow(inWorld, inRandom, inOriginX - 4, inOriginY + 4, inOriginZ - 4, inOriginX + 4, inOriginY + 8, inOriginZ + 4, fillBlocks, false, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 3, inOriginY + 4, inOriginZ - 3, inOriginX + 3, inOriginY + 7, inOriginZ + 3, air);
		
		
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 4, inOriginY + 4, inOriginZ - 2, inOriginX - 4, inOriginY + 4, inOriginZ + 2, air);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 4, inOriginY + 4, inOriginZ - 2, inOriginX + 4, inOriginY + 4, inOriginZ + 2, air);
		
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 2, inOriginY + 4, inOriginZ - 4, inOriginX + 2, inOriginY + 4, inOriginZ - 4, air);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 2, inOriginY + 4, inOriginZ + 4, inOriginX + 2, inOriginY + 4, inOriginZ + 4, air);
		
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 3, inOriginY + 5, inOriginZ - 3, inOriginX - 3, inOriginY + 7, inOriginZ - 3, fillBlocks, false, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 3, inOriginY + 5, inOriginZ + 3, inOriginX - 3, inOriginY + 7, inOriginZ + 3, fillBlocks, false, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 3, inOriginY + 5, inOriginZ - 3, inOriginX + 3, inOriginY + 7, inOriginZ - 3, fillBlocks, false, true);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX + 3, inOriginY + 5, inOriginZ + 3, inOriginX + 3, inOriginY + 7, inOriginZ + 3, fillBlocks, false, true);
		
		// ceiling cross
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 5, inOriginY + 5, inOriginZ, inOriginX + 5, inOriginY + 5, inOriginZ, fillBlocks);
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX, inOriginY + 5, inOriginZ - 5, inOriginX, inOriginY + 5, inOriginZ + 5, fillBlocks);
		
		WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 1, inOriginY + 5, inOriginZ - 1, inOriginX + 1, inOriginY + 5, inOriginZ + 1, fillBlocks);
		
		if(Catacomb.getLevel(inOriginY) == 4){
			Spawner.generate(inWorld, inRandom, settings.getSpawners(), inOriginX, inOriginY + 5, inOriginZ, 4, Spawner.BLAZE);
			WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 1, inOriginY + 6, inOriginZ - 1, inOriginX + 1, inOriginY + 6, inOriginZ + 1, new MetaBlock(Blocks.stone_slab, 6), true, true);
			WorldGenPrimitive.fillRectSolid(inWorld, inRandom, inOriginX - 1, inOriginY + 4, inOriginZ - 1, inOriginX + 1, inOriginY + 4, inOriginZ + 1, new MetaBlock(Blocks.stone_slab, 14), true, true);
		} else {
			WorldGenPrimitive.setBlock(inWorld, inOriginX, inOriginY + 5, inOriginZ, air);	
		}
		
		return false;
	}
	
	public int getSize(){
		return 10;
	}

}
