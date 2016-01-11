package greymerk.roguelike.dungeon.segment.part;

import java.util.Random;

import greymerk.roguelike.dungeon.IDungeonLevel;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.treasure.loot.Potion;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.IStair;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.redstone.Dispenser;
import greymerk.roguelike.worldgen.redstone.Torch;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SegmentTrap extends SegmentBase{

	@Override
	protected void genWall(IWorldEditor editor, Random rand, IDungeonLevel level, Cardinal dir, ITheme theme, Coord origin) {
		
		MetaBlock plate = BlockType.get(BlockType.PRESSURE_PLATE_STONE);
		MetaBlock wire = BlockType.get(BlockType.REDSTONE_WIRE);
		MetaBlock vine = BlockType.get(BlockType.VINE);
		MetaBlock air = BlockType.get(BlockType.AIR);
		IStair stair = theme.getPrimaryStair();
		IBlockFactory wall = theme.getPrimaryWall();
		
		Cardinal[] orth = Cardinal.orthogonal(dir);
		
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
			stair.setOrientation(Cardinal.reverse(side), false).setBlock(editor, cursor);
			cursor.add(Cardinal.UP, 2);
			stair.setOrientation(Cardinal.reverse(side), true).setBlock(editor, cursor);
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
		
		for(int i = 0; i < 5; i++){
			Dispenser.add(editor, cursor, rand.nextInt(9), new ItemStack(Items.arrow, rand.nextInt(4) + 1));
		}
		
		Dispenser.add(editor, cursor, 5, getPayload(rand));
	}
	
	private ItemStack getPayload(Random rand){
		
		switch(rand.nextInt(3)){
		case 0: return BlockType.getItem(BlockType.TNT);
		case 1: return Potion.getSpecific(rand, Potion.POISON, false, false, true);
		case 2: return Potion.getSpecific(rand, Potion.HARM, false, false, true);
		default: return BlockType.getItem(BlockType.TNT);
		}
	}
}
