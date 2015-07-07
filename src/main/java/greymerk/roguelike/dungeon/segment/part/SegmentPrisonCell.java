package greymerk.roguelike.dungeon.segment.part;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.segment.IAlcove;
import greymerk.roguelike.dungeon.segment.alcove.PrisonCell;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import greymerk.roguelike.worldgen.blocks.Door;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SegmentPrisonCell extends SegmentBase {
	
	@Override
	protected void genWall(World world, Random rand, DungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock stair = theme.getSecondaryStair();
		
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
			Door.generate(world, cursor, Cardinal.reverse(dir), Door.OAK);
		} else {
			IAlcove cell = new PrisonCell();
			if(cell.isValidLocation(world, x, y, z, dir)){
				cell.generate(world, rand, level.getSettings(), x, y, z, dir);
			}
		}
	}	
}
