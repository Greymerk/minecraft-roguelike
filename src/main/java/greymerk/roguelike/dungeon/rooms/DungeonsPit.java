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
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.redstone.Piston;
import greymerk.roguelike.worldgen.redstone.Torch;
import net.minecraft.init.Blocks;

public class DungeonsPit extends DungeonBase {
	WorldEditor editor;
	Random rand;
	int originX;
	int originY;
	int originZ;
	byte dungeonHeight;
	int dungeonLength;
	int dungeonWidth;
	int woolColor;
	int numChests;
	IBlockFactory blocks;
	
	public DungeonsPit() {
		dungeonHeight = 3;
		dungeonLength = 2;
		dungeonWidth = 2;
	}

	public boolean generate(WorldEditor editor, Random inRandom, LevelSettings settings, Cardinal[] entrances, Coord origin) {

		ITheme theme = settings.getTheme();

		this.editor = editor;
		rand = inRandom;
		originX = origin.getX();
		originY = origin.getY();
		originZ = origin.getZ();

		blocks = theme.getPrimaryWall();
		
		buildWalls();
		buildFloor();
		buildRoof();
		buildPit();
		

		for (Cardinal dir : Cardinal.directions){
			setTrap(editor, rand, settings, dir, origin);
		}
		
		List<Coord> space = new ArrayList<Coord>();
		space.add(new Coord(originX - 2, originY, originZ - 2));
		space.add(new Coord(originX - 2, originY, originZ + 2));
		space.add(new Coord(originX + 2, originY, originZ - 2));
		space.add(new Coord(originX + 2, originY, originZ + 2));
		
		TreasureChest.createChests(editor, inRandom, settings, 1, space);
		
		return true;
	}

	MetaBlock air = new MetaBlock(Blocks.air);
	protected void buildWalls() {
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++) {
			for (int blockY = originY + dungeonHeight; blockY >= originY - 1; blockY--) {
				for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++) {
					if (blockX == originX - dungeonLength - 1
							|| blockZ == originZ - dungeonWidth - 1
							|| blockX == originX + dungeonLength + 1
							|| blockZ == originZ + dungeonWidth + 1){

						if (blockY >= 0 && !editor.getBlock(new Coord(blockX, blockY - 1, blockZ)).getBlock().getMaterial().isSolid()) {
							editor.setBlock(blockX, blockY, blockZ, Blocks.air);
							continue;
						}
						
						if (!editor.getBlock(new Coord(blockX, blockY, blockZ)).getBlock().getMaterial().isSolid()) continue;
						
						blocks.setBlock(editor, rand, new Coord(blockX, blockY, blockZ));
						
					} else {
						editor.setBlock(blockX, blockY, blockZ, Blocks.air);
					}
				}
			}
		}
	}
	
	protected void buildFloor(){
		
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++){
			for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++){
				blocks.setBlock(editor, rand, new Coord(blockX, originY - 1, blockZ));				
			}
		}
	}
	
	protected void buildRoof(){
		for (int blockX = originX - dungeonLength - 1; blockX <= originX + dungeonLength + 1; blockX++){
			for (int blockZ = originZ - dungeonWidth - 1; blockZ <= originZ + dungeonWidth + 1; blockZ++){
				blocks.setBlock(editor, rand, new Coord(blockX, dungeonHeight + 1, blockZ));
			}
		}
	}

	private void buildPit(){
		
		for(int x = originX - 2; x <= originX + 2; x++){
			for(int z = originZ - 2; z <= originZ + 2; z++){
				for(int y = originY - 1; y > 0; y--){
					
					if(editor.isAirBlock(new Coord(x, y, z))){
						continue;
					}
					
					if(y < 0 + rand.nextInt(5) && editor.getBlock(new Coord(x, y, z)).getBlock() == Blocks.bedrock){
						continue;
					}
					
					if(    x == originX - 2
						|| x == originX +2
						|| z == originZ -2
						|| z == originZ +2){
						
						blocks.setBlock(editor, rand, new Coord(x, y, z), true, true);
						continue;
						
					}
					
					if(y < 10){
						editor.setBlock(x, y, z, Blocks.flowing_water);
						continue;
					}
					
					editor.setBlock(x, y, z, Blocks.air);
				}
			}
		}
	}
	
	private void setTrap(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord origin){
		ITheme theme = settings.getTheme();
		IBlockFactory walls = theme.getPrimaryWall();
		MetaBlock plate = new MetaBlock(Blocks.stone_pressure_plate);
		MetaBlock wire = new MetaBlock(Blocks.redstone_wire);
		Coord start;
		Coord end;
		Coord cursor;
		
		Cardinal[] orth = Cardinal.getOrthogonal(dir);
		
		start = new Coord(origin);
		start.add(dir, 3);
		start.add(Cardinal.DOWN);
		start.add(orth[0]);
		end = new Coord(origin);
		end.add(dir, 6);
		end.add(Cardinal.UP, 3);
		end.add(orth[1]);
		
		
		
		List<Coord> box = editor.getRectHollow(start, end);
		
		for(Coord cell : box){
			if(editor.isAirBlock(cell)) return;
			walls.setBlock(editor, rand, cell);
		}
		
		cursor = new Coord(origin);
		cursor.add(dir, 2);
		plate.setBlock(editor, cursor);
		
		cursor = new Coord(origin);
		cursor.add(Cardinal.DOWN);
		cursor.add(dir, 3);
		Torch.generate(editor, Torch.REDSTONE, dir, cursor);
		cursor.add(dir);
		wire.setBlock(editor, cursor);
		cursor.add(Cardinal.UP);
		cursor.add(dir);
		Torch.generate(editor, Torch.REDSTONE_UNLIT, Cardinal.UP, cursor);
		cursor.add(Cardinal.reverse(dir));
		cursor.add(Cardinal.UP);
		Piston.generate(editor, cursor, Cardinal.reverse(dir), true);
	}
	
	public int getSize(){
		return 4;
	}
}
