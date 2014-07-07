package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Log;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsWood extends DungeonBase {
	



	@Override
	public boolean generate(World world, Random rand, ITheme theme, Cardinal[] entrances, int x, int y, int z) {
		
		final int HEIGHT = 3;
		final int WIDTH = rand.nextInt(2) + 2;
		final int LENGTH = rand.nextInt(2) + 3;
		
		int woodType = rand.nextInt(Log.values().length);
		Log type = Log.values()[woodType];
		
		MetaBlock pillar = Log.getLog(type);
		MetaBlock planks = new MetaBlock(Blocks.planks, woodType);
		
		MetaBlock glowstone = new MetaBlock(Blocks.glowstone);
		MetaBlock air = new MetaBlock(Blocks.air);
		
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - WIDTH, y, z - LENGTH, x + WIDTH, y + HEIGHT, z + LENGTH, air);
		WorldGenPrimitive.fillRectHollow(world, rand, x - WIDTH - 1, y - 1, z - LENGTH - 1, x + WIDTH + 1, y + HEIGHT + 1, z + LENGTH + 1, planks, false, true);
		
		// log beams
		WorldGenPrimitive.fillRectSolid(world, rand, x - WIDTH, y, z - LENGTH, x - WIDTH, y + HEIGHT, z - LENGTH, pillar, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - WIDTH, y, z + LENGTH, x - WIDTH, y + HEIGHT, z + LENGTH, pillar, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + WIDTH, y, z - LENGTH, x + WIDTH, y + HEIGHT, z - LENGTH, pillar, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + WIDTH, y, z + LENGTH, x + WIDTH, y + HEIGHT, z + LENGTH, pillar, true, true);

		// glowstone
		WorldGenPrimitive.setBlock(world, x - WIDTH + 1, y - 1, z - LENGTH + 1, glowstone);
		WorldGenPrimitive.setBlock(world, x - WIDTH + 1, y - 1, z + LENGTH - 1, glowstone);
		WorldGenPrimitive.setBlock(world, x + WIDTH - 1, y - 1, z - LENGTH + 1, glowstone);
		WorldGenPrimitive.setBlock(world, x + WIDTH - 1, y - 1, z + LENGTH - 1, glowstone);
		
		WorldGenPrimitive.setBlock(world, rand, x, y, z, planks, true, true);
		WorldGenPrimitive.setBlock(world, x, y + 1, z, Blocks.cake);
		
		List<Coord> space = new ArrayList<Coord>();
		space.add(new Coord(x - WIDTH, y, z - LENGTH + 1));
		space.add(new Coord(x - WIDTH, y, z + LENGTH - 1));
		space.add(new Coord(x + WIDTH, y, z - LENGTH + 1));
		space.add(new Coord(x + WIDTH, y, z + LENGTH - 1));
		
		TreasureChest.generate(world, rand, space, TreasureChest.FOOD);
		
		return true;
	}
	
	public int getSize(){
		return 6;
	}

}
