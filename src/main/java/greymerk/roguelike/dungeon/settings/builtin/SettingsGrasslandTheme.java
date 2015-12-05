package greymerk.roguelike.dungeon.settings.builtin;

import java.util.ArrayList;
import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonRoom;
import greymerk.roguelike.dungeon.base.SecretFactory;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SpawnCriteria;
import net.minecraftforge.common.BiomeDictionary;

public class SettingsGrasslandTheme extends DungeonSettings{

	public SettingsGrasslandTheme(){
		
		this.criteria = new SpawnCriteria();
		List<BiomeDictionary.Type> biomes = new ArrayList<BiomeDictionary.Type>();
		biomes.add(BiomeDictionary.Type.PLAINS);
		this.criteria.setBiomeTypes(biomes);
		
		for(int i = 0; i < 5; ++i){
			
			SecretFactory factory = new SecretFactory();

			switch(i){
			case 0:
				factory.addRoom(DungeonRoom.BEDROOM, 2);
				factory.addRoom(DungeonRoom.SMITH);
				factory.addRoom(DungeonRoom.FIREWORK);
				break;
			case 1:
				factory.addRoom(DungeonRoom.BTEAM);
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
			
			LevelSettings level = new LevelSettings();
			level.setSecrets(factory);
			levels.put(i, level);
		}
		
	}
	
	
}
