package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldEditor;
import net.minecraft.init.Blocks;

public class DungeonsPrison extends DungeonBase {

	WorldEditor editor;
	Random rand;
	IBlockFactory blocks;
	IBlockFactory pillar;
	LevelSettings settings;
	
	
	public DungeonsPrison(){}
	
	@Override
	public boolean generate(WorldEditor editor, Random inRandom, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		ITheme theme = settings.getTheme();
		this.settings = settings;
		
		this.editor = editor;
		rand = inRandom;
		
		blocks = theme.getPrimaryWall();
		pillar = theme.getPrimaryPillar();
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// clear air
		editor.fillRectSolid(rand, x - 7, y, z - 7, x + 7, y + 3, z + 7, air);
		
		// create outer walls
		editor.fillRectHollow(rand, x - 8, y - 1, z - 8, x + 8, y + 5, z + 8, blocks, false, true);
		
		// fill hallway ceiling beams
		editor.fillRectSolid(rand, x - 7, y + 3, z - 2, x + 7, y + 3, z - 2, blocks);
		editor.fillRectSolid(rand, x - 7, y + 3, z + 2, x + 7, y + 3, z + 2, blocks);
		
		editor.fillRectSolid(rand, x - 2, y + 3, z - 7, x - 2, y + 3, z + 7, blocks);
		editor.fillRectSolid(rand, x + 2, y + 3, z - 7, x + 2, y + 3, z + 7, blocks);
		
		// fill hallway roofs
		editor.fillRectSolid(rand, x - 7, y + 4, z - 1, x - 2, y + 4, z + 1, blocks);
		editor.fillRectSolid(rand, x + 2, y + 4, z - 1, x + 7, y + 4, z + 1, blocks);

		editor.fillRectSolid(rand, x - 1, y + 4, z - 7, x + 1, y + 4, z - 2, blocks);
		editor.fillRectSolid(rand, x - 1, y + 4, z + 2, x + 1, y + 4, z + 7, blocks);
		
		editor.fillRectSolid(rand, x - 1, y + 4, z - 1, x + 1, y + 4, z + 1, blocks);
		
		List<Coord> cells = new ArrayList<Coord>();
		cells.add(new Coord(x - 5, y, z - 5));
		cells.add(new Coord(x - 5, y, z + 5));
		cells.add(new Coord(x + 5, y, z - 5));
		cells.add(new Coord(x + 5, y, z + 5));
		
		for (Coord cell : cells){
			createCell(cell);
		}
		
		int numChests = 1;
		if(rand.nextInt(5) == 0){
			numChests += 1;
		}
		
		TreasureChest.createChests(editor, inRandom, settings, numChests, cells);
		
		return false;
	}
	
	
	private void createCell(Coord cell){
		
		int inX = cell.getX();
		int inY = cell.getY();
		int inZ = cell.getZ();
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// floor
		editor.fillRectSolid(rand, inX - 3, inY - 1, inZ - 3, inX + 3, inY - 1, inZ + 3, blocks);
		editor.fillRectSolid(rand, inX - 1, inY - 1, inZ - 1, inX + 1, inY - 1, inZ + 1, new MetaBlock(Blocks.mossy_cobblestone));
		
		// pillars
		editor.fillRectSolid(rand, inX - 2, inY, inZ - 2, inX - 2, inY + 2, inZ - 2, pillar);
		editor.fillRectSolid(rand, inX - 2, inY, inZ + 2, inX - 2, inY + 2, inZ + 2, pillar);
		editor.fillRectSolid(rand, inX + 2, inY, inZ - 2, inX + 2, inY + 2, inZ - 2, pillar);
		editor.fillRectSolid(rand, inX + 2, inY, inZ + 2, inX + 2, inY + 2, inZ + 2, pillar);
		
		// roof
		editor.fillRectSolid(rand, inX - 3, inY + 3, inZ - 3, inX + 3, inY + 6, inZ + 3, blocks);
		
		
		// torches
		editor.fillRectSolid(rand, inX - 1, inY + 4, inZ - 2, inX + 1, inY + 4, inZ - 2, air);
		editor.setBlock(inX, inY + 4, inZ - 2, Blocks.redstone_torch);
		
		editor.fillRectSolid(rand, inX - 1, inY + 4, inZ + 2, inX + 1, inY + 4, inZ + 2, air);
		editor.setBlock(inX, inY + 4, inZ + 2, Blocks.redstone_torch);
		
		editor.fillRectSolid(rand, inX - 2, inY + 4, inZ - 1, inX - 2, inY + 4, inZ + 1, air);
		editor.setBlock(inX - 2, inY + 4, inZ, Blocks.redstone_torch);

		editor.fillRectSolid(rand, inX + 2, inY + 4, inZ - 1, inX + 2, inY + 4, inZ + 1, air);
		editor.setBlock(inX + 2, inY + 4, inZ, Blocks.redstone_torch);

		// ceiling holes
		editor.fillRectSolid(rand, inX, inY + 3, inZ, inX, inY + 6, inZ, air);
		editor.fillRectSolid(rand, inX - 1, inY + 3, inZ - 1, inX - 1, inY + 6, inZ - 1, air);
		editor.fillRectSolid(rand, inX - 1, inY + 3, inZ + 1, inX - 1, inY + 6, inZ + 1, air);
		editor.fillRectSolid(rand, inX + 1, inY + 3, inZ - 1, inX + 1, inY + 6, inZ - 1, air);
		editor.fillRectSolid(rand, inX + 1, inY + 3, inZ + 1, inX + 1, inY + 6, inZ + 1, air);
		
		MetaBlock bars = new MetaBlock(Blocks.iron_bars);
		
		// bars
		editor.fillRectSolid(rand, inX - 1, inY, inZ - 2, inX + 1, inY + 2, inZ - 2, bars);
		editor.fillRectSolid(rand, inX, inY, inZ - 2, inX, inY + 1, inZ - 2, air);
		editor.fillRectSolid(rand, inX - 1, inY, inZ + 2, inX + 1, inY + 2, inZ + 2, bars);
		editor.fillRectSolid(rand, inX, inY, inZ + 2, inX, inY + 1, inZ + 2, air);
		
		editor.fillRectSolid(rand, inX - 2, inY, inZ - 1, inX - 2, inY + 2, inZ + 1, bars);
		editor.fillRectSolid(rand, inX - 2, inY, inZ, inX - 2, inY + 1, inZ, air);
		editor.fillRectSolid(rand, inX + 2, inY, inZ - 1, inX + 2, inY + 2, inZ + 1, bars);
		editor.fillRectSolid(rand, inX + 2, inY, inZ, inX + 2, inY + 1, inZ, air);
		
		if(rand.nextBoolean()){
			switch(rand.nextInt(4)){
			case 0:
				Spawner.generate(editor, rand, settings, new Coord(inX - 2, inY + 4, inZ - 2), Spawner.ZOMBIE);
				break;
			case 1:
				Spawner.generate(editor, rand, settings, new Coord(inX - 2, inY + 4, inZ + 2), Spawner.ZOMBIE);
				break;
			case 2:
				Spawner.generate(editor, rand, settings, new Coord(inX + 2, inY + 4, inZ - 2), Spawner.ZOMBIE);
				break;
			case 3:
				Spawner.generate(editor, rand, settings, new Coord(inX + 2, inY + 4, inZ + 2), Spawner.ZOMBIE);
				break;
			}
		}
	}
	
	public int getSize(){
		return 10;
	}
}
