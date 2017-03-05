package greymerk.roguelike.dungeon.settings;

public class SettingIdentifier {

	private String namespace;
	private String name;
	
	public SettingIdentifier(String namespace, String name){
		this.namespace = namespace;
		this.name = name;
	}
	
	public SettingIdentifier(String name){
		
		String[] parts;
		parts = name.split(":");
		if(parts.length > 1){
			this.namespace = parts[0];
			this.name = parts[1];
			return;
		}
		
		this.namespace = SettingsContainer.DEFAULT_NAMESPACE;
		this.name = name;
	}
	
	public String getNamespace(){
		if(namespace == null) return SettingsContainer.DEFAULT_NAMESPACE;
		return this.namespace;
	}
	
	public String getName(){
		return this.name;
	}
	
	@Override
	public boolean equals(Object o){
		SettingIdentifier other = (SettingIdentifier)o;
		if(this.namespace != other.namespace) return false;
		if(this.name != other.name) return false;
		return true;
	}
	
	@Override
	public String toString(){
		return this.getNamespace() + ":" + this.getName();
	}
}
