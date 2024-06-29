package com.greymerk.roguelike.debug;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DebugLayout {
	
	Path root;
	Path debug;
	
	public DebugLayout(Path root) {
		this.root = root;
		this.debug = root.resolve("debug");
		this.debug.toFile().mkdir();
	}
	
	public void toFile(String name, JsonObject content) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonElement je = JsonParser.parseString(content.toString());
		String pretty = gson.toJson(je);
		
		Path filepath = this.debug.resolve(name);
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
