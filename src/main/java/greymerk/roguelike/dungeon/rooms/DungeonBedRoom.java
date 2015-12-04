package greymerk.roguelike.dungeon.rooms;

import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.Bed;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.FlowerPot;
import greymerk.roguelike.worldgen.blocks.Furnace;
import greymerk.roguelike.worldgen.redstone.Torch;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class DungeonBedRoom extends DungeonBase {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		ITheme theme = settings.getTheme();
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal dir = entrances[0];
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(x, y, z);
		end = new Coord(x, y, z);
		
		start.add(orth[0], 4);
		end.add(orth[1], 4);
		start.add(Cardinal.reverse(dir), 4);
		end.add(dir, 4);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.UP, 4);
		
		editor.fillRectHollow(rand, start, end, theme.getPrimaryWall(), false, true);
		
		start = new Coord(x, y, z);
		start.add(Cardinal.DOWN);
		end = new Coord(start);
		start.add(orth[0], 1);
		end.add(orth[1], 1);
		start.add(Cardinal.reverse(dir), 2);
		end.add(dir, 2);
		
		editor.fillRectSolid(rand, start, end, theme.getSecondaryWall(), true, true);
		
		for(Cardinal o : orth){
			IStair stair = theme.getSecondaryStair();
			stair.setOrientation(Cardinal.reverse(o), true);
			
			start = new Coord(x, y, z);
			start.add(o, 3);
			end = new Coord(start);
			start.add(Cardinal.getOrthogonal(o)[0], 2);
			end.add(Cardinal.getOrthogonal(o)[1], 2);
			
			editor.fillRectSolid(rand, start, end, stair, true, true);
			start.add(Cardinal.UP, 2);
			end.add(Cardinal.UP, 2);
			editor.fillRectSolid(rand, start, end, stair, true, true);
			start.add(Cardinal.UP);
			end.add(Cardinal.UP);
			editor.fillRectSolid(rand, start, end, theme.getPrimaryWall(), true, true);
			start.add(Cardinal.reverse(o));
			end.add(Cardinal.reverse(o));
			editor.fillRectSolid(rand, start, end, stair, true, true);
		}
		
		for(Cardinal o : orth){
			cursor = new Coord(x, y, z);
			cursor.add(o, 3);
			pillar(editor, rand, o, theme, cursor);
			for(Cardinal p : Cardinal.getOrthogonal(o)){
				Coord c = new Coord(cursor);
				c.add(p, 3);
				pillar(editor, rand, o, theme, c);
			}
		}
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.UP, 3);
		cursor.add(Cardinal.reverse(dir), 3);
		
		for(int i = 0; i < 3; ++i){
			start = new Coord(cursor);
			end = new Coord(cursor);
			start.add(orth[0], 2);
			end.add(orth[1], 2);
			editor.fillRectSolid(rand, start, end, theme.getSecondaryWall(), true, true);
			cursor.add(dir, 3);
		}
		
		Cardinal side = orth[rand.nextInt(orth.length)];
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 3);
		cursor.add(side, 1);
		Bed.generate(editor, Cardinal.reverse(dir), cursor);
		cursor.add(side);
		editor.setBlock(cursor, BlockType.get(BlockType.SHELF));
		cursor.add(Cardinal.UP);
		FlowerPot.generate(editor, rand, cursor);
		cursor.add(Cardinal.reverse(side), 3);
		cursor.add(Cardinal.DOWN);
		IStair stair = theme.getSecondaryStair();
		stair.setOrientation(Cardinal.reverse(dir), true);
		stair.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);
		
		side = orth[rand.nextInt(orth.length)];
		cursor = new Coord(x, y, z);
		cursor.add(dir);
		cursor.add(side, 3);
		Treasure.generate(editor, rand, settings, cursor, Treasure.STARTER);
		cursor.add(Cardinal.reverse(side), 6);
		if(rand.nextBoolean()){
			cursor.add(Cardinal.UP);
			Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);
			cursor.add(Cardinal.DOWN);
			cursor.add(dir);
			editor.setBlock(cursor, BlockType.get(BlockType.CRAFTING_TABLE));
		} else {
			editor.setBlock(cursor, BlockType.get(BlockType.CRAFTING_TABLE));
			cursor.add(dir);
			cursor.add(Cardinal.UP);
			Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);
			cursor.add(Cardinal.DOWN);
		}
		
		side = orth[rand.nextInt(orth.length)];
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.reverse(dir));
		cursor.add(side, 3);
		if(rand.nextBoolean()) cursor.add(Cardinal.reverse(dir));
		Furnace.generate(editor, new ItemStack(Items.coal, 2 + rand.nextInt(3)), true, Cardinal.reverse(side), cursor);
		
		
		return true;
	}
	
	public static void pillar(WorldEditor editor, Random rand, Cardinal dir, ITheme theme, final Coord base){
		Coord start = new Coord(base);
		Coord end = new Coord(base);
		
		end.add(Cardinal.UP, 2);
		editor.fillRectSolid(rand, start, end, theme.getSecondaryPillar(), true, true);
		IStair stair = theme.getSecondaryStair();
		stair.setOrientation(Cardinal.reverse(dir), true);
		end.add(Cardinal.reverse(dir));
		stair.setBlock(editor, end);
	}

	@Override
	public int getSize() {
		
		return 5;
	}

	@Override
	public boolean validLocation(WorldEditor editor, Cardinal dir, int x, int y, int z) {
		Coord start;
		Coord end;
		
		start = new Coord(x, y, z);
		end = new Coord(start);
		start.add(Cardinal.reverse(dir), 5);
		end.add(dir, 5);
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start.add(orth[0], 5);
		end.add(orth[1], 5);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.UP, 3);
		
		for(Coord c : editor.getRectHollow(start, end)){
			if(editor.isAirBlock(c)) return false;
		}
		
		return true;
	}
}
