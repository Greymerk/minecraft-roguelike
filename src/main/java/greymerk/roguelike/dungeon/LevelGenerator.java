package greymerk.roguelike.dungeon;

import java.util.Random;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.rooms.DungeonLinker;
import greymerk.roguelike.dungeon.rooms.DungeonLinkerTop;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import net.minecraft.world.World;

public enum LevelGenerator {
	
	CLASSIC, MST;
	
	public static ILevelGenerator getGenerator(World world, Random rand, LevelGenerator type, IDungeonLevel level){
			switch(type){
			case CLASSIC:
				return new LevelGeneratorClassic(world, rand, level);
			case MST:
				return new LevelGeneratorMST(world, rand, level);
			default:
				return new LevelGeneratorClassic(world, rand, level);
			}
	}
	
	public static void generateLevelLink(World world, Random rand, LevelSettings settings, Coord start, DungeonNode oldEnd) {
		
		IDungeonRoom downstairs = new DungeonLinker();
		downstairs.generate(world, rand, settings, Cardinal.directions, start);
		
		if(oldEnd == null) return;
		
		IDungeonRoom upstairs = new DungeonLinkerTop();
		upstairs.generate(world, rand, settings, oldEnd.getEntrances(), oldEnd.getPosition());
		
		MetaBlock stair = settings.getTheme().getPrimaryStair();
		
		Coord cursor = new Coord(start);
		for (int i = 0; i < oldEnd.getPosition().getY() - start.getY(); i++){
			WorldGenPrimitive.spiralStairStep(world, rand, cursor, stair, settings.getTheme().getPrimaryPillar());
			cursor.add(Cardinal.UP);
		}	
	}
}