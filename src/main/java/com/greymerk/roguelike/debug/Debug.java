package com.greymerk.roguelike.debug;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.greymerk.roguelike.Roguelike;
import com.greymerk.roguelike.config.Config;
import com.greymerk.roguelike.editor.IWorldEditor;

public class Debug {
	
	public static boolean isOn() {
		return Config.ofBoolean(Config.DEBUG);
	}
	
	public static void info(String message) {
		if(Debug.isOn()) Roguelike.LOGGER.info("DEBUG: " + message);
	}
	
	private static Path debugPath(Path root) {
		Path debug = root.resolve("debug");
		debug.toFile().mkdir();
		return debug;
	}
	
	public static void toFile(IWorldEditor editor, String name, JsonElement content) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonElement je = JsonParser.parseString(content.toString());
		String pretty = gson.toJson(je);
		
		Path filepath = Debug.debugPath(editor.getInfo().getWorldDirectory()).resolve(name);
		File fh = filepath.toFile();
		fh.delete();
		try {
			fh.createNewFile();
		} catch (IOException e) {
			return;
		}
		FileWriter fw;
		try {
			fw = new FileWriter(fh);
		} catch (IOException e) {
			return;
		}
		
		try {
			fw.write(pretty);
		} catch (IOException e1) {
			try {
				fw.close();
			} catch (IOException e) {
				return;
			}
		}
		
		try {
			fw.close();
		} catch (IOException e) {
			return;
		}
	}
}
