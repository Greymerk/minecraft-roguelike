package greymerk.roguelike.dungeon.rooms;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.loot.Firework;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.ColorBlock;
import greymerk.roguelike.worldgen.blocks.DyeColor;
import greymerk.roguelike.worldgen.redstone.Comparator;
import greymerk.roguelike.worldgen.redstone.Dispenser;
import greymerk.roguelike.worldgen.redstone.Dropper;
import greymerk.roguelike.worldgen.redstone.Hopper;
import greymerk.roguelike.worldgen.redstone.Lever;
import greymerk.roguelike.worldgen.redstone.Repeater;
import greymerk.roguelike.worldgen.redstone.Torch;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class DungeonFirework implements IDungeonRoom {

	@Override
	public boolean generate(WorldEditor editor, Random rand, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		MetaBlock breadboard = ColorBlock.get(ColorBlock.CLAY, DyeColor.GREEN);
		
		Coord cursor;
		Coord start;
		Coord end;
		
		Cardinal dir = entrances[0];
		start = new Coord(x, y, z);
		end = new Coord(start);
		start.add(Cardinal.reverse(dir), 9);
		end.add(dir, 9);
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start.add(orth[0], 4);
		end.add(orth[1], 4);
		start.add(Cardinal.DOWN);
		end.add(Cardinal.UP, 3);
		editor.fillRectHollow(rand, start, end, BlockType.get(BlockType.COBBLESTONE), false, true);
		
		start = new Coord(x, y, z);
		start.add(orth[0], 2);
		end = new Coord(start);
		start.add(Cardinal.reverse(dir), 3);
		end.add(dir, 7);
		end.add(Cardinal.UP);
		editor.fillRectSolid(rand, start, end, breadboard, true, true);
		
		start.add(orth[1], 2);
		end.add(orth[1], 2);
		editor.fillRectSolid(rand, start, end, breadboard, true, true);
		
		start.add(orth[1], 2);
		end.add(orth[1], 2);
		editor.fillRectSolid(rand, start, end, breadboard, true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(orth[0], 2);
		
		launcher(editor, rand, dir, cursor);
		cursor.add(orth[1], 2);
		launcher(editor, rand, dir, cursor);
		cursor.add(orth[1], 2);
		launcher(editor, rand, dir, cursor);
		cursor.add(dir, 6);
		launcher(editor, rand, dir, cursor);
		cursor.add(orth[0], 2);
		launcher(editor, rand, dir, cursor);
		cursor.add(orth[0], 2);
		launcher(editor, rand, dir, cursor);
		
		start = new Coord(x, y, z);
		start.add(dir, 4);
		end = new Coord(start);
		start.add(orth[0], 2);
		end.add(orth[1], 2);
		end.add(dir, 2);
		editor.fillRectSolid(rand, start, end, BlockType.get(BlockType.AIR), true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(dir, 2);
		Repeater.generate(editor, rand, dir, 0, cursor);
		cursor.add(orth[0], 2);
		Repeater.generate(editor, rand, dir, 0, cursor);
		cursor.add(orth[1], 4);
		Repeater.generate(editor, rand, dir, 0, cursor);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.reverse(dir), 3);
		cursor.add(orth[0]);
		Repeater.generate(editor, rand, orth[0], 0, cursor);
		cursor.add(orth[1], 2);
		Repeater.generate(editor, rand, orth[1], 0, cursor);
		
		MetaBlock wire = BlockType.get(BlockType.REDSTONE_WIRE);
		
		start = new Coord(x, y, z);
		start.add(Cardinal.DOWN, 2);
		start.add(orth[1]);
		start.add(Cardinal.reverse(dir), 2);
		end = new Coord(start);
		end.add(orth[0], 5);
		end.add(Cardinal.reverse(dir), 5);
		end.add(Cardinal.DOWN, 2);
		editor.fillRectSolid(rand, start, end, BlockType.get(BlockType.COBBLESTONE), true, true);
		
		cursor = new Coord(x, y, z);
		cursor.add(Cardinal.reverse(dir), 3);
		cursor.add(Cardinal.DOWN);
		Torch.generate(editor, Torch.REDSTONE, Cardinal.UP, cursor);
		cursor.add(Cardinal.DOWN);
		breadboard.setBlock(editor, cursor);
		cursor.add(orth[0]);
		Torch.generate(editor, Torch.REDSTONE, orth[0], cursor);
		cursor.add(orth[0]);
		wire.setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		wire.setBlock(editor, cursor);
		cursor.add(Cardinal.reverse(dir));
		wire.setBlock(editor, cursor);
		cursor.add(orth[1]);
		wire.setBlock(editor, cursor);
		cursor.add(orth[1]);
		wire.setBlock(editor, cursor);
		cursor.add(dir);
		Repeater.generate(editor, rand, Cardinal.reverse(dir), 4, cursor);
		cursor.add(Cardinal.UP);
		cursor.add(Cardinal.reverse(dir));
		breadboard.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		Lever.generate(editor, Cardinal.UP, cursor, true);
		
		return false;
	}


	private void launcher(WorldEditor editor, Random rand, Cardinal dir, Coord pos){
		Coord cursor = new Coord(pos);
		editor.setBlock(cursor, BlockType.get(BlockType.REDSTONE_WIRE));
		cursor.add(Cardinal.reverse(dir));
		editor.setBlock(cursor, BlockType.get(BlockType.REDSTONE_WIRE));
		cursor.add(Cardinal.reverse(dir));
		Repeater.generate(editor, rand, dir, 0, cursor);
		cursor.add(Cardinal.reverse(dir));
		cursor.add(Cardinal.UP);
		
		Dropper dropper = new Dropper();
		dropper.generate(editor, Cardinal.UP, cursor);
		for(int i = 0;i < 8; ++i){
			dropper.add(editor, cursor, i, new ItemStack(Items.dye, 1, i));
		}
		dropper.add(editor, cursor, 8, new ItemStack(Items.wooden_hoe));
		
		cursor.add(Cardinal.UP);
		Hopper.generate(editor, Cardinal.DOWN, cursor);
		cursor.add(dir);
		Comparator.generate(editor, rand, dir, false, cursor);
		cursor.add(dir);
		editor.setBlock(cursor, BlockType.get(BlockType.REDSTONE_WIRE));
		cursor.add(dir);
		editor.setBlock(cursor, BlockType.get(BlockType.REDSTONE_WIRE));
		cursor.add(dir);
		
		Coord top = new Coord(pos.getX(), 80, pos.getZ());
		while(top.getY() > pos.getY()){
			top.add(Cardinal.DOWN);
			if(editor.getBlock(top).getBlock().getMaterial().isSolid()) break;
		}
		
		if(top.getY() >= 100) return;
		
		Coord start = new Coord(cursor);
		start.add(Cardinal.UP);
		

		start.add(dir);
		Coord end = new Coord(start);
		
		MetaBlock breadboard = ColorBlock.get(ColorBlock.CLAY, DyeColor.GREEN);
		
		
		boolean torch = false;
		while(end.getY() < top.getY()){
			if(torch){
				Torch.generate(editor, Torch.REDSTONE, Cardinal.UP, cursor);
			} else {
				breadboard.setBlock(editor, cursor);
			}
			torch = !torch;
			cursor.add(Cardinal.UP);
			end.add(Cardinal.UP);
		}
		
		if(torch){
			cursor.add(Cardinal.DOWN);
		}
		
		Dispenser.generate(editor, Cardinal.UP, cursor);
		for(int i = 0; i < 9; i++){
			Dispenser.add(editor, cursor, i, Firework.get(rand, 16 + rand.nextInt(16)));
		}
		
		cursor.add(Cardinal.UP);
		MetaBlock cob = BlockType.get(BlockType.COBBLESTONE);
		editor.fillRectSolid(rand, start, end, cob, true, true);
		start.add(Cardinal.reverse(dir), 2);
		end.add(Cardinal.reverse(dir), 2);
		editor.fillRectSolid(rand, start, end, cob, true, true);
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		start.add(dir);
		end.add(dir);
		Coord above = new Coord(end);
		above.add(Cardinal.UP, 10);
		List<Coord> tubeEnd = WorldEditor.getRectSolid(cursor, above);
		MetaBlock air = BlockType.get(BlockType.AIR);
		for(Coord c : tubeEnd){
			if(editor.getBlock(c).getBlock().getMaterial().isSolid()){
				air.setBlock(editor, c);
			}
		}
		start.add(orth[0]);
		end.add(orth[0]);
		editor.fillRectSolid(rand, start, end, cob, true, true);
		start.add(orth[1], 2);
		end.add(orth[1], 2);
		editor.fillRectSolid(rand, start, end, cob, true, true);
	}
	
	
	@Override
	public int getSize() {
		return 10;
	}

	@Override
	public boolean validLocation(WorldEditor editor, Cardinal dir, int x, int y, int z) {
		Coord start;
		Coord end;
		
		start = new Coord(x, y, z);
		end = new Coord(start);
		start.add(Cardinal.reverse(dir), 9);
		end.add(dir, 9);
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
