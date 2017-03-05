package greymerk.roguelike.dungeon.settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsContainer {

	public static final String DEFAULT_NAMESPACE = "default";
	public static final String BUILTIN_NAMESPACE = "builtin";
	
	private Map<String, Map<String, DungeonSettings>> settingsByNamespace;
	
	public SettingsContainer(){
		this.settingsByNamespace = new HashMap<String, Map<String, DungeonSettings>>();
	}
	
	public void put(DungeonSettings setting){
		String namespace = setting.getNameSpace() != null ? setting.getNameSpace() : DEFAULT_NAMESPACE;
		String name = setting.getName();
		
		if(!settingsByNamespace.containsKey(namespace)){
			settingsByNamespace.put(namespace, new HashMap<String, DungeonSettings>());
		}
		
		Map<String, DungeonSettings> settings = this.settingsByNamespace.get(namespace);
		settings.put(name, setting);
	}
	
	public Collection<DungeonSettings> getByNamespace(String namespace){
		if(!this.settingsByNamespace.containsKey(namespace)) return new ArrayList<DungeonSettings>();
		return this.settingsByNamespace.get(namespace).values();
	}
	
	public Collection<DungeonSettings> getBuiltinSettings(){
		List<DungeonSettings> settings = new ArrayList<DungeonSettings>();
		
		for(String namespace : settingsByNamespace.keySet()){
			if(!namespace.equals(SettingsContainer.BUILTIN_NAMESPACE)) continue;
			settings.addAll(settingsByNamespace.get(namespace).values());
		}
		
		return settings;
	}
	
	public Collection<DungeonSettings> getCustomSettings(){
		
		List<DungeonSettings> settings = new ArrayList<DungeonSettings>();
		
		for(String namespace : settingsByNamespace.keySet()){
			if(namespace.equals(SettingsContainer.BUILTIN_NAMESPACE)) continue;
			settings.addAll(settingsByNamespace.get(namespace).values());
		}
		
		return settings;
	}
	
	public DungeonSettings get(SettingIdentifier id){
		if(!contains(id)) return null;
		Map<String, DungeonSettings> settings = settingsByNamespace.get(id.getNamespace());
		return settings.get(id.getName());
	}
	
	public boolean contains(SettingIdentifier id){
		
		if(!settingsByNamespace.containsKey(id.getNamespace())) return false;
		
		Map<String, DungeonSettings> settings = settingsByNamespace.get(id.getNamespace());
		if(!settings.containsKey(id.getName())) return false;
		
		return true;
	}
	
	@Override
	public String toString(){
		String strg = "";
		for(String namespace : settingsByNamespace.keySet()){
			Map<String, DungeonSettings> settings = settingsByNamespace.get(namespace); 
				
			for(DungeonSettings setting : settings.values()){
				strg += setting.id.toString() + " ";
			}
		}
		
		return strg;
	}
}
