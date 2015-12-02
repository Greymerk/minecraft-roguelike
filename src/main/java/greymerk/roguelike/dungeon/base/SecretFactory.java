package greymerk.roguelike.dungeon.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;

public class SecretFactory implements ISecretRoom{

	private int count;
	private List<ISecretRoom> secrets;

	
	public SecretFactory(){
		count = 0;
		secrets = new ArrayList<ISecretRoom>();
	}
	
	public SecretFactory(SecretFactory toCopy){
		secrets = new ArrayList<ISecretRoom>();
		this.count = toCopy.count;
		
		for(ISecretRoom room : toCopy.secrets){
			
			ISecretRoom toAdd;
			
			if(room instanceof SecretFactory){
				toAdd = new SecretFactory((SecretFactory)room);
			} else {
				toAdd = new SecretRoom((SecretRoom)room);
			}
			
			this.secrets.add(toAdd);
		}
	}
	
	public SecretFactory(JsonArray data){
		secrets = new ArrayList<ISecretRoom>();
		for(JsonElement e : data){
			JsonObject room = e.getAsJsonObject();
			String type = room.get("type").getAsString();
			int num = room.get("num").getAsInt();
			this.count += num;
			secrets.add(new SecretRoom(DungeonRoom.valueOf(type), num));
		}
	}
	
	public void addRoom(DungeonRoom type){
		addRoom(type, 1);
	}
	
	public void addRoom(DungeonRoom type, int count){
		secrets.add(new SecretRoom(type, count));
		this.count += count;
	}
	
	public void addRoom(List<DungeonRoom> rooms, int count){
		
		SecretFactory toAdd = new SecretFactory();
		
		for(DungeonRoom type : rooms){
			toAdd.addRoom(type);
		}
		
		this.secrets.add(toAdd);
		this.count += count;
	}
	
	public IDungeonRoom genRoom(WorldEditor editor, Random rand, LevelSettings settings, Cardinal dir, Coord pos){
		if(count <= 0) return null;
		
		Collections.shuffle(this.secrets, rand);
		
		for(ISecretRoom room : secrets){
			IDungeonRoom generated = room.genRoom(editor, rand, settings, dir, pos);
			if(generated != null){
				return generated;
			}
		}
		
		return null;
	}
}