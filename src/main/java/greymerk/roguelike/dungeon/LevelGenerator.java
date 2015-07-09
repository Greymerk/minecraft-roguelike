package greymerk.roguelike.dungeon;

import java.util.Random;

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
	
}
