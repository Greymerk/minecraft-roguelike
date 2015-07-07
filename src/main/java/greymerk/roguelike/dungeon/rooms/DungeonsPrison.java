package greymerk.roguelike.dungeon.rooms;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.CatacombLevelSettings;
import greymerk.roguelike.dungeon.theme.ITheme;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class DungeonsPrison extends DungeonBase {

	World world;
	Random rand;
	IBlockFactory blocks;
	IBlockFactory pillar;
	CatacombLevelSettings settings;
	
	
	public DungeonsPrison(){}
	
	@Override
	public boolean generate(World inWorld, Random inRandom, CatacombLevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		
		ITheme theme = settings.getTheme();
		this.settings = settings;
		
		world = inWorld;
		rand = inRandom;
		
		blocks = theme.getPrimaryWall();
		pillar = theme.getPrimaryPillar();
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// clear air
		WorldGenPrimitive.fillRectSolid(inWorld, rand, x - 7, y, z - 7, x + 7, y + 3, z + 7, air);
		
		// create outer walls
		WorldGenPrimitive.fillRectHollow(world, rand, x - 8, y - 1, z - 8, x + 8, y + 5, z + 8, blocks, false, true);
		
		// fill hallway ceiling beams
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y + 3, z - 2, x + 7, y + 3, z - 2, blocks);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y + 3, z + 2, x + 7, y + 3, z + 2, blocks);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 3, z - 7, x - 2, y + 3, z + 7, blocks);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y + 3, z - 7, x + 2, y + 3, z + 7, blocks);
		
		// fill hallway roofs
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y + 4, z - 1, x - 2, y + 4, z + 1, blocks);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y + 4, z - 1, x + 7, y + 4, z + 1, blocks);

		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y + 4, z - 7, x + 1, y + 4, z - 2, blocks);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y + 4, z + 2, x + 1, y + 4, z + 7, blocks);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y + 4, z - 1, x + 1, y + 4, z + 1, blocks);
		
		List<Coord> cells = new ArrayList<Coord>();
		cells.add(new Coord(x - 5, y, z - 5));
		cells.add(new Coord(x - 5, y, z + 5));
		cells.add(new Coord(x + 5, y, z - 5));
		cells.add(new Coord(x + 5, y, z + 5));
		
		for (Coord cell : cells){
			createCell(cell);
		}
		
		int numChests = 1;
		if(rand.nextInt(5) == 0){
			numChests += 1;
		}
		
		TreasureChest.createChests(inWorld, inRandom, settings, numChests, cells);
		
		return false;
	}
	
	
	private void createCell(Coord cell){
		
		int inX = cell.getX();
		int inY = cell.getY();
		int inZ = cell.getZ();
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// floor
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 3, inY - 1, inZ - 3, inX + 3, inY - 1, inZ + 3, blocks);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY - 1, inZ - 1, inX + 1, inY - 1, inZ + 1, new MetaBlock(Blocks.mossy_cobblestone));
		
		// pillars
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, inY, inZ - 2, inX - 2, inY + 2, inZ - 2, pillar);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, inY, inZ + 2, inX - 2, inY + 2, inZ + 2, pillar);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, inY, inZ - 2, inX + 2, inY + 2, inZ - 2, pillar);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, inY, inZ + 2, inX + 2, inY + 2, inZ + 2, pillar);
		
		// roof
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 3, inY + 3, inZ - 3, inX + 3, inY + 6, inZ + 3, blocks);
		
		
		// torches
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY + 4, inZ - 2, inX + 1, inY + 4, inZ - 2, air);
		WorldGenPrimitive.setBlock(world, inX, inY + 4, inZ - 2, Blocks.redstone_torch);
		
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY + 4, inZ + 2, inX + 1, inY + 4, inZ + 2, air);
		WorldGenPrimitive.setBlock(world, inX, inY + 4, inZ + 2, Blocks.redstone_torch);
		
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, inY + 4, inZ - 1, inX - 2, inY + 4, inZ + 1, air);
		WorldGenPrimitive.setBlock(world, inX - 2, inY + 4, inZ, Blocks.redstone_torch);

		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, inY + 4, inZ - 1, inX + 2, inY + 4, inZ + 1, air);
		WorldGenPrimitive.setBlock(world, inX + 2, inY + 4, inZ, Blocks.redstone_torch);

		// ceiling holes
		WorldGenPrimitive.fillRectSolid(world, rand, inX, inY + 3, inZ, inX, inY + 6, inZ, air);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY + 3, inZ - 1, inX - 1, inY + 6, inZ - 1, air);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY + 3, inZ + 1, inX - 1, inY + 6, inZ + 1, air);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 1, inY + 3, inZ - 1, inX + 1, inY + 6, inZ - 1, air);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 1, inY + 3, inZ + 1, inX + 1, inY + 6, inZ + 1, air);
		
		MetaBlock bars = new MetaBlock(Blocks.iron_bars);
		
		// bars
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY, inZ - 2, inX + 1, inY + 2, inZ - 2, bars);
		WorldGenPrimitive.fillRectSolid(world, rand, inX, inY, inZ - 2, inX, inY + 1, inZ - 2, air);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 1, inY, inZ + 2, inX + 1, inY + 2, inZ + 2, bars);
		WorldGenPrimitive.fillRectSolid(world, rand, inX, inY, inZ + 2, inX, inY + 1, inZ + 2, air);
		
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, inY, inZ - 1, inX - 2, inY + 2, inZ + 1, bars);
		WorldGenPrimitive.fillRectSolid(world, rand, inX - 2, inY, inZ, inX - 2, inY + 1, inZ, air);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, inY, inZ - 1, inX + 2, inY + 2, inZ + 1, bars);
		WorldGenPrimitive.fillRectSolid(world, rand, inX + 2, inY, inZ, inX + 2, inY + 1, inZ, air);
		
		if(rand.nextBoolean()){
			switch(rand.nextInt(4)){
			case 0:
				Spawner.generate(world, rand, settings, new Coord(inX - 2, inY + 4, inZ - 2), Spawner.ZOMBIE);
				break;
			case 1:
				Spawner.generate(world, rand, settings, new Coord(inX - 2, inY + 4, inZ + 2), Spawner.ZOMBIE);
				break;
			case 2:
				Spawner.generate(world, rand, settings, new Coord(inX + 2, inY + 4, inZ - 2), Spawner.ZOMBIE);
				break;
			case 3:
				Spawner.generate(world, rand, settings, new Coord(inX + 2, inY + 4, inZ + 2), Spawner.ZOMBIE);
				break;
			}
		}
	}
	
	public int getSize(){
		return 10;
	}
}
