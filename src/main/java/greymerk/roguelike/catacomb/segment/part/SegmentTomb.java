package greymerk.roguelike.catacomb.segment.part;

import greymerk.roguelike.catacomb.CatacombLevel;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentTomb extends SegmentBase {
	
	@Override
	protected void genWall(World world, Random rand, CatacombLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock stair = theme.getPrimaryStair();
		
		Coord cursor = new Coord(x, y, z);
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		cursor.add(dir, 2);
		start = new Coord(cursor);
		start.add(orth[0], 1);
		end = new Coord(cursor);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		
		start.add(dir, 1);
		end.add(dir, 1);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getSecondaryWall(), false, true);

		cursor.add(Cardinal.UP, 2);
		for(Cardinal d : orth){
			Coord c = new Coord(cursor);
			c.add(d, 1);
			stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(d), true));
			WorldGenPrimitive.setBlock(world, rand, c, stair, true, true);
		}
		
		tomb(world, rand, level.getSettings().getLoot(), theme, dir, new Coord(x, y, z));
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP);
		cursor.add(dir, 3);
		WorldGenPrimitive.setBlock(world, cursor, Blocks.quartz_block);
		
	}
	
	private static void tomb(World world, Random rand, LootSettings loot, ITheme theme, Cardinal dir, Coord pos){
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start = new Coord(pos);
		start.add(dir, 3);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 3);
		end.add(dir, 3);
		List<Coord> box = WorldGenPrimitive.getRectHollow(start, end);
		
		// make sure the box is solid wall
		for(Coord c : box){
			if(!world.getBlock(c.getX(), c.getY(), c.getZ()).getMaterial().isSolid()) return;
		}
		
		WorldGenPrimitive.fillRectHollow(world, rand, start, end, theme.getPrimaryWall(), true, true);
		if(!(rand.nextInt(3) == 0)) return;
		cursor = new Coord(pos);
		cursor.add(Cardinal.UP);
		cursor.add(dir, 4);
		Spawner.generate(world, rand, cursor, rand.nextBoolean() ? Spawner.SKELETON : Spawner.ZOMBIE);
		cursor.add(dir);
		TreasureChest.generate(world, rand, loot, cursor, rand.nextBoolean() ? TreasureChest.ARMOUR : TreasureChest.WEAPONS);
		
	}
}
