package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChestBase;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;



public class DungeonMess extends DungeonBase {

	IBlockFactory plank;
	MetaBlock stairSpruce;
	IBlockFactory log;
	
	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		ITheme theme = settings.getTheme();
		
		plank = theme.getSecondaryWall();
		stairSpruce = theme.getSecondaryStair();
		log = theme.getSecondaryPillar();
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// air		
		editor.fillRectSolid(rand, x - 6, y, z - 6, x + 6, y + 2, z + 6, air);

		// ceiling
		editor.fillRectSolid(rand, x - 6, y + 3, z - 6, x + 6, y + 4, z + 6, plank, true, true);
		editor.fillRectSolid(rand, x - 4, y + 3, z - 2, x + 4, y + 3, z - 2, WorldEditor.blockOrientation(stairSpruce, Cardinal.SOUTH, true), true, true);
		editor.fillRectSolid(rand, x - 4, y + 3, z + 2, x + 4, y + 3, z + 2, WorldEditor.blockOrientation(stairSpruce, Cardinal.NORTH, true), true, true);
		
		editor.fillRectSolid(rand, x - 2, y + 3, z - 4, x - 2, y + 3, z + 4, WorldEditor.blockOrientation(stairSpruce, Cardinal.EAST, true), true, true);
		editor.fillRectSolid(rand, x + 2, y + 3, z - 4, x + 2, y + 3, z + 4, WorldEditor.blockOrientation(stairSpruce, Cardinal.WEST, true), true, true);
		
		editor.fillRectSolid(rand, x - 1, y + 3, z - 4, x + 1, y + 3, z + 4, air);
		editor.fillRectSolid(rand, x - 4, y + 3, z - 1, x + 4, y + 3, z + 1, air);
		
		MetaBlock brownClay = ColorBlock.get(Blocks.stained_hardened_clay, EnumDyeColor.BROWN);
		
		// floor
		editor.fillRectSolid(rand, x - 7, y - 1, z - 7, x + 7, y - 1, z + 7, brownClay, true, true);
		editor.fillRectSolid(rand, x - 1, y - 1, z - 6, x + 1, y - 1, z + 6, log, true, true);
		editor.fillRectSolid(rand, x - 5, y - 1, z - 1, x - 2, y - 1, z + 1, log, true, true);
		editor.fillRectSolid(rand, x + 2, y - 1, z - 1, x + 5, y - 1, z + 1, log, true, true);

		
		
		// walls
		editor.fillRectSolid(rand, x + 7, y, z - 2, x + 7, y + 2, z + 6, plank, false, true);
		editor.fillRectSolid(rand, x - 7, y, z - 6, x - 7, y + 2, z + 6, plank, false, true);
		
		editor.fillRectSolid(rand, x - 6, y, z - 7, x + 2, y + 2, z - 7, plank, false, true);
		editor.fillRectSolid(rand, x - 6, y, z + 7, x + 6, y + 2, z + 7, plank, false, true);
		
		// pillars
		for(int i = - 6; i <= 6; i = i += 4){
			for(int j = - 6; j <= 6; j += 4){
				if(i % 6 == 0 || j % 6 == 0){
					pillar(editor, rand, theme, 2, x + i, y, z + j);	
				} else {
					pillar(editor, rand, theme, 3, x + i, y, z + j);
				}
				
			}
		}
				
		// stove
		stove(editor, rand, x + 4, y, z - 4);
		
		// storage
		storage(editor, rand, settings.getLoot(), x + 4, y, z + 4);
		
		// table north
		northTable(editor, rand, x - 4, y, z - 4);
		
		
		// table south
		southTable(editor, rand, x - 4, y, z + 4);

		return true;
	}

	private void stove(WorldEditor editor, Random rand, int x, int y, int z){
		
		MetaBlock brick = new MetaBlock(Blocks.brick_block);
		MetaBlock stair = new MetaBlock(Blocks.brick_stairs);
		
		// floor
		
		// fire pit
		editor.fillRectSolid(rand, x - 1, y - 1, z - 4, x + 2, y - 1, z + 1, brick);
		editor.fillRectSolid(rand, x - 1, y, z - 4, x + 1, y + 2, z - 3, brick);
		editor.setBlock(rand, x - 1, y, z - 2, WorldEditor.blockOrientation(stair, Cardinal.EAST, false), true, true);
		editor.setBlock(rand, x - 1, y + 1, z - 2, WorldEditor.blockOrientation(stair, Cardinal.EAST, true), true, true);
		
		editor.setBlock(rand, x + 1, y, z - 2, WorldEditor.blockOrientation(stair, Cardinal.WEST, false), true, true);
		editor.setBlock(rand, x + 1, y + 1, z - 2, WorldEditor.blockOrientation(stair, Cardinal.WEST, true), true, true);
		
		editor.fillRectSolid(rand, x - 1, y + 2, z - 2, x + 1, y + 2, z - 2, brick);
		editor.fillRectSolid(rand, x - 1, y + 2, z - 1, x + 2, y + 2, z - 1, WorldEditor.blockOrientation(stair, Cardinal.SOUTH, true), true, true);
		
		editor.setBlock(x, y - 1, z - 3, Blocks.netherrack);
		editor.setBlock(x, y, z - 3, Blocks.fire);
		editor.setBlock(rand, x, y + 1, z - 3, WorldEditor.blockOrientation(stair, Cardinal.SOUTH, true), true, true);

		// furnace
		editor.fillRectSolid(rand, x + 2, y, z - 1, x + 2, y + 2, z + 1, brick);
		editor.fillRectSolid(rand, x + 1, y + 2, z - 1, x + 1, y + 2, z + 1, WorldEditor.blockOrientation(stair, Cardinal.WEST, true), true, true);
		
		editor.setBlock(x + 2, y, z, Blocks.furnace);
		editor.setBlock(rand, x + 2, y + 1, z, WorldEditor.blockOrientation(stair, Cardinal.WEST, true), true, true);
		
		// ceiling
		editor.fillRectSolid(rand, x - 1, y + 3, z - 1, x + 1, y + 3, z + 1, brick);
	}
	
	private void storage(WorldEditor editor, Random rand, LootSettings loot, int x, int y, int z){
		
		// floor
		editor.fillRectSolid(rand, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// east shelf
		editor.setBlock(rand, x + 2, y, z - 1, WorldEditor.blockOrientation(stairSpruce, Cardinal.SOUTH, true), true, true);
		editor.setBlock(rand, x + 2, y, z, WorldEditor.blockOrientation(stairSpruce, Cardinal.WEST, true), true, true);
		editor.setBlock(rand, x + 2, y, z + 1, WorldEditor.blockOrientation(stairSpruce, Cardinal.NORTH, true), true, true);
		new TreasureChestFoodStore().generate(editor, rand, loot, new Coord(x + 2, y + 1, z), 1, false);
		
		// south shelf
		editor.setBlock(rand, x - 1, y, z + 2, WorldEditor.blockOrientation(stairSpruce, Cardinal.EAST, true), true, true);
		editor.setBlock(rand, x, y, z + 2, WorldEditor.blockOrientation(stairSpruce, Cardinal.NORTH, true), true, true);
		editor.setBlock(rand, x + 1, y, z + 2, WorldEditor.blockOrientation(stairSpruce, Cardinal.WEST, true), true, true);
	}
	
	private void northTable(WorldEditor editor, Random rand, int x, int y, int z){
		// floor
		editor.fillRectSolid(rand, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// benches
		editor.fillRectSolid(rand, x - 1, y, z - 2, x + 1, y, z - 2, WorldEditor.blockOrientation(stairSpruce, Cardinal.SOUTH, false), true, true);
		editor.fillRectSolid(rand, x - 2, y, z - 1, x - 2, y, z + 1, WorldEditor.blockOrientation(stairSpruce, Cardinal.EAST, false), true, true);
		
		// table
		editor.setBlock(rand, x, y, z, WorldEditor.blockOrientation(stairSpruce, Cardinal.NORTH, true), true, true);
		editor.setBlock(rand, x + 1, y, z, WorldEditor.blockOrientation(stairSpruce, Cardinal.EAST, true), true, true);
		editor.setBlock(rand, x + 1, y, z + 1, WorldEditor.blockOrientation(stairSpruce, Cardinal.SOUTH, true), true, true);
		editor.setBlock(rand, x, y, z + 1, WorldEditor.blockOrientation(stairSpruce, Cardinal.WEST, true), true, true);
		editor.setBlock(x, y + 1, z, Blocks.torch);
	}

	private void southTable(WorldEditor editor, Random rand, int x, int y, int z){
		// floor
		editor.fillRectSolid(rand, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// benches
		editor.fillRectSolid(rand, x - 1, y, z + 2, x + 1, y, z + 2, WorldEditor.blockOrientation(stairSpruce, Cardinal.NORTH, false), true, true);
		editor.fillRectSolid(rand, x - 2, y, z - 1, x - 2, y, z + 1, WorldEditor.blockOrientation(stairSpruce, Cardinal.EAST, false), true, true);
		
		// table
		editor.setBlock(rand, x, y, z - 1, WorldEditor.blockOrientation(stairSpruce, Cardinal.NORTH, true), true, true);
		editor.setBlock(rand, x + 1, y, z - 1, WorldEditor.blockOrientation(stairSpruce, Cardinal.EAST, true), true, true);
		editor.setBlock(rand, x + 1, y, z, WorldEditor.blockOrientation(stairSpruce, Cardinal.SOUTH, true), true, true);
		editor.setBlock(rand, x, y, z, WorldEditor.blockOrientation(stairSpruce, Cardinal.WEST, true), true, true);
		editor.setBlock(x, y + 1, z, Blocks.torch);
				
	}
	
	private static void pillar(WorldEditor editor, Random rand, ITheme theme, int height, int x, int y, int z){
		
		MetaBlock stair = theme.getSecondaryStair();
		IBlockFactory pillar = theme.getSecondaryPillar();
		IBlockFactory wall = theme.getSecondaryWall();
		
		editor.fillRectSolid(rand, x, y, z, x, y + height - 1, z, pillar, true, true);
		editor.setBlock(rand, x, y + height, z, wall, true, true);
		editor.setBlock(rand, x + 1, y + height, z, WorldEditor.blockOrientation(stair, Cardinal.EAST, true), true, true);
		editor.setBlock(rand, x - 1, y + height, z, WorldEditor.blockOrientation(stair, Cardinal.WEST, true), true, true);
		editor.setBlock(rand, x, y + height, z + 1, WorldEditor.blockOrientation(stair, Cardinal.SOUTH, true), true, true);
		editor.setBlock(rand, x, y + height, z - 1, WorldEditor.blockOrientation(stair, Cardinal.NORTH, true), true, true);
	}
	
	private class TreasureChestFoodStore extends TreasureChestBase{

		@Override
		protected void fillChest(TileEntityChest chest, LootSettings loot, int level) {
			ItemStack item;
			
			int stacks = RogueConfig.getBoolean(RogueConfig.GENEROUS) ? chest.getSizeInventory() : 12; 
			
			for (int i = 0; i < stacks; i++) {
				if(rand.nextInt(10) < 8){
					item = loot.get(Loot.FOOD, rand);
					chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);	
				}
			}
		}
		
	}
	
	public int getSize(){
		return 10;
	}
}
