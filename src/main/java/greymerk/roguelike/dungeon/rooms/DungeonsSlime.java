package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;

public class DungeonsSlime extends DungeonBase {
	WorldEditor editor;
	Random rand;
	int originX;
	int originY;
	int originZ;

	IBlockFactory fillBlocks;
	MetaBlock liquid;
	
	public DungeonsSlime() {
		super();
	}

	public boolean generate(WorldEditor editor, Random inRandom, LevelSettings settings, Cardinal[] entrances, Coord origin) {

		ITheme theme = settings.getTheme();
		
		this.editor = editor;
		rand = inRandom;
		originX = origin.getX();
		originY = origin.getY();
		originZ = origin.getZ();

		liquid = Dungeon.getLevel(originY) == 4 ? BlockType.get(BlockType.LAVA_FLOWING) : BlockType.get(BlockType.WATER_FLOWING);

		fillBlocks = theme.getPrimaryWall();
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		// fill air
		editor.fillRectSolid(rand, originX - 6, originY, originZ - 6, originX + 6, originY + 3, originZ + 6, air);
		
		// shell
		editor.fillRectHollow(rand, originX - 7, originY - 2, originZ - 7, originX + 7, originY + 4, originZ + 7, fillBlocks, false, true);
		
		// floor
		editor.fillRectSolid(rand, originX - 7, originY - 2, originZ - 7, originX + 7, originY - 1, originZ + 7, fillBlocks);
		
		pool(originX - 4, originZ - 4);
		pool(originX - 4, originZ + 4);
		pool(originX + 4, originZ - 4);
		pool(originX + 4, originZ + 4);
		
		// centre top lip
		editor.fillRectSolid(rand, originX - 1, originY + 3, originZ - 2, originX + 1, originY + 3, originZ - 2, fillBlocks);
		editor.fillRectSolid(rand, originX - 1, originY + 3, originZ + 2, originX + 1, originY + 3, originZ + 2, fillBlocks);
		editor.fillRectSolid(rand, originX - 2, originY + 3, originZ - 1, originX - 2, originY + 3, originZ + 1, fillBlocks);
		editor.fillRectSolid(rand, originX + 2, originY + 3, originZ - 1, originX + 2, originY + 3, originZ + 1, fillBlocks);
		
		if(Dungeon.getLevel(originY) == 4){
			Spawner.generate(editor, rand, settings, new Coord(originX, originY + 5, originZ), Spawner.LAVASLIME);
		} else {
			editor.randomVines(rand, originX - 7, originY + 2, originZ - 7, originX + 7, originY + 5, originZ + 7);			
		}
		
		return true;
	}
	
	private void pool(int inX, int inZ){
		// water pool
		editor.fillRectSolid(rand, inX - 1, originY - 1, inZ - 1, inX + 1, originY - 1, inZ + 1, liquid);
		
		// pillars
		editor.fillRectSolid(rand, inX - 2, originY - 1, inZ - 2, inX - 2, originY + 3, inZ - 2, fillBlocks);
		editor.fillRectSolid(rand, inX - 2, originY - 1, inZ + 2, inX - 2, originY + 3, inZ + 2, fillBlocks);
		editor.fillRectSolid(rand, inX + 2, originY - 1, inZ - 2, inX + 2, originY + 3, inZ - 2, fillBlocks);
		editor.fillRectSolid(rand, inX + 2, originY - 1, inZ + 2, inX + 2, originY + 3, inZ + 2, fillBlocks);
		
		// base lip
		editor.fillRectSolid(rand, inX - 1, originY - 1, inZ - 2, inX + 1, originY - 1, inZ - 2, fillBlocks);
		editor.fillRectSolid(rand, inX - 1, originY - 1, inZ + 2, inX + 1, originY - 1, inZ + 2, fillBlocks);
		editor.fillRectSolid(rand, inX - 2, originY - 1, inZ - 1, inX - 2, originY - 1, inZ + 1, fillBlocks);
		editor.fillRectSolid(rand, inX + 2, originY - 1, inZ - 1, inX + 2, originY - 1, inZ + 1, fillBlocks);
		
		// top lip
		editor.fillRectSolid(rand, inX - 1, originY + 3, inZ - 2, inX + 1, originY + 3, inZ - 2, fillBlocks);
		editor.fillRectSolid(rand, inX - 1, originY + 3, inZ + 2, inX + 1, originY + 3, inZ + 2, fillBlocks);
		editor.fillRectSolid(rand, inX - 2, originY + 3, inZ - 1, inX - 2, originY + 3, inZ + 1, fillBlocks);
		editor.fillRectSolid(rand, inX + 2, originY + 3, inZ - 1, inX + 2, originY + 3, inZ + 1, fillBlocks);

		// carve ceiling air
		editor.fillRectSolid(rand, inX - 1, originY + 1, inZ - 1, inX + 1, originY + 6, inZ + 1, BlockType.get(BlockType.AIR));
		
		// roof
		editor.fillRectSolid(rand, inX - 2, originY + 7, inZ - 2, inX + 2, originY + 8, inZ + 2, fillBlocks);
		editor.fillRectSolid(rand, inX - 1, originY + 8, inZ - 1, inX + 1, originY + 8, inZ + 1, liquid);
		
		
	}
	
	public boolean isValidDungeonLocation(WorldEditor editor, int originX, int originY, int originZ) {
		return false;
	}
	
	public int getSize(){
		return 8;
	}
}
