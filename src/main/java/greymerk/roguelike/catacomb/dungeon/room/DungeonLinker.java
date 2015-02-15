package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonLinker extends DungeonBase{

	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		ITheme theme = settings.getTheme();
		
		IBlockFactory pillar = theme.getPrimaryPillar();
		IBlockFactory wall = theme.getPrimaryWall();
		IBlockFactory floor = theme.getPrimaryFloor();
		MetaBlock bars = new MetaBlock(Blocks.iron_bars);

		Coord start;
		Coord end;
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-4, -1, -4);
		end.add(4, 9, 4);
		wall.fillRectHollow(world, rand, start, end, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-4, 9, -4);
		end.add(4, 9, 4);
		wall.fillRectSolid(world, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(-4, -1, -4);
		end.add(4, -1, 4);
		floor.fillRectSolid(world, rand, start, end, true, true);
		
		for(Cardinal dir : Cardinal.directions){
			
			Cardinal[] orth = Cardinal.getOrthogonal(dir);
			
			start = new Coord(origin);
			start.add(dir, 4);
			end = new Coord(start);
			end.add(Cardinal.UP, 8);
			start.add(Cardinal.DOWN);
			start.add(orth[0], 4);
			end.add(orth[1], 4);
			bars.fillRectSolid(world, rand, start, end, true, false);
			
			start = new Coord(origin);
			end = new Coord(origin);
			start.add(dir, 3);
			start.add(orth[0], 3);
			end.add(dir, 4);
			end.add(orth[0], 4);
			end.add(Cardinal.UP, 8);
			pillar.fillRectSolid(world, rand, start, end, true, true);
		}
		

		
		
		return true;
	}

	@Override
	public int getSize() {
		return 6;
	}

}
