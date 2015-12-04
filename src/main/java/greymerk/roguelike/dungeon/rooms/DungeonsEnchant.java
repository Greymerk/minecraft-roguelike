package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Quartz;

public class DungeonsEnchant extends DungeonBase {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		MetaBlock chiselQuartz = Quartz.get(Quartz.CHISELED);
		MetaBlock pillar = Quartz.getPillar(Cardinal.UP);
		MetaBlock glowstone = BlockType.get(BlockType.GLOWSTONE);
		
		// clear space
		editor.fillRectSolid(rand, x - 5, y, z - 5, x + 5, y + 4, z + 5, air);
		editor.fillRectSolid(rand, x - 3, y + 5, z - 3, x + 3, y + 5, z + 3, air);
		
		
		// doors
		editor.fillRectSolid(rand, x - 6, y - 1, z - 2, x - 6, y + 3, z - 2, chiselQuartz, true, true);
		editor.fillRectSolid(rand, x - 6, y - 1, z + 2, x - 6, y + 3, z + 2, chiselQuartz, true, true);
		
		editor.fillRectSolid(rand, x + 6, y - 1, z - 2, x + 6, y + 3, z - 2, chiselQuartz, true, true);
		editor.fillRectSolid(rand, x + 6, y - 1, z + 2, x + 6, y + 3, z + 2, chiselQuartz, true, true);
		
		editor.fillRectSolid(rand, x - 2, y - 1, z - 6, x - 2, y + 3, z - 6, chiselQuartz, true, true);
		editor.fillRectSolid(rand, x + 2, y - 1, z - 6, x + 2, y + 3, z - 6, chiselQuartz, true, true);
		
		editor.fillRectSolid(rand, x - 2, y - 1, z + 6, x - 2, y + 3, z + 6, chiselQuartz, true, true);
		editor.fillRectSolid(rand, x + 2, y - 1, z + 6, x + 2, y + 3, z + 6, chiselQuartz, true, true);
		
		
		editor.fillRectSolid(rand, x - 6, y - 1, z - 1, x - 6, y + 3, z + 1, pillar, false, true);
		editor.fillRectSolid(rand, x + 6, y - 1, z - 1, x + 6, y + 3, z + 1, pillar, false, true);
		
		editor.fillRectSolid(rand, x - 1, y - 1, z - 6, x + 1, y + 3, z - 6, pillar, false, true);
		editor.fillRectSolid(rand, x - 1, y - 1, z + 6, x + 1, y + 3, z + 6, pillar, false, true);
		
		// pillars
		editor.fillRectSolid(rand, x - 4, y, z - 4, x - 4, y + 4, z - 4, chiselQuartz, true, true);
		editor.fillRectSolid(rand, x - 4, y, z + 4, x - 4, y + 4, z + 4, chiselQuartz, true, true);
		editor.fillRectSolid(rand, x + 4, y, z - 4, x + 4, y + 4, z - 4, chiselQuartz, true, true);
		editor.fillRectSolid(rand, x + 4, y, z + 4, x + 4, y + 4, z + 4, chiselQuartz, true, true);
		
		MetaBlock decor = ColorBlock.get(ColorBlock.CLAY, rand);
		MetaBlock lining = ColorBlock.get(ColorBlock.CLAY, rand);
		
		//lapis shell
		editor.fillRectSolid(rand, x - 5, y, z - 3, x - 5, y + 4, z - 3, decor, true, true);
		editor.fillRectSolid(rand, x - 5, y, z + 3, x - 5, y + 4, z + 3, decor, true, true);
		
		editor.fillRectSolid(rand, x + 5, y, z - 3, x + 5, y + 4, z - 3, decor, true, true);
		editor.fillRectSolid(rand, x + 5, y, z + 3, x + 5, y + 4, z + 3, decor, true, true);
		
		editor.fillRectSolid(rand, x - 3, y, z - 5, x - 3, y + 4, z - 5, decor, true, true);
		editor.fillRectSolid(rand, x + 3, y, z - 5, x + 3, y + 4, z - 5, decor, true, true);
		
		editor.fillRectSolid(rand, x - 3, y, z + 5, x - 3, y + 4, z + 5, decor, true, true);
		editor.fillRectSolid(rand, x + 3, y, z + 5, x + 3, y + 4, z + 5, decor, true, true);
		
		// tops & bottoms
		
		editor.fillRectSolid(rand, x - 5, y - 1, z - 5, x - 3, y - 1, z - 3, decor, true, true);
		editor.fillRectSolid(rand, x - 5, y + 4, z - 5, x - 3, y + 4, z - 3, decor, true, true);
		
		editor.fillRectSolid(rand, x - 5, y - 1, z + 3, x - 3, y - 1, z + 5, decor, true, true);
		editor.fillRectSolid(rand, x - 5, y + 4, z + 3, x - 3, y + 4, z + 5, decor, true, true);
		
		editor.fillRectSolid(rand, x + 3, y - 1, z + 3, x + 5, y - 1, z + 5, decor, true, true);
		editor.fillRectSolid(rand, x + 3, y + 4, z + 3, x + 5, y + 4, z + 5, decor, true, true);
		
		editor.fillRectSolid(rand, x + 3, y - 1, z - 5, x + 5, y - 1, z - 3, decor, true, true);
		editor.fillRectSolid(rand, x + 3, y + 4, z - 5, x + 5, y + 4, z - 3, decor, true, true);
		
		// arch beams
		
		editor.fillRectSolid(rand, x - 4, y + 4, z - 2, x - 4, y + 4, z + 2, decor, true, true);
		editor.fillRectSolid(rand, x - 5, y + 3, z - 2, x - 5, y + 3, z + 2, decor, true, true);

		editor.fillRectSolid(rand, x + 4, y + 4, z - 2, x + 4, y + 4, z + 2, decor, true, true);
		editor.fillRectSolid(rand, x + 5, y + 3, z - 2, x + 5, y + 3, z + 2, decor, true, true);
		
		editor.fillRectSolid(rand, x - 2, y + 4, z - 4, x + 2, y + 4, z - 4, decor, true, true);
		editor.fillRectSolid(rand, x - 2, y + 3, z - 5, x + 2, y + 3, z - 5, decor, true, true);
		
		editor.fillRectSolid(rand, x - 2, y + 4, z + 4, x + 2, y + 4, z + 4, decor, true, true);
		editor.fillRectSolid(rand, x - 2, y + 3, z + 5, x + 2, y + 3, z + 5, decor, true, true);
		
		// roof
		editor.fillRectSolid(rand, x - 3, y + 5, z - 3, x + 3, y + 5, z + 3, lining, true, true);
		editor.fillRectSolid(rand, x - 2, y + 5, z - 1, x + 2, y + 5, z + 1, decor, true, true);
		editor.fillRectSolid(rand, x - 1, y + 5, z - 2, x + 1, y + 5, z - 2, decor, true, true);
		editor.fillRectSolid(rand, x - 1, y + 5, z + 2, x + 1, y + 5, z + 2, decor, true, true);
		
		editor.fillRectSolid(rand, x - 1, y + 5, z, x + 1, y + 5, z, glowstone);
		editor.setBlock(x, y + 5, z - 1, BlockType.get(BlockType.GLOWSTONE));
		editor.setBlock(x, y + 5, z + 1, BlockType.get(BlockType.GLOWSTONE));
		
		// enchanting floor
		
		editor.fillRectSolid(rand, x - 5, y - 1, z - 1, x - 3, y - 1, z + 1, lining, true, true);
		editor.fillRectSolid(rand, x + 3, y - 1, z - 1, x + 5, y - 1, z + 1, lining, true, true);
		editor.fillRectSolid(rand, x - 1, y - 1, z - 5, x + 1, y - 1, z - 3, lining, true, true);
		editor.fillRectSolid(rand, x - 1, y - 1, z + 3, x + 1, y - 1, z + 5, lining, true, true);
		
		editor.fillRectSolid(rand, x - 5, y - 1, z + 2, x - 3, y - 1, z + 2, pillar, true, true);
		editor.fillRectSolid(rand, x - 5, y - 1, z - 2, x - 3, y - 1, z - 2, pillar, true, true);
		
		editor.fillRectSolid(rand, x + 3, y - 1, z + 2, x + 5, y - 1, z + 2, pillar, true, true);
		editor.fillRectSolid(rand, x + 3, y - 1, z - 2, x + 5, y - 1, z - 2, pillar, true, true);
		
		editor.fillRectSolid(rand, x - 2, y - 1, z - 5, x - 2, y - 1, z - 3, pillar, true, true);
		editor.fillRectSolid(rand, x + 2, y - 1, z - 5, x + 2, y - 1, z - 3, pillar, true, true);
		
		editor.fillRectSolid(rand, x - 2, y - 1, z + 3, x - 2, y - 1, z + 5, pillar, true, true);
		editor.fillRectSolid(rand, x + 2, y - 1, z + 3, x + 2, y - 1, z + 5, pillar, true, true);
		
		editor.fillRectSolid(rand, x - 2, y - 1, z - 2, x + 2, y - 1, z + 2, decor, true, true);
		
		editor.setBlock(x, y - 1, z, BlockType.get(BlockType.GLOWSTONE));
		
		if(RogueConfig.getBoolean(RogueConfig.GENEROUS)){
			editor.setBlock(x, y, z, BlockType.get(BlockType.ENCHANTING_TABLE));
		} else {
			Treasure.generate(editor, rand, new Coord(x, y, z), Treasure.ENCHANTING, 4, false);
		}
		return false;
	}	
	
	public int getSize(){
		return 8;
	}
}
