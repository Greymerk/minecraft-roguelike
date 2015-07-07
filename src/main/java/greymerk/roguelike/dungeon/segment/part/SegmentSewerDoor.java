package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import greymerk.roguelike.worldgen.blocks.Door;
import greymerk.roguelike.worldgen.blocks.Leaves;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentSewerDoor extends SegmentBase {
	
	@Override
	protected void genWall(World world, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock stair = theme.getSecondaryStair();
		MetaBlock bars = new MetaBlock(Blocks.iron_bars);
		MetaBlock water = new MetaBlock(Blocks.flowing_water);
		MetaBlock leaves = Leaves.get(Leaves.SPRUCE, false);
		MetaBlock glowstone = new MetaBlock(Blocks.glowstone);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.DOWN);
		bars.setBlock(world, cursor);
		start = new Coord(cursor);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		WorldGenPrimitive.blockOrientation(stair, orth[0], true).setBlock(world, start);
		WorldGenPrimitive.blockOrientation(stair, orth[1], true).setBlock(world, end);
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.DOWN);
		bars.setBlock(world, cursor);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.DOWN);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, water, true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 3);
		bars.setBlock(world, cursor);
		cursor.add(Cardinal.UP);
		WorldGenPrimitive.setBlock(world, rand, cursor, leaves, false, true);
		cursor.add(dir);
		WorldGenPrimitive.setBlock(world, rand, cursor, water, false, true);
		cursor.add(dir);
		WorldGenPrimitive.setBlock(world, rand, cursor, glowstone, false, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		start = new Coord(cursor);
		start.add(orth[0], 1);
		end = new Coord(cursor);
		end.add(orth[1], 1);
		end.add(Cardinal.UP, 2);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, air, true, true);
		
		SecretFactory secrets = level.getSettings().getSecrets();
		boolean room = secrets.genRoom(world, rand, level.getSettings(), dir, new Coord(x, y, z));
		
		start.add(dir, 1);
		end.add(dir, 1);
		WorldGenPrimitive.fillRectSolid(world, rand, start, end, theme.getSecondaryWall(), false, true);

		cursor.add(Cardinal.UP, 2);
		for(Cardinal d : orth){
			Coord c = new Coord(cursor);
			c.add(d, 1);
			WorldGenPrimitive.blockOrientation(stair, Cardinal.reverse(d), true);
			WorldGenPrimitive.setBlock(world, rand, c, stair, true, true);
		}
		
		if(room){
			cursor = new Coord(x, y, z);
			cursor.add(dir, 3);
			Door.generate(world, cursor, Cardinal.reverse(dir), Door.IRON);
		}
	}	
}
