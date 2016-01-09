package greymerk.roguelike.dungeon.settings.builtin;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.Treasure;
import greymerk.roguelike.treasure.loot.LootRuleManager;
import greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import greymerk.roguelike.util.WeightedRandomizer;
import greymerk.roguelike.worldgen.blocks.Log;
import greymerk.roguelike.worldgen.blocks.Wood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BiomeDictionary;

public class SettingsGrasslandTheme extends DungeonSettings{

	public SettingsGrasslandTheme(){
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.PLAINS);
		this.criteria.setBiomeTypes(biomes);
		
		this.towerSettings = new TowerSettings(Tower.ROGUE, Theme.getTheme(Theme.TOWER));
		
		this.lootRules = new LootRuleManager();
		WeightedRandomizer<ItemStack> wood = new WeightedRandomizer<ItemStack>(1);
		wood.add(new WeightedRandomLoot(Log.getLog(Wood.OAK).getBlock(), 0, 2, 8, 1));
		wood.add(new WeightedRandomLoot(Log.getLog(Wood.BIRCH).getBlock(), 0, 2, 8, 1));
		this.lootRules.add(Treasure.BLOCKS, wood, 0, true, 1);
		this.lootRules.add(Treasure.BLOCKS, wood, 1, true, 1);
		this.lootRules.add(Treasure.STARTER, wood, 0, true, 1);
		
		for(int i = 0; i < 5; ++i){
			
			LevelSettings level = new LevelSettings();
			SecretFactory secrets = new SecretFactory();

			switch(i){
			case 0:
				secrets.addRoom(DungeonRoom.BEDROOM, 2);
				secrets.addRoom(DungeonRoom.SMITH);
				secrets.addRoom(DungeonRoom.ENCHANT);
				secrets.addRoom(DungeonRoom.FIREWORK);
				break;
			case 1:
				secrets.addRoom(DungeonRoom.BTEAM);
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			default:
				break;
			}
			
			level.setSecrets(secrets);
			levels.put(i, level);
		}
		
	}
	
	
}
