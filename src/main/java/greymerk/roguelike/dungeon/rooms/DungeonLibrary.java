package greymerk.roguelike.dungeon.rooms;

import java.util.Arrays;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.MetaStair;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.Door;
import greymerk.roguelike.worldgen.blocks.DyeColor;
import greymerk.roguelike.worldgen.blocks.FlowerPot;
import greymerk.roguelike.worldgen.blocks.StairType;
import greymerk.roguelike.worldgen.blocks.TallPlant;
import greymerk.roguelike.worldgen.blocks.Trapdoor;
import greymerk.roguelike.worldgen.redstone.Torch;


public class DungeonLibrary extends DungeonBase{

	@Override
	public boolean generate(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		IBlockFactory walls = settings.getTheme().getPrimaryWall();
		
		IStair stair = settings.getTheme().getPrimaryStair();
		
		MetaBlock air = BlockType.get(BlockType.AIR);

		Coord cursor;
		Coord start;
		Coord end;
		
		
		editor.fillRectSolid(rand, new Coord(x - 4, y, z - 4), new Coord(x + 4, y + 3, z + 4), air, true, true);
		editor.fillRectSolid(rand, new Coord(x - 3, y + 4, z - 3), new Coord(x + 3, y + 6, z + 3), air, true, true);
		editor.fillRectSolid(rand, new Coord(x - 2, y + 7, z - 2), new Coord(x + 2, y + 7, z + 2), air, true, true);
		
		editor.fillRectHollow(rand, new Coord(x - 5, y, z - 5), new Coord(x + 5, y + 4, z + 5), walls, false, true);
		editor.fillRectHollow(rand, new Coord(x - 4, y + 3, z - 4), new Coord(x + 4, y + 7, z + 4), walls, false, true);
		editor.fillRectHollow(rand, new Coord(x - 3, y + 6, z - 3), new Coord(x + 3, y + 8, z + 3), walls, false, true);
		
		editor.fillRectSolid(rand, new Coord(x - 5, y - 1, z - 5), new Coord(x + 5, y - 1, z + 5), settings.getTheme().getPrimaryFloor(), true, true);
		
		start = new Coord(origin);
		start.add(Cardinal.UP, 5);
		editor.setBlock(start, BlockType.get(BlockType.REDSTONE_BLOCK));
		start.add(Cardinal.DOWN);
		editor.setBlock(start, BlockType.get(BlockType.REDSTONE_LAMP_LIT));
		start = new Coord(origin);
		start.add(Cardinal.UP, 6);
		end = new Coord(start);
		end.add(Cardinal.UP);
		editor.fillRectSolid(rand, start, end, settings.getTheme().getPrimaryPillar(), true, true);
		
		
		
		for(Cardinal dir : Cardinal.directions){
			
			if(Arrays.asList(entrances).contains(dir)){
				door(editor, rand, settings.getTheme(), dir, origin);
			} else {
				if(rand.nextBoolean()){
					desk(editor, rand, settings.getTheme(), dir, origin);	
				} else {
					plants(editor, rand, settings.getTheme(), dir, origin);
				}
				
			}
			
			start = new Coord(origin);
			start.add(dir, 4);
			start.add(Cardinal.left(dir), 4);
			end = new Coord(start);
			end.add(Cardinal.UP, 4);
			editor.fillRectSolid(rand, start, end, settings.getTheme().getPrimaryPillar(), true, true);
			
			start = new Coord(origin);
			start.add(dir, 3);
			start.add(Cardinal.left(dir), 3);
			start.add(Cardinal.UP, 3);
			end = new Coord(start);
			end.add(Cardinal.UP, 3);
			editor.fillRectSolid(rand, start, end, settings.getTheme().getPrimaryPillar(), true, true);
			
			cursor = new Coord(end);
			cursor.add(Cardinal.reverse(dir));
			cursor.add(Cardinal.right(dir));
			cursor.add(Cardinal.UP);
			editor.setBlock(rand, cursor, walls, true, true);
			
			for(Cardinal o : Cardinal.orthogonal(dir)){
				cursor = new Coord(origin);
				cursor.add(dir, 4);
				cursor.add(o, 3);
				cursor.add(Cardinal.UP, 2);
				
				stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
				cursor.add(Cardinal.UP);
				editor.setBlock(rand, cursor, walls, true, true);
				cursor.add(Cardinal.reverse(o));
				stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
				cursor.add(Cardinal.UP, 3);
				cursor.add(Cardinal.reverse(dir));
				stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
			}
			
			// Light fixture related stuff
			cursor = new Coord(origin);
			cursor.add(Cardinal.UP, 4);
			cursor.add(dir);
			stair.setOrientation(dir, true).setBlock(editor, cursor);
			cursor.add(dir, 2);
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			cursor.add(Cardinal.UP);
			start = new Coord(cursor);
			end = new Coord(cursor);
			start.add(Cardinal.reverse(dir), 2);
			editor.fillRectSolid(rand, start, end, walls, true, true);
			cursor.add(Cardinal.UP);
			editor.setBlock(rand, cursor, walls, true, true);
			cursor.add(Cardinal.UP);
			cursor.add(Cardinal.reverse(dir));
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			cursor.add(Cardinal.reverse(dir));
			stair.setOrientation(dir, true).setBlock(editor, cursor);
		}
		
		
		
		
		return false;
	}

	private void door(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord pos){
		Coord start;
		Coord end;
		
		start = new Coord(pos);
		start.add(dir, 7);
		end = new Coord(start);
		start.add(Cardinal.left(dir));
		end.add(Cardinal.right(dir));
		end.add(Cardinal.UP, 2);
		
		editor.fillRectSolid(rand, start, end, theme.getPrimaryWall(), true, true);
		
		Coord cursor = new Coord(pos);
		cursor.add(dir, 7);
		Door.generate(editor, cursor, dir, Door.OAK);
		
		for(Cardinal o : Cardinal.orthogonal(dir)){
			
			cursor = new Coord(pos);
			cursor.add(dir, 5);
			cursor.add(o);
			cursor.add(Cardinal.UP, 2);
			
			IStair stair = theme.getPrimaryStair();
			stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
			cursor.add(dir);
			stair.setOrientation(Cardinal.reverse(o), true).setBlock(editor, cursor);
		}
	}
	
	private void desk(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord pos){
		
		Coord cursor;
		Coord start;
		Coord end;
		MetaBlock shelf = BlockType.get(BlockType.SHELF);
		
		cursor = new Coord(pos);
		cursor.add(dir, 5);
		start = new Coord(cursor);
		end = new Coord(cursor);
		start.add(Cardinal.left(dir), 2);
		end.add(Cardinal.right(dir), 2);
		end.add(Cardinal.UP, 2);
		BlockType.get(BlockType.AIR).fillRectSolid(editor, rand, start, end, true, true);
		start.add(dir);
		end.add(dir);
		theme.getPrimaryWall().fillRectSolid(editor, rand, start, end, false, true);
		
		for(Cardinal o : Cardinal.orthogonal(dir)){
			Coord c = new Coord(cursor);
			c.add(o, 2);
			c.add(Cardinal.UP, 2);
			theme.getPrimaryStair().setOrientation(Cardinal.reverse(o), true).setBlock(editor, c);
			c.add(dir);
			c.add(Cardinal.DOWN);
			shelf.setBlock(editor, c);
			c.add(Cardinal.DOWN);
			shelf.setBlock(editor, c);
		}
		
		cursor = new Coord(pos);
		cursor.add(dir, 4);
		
		IStair stair = new MetaStair(StairType.OAK);
		stair.setOrientation(dir, false).setBlock(editor, cursor);
		
		cursor.add(dir);
		stair.setOrientation(Cardinal.reverse(dir), true).setBlock(editor, cursor);
		
		cursor.add(Cardinal.left(dir));
		stair.setOrientation(Cardinal.right(dir), true).setBlock(editor, cursor);
		
		cursor.add(Cardinal.right(dir), 2);
		stair.setOrientation(Cardinal.left(dir), true).setBlock(editor, cursor);
		
		cursor.add(Cardinal.UP);
		FlowerPot.generate(editor, rand, cursor);
		
		cursor.add(Cardinal.left(dir));
		ColorBlock.get(ColorBlock.CARPET, DyeColor.GREEN).setBlock(editor, cursor);
		
		cursor.add(Cardinal.left(dir));
		Torch.generate(editor, Torch.WOODEN, Cardinal.UP, cursor);		
	}
	
	private void plants(IWorldEditor editor, Random rand, ITheme theme, Cardinal dir, Coord origin){
		Coord cursor;
		Coord start;
		Coord end;
		
		cursor = new Coord(origin);
		cursor.add(dir, 5);
		start = new Coord(cursor);
		end = new Coord(cursor);
		start.add(Cardinal.left(dir), 2);
		end.add(Cardinal.left(dir), 2);
		end.add(Cardinal.UP, 2);
		BlockType.get(BlockType.AIR).fillRectSolid(editor, rand, start, end, true, true);
		start.add(dir);
		end.add(dir);
		theme.getPrimaryWall().fillRectSolid(editor, rand, start, end, false, true);
		
		for(Cardinal o : Cardinal.orthogonal(dir)){
			Coord c = new Coord(cursor);
			c.add(o, 2);
			c.add(Cardinal.UP, 2);
			theme.getPrimaryStair().setOrientation(Cardinal.reverse(o), true).setBlock(editor, c);
		}
		
		start = new Coord(cursor);
		end = new Coord(cursor);
		start.add(Cardinal.left(dir));
		end.add(Cardinal.right(dir));
		for(Coord c : editor.getRectSolid(start, end)){
			plant(editor, rand, theme, c);
		}
	}
	
	private void plant(IWorldEditor editor, Random rand, ITheme theme, Coord origin){
		Coord cursor;
		BlockType.get(BlockType.DIRT_PODZOL).setBlock(editor, origin);
		
		for(Cardinal dir : Cardinal.directions){
			cursor = new Coord(origin);
			cursor.add(dir);
			Trapdoor.get(Trapdoor.OAK, Cardinal.reverse(dir), true, true).setBlock(editor, rand, cursor, true, false);
		}
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		TallPlant[] plants = new TallPlant[]{TallPlant.FERN, TallPlant.ROSE, TallPlant.PEONY};
		TallPlant.generate(editor, plants[rand.nextInt(plants.length)], cursor);
	}

	
	@Override
	public int getSize() {
		return 8;
	}

}
