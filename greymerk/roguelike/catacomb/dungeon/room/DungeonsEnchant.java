package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.IDungeon;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class DungeonsEnchant implements IDungeon{

	@Override
	public boolean generate(World world, Random rand, ITheme theme, int x, int y, int z) {
		
		
		// clear space
		WorldGenPrimitive.fillRectSolid(world, x - 5, y, z - 5, x + 5, y + 4, z + 5, 0);
		WorldGenPrimitive.fillRectSolid(world, x - 3, y + 5, z - 3, x + 3, y + 5, z + 3, 0);
		
		
		// doors
		WorldGenPrimitive.fillRectSolid(world, x - 6, y - 1, z - 2, x - 6, y + 3, z - 2, Block.blockNetherQuartz.blockID, 2, 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 6, y - 1, z + 2, x - 6, y + 3, z + 2, Block.blockNetherQuartz.blockID, 2, 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x + 6, y - 1, z - 2, x + 6, y + 3, z - 2, Block.blockNetherQuartz.blockID, 2, 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 6, y - 1, z + 2, x + 6, y + 3, z + 2, Block.blockNetherQuartz.blockID, 2, 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y - 1, z - 6, x - 2, y + 3, z - 6, Block.blockNetherQuartz.blockID, 2, 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y - 1, z - 6, x + 2, y + 3, z - 6, Block.blockNetherQuartz.blockID, 2, 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y - 1, z + 6, x - 2, y + 3, z + 6, Block.blockNetherQuartz.blockID, 2, 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y - 1, z + 6, x + 2, y + 3, z + 6, Block.blockNetherQuartz.blockID, 2, 2, true, true);
		
		
		WorldGenPrimitive.fillRectSolid(world, x - 6, y - 1, z - 1, x - 6, y + 3, z + 1, Block.blockNetherQuartz.blockID, 1, 2, false, true);
		WorldGenPrimitive.fillRectSolid(world, x + 6, y - 1, z - 1, x + 6, y + 3, z + 1, Block.blockNetherQuartz.blockID, 1, 2, false, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z - 6, x + 1, y + 3, z - 6, Block.blockNetherQuartz.blockID, 1, 2, false, true);
		WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z + 6, x + 1, y + 3, z + 6, Block.blockNetherQuartz.blockID, 1, 2, false, true);
		
		// pillars
		WorldGenPrimitive.fillRectSolid(world, x - 4, y, z - 4, x - 4, y + 4, z - 4, Block.blockNetherQuartz.blockID, 2, 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 4, y, z + 4, x - 4, y + 4, z + 4, Block.blockNetherQuartz.blockID, 2, 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 4, y, z - 4, x + 4, y + 4, z - 4, Block.blockNetherQuartz.blockID, 2, 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 4, y, z + 4, x + 4, y + 4, z + 4, Block.blockNetherQuartz.blockID, 2, 2, true, true);
		
		MetaBlock decor = new MetaBlock(Block.stainedClay.blockID, rand.nextInt(16));
		MetaBlock lining = new MetaBlock(Block.stainedClay.blockID, rand.nextInt(16));
		
		//lapis shell
		WorldGenPrimitive.fillRectSolid(world, x - 5, y, z - 3, x - 5, y + 4, z - 3, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y, z + 3, x - 5, y + 4, z + 3, decor, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x + 5, y, z - 3, x + 5, y + 4, z - 3, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 5, y, z + 3, x + 5, y + 4, z + 3, decor, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 3, y, z - 5, x - 3, y + 4, z - 5, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y, z - 5, x + 3, y + 4, z - 5, decor, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 3, y, z + 5, x - 3, y + 4, z + 5, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y, z + 5, x + 3, y + 4, z + 5, decor, true, true);
		
		// tops & bottoms
		
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z - 5, x - 3, y - 1, z - 3, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y + 4, z - 5, x - 3, y + 4, z - 3, decor, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z + 3, x - 3, y - 1, z + 5, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y + 4, z + 3, x - 3, y + 4, z + 5, decor, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x + 3, y - 1, z + 3, x + 5, y - 1, z + 5, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y + 4, z + 3, x + 5, y + 4, z + 5, decor, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x + 3, y - 1, z - 5, x + 5, y - 1, z - 3, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y + 4, z - 5, x + 5, y + 4, z - 3, decor, true, true);
		
		// arch beams
		
		WorldGenPrimitive.fillRectSolid(world, x - 4, y + 4, z - 2, x - 4, y + 4, z + 2, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y + 3, z - 2, x - 5, y + 3, z + 2, decor, true, true);

		WorldGenPrimitive.fillRectSolid(world, x + 4, y + 4, z - 2, x + 4, y + 4, z + 2, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 5, y + 3, z - 2, x + 5, y + 3, z + 2, decor, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 4, z - 4, x + 2, y + 4, z - 4, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 3, z - 5, x + 2, y + 3, z - 5, decor, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 4, z + 4, x + 2, y + 4, z + 4, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 3, z + 5, x + 2, y + 3, z + 5, decor, true, true);
		
		// roof
		WorldGenPrimitive.fillRectSolid(world, x - 3, y + 5, z - 3, x + 3, y + 5, z + 3, lining, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 2, y + 5, z - 1, x + 2, y + 5, z + 1, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 5, z - 2, x + 1, y + 5, z - 2, decor, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 5, z + 2, x + 1, y + 5, z + 2, decor, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 1, y + 5, z, x + 1, y + 5, z, Block.glowStone.blockID);
		WorldGenPrimitive.setBlock(world, x, y + 5, z - 1, Block.glowStone.blockID);
		WorldGenPrimitive.setBlock(world, x, y + 5, z + 1, Block.glowStone.blockID);
		
		// enchanting floor
		
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z - 1, x - 3, y - 1, z + 1, lining, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y - 1, z - 1, x + 5, y - 1, z + 1, lining, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z - 5, x + 1, y - 1, z - 3, lining, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 1, y - 1, z + 3, x + 1, y - 1, z + 5, lining, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z + 2, x - 3, y - 1, z + 2, Block.blockNetherQuartz.blockID, 1, 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 5, y - 1, z - 2, x - 3, y - 1, z - 2, Block.blockNetherQuartz.blockID, 1, 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x + 3, y - 1, z + 2, x + 5, y - 1, z + 2, Block.blockNetherQuartz.blockID, 1, 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 3, y - 1, z - 2, x + 5, y - 1, z - 2, Block.blockNetherQuartz.blockID, 1, 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y - 1, z - 5, x - 2, y - 1, z - 3, Block.blockNetherQuartz.blockID, 1, 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y - 1, z - 5, x + 2, y - 1, z - 3, Block.blockNetherQuartz.blockID, 1, 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y - 1, z + 3, x - 2, y - 1, z + 5, Block.blockNetherQuartz.blockID, 1, 2, true, true);
		WorldGenPrimitive.fillRectSolid(world, x + 2, y - 1, z + 3, x + 2, y - 1, z + 5, Block.blockNetherQuartz.blockID, 1, 2, true, true);
		
		WorldGenPrimitive.fillRectSolid(world, x - 2, y - 1, z - 2, x + 2, y - 1, z + 2, decor, true, true);
		
		WorldGenPrimitive.setBlock(world, x, y - 1, z, Block.glowStone.blockID);
		
		if(RogueConfig.getBoolean(RogueConfig.GENEROUS)){
			WorldGenPrimitive.setBlock(world, x, y, z, Block.enchantmentTable.blockID);
		} else {
			TreasureChest.generate(world, rand, x, y, z, TreasureChest.ENCHANTING, 4, false);
		}
		return false;
	}	
	
	public int getSize(){
		return 8;
	}
}
