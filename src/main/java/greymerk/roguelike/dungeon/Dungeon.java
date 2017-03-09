package greymerk.roguelike.dungeon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import greymerk.roguelike.Roguelike;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.treasure.loot.Book;
import greymerk.roguelike.util.WeightedChoice;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;


public class Dungeon implements IDungeon{
		
	private static final String SETTINGS_DIRECTORY = RogueConfig.configDirName + "/settings";
	public static SettingsResolver settingsResolver;
	
	private DungeonGenerator generator;
	private Coord pos;
	private IWorldEditor editor;
	
	static{
		try{
			RogueConfig.reload(false);
			initResolver();
		} catch(Exception e) {
			// do nothing
		}
	}
	
	public static void initResolver() throws Exception{
		File settingsDir = new File(SETTINGS_DIRECTORY);
		
		if(settingsDir.exists() && !settingsDir.isDirectory()){
			throw new Exception("Settings directory is a file");
		}
		
		if(!settingsDir.exists()){
			settingsDir.mkdir();
		}
		
		File[] settingsFiles = settingsDir.listFiles();
		Arrays.sort(settingsFiles);
		
		settingsResolver = new SettingsResolver();
		
		Map<String, String> files = new HashMap<String, String>();
		
		for(File file : Arrays.asList(settingsFiles)){
			try {
				String content = Files.toString(file, Charsets.UTF_8);
				files.put(file.getName(), content); 
			} catch (IOException e) {				
				throw new Exception("Error reading file : " + file.getName());
			}
		}

		settingsResolver.parseCustomSettings(files);
				
	}
		
	
	public Dungeon(IWorldEditor editor){
		this.generator = new DungeonGenerator();
		this.editor = editor;
	}
	
	public void generateNear(Random rand, int x, int z){
		if(Dungeon.settingsResolver == null) return;
		
		int attempts = 50;
		
		for(int i = 0;i < attempts;i++){
			Coord location = getNearbyCoord(rand, x, z, 40, 100);
			
			if(!validLocation(rand, location.getX(), location.getZ())) continue;
			
			ISettings setting = Dungeon.settingsResolver.getSettings(editor, rand, location); 
			
			if(setting == null) return;
			
			generate(setting, location.getX(), location.getZ());
			return;
		}
	}
	
	public void generate(ISettings settings, int inX, int inZ){
		generator.generate(editor, settings, inX, inZ);
		this.pos = new Coord(inX, 50, inZ);

		Random rand = getRandom(editor, this.pos.getX(), this.pos.getZ());
		TreasureManager treasure = editor.getTreasure();

		settings.getLootRules().process(rand, treasure);
		 // TODO: Change start book details
		Book book = new Book("Greymerk", "Statistics");
		book.addPage("~Architect's Resource Notes~\n\n"
			+ "StoneBrick: " + editor.getStat(Blocks.STONEBRICK) + "\n"
			+ "Cobblestone: " + editor.getStat(Blocks.COBBLESTONE) + "\n"
			+ "Logs: " + (editor.getStat(Blocks.LOG) + editor.getStat(Blocks.LOG2)) + "\n"
			+ "Iron Bars: " + editor.getStat(Blocks.IRON_BARS) + "\n"
			+ "Chests: " + (editor.getStat(Blocks.CHEST) + editor.getStat(Blocks.TRAPPED_CHEST)) + "\n"
			+ "Mob Spawners: " + editor.getStat(Blocks.MOB_SPAWNER) + "\n"
			+ "TNT: " + editor.getStat(Blocks.TNT) + "\n"
			+ "\n-Greymerk");
		book.addPage("Roguelike Dungeons v" + Roguelike.version + "\n"
			+ Roguelike.date + "\n\n"
			+ "Credits\n\n"
			+ "Author: Greymerk\n\n"
			+ "Bits: Drainedsoul\n\n"
			+ "Ideas: Eniko @enichan");
		treasure.addItemToAll(rand, Treasure.STARTER, new WeightedChoice<ItemStack>(book.get(), 1), 1);
	}
	
	public static boolean canSpawnInChunk(int chunkX, int chunkZ, IWorldEditor editor){
		
		if(!RogueConfig.getBoolean(RogueConfig.DONATURALSPAWN)) return false;
		
		int dim = editor.getDimension();
		List<Integer> wl = new ArrayList<Integer>();
		wl.addAll(RogueConfig.getIntList(RogueConfig.DIMENSIONWL));
		List<Integer> bl = new ArrayList<Integer>();
		bl.addAll(RogueConfig.getIntList(RogueConfig.DIMENSIONBL));
		if(!SpawnCriteria.isValidDimension(dim, wl, bl)) return false;
		
		int frequency = RogueConfig.getInt(RogueConfig.SPAWNFREQUENCY);
		int min = 8 * frequency / 10;
		int max = 32 * frequency / 10;
		
		min = min < 2 ? 2 : min;
		max = max < 8 ? 8 : max;
		
		int tempX = chunkX < 0 ? chunkX - (max - 1) : chunkX;
		int tempZ = chunkZ < 0 ? chunkZ - (max - 1) : chunkZ;

		int m = tempX / max;
		int n = tempZ / max;
		
		Random r = editor.getSeededRandom(m, n, 10387312);
		
		m *= max;
		n *= max;
		
		m += r.nextInt(max - min);
		n += r.nextInt(max - min);
		
		if(!(chunkX == m && chunkZ == n)){
			return false;
		}
		
		return true;
	}
	
	public void spawnInChunk(Random rand, int chunkX, int chunkZ) {
		
		if(Dungeon.canSpawnInChunk(chunkX, chunkZ, editor)){
			int x = chunkX * 16 + 4;
			int z = chunkZ * 16 + 4;
			
			generateNear(rand, x, z);
		}
	}
	
	public static int getLevel(int y){
		
		if (y < 15)	return 4;
		if (y < 25) return 3;
		if (y < 35) return 2;
		if (y < 45) return 1;
		return 0;
	}
	
	public boolean validLocation(Random rand, int x, int z){
		
		Coord pos = new Coord(x, 0, z);
		Biome biome = editor.getBiome(pos);

		Type[] invalidBiomes = new Type[]{
				BiomeDictionary.Type.RIVER,
				BiomeDictionary.Type.BEACH,
				BiomeDictionary.Type.MUSHROOM,
				BiomeDictionary.Type.OCEAN
		};
		
		for(Type type : invalidBiomes){
			if(BiomeDictionary.isBiomeOfType(biome, type)) return false;
		}
				
		int upperLimit = RogueConfig.getInt(RogueConfig.UPPERLIMIT);
		int lowerLimit = RogueConfig.getInt(RogueConfig.LOWERLIMIT);
		
		Coord cursor = new Coord(x, upperLimit, z);
		
		if(!editor.isAirBlock(cursor)){
			return false;
		}
		
		while(!editor.validGroundBlock(cursor)){
			cursor.add(Cardinal.DOWN);
			if(cursor.getY() < lowerLimit) return false;
			if(editor.getBlock(cursor).getMaterial() == Material.WATER) return false;
		}

		for (Coord c : new RectSolid(new Coord(x - 4, cursor.getY() + 4, z - 4), new Coord(x + 4, cursor.getY() + 4, z + 4))){
			if(editor.validGroundBlock(c)){
				return false;
			}
		}
		
		int airCount = 0;
		for (Coord c : new RectSolid(new Coord(x - 4, cursor.getY() - 3, z - 4), new Coord(x + 4, cursor.getY() - 3, z + 4))){
			if(!editor.validGroundBlock(c)){
				airCount++;
			}
			if(airCount > 8){
				return false;
			}
		}
		
		return true;
	}
	
	public static Coord getNearbyCoord(Random rand, int x, int z, int min, int max){
		
		int distance = min + rand.nextInt(max - min);
		
		double angle = rand.nextDouble() * 2 * Math.PI;
		
		int xOffset = (int) (Math.cos(angle) * distance);
		int zOffset = (int) (Math.sin(angle) * distance);
		
		Coord nearby = new Coord(x + xOffset, 0, z + zOffset);		
		return nearby;
	}
	
	public static Random getRandom(IWorldEditor editor, int x, int z){
		long seed = editor.getSeed() * x * z;
		Random rand = new Random();
		rand.setSeed(seed);
		return rand;
	}

	@Override
	public List<ITreasureChest> getChests() {
		return this.editor.getTreasure().getChests();
	}

	public Coord getPosition() throws Exception {
		if(this.pos == null){
			throw new Exception("Dungeon not yet generated");
		}
		return this.pos;
	}
}
