package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.BlockWeightedRandom;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class DungeonsNetherBrick extends DungeonBase {
	

	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {

		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();
		
		int height = 3;
		int length = 2 + rand.nextInt(3);
		int width = 2 + rand.nextInt(3);
		
		IBlockFactory walls = theme.getPrimaryWall();
		editor.fillRectHollow(rand, x - length - 1, y - 1, z - width - 1, x + length + 1, y + height + 1, z + width + 1, walls, false, true);
		
		
		IBlockFactory floor = theme.getPrimaryFloor();
		editor.fillRectSolid(rand, x - length - 1, y - 1, z - width - 1, x + length + 1, y - 1, z + width + 1, floor);

		// lava crap under the floor
		BlockWeightedRandom subFloor = new BlockWeightedRandom();
		subFloor.addBlock(BlockType.get(BlockType.LAVA_FLOWING), 8);
		subFloor.addBlock(BlockType.get(BlockType.OBSIDIAN), 3);
		editor.fillRectSolid(rand, x - length, y - 5, z - width, x + length, y - 2, z + width, subFloor);
		
		BlockWeightedRandom ceiling = new BlockWeightedRandom();
		ceiling.addBlock(BlockType.get(BlockType.FENCE_NETHER_BRICK), 10);
		ceiling.addBlock(BlockType.get(BlockType.AIR), 5);
		editor.fillRectSolid(rand, x - length, y + height, z - width, x + length, y + height, z + width, ceiling);
		
		Treasure.createChests(editor, rand, 1, WorldEditor.getRectSolid(x - length, y, z - width, x + length, y, z + width), Dungeon.getLevel(y));

		Spawner.generate(editor, rand, settings, new Coord(x - length - 1, y + rand.nextInt(2), z - width - 1));
		Spawner.generate(editor, rand, settings, new Coord(x - length - 1, y + rand.nextInt(2), z + width + 1));
		Spawner.generate(editor, rand, settings, new Coord(x + length + 1, y + rand.nextInt(2), z - width - 1));
		Spawner.generate(editor, rand, settings, new Coord(x + length + 1, y + rand.nextInt(2), z + width + 1));

		return true;
	}
	
	public int getSize(){
		return 6;
	}
}
