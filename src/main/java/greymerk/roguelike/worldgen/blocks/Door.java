package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public enum Door {

	IRON, OAK, BIRCH, SPRUCE, JUNGLE, ACACIA, DARKOAK;
	
	public static void generate(IWorldEditor editor, Coord pos, Cardinal dir, Door type){
		generate(editor, pos, dir, type, false);
	}
	
	public static void generate(IWorldEditor editor, Coord pos, Cardinal dir, Door type, boolean open){
		
		Coord cursor = new Coord(pos);
		MetaBlock doorBase = getMeta(type, false, dir, open, false);
		doorBase.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		MetaBlock doorTop = getMeta(type, true, dir, open, false);
		doorTop.setBlock(editor, cursor);
	}
	
	private static MetaBlock getMeta(Door type, boolean top, Cardinal dir, boolean open, boolean hingeLeft){
		
		IBlockState door;
		switch(type){
		case IRON: door = new MetaBlock(Blocks.iron_door.getDefaultState()); break;
		case BIRCH: door = new MetaBlock(Blocks.birch_door.getDefaultState()); break;
		case SPRUCE: door = new MetaBlock(Blocks.spruce_door.getDefaultState()); break;
		case JUNGLE: door = new MetaBlock(Blocks.jungle_door.getDefaultState()); break;
		case ACACIA: door = new MetaBlock(Blocks.acacia_door.getDefaultState()); break;
		case DARKOAK: door = new MetaBlock(Blocks.dark_oak_door.getDefaultState()); break;
		default: door = new MetaBlock(Blocks.oak_door.getDefaultState()); break;
		}
		
		door = door.withProperty(BlockDoor.HALF, top ? BlockDoor.EnumDoorHalf.UPPER : BlockDoor.EnumDoorHalf.LOWER);
		
		door = door.withProperty(BlockDoor.FACING, Cardinal.getFacing(dir));
		
		door = door.withProperty(BlockDoor.OPEN, open);
		
		door = door.withProperty(BlockDoor.HINGE, hingeLeft ? BlockDoor.EnumHingePosition.LEFT : BlockDoor.EnumHingePosition.RIGHT);
		
		return new MetaBlock(door);
	}

}
