package greymerk.roguelike.dungeon.rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.Spawner;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.blocks.Crops;

public class DungeonsNetherBrickFortress extends DungeonBase {
	
	WorldEditor editor;
	Random rand;
	int originX;
	int originY;
	int originZ;
	Spawner spawner;
	
	public DungeonsNetherBrickFortress() {
		super();
	}
	
	
	public boolean generate(WorldEditor editor, Random inRandom, LevelSettings settings, Cardinal[] entrances, Coord origin) {
		
		this.editor = editor;
		rand = inRandom;
		originX = origin.getX();
		originY = origin.getY();
		originZ = origin.getZ();
		
		MetaBlock air = BlockType.get(BlockType.AIR);

		// cut out air space
		editor.fillRectSolid(rand, originX - 5, originY, originZ - 5,
				originX + 5, originY + 3, originZ + 5, air);

		buildWalls(settings);
		buildFloor();
		buildRoof();
		
		ArrayList<TreasureChest> types = new ArrayList<TreasureChest>(Arrays.asList(TreasureChest.SPECIAL));		
		TreasureChest.createChests(editor, rand, settings, 2, WorldEditor.getRectSolid(
				originX - 6, originY, originZ - 6,
				originX + 6, originY, originZ + 6),
				types);

		return true;
	}
	
    public boolean isValidDungeonLocation(WorldEditor editor, int originX, int originY, int originZ){
    	return false;
    }
	
	protected void buildRoof(){
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		
		// top
		editor.fillRectSolid(rand, originX - 6, originY + 4, originZ - 6,
				originX + 6, originY + 6, originZ + 6, BlockType.get(BlockType.NETHERBRICK));

		List<Coord> lavaArea = WorldEditor.getRectSolid(	originX - 3, originY + 6, originZ - 3,
																originX + 3, originY + 6, originZ + 3);
		
		for (Coord block : lavaArea){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if(rand.nextBoolean()){
				editor.setBlock(x, y, z, BlockType.get(BlockType.LAVA_FLOWING));
			}
		}
		
		
		// sub-ceiling air square
		editor.fillRectSolid(rand, originX - 3, originY + 4, originZ - 3,
				originX + 3, originY + 4, originZ + 3, air);
		
		
		// arches
		BlockJumble fenceRandom = new BlockJumble();
		fenceRandom.addBlock(BlockType.get(BlockType.NETHERBRICK));
		fenceRandom.addBlock(BlockType.get(BlockType.FENCE_NETHER_BRICK));
		
		editor.fillRectSolid(rand, originX - 5, originY + 4, originZ - 3, originX - 4, originY + 4, originZ + 3, fenceRandom);
		editor.fillRectSolid(rand, originX + 4, originY + 4, originZ - 3, originX + 5, originY + 4, originZ + 3, fenceRandom);
		editor.fillRectSolid(rand, originX - 3, originY + 4, originZ - 5, originX + 3, originY + 4, originZ - 4, fenceRandom);
		editor.fillRectSolid(rand, originX - 3, originY + 4, originZ + 4, originX + 3, originY + 4, originZ + 5, fenceRandom);

	}
	
    protected void buildWalls(LevelSettings settings){
    	
    	// door walls
		List<Coord> outerWall = editor.getRectHollow(originX - 6, originY - 1, originZ - 6, 
																originX + 6, originY + 5, originZ + 6);
		for (Coord block : outerWall){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if(y >= originY && y <= originY + 4){
				editor.setBlock(rand, x, y, z, BlockType.get(BlockType.NETHERBRICK), false, true);
			}
		}
		
		// pillars
		List<Coord> arch1 = WorldEditor.getRectSolid(	originX - 5, originY, originZ - 5,
															originX - 4, originY + 4, originZ - 4);

		List<Coord> arch2 = WorldEditor.getRectSolid(	originX - 5, originY, originZ + 4,
															originX - 4, originY + 4, originZ + 5);	
		
		List<Coord> arch3 = WorldEditor.getRectSolid(	originX + 4, originY, originZ - 5,
															originX + 5, originY + 4, originZ - 4);	
		
		List<Coord> arch4 = WorldEditor.getRectSolid(	originX + 4, originY, originZ + 4,
															originX + 5, originY + 4, originZ + 5);	
		
		List<Coord> pillars = new ArrayList<Coord>();
		pillars.addAll(arch1);
		pillars.addAll(arch2);
		pillars.addAll(arch3);
		pillars.addAll(arch4);
		
		Collections.shuffle(pillars, rand);
		
		for (Coord block : pillars){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			editor.setBlock(x, y, z, BlockType.get(BlockType.NETHERBRICK));
		}
		
		for(int i = 0; i < rand.nextInt(5) + 5; ++i){
			Spawner toSpawn = this.pickMobSpawner(rand);
			Spawner.generate(editor, rand, settings, pillars.get(i), toSpawn);
		}
	}
    	
    	
    
    protected void buildFloor(){

		// base
		editor.fillRectSolid(rand, originX - 6, originY - 4, originZ - 6, originX + 6, originY - 1, originZ + 6, BlockType.get(BlockType.NETHERBRICK));
    	
		List<Coord> soulSand = WorldEditor.getRectSolid(	originX - 5, originY - 1, originZ - 5,
																originX + 5, originY - 1, originZ + 5);

		MetaBlock sand = BlockType.get(BlockType.SOUL_SAND);
		MetaBlock wart = Crops.get(Crops.NETHERWART);
		
		for (Coord block : soulSand){
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			if(rand.nextBoolean() && editor.isAirBlock(new Coord(x, y + 1, z))){
				editor.setBlock(x, y, z, sand);
				if(rand.nextBoolean()){
					editor.setBlock(x, y + 1, z, wart);
				}
			}
		}
		
 
    }
    
    protected Spawner pickMobSpawner(Random random)
    {
    	
    	if(rand.nextInt(10) == 0){
    		return Spawner.CAVESPIDER;
    	}
    	
    	if(rand.nextInt(5) == 0){
    		return Spawner.CREEPER;
    	}
    	
        int choice = random.nextInt(3);
        
        switch(choice){
        
        case 0:
        	return Spawner.SKELETON;
        case 1:
        	return Spawner.BLAZE;
        case 2:
        	return Spawner.ZOMBIE;
        
        default:
        	return Spawner.ZOMBIE;
        }
    }
    
	public int getSize(){
		return 8;
	}
    
    
}
