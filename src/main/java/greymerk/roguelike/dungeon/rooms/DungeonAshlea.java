package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChestBase;
import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.treasure.loot.provider.ItemFood;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.StairType;
import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;



public class DungeonAshlea extends DungeonBase {

	IBlockFactory plank;
	IStair stair;
	IBlockFactory log;
	
	
	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		ITheme theme = settings.getTheme();
		
		plank = theme.getSecondaryWall();
		stair = theme.getSecondaryStair();
		log = theme.getSecondaryPillar();
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// air		
		editor.fillRectSolid(rand, x - 6, y, z - 6, x + 6, y + 2, z + 6, air);

		// ceiling
		editor.fillRectSolid(rand, x - 6, y + 3, z - 6, x + 6, y + 4, z + 6, plank, true, true);
		editor.fillRectSolid(rand, x - 4, y + 3, z - 2, x + 4, y + 3, z - 2, stair.setOrientation(Cardinal.SOUTH, true), true, true);
		editor.fillRectSolid(rand, x - 4, y + 3, z + 2, x + 4, y + 3, z + 2, stair.setOrientation(Cardinal.NORTH, true), true, true);
		
		editor.fillRectSolid(rand, x - 2, y + 3, z - 4, x - 2, y + 3, z + 4, stair.setOrientation(Cardinal.EAST, true), true, true);
		editor.fillRectSolid(rand, x + 2, y + 3, z - 4, x + 2, y + 3, z + 4, stair.setOrientation(Cardinal.WEST, true), true, true);
		
		editor.fillRectSolid(rand, x - 1, y + 3, z - 4, x + 1, y + 3, z + 4, air);
		editor.fillRectSolid(rand, x - 4, y + 3, z - 1, x + 4, y + 3, z + 1, air);
		
		MetaBlock clay = new MetaBlock(Blocks.stained_hardened_clay.getDefaultState());
		clay.withProperty(BlockColored.COLOR, EnumDyeColor.PINK);
		
		// floor
		editor.fillRectSolid(rand, x - 7, y - 1, z - 7, x + 7, y - 1, z + 7, clay, true, true);
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
				pillar(editor, rand, theme, x + i, y, z + j);
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
		IStair stair = new MetaStair(StairType.BRICK);
		
		// floor
		
		// fire pit
		editor.fillRectSolid(rand, x - 1, y - 1, z - 4, x + 2, y - 1, z + 1, brick);
		editor.fillRectSolid(rand, x - 1, y, z - 4, x + 1, y + 2, z - 3, brick);
		editor.setBlock(rand, x - 1, y, z - 2, stair.setOrientation(Cardinal.EAST, false), true, true);
		editor.setBlock(rand, x - 1, y + 1, z - 2, stair.setOrientation(Cardinal.EAST, true), true, true);
		
		editor.setBlock(rand, x + 1, y, z - 2, stair.setOrientation(Cardinal.WEST, false), true, true);
		editor.setBlock(rand, x + 1, y + 1, z - 2, stair.setOrientation(Cardinal.WEST, true), true, true);
		
		editor.fillRectSolid(rand, x - 1, y + 2, z - 2, x + 1, y + 2, z - 2, brick);
		editor.fillRectSolid(rand, x - 1, y + 2, z - 1, x + 2, y + 2, z - 1, stair.setOrientation(Cardinal.SOUTH, true), true, true);
		
		editor.setBlock(x, y - 1, z - 3, Blocks.netherrack);
		editor.setBlock(x, y, z - 3, Blocks.fire);
		editor.setBlock(rand, x, y + 1, z - 3, stair.setOrientation(Cardinal.SOUTH, true), true, true);

		// furnace
		editor.fillRectSolid(rand, x + 2, y, z - 1, x + 2, y + 2, z + 1, brick);
		editor.fillRectSolid(rand, x + 1, y + 2, z - 1, x + 1, y + 2, z + 1, stair.setOrientation(Cardinal.WEST, true), true, true);
		
		editor.setBlock(x + 2, y, z, Blocks.furnace);
		editor.setBlock(rand, x + 2, y + 1, z, stair.setOrientation(Cardinal.WEST, true), true, true);
		
		// ceiling
		editor.fillRectSolid(rand, x - 1, y + 3, z - 1, x + 1, y + 3, z + 1, brick);
	}

	private void northTable(WorldEditor editor, Random rand, int x, int y, int z){
		// floor
		editor.fillRectSolid(rand, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// benches
		editor.fillRectSolid(rand, x - 1, y, z - 2, x + 1, y, z - 2, stair.setOrientation(Cardinal.SOUTH, false), true, true);
		editor.fillRectSolid(rand, x - 2, y, z - 1, x - 2, y, z + 1, stair.setOrientation(Cardinal.EAST, false), true, true);
		
		// table
		editor.setBlock(rand, x, y, z, stair.setOrientation(Cardinal.NORTH, true), true, true);
		editor.setBlock(rand, x + 1, y, z, stair.setOrientation(Cardinal.EAST, true), true, true);
		editor.setBlock(rand, x + 1, y, z + 1, stair.setOrientation(Cardinal.SOUTH, true), true, true);
		editor.setBlock(rand, x, y, z + 1, stair.setOrientation(Cardinal.WEST, true), true, true);
		editor.setBlock(x, y + 1, z, Blocks.torch);
	}

	private void southTable(WorldEditor editor, Random rand, int x, int y, int z){
		// floor
		editor.fillRectSolid(rand, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// benches
		editor.fillRectSolid(rand, x - 1, y, z + 2, x + 1, y, z + 2, stair.setOrientation(Cardinal.NORTH, false), true, true);
		editor.fillRectSolid(rand, x - 2, y, z - 1, x - 2, y, z + 1, stair.setOrientation(Cardinal.EAST, false), true, true);
		
		// table
		editor.setBlock(rand, x, y, z - 1, stair.setOrientation(Cardinal.NORTH, true), true, true);
		editor.setBlock(rand, x + 1, y, z - 1, stair.setOrientation(Cardinal.EAST, true), true, true);
		editor.setBlock(rand, x + 1, y, z, stair.setOrientation(Cardinal.SOUTH, true), true, true);
		editor.setBlock(rand, x, y, z, stair.setOrientation(Cardinal.WEST, true), true, true);
		editor.setBlock(x, y + 1, z, Blocks.torch);
				
	}
	
	private void storage(WorldEditor editor, Random rand, LootSettings loot, int x, int y, int z){
		
		// floor
		editor.fillRectSolid(rand, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// east shelf
		editor.setBlock(rand, x + 2, y, z - 1, stair.setOrientation(Cardinal.SOUTH, true), true, true);
		editor.setBlock(rand, x + 2, y, z, stair.setOrientation(Cardinal.WEST, true), true, true);
		editor.setBlock(rand, x + 2, y, z + 1, stair.setOrientation(Cardinal.NORTH, true), true, true);
		new TreasureChestAshlea().generate(editor, rand, loot, new Coord(x + 2, y + 1, z), 1, false);
		
		// south shelf
		editor.setBlock(rand, x - 1, y, z + 2, stair.setOrientation(Cardinal.EAST, true), true, true);
		editor.setBlock(rand, x, y, z + 2, stair.setOrientation(Cardinal.NORTH, true), true, true);
		editor.setBlock(rand, x + 1, y, z + 2, stair.setOrientation(Cardinal.WEST, true), true, true);
		new TreasureChestAshlea().generate(editor, rand, loot, new Coord(x, y + 1, z + 2), 1, false);
	}
	
	private static void pillar(WorldEditor editor, Random rand, ITheme theme, int x, int y, int z){
		
		IStair stair = theme.getSecondaryStair();
		IBlockFactory pillar = theme.getSecondaryPillar();
		IBlockFactory wall = theme.getSecondaryWall();
		
		editor.fillRectSolid(rand, x, y, z, x, y + 1, z, pillar, true, true);
		editor.setBlock(rand, x, y + 2, z, wall, true, true);
		editor.setBlock(rand, x + 1, y + 2, z, stair.setOrientation(Cardinal.EAST, true), true, true);
		editor.setBlock(rand, x - 1, y + 2, z, stair.setOrientation(Cardinal.WEST, true), true, true);
		editor.setBlock(rand, x, y + 2, z + 1, stair.setOrientation(Cardinal.SOUTH, true), true, true);
		editor.setBlock(rand, x, y + 2, z - 1, stair.setOrientation(Cardinal.NORTH, true), true, true);
		editor.fillRectSolid(rand, x - 1, y + 3, z - 1, x + 1, y + 3, z + 1, wall, true, true);
		
	}
	
	private class TreasureChestAshlea extends TreasureChestBase{

		@Override
		protected void fillChest(TileEntityChest chest, LootSettings loot, int level) {
			ItemStack item;
			
			int stacks = RogueConfig.getBoolean(RogueConfig.GENEROUS) ? chest.getSizeInventory() : 12; 
			
			for (int i = 0; i < stacks; i++) {
				if(rand.nextInt(10) < 8){
					item = ItemFood.getDessert(rand);
					chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), item);	
				}
			}
		}
		
	}
	
	public int getSize(){
		return 10;
	}
}
