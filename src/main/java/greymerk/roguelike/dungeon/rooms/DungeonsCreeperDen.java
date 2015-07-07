package greymerk.roguelike.dungeon.rooms;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsCreeperDen extends DungeonBase {

	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {

		ITheme theme = settings.getTheme();
		
		Coord start;
		Coord end;
		
		MetaBlock tnt = new MetaBlock(Blocks.tnt);
		
		BlockWeightedRandom mossy = new BlockWeightedRandom();
		mossy.addBlock(theme.getPrimaryWall(), 3);
		mossy.addBlock(new MetaBlock(Blocks.mossy_cobblestone), 1);
		
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(theme.getPrimaryFloor(), 1);
		mossy.addBlock(new MetaBlock(Blocks.mossy_cobblestone), 1);
		floor.addBlock(new MetaBlock(Blocks.gravel), 3);
		
		BlockWeightedRandom subfloor = new BlockWeightedRandom();
		subfloor.addBlock(floor, 3);
		subfloor.addBlock(tnt, 1);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-4, -4, -4));
		end.add(new Coord(4, 5, 4));
		mossy.fillRectHollow(world, rand, start, end, false, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-3, -1, -3));
		end.add(new Coord(3, -1, 3));
		floor.fillRectSolid(world, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-3, -3, -3));
		end.add(new Coord(3, -2, 3));
		subfloor.fillRectSolid(world, rand, start, end, true, true);
		
		start = new Coord(origin);
		end = new Coord(origin);
		start.add(new Coord(-3, 0, -3));
		end.add(new Coord(3, 0, 3));
		
		List<Coord> chestSpaces = WorldGenPrimitive.getRectSolid(start, end);
		Collections.shuffle(chestSpaces);
		
		int counter = 0;
		for(Coord spot : chestSpaces){
			if(TreasureChest.isValidChestSpace(world, spot)){
				TreasureChest.generate(world, rand, settings, spot, TreasureChest.ORE);
				Coord cursor = new Coord(spot);
				cursor.add(Cardinal.DOWN, 2);
				tnt.setBlock(world, cursor);
				++counter;
			}
			
			if(counter >= 2) break;
		}
		
		Spawner.generate(world, rand, settings, new Coord(origin), Spawner.CREEPER);
		
		return true;
	}
	
	public int getSize(){
		return 7;
	}
}
