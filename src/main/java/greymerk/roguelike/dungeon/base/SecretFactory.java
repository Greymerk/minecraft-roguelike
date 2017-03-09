package greymerk.roguelike.dungeon.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;

public class SecretFactory{

	private Map<DungeonRoom, ISecretRoom> secrets;

	
	public SecretFactory(){
		secrets = new HashMap<DungeonRoom, ISecretRoom>();
	}
	
	public SecretFactory(SecretFactory toCopy){
		this();
		for(DungeonRoom type : toCopy.secrets.keySet()){
			int count = toCopy.secrets.get(type).getCount();
			this.addRoom(type, count);			
		}
	}
	
	public SecretFactory(SecretFactory base, SecretFactory other){
		this();
		if(base != null){
			for(DungeonRoom type : base.secrets.keySet()){
				int count = base.secrets.get(type).getCount();
				this.addRoom(type, count);
			}
		}
		
		if(other != null){
			for(DungeonRoom type : other.secrets.keySet()){
				int count = other.secrets.get(type).getCount();
				this.addRoom(type, count);
			}	
		}
	}
	
	public SecretFactory(JsonArray data){
		this();
		for(JsonElement e : data){
			JsonObject room = e.getAsJsonObject();
			String type = room.get("type").getAsString();
			int num = room.get("num").getAsInt();
			this.addRoom(DungeonRoom.valueOf(type), num);
		}
	}
	
	public void addRoom(DungeonRoom type){
		addRoom(type, 1);
	}
	
	public void addRoom(DungeonRoom type, int count){
		
		ISecretRoom room;
		
		if(this.secrets.containsKey(type)){
			room = this.secrets.get(type);
			room.add(count);
			return;
		}
		
		room = new SecretRoom(type, count);
		this.secrets.put(type, room);
	}
	
	public IDungeonRoom genRoom(IWorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord pos){
		
		for(ISecretRoom room : this.secrets.values()){
			IDungeonRoom generated = room.genRoom(editor, rand, settings, dir, pos);
			if(generated != null){
				return generated;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean equals(Object o){
		
		SecretFactory other = (SecretFactory)o;
		
		if(!this.secrets.keySet().equals(other.secrets.keySet())) return false;
		
		for(DungeonRoom type : this.secrets.keySet()){
			if(!this.secrets.get(type).equals(other.secrets.get(type))) return false;
		}
		
		return true;
	}

	public static SecretFactory getRandom(Random rand, int count) {
		SecretFactory secrets = new SecretFactory();
		for(int i = 0; i < count; ++i){
			secrets.addRoom(DungeonRoom.getRandomSecret(rand));	
		}
		return secrets;
	}
}