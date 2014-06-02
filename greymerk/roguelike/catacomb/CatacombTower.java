package greymerk.roguelike.catacomb;

import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.catacomb.theme.Themes;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.TreasureChestStarter;
import greymerk.roguelike.worldgen.BlockRandomizer;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.World;

public class CatacombTower {

	public CatacombTower(){
	}

	private Coord getBaseCoord(World world, int x, int y, int z){
		
		List<Integer> invalidBlocks = new ArrayList<Integer>();
		invalidBlocks.add(0); // Air
		invalidBlocks.add(Block.wood.blockID);
		invalidBlocks.add(Block.leaves.blockID);
		invalidBlocks.add(Block.cactus.blockID);
		invalidBlocks.add(Block.reed.blockID);
		invalidBlocks.add(Block.vine.blockID);
		invalidBlocks.add(Block.snow.blockID);
		invalidBlocks.add(Block.cocoaPlant.blockID);
		
		int tempY = 128;
		int block = world.getBlockId(x, tempY, z);

		while(tempY > 60){

			if(invalidBlocks.indexOf(block) == -1){
				break;
			}

			tempY = tempY - 1;

			block = world.getBlockId(x, tempY, z);

		}

		int yOffset = tempY - y;

		if(yOffset < 14){
			yOffset = 14;
		}
		
		return new Coord(x, y + yOffset, z);

	}
	
	public void generate(World world, Random rand, int x, int y, int z){
		
		ITheme theme = getTheme(world.getBiomeGenForCoords(x, z), rand);
		
		IBlockFactory blocks = theme.getPrimaryWall();
		
		MetaBlock stair = theme.getPrimaryStair();
		
		Coord floor = this.getBaseCoord(world, x, y, z);
		int ground = floor.getY() - 1;
		int main = floor.getY() + 4;
		int roof = floor.getY() + 9;
		
		WorldGenPrimitive.fillRectSolid(world, x - 3, ground, z - 3, x + 3, floor.getY() + 12, z + 3, 0);

		Coord start;
		Coord end;
		Coord cursor;
		
		WorldGenPrimitive.fillRectSolid(world, x - 3, main, z - 3, x + 3, main, z + 3, new MetaBlock(Block.planks.blockID, 1), true, true);
		WorldGenPrimitive.fillRectSolid(world, x - 3, roof, z - 3, x + 3, roof, z + 3, blocks, true, true);
		
		MetaBlock air = new MetaBlock(0);
		
		for(Cardinal dir : Cardinal.directions){
			for (Cardinal orth : Cardinal.getOrthogonal(dir)){
				// ground floor
				start = new Coord(floor);
				start.add(Cardinal.DOWN, 1);
				start.add(dir, 2);
				end = new Coord(start);
				end.add(dir, 3);
				end.add(orth, 1);
				WorldGenPrimitive.fillRectSolid(world, start, end, blocks, true, true);
				start.add(orth, 2);
				end.add(Cardinal.reverse(dir), 2);
				end.add(orth, 2);
				WorldGenPrimitive.fillRectSolid(world, start, end, blocks, true, true);
				
				cursor = new Coord(floor);
				cursor.add(dir, 5);
				cursor.add(orth, 1);
				start = new Coord(cursor);
				end = new Coord(cursor);
				end.add(Cardinal.reverse(dir), 1);
				end.add(Cardinal.UP, 2);
				WorldGenPrimitive.fillRectSolid(world, start, end, blocks, true, true);
				start = new Coord(end);
				start.add(dir, 1);
				start.add(Cardinal.reverse(orth), 1);
				WorldGenPrimitive.fillRectSolid(world, start, end, blocks, true, true);
				cursor.add(Cardinal.UP, 2);
				stair.setMeta(WorldGenPrimitive.blockOrientation(orth, false));
				WorldGenPrimitive.setBlock(world, cursor, stair, true, true);
				
				start = new Coord(floor);
				start.add(dir, 4);
				end = new Coord(start);
				end.add(Cardinal.UP, 9);
				end.add(orth, 2);
				WorldGenPrimitive.fillRectSolid(world, start, end, blocks, true, true);
				
				start = new Coord(floor);
				start.add(dir, 3);
				start.add(orth, 3);
				end = new Coord(start);
				end.add(Cardinal.UP, 9);
				WorldGenPrimitive.fillRectSolid(world, start, end, blocks, true, true);
				
				start = new Coord(floor);
				start.add(dir, 4);
				end = new Coord(start);
				end.add(dir, 1);
				end.add(Cardinal.UP, 1);
				WorldGenPrimitive.fillRectSolid(world, start, end, air, true, true);
				
				cursor = new Coord(floor);
				cursor.add(dir, 3);
				cursor.add(orth, 2);
				cursor.add(Cardinal.UP, 3);
				stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(orth), true));
				WorldGenPrimitive.setBlock(world, cursor, stair, true, true);
				cursor.add(Cardinal.UP, 5);
				stair.setMeta(WorldGenPrimitive.blockOrientation(Cardinal.reverse(orth), true));
				WorldGenPrimitive.setBlock(world, cursor, stair, true, true);
				
				start = new Coord(floor);
				start.add(dir, 4);
				start.add(orth, 3);
				start.add(Cardinal.UP, 4);
				stair.setMeta(WorldGenPrimitive.blockOrientation(orth, true));
				WorldGenPrimitive.setBlock(world, start, stair, true, true);
				
				start.add(Cardinal.UP, 1);
				end = new Coord(start);
				end.add(Cardinal.UP, 4);
				WorldGenPrimitive.fillRectSolid(world, start, end, blocks, true, true);
				
				start = new Coord(floor);
				start.add(dir, 5);
				start.add(Cardinal.UP, 4);
				stair.setMeta(WorldGenPrimitive.blockOrientation(dir, true));
				WorldGenPrimitive.setBlock(world, start, stair, true, true);
				
				cursor = new Coord(start);
				cursor.add(orth, 1);
				stair.setMeta(WorldGenPrimitive.blockOrientation(orth, true));
				WorldGenPrimitive.setBlock(world, cursor, stair, true, true);
				
				start.add(Cardinal.UP, 3);
				stair.setMeta(WorldGenPrimitive.blockOrientation(dir, true));
				WorldGenPrimitive.setBlock(world, start, stair, true, true);
				
				cursor = new Coord(start);
				cursor.add(orth, 1);
				stair.setMeta(WorldGenPrimitive.blockOrientation(orth, true));
				WorldGenPrimitive.setBlock(world, cursor, stair, true, true);
				
				start.add(Cardinal.UP, 1);
				end = new Coord(start);
				end.add(orth, 1);
				end.add(Cardinal.UP, 1);
				WorldGenPrimitive.fillRectSolid(world, start, end, blocks, true, true);
				
				cursor = new Coord(end);
				cursor.add(orth, 1);
				cursor.add(Cardinal.DOWN, 1);
				stair.setMeta(WorldGenPrimitive.blockOrientation(orth, true));
				WorldGenPrimitive.setBlock(world, cursor, stair, true, true);
				cursor.add(Cardinal.UP, 1);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, cursor, stair, true, true);
				
				cursor.add(Cardinal.reverse(orth), 1);
				WorldGenPrimitive.setBlock(world, cursor, blocks, true, true);
				cursor.add(Cardinal.UP, 1);
				WorldGenPrimitive.setBlock(world, cursor, blocks, true, true);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, cursor, blocks, true, true);
				cursor.add(Cardinal.UP, 1);
				this.addCrenellation(world, cursor, blocks);
				
				cursor.add(Cardinal.DOWN, 2);
				cursor.add(Cardinal.reverse(dir), 1);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, cursor, blocks, true, true);
				cursor.add(Cardinal.DOWN, 1);
				WorldGenPrimitive.setBlock(world, cursor, blocks, true, true);
				
				cursor = new Coord(floor);
				cursor.add(dir, 6);
				cursor.add(Cardinal.UP, 9);
				
				stair.setMeta(WorldGenPrimitive.blockOrientation(dir, true));
				WorldGenPrimitive.setBlock(world, cursor, stair, true, true);
				
				cursor.add(orth, 1);
				stair.setMeta(WorldGenPrimitive.blockOrientation(orth, true));
				WorldGenPrimitive.setBlock(world, cursor, stair, true, true);
				
				cursor.add(Cardinal.reverse(orth), 1);
				cursor.add(Cardinal.UP, 1);
				WorldGenPrimitive.setBlock(world, cursor, blocks, true, true);
				cursor.add(orth, 1);
				WorldGenPrimitive.setBlock(world, cursor, blocks, true, true);
				cursor.add(Cardinal.UP, 1);
				this.addCrenellation(world, cursor, blocks);
				
				cursor = new Coord(floor);
				cursor.add(dir, 4);
				cursor.add(Cardinal.UP, 5);
				WorldGenPrimitive.setBlock(world, cursor, air, true, true);
				cursor.add(Cardinal.UP, 1);
				WorldGenPrimitive.setBlock(world, cursor, air, true, true);
				cursor.add(orth, 2);
				WorldGenPrimitive.setBlock(world, cursor, new MetaBlock(Block.fenceIron.blockID), true, true);
			}
			

			
			for(int i = main; i > y; --i){
				WorldGenPrimitive.spiralStairStep(world, x, i, z, stair, blocks);
			}
		}
	}
	
	
	private void addCrenellation(World world, Coord cursor, IBlockFactory blocks){
		
		WorldGenPrimitive.setBlock(world, cursor, blocks, true, true);
		
		if(world.getBlockId(cursor.getX(), cursor.getY(), cursor.getZ()) == 0){
			return;
		}

		cursor.add(Cardinal.UP, 1);
		WorldGenPrimitive.setBlock(world, cursor, new MetaBlock(Block.torchWood.blockID), true, true);
	}
	
	private ITheme getTheme(BiomeGenBase biome, Random rand){
		boolean hot = biome.temperature >= 1.0F;
		boolean cold = biome.temperature <= 0.1F;
		boolean wet = biome.rainfall >= 0.85F;
		boolean dry = biome.rainfall <= 0.1;
		
		if(hot && dry) return Themes.getTheme(Themes.SANDSTONE, rand);
		if(hot && wet) return Themes.getTheme(Themes.JUNGLE, rand);
		return Themes.getTheme(Themes.TOWER, rand);
	}
}
