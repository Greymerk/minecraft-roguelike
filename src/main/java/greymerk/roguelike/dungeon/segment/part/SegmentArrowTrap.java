package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.redstone.Dispenser;
import greymerk.roguelike.worldgen.redstone.Torch;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SegmentArrowTrap extends SegmentBase{

	@Override
	protected void genWall(WorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, int x, int y, int z) {
		
		MetaBlock plate = new MetaBlock(Blocks.stone_pressure_plate);
		MetaBlock wire = new MetaBlock(Blocks.redstone_wire);
		MetaBlock vine = new MetaBlock(Blocks.vine);
		MetaBlock air = new MetaBlock(Blocks.air);
		MetaBlock stair = theme.getPrimaryStair();
		IBlockFactory wall = theme.getPrimaryWall();
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		Coord origin = new Coord(x, y, z);
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(dir, 2);
		end = new Coord(start);
		start.add(orth[0]);
		end.add(orth[1]);
		end.add(Cardinal.UP, 2);
		editor.fillRectSolid(rand, start, end, vine, true, true);
		start.add(dir);
		end.add(dir);
		editor.fillRectSolid(rand, start, end, wall, true, true);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.UP);
		cursor.add(dir, 3);
		air.setBlock(editor, cursor);
		
		for (Cardinal side : orth){
			cursor = new Coord(origin);
			cursor.add(dir, 2);
			cursor.add(side);
			WorldEditor.blockOrientation(stair, Cardinal.reverse(side), false).setBlock(editor, cursor);
			cursor.add(Cardinal.UP, 2);
			WorldEditor.blockOrientation(stair, Cardinal.reverse(side), true).setBlock(editor, cursor);
		}
		
		start = new Coord(origin);
		end = new Coord(start);
		start.add(dir);
		end.add(Cardinal.reverse(dir));
		
		editor.fillRectSolid(rand, start, end, plate, true, true);
		
		end.add(Cardinal.DOWN, 2);
		start = new Coord(end);
		start.add(dir, 3);
		
		editor.fillRectSolid(rand, start, end, wire, true, true);
		
		cursor = new Coord(start);
		cursor.add(dir, 2);
		Torch.generate(editor, Torch.REDSTONE, dir, cursor);
		cursor.add(Cardinal.UP, 2);
		Torch.generate(editor, Torch.REDSTONE, Cardinal.UP, cursor);
		cursor.add(Cardinal.UP);
		Dispenser.generate(editor, Cardinal.reverse(dir), cursor);
		for(int i = 0; i <= 3; i++){
			ItemStack arrows = new ItemStack(Items.arrow, rand.nextInt(4) + 1);
			Dispenser.add(editor, cursor, rand.nextInt(9), arrows);
		}
	}
}
