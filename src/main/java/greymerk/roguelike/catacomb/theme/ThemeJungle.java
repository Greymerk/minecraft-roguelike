package greymerk.roguelike.catacomb.theme;

import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;
import net.minecraft.init.Blocks;

public class ThemeJungle extends ThemeBase{

	public ThemeJungle(){
	
		BlockWeightedRandom walls = new BlockWeightedRandom();
		walls.addBlock(new MetaBlock(Blocks.mossy_cobblestone), 50);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 1), 30);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 2), 20);
		walls.addBlock(new MetaBlock(Blocks.stonebrick, 3), 15);
		
		MetaBlock stair = new MetaBlock(Blocks.stone_stairs);
		
		MetaBlock pillar = new MetaBlock(Blocks.stonebrick, 3);
		MetaBlock pillar2 = new MetaBlock(Blocks.log, 3);
		
		BlockJumble stairJumble = new BlockJumble();
		stairJumble.addBlock(new MetaBlock(WorldGenPrimitive.blockOrientation(stair, Cardinal.NORTH, false)));
		stairJumble.addBlock(new MetaBlock(WorldGenPrimitive.blockOrientation(stair, Cardinal.SOUTH, false)));
		stairJumble.addBlock(new MetaBlock(WorldGenPrimitive.blockOrientation(stair, Cardinal.WEST, false)));
		stairJumble.addBlock(new MetaBlock(WorldGenPrimitive.blockOrientation(stair, Cardinal.EAST, false)));
		
		BlockWeightedRandom floor = new BlockWeightedRandom();
		floor.addBlock(stairJumble, 1);
		floor.addBlock(walls, 3);
		
		
		this.primary = new BlockSet(floor, walls, stair, pillar);
		this.secondary = new BlockSet(new MetaBlock(Blocks.stonebrick, 3), stair, pillar2);
	}
}
