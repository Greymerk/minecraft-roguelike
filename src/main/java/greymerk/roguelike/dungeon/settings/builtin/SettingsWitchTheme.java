package greymerk.roguelike.dungeon.settings.builtin;

import greymerk.roguelike.dungeon.base.DungeonFactory;
import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import greymerk.roguelike.util.WeightedRandomizer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BiomeDictionary;

public class SettingsWitchTheme extends DungeonSettings{
	
	public SettingsWitchTheme(){
		
		this.depth = 2;
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.SWAMP);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new TowerSettings(Tower.WITCH, Theme.getTheme(Theme.DARKOAK));
		
		Theme[] themes = {Theme.DARKHALL, Theme.MUDDY, Theme.MOSSY, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			
			LevelSettings level = new LevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			
			if(i == 0){
				level.setDifficulty(1);
				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addRandom(DungeonRoom.BRICK, 5);
				factory.addRandom(DungeonRoom.CORNER, 5);
				factory.addRandom(DungeonRoom.DARKHALL, 1);
				factory.addSingle(DungeonRoom.LIBRARY);
				factory.addSingle(DungeonRoom.FIRE);
				level.setRooms(factory);
			}
			
			if(i == 1){
				level.setDifficulty(2);
				DungeonFactory factory;
				factory = new DungeonFactory();
				factory.addRandom(DungeonRoom.BRICK, 5);
				factory.addRandom(DungeonRoom.CORNER, 5);
				factory.addRandom(DungeonRoom.PIT, 2);
				factory.addSingle(DungeonRoom.PRISON);
				factory.addRandom(DungeonRoom.LAB, 2);
				factory.addRandom(DungeonRoom.SPIDER, 3);
				level.setRooms(factory);
				
			}
			
			LootSettings loot = new LootSettings(2);
			WeightedRandomizer<ItemStack> junk = new WeightedRandomizer<ItemStack>();
			junk.add(new WeightedRandomLoot(Items.ghast_tear, 1));
			junk.add(new WeightedRandomLoot(Items.blaze_powder, 3));
			junk.add(new WeightedRandomLoot(Items.poisonous_potato, 5));
			junk.add(new WeightedRandomLoot(Blocks.red_mushroom, 5));
			junk.add(new WeightedRandomLoot(Blocks.brown_mushroom, 5));
			junk.add(new WeightedRandomLoot(Items.stick, 10));
			junk.add(new WeightedRandomLoot(Items.blaze_rod, 1));
			junk.add(new WeightedRandomLoot(Items.sugar, 8));
			junk.add(new WeightedRandomLoot(Items.nether_wart, 1));
			junk.add(new WeightedRandomLoot(Items.magma_cream, 2));
			junk.add(new WeightedRandomLoot(Items.reeds, 1));
			junk.add(new WeightedRandomLoot(Items.gunpowder, 3));
			junk.add(new WeightedRandomLoot(Items.glass_bottle, 8));
			junk.add(new WeightedRandomLoot(Items.redstone, 0, 1, 3, 5));
			loot.set(Loot.JUNK, junk);
			level.setLoot(loot);
			
			levels.put(i, level);
		}
	}
}
