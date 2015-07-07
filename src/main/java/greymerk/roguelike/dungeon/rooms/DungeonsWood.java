package greymerk.roguelike.dungeon.rooms;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import greymerk.roguelike.worldgen.blocks.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsWood extends DungeonBase {
	



	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		final int HEIGHT = 3;
		final int WIDTH = rand.nextInt(2) + 2;
		final int LENGTH = rand.nextInt(2) + 3;

		MetaBlock pillar = Log.getLog(Log.values()[rand.nextInt(Log.values().length)]);
		MetaBlock planks = new MetaBlock(Blocks.planks);
		planks.withProperty(BlockPlanks.VARIANT_PROP, pillar.getValue(BlockPlanks.VARIANT_PROP));
		
		MetaBlock glowstone = new MetaBlock(Blocks.glowstone);
		MetaBlock air = new MetaBlock(Blocks.air);
		
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - WIDTH, y, z - LENGTH, x + WIDTH, y + HEIGHT, z + LENGTH, air);
		WorldGenPrimitive.fillRectHollow(world, rand, new Coord(x - WIDTH - 1, y - 1, z - LENGTH - 1), new Coord(x + WIDTH + 1, y + HEIGHT + 1, z + LENGTH + 1), planks, false, true);
		
		// log beams
		WorldGenPrimitive.fillRectSolid(world, rand, x - WIDTH, y, z - LENGTH, x - WIDTH, y + HEIGHT, z - LENGTH, pillar, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - WIDTH, y, z + LENGTH, x - WIDTH, y + HEIGHT, z + LENGTH, pillar, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + WIDTH, y, z - LENGTH, x + WIDTH, y + HEIGHT, z - LENGTH, pillar, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + WIDTH, y, z + LENGTH, x + WIDTH, y + HEIGHT, z + LENGTH, pillar, true, true);

		// glowstone
		glowstone.setBlock(world, new Coord(x - WIDTH + 1, y - 1, z - LENGTH + 1));
		glowstone.setBlock(world, new Coord(x - WIDTH + 1, y - 1, z + LENGTH - 1));
		glowstone.setBlock(world, new Coord(x + WIDTH - 1, y - 1, z - LENGTH + 1));
		glowstone.setBlock(world, new Coord(x + WIDTH - 1, y - 1, z + LENGTH - 1));
		
		WorldGenPrimitive.setBlock(world, rand, x, y, z, planks, true, true);
		new MetaBlock(Blocks.cake).setBlock(world, new Coord(x, y + 1, z));
		
		List<Coord> space = new ArrayList<Coord>();
		space.add(new Coord(x - WIDTH, y, z - LENGTH + 1));
		space.add(new Coord(x - WIDTH, y, z + LENGTH - 1));
		space.add(new Coord(x + WIDTH, y, z - LENGTH + 1));
		space.add(new Coord(x + WIDTH, y, z + LENGTH - 1));
		
		TreasureChest.generate(world, rand, settings, space, TreasureChest.FOOD);
		
		return true;
	}
	
	public int getSize(){
		return 6;
	}

}
