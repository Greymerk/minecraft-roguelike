package greymerk.roguelike.dungeon.settings;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import greymerk.roguelike.config.RogueConfig;

public class DungeonSettingsTest {

	@Before
	public void before(){
		RogueConfig.testing = true;
	}
	
	@Test
	public void overridesMerge() {
		DungeonSettings base = new DungeonSettings();
		DungeonSettings other = new DungeonSettings();
		other.overrides.add(SettingsType.LOOTRULES);
		
		DungeonSettings merge = new DungeonSettings(base, other);
		assertTrue(merge.overrides.contains(SettingsType.LOOTRULES));
		
		merge = new DungeonSettings(other, base);
		assertTrue(merge.overrides.contains(SettingsType.LOOTRULES));
	}
	
	@Test
	public void overridesCopy(){
		DungeonSettings setting = new DungeonSettings();
		setting.overrides.add(SettingsType.LOOTRULES);
		
		DungeonSettings copy = new DungeonSettings(setting);
		assertTrue(copy.overrides.contains(SettingsType.LOOTRULES));
	}
}
