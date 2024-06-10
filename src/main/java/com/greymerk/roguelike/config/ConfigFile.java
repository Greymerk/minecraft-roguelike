package com.greymerk.roguelike.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.greymerk.roguelike.Roguelike;

public class ConfigFile {

	private static final String configDirPath = "config";
	private static final String configFilePath = "roguelike-dungeons.json";
	private static final String configFullPath = configDirPath + File.separatorChar + configFilePath;
	
	private ConfigSettings settings;
	
	public ConfigFile(ConfigSettings settings) {
		this.settings = settings;
	}
	
	public void read() {
		Optional<String> contents = getFileContents();
		if(contents.isEmpty()) return;
		try {
			String str = contents.get();
			JsonElement jsonElement = JsonParser.parseString(str);
			JsonObject json = jsonElement.getAsJsonObject();
			settings.parse(json);	
		} catch (IllegalStateException e) {
			Roguelike.LOGGER.error(e.toString());
			Roguelike.LOGGER.error("Invalid Json Config - Replacing with defaults");
			this.writeConfigFile();
			return;
		} catch (Exception e){
			Roguelike.LOGGER.error(e.toString());
			Roguelike.LOGGER.error("Something's wrong with the config file - using defaults for now");
			return;
		}
	}
	
	public Optional<String> getFileContents() {
		File file = this.getFile();
		try {
			return Optional.of(IOUtils.toString(new FileReader(file)));
		} catch (FileNotFoundException e) {
			return Optional.empty();
		} catch (IOException e) {
			return Optional.empty();
		}
	}
	
	public File getFile() {
		File file = new File(configFullPath);
		if(!file.exists()) {
			writeConfigFile();
		}
		return file;
	}
	
	public void writeConfigFile() {
		File directory = new File(configDirPath);
		if(!directory.exists()) {
			directory.mkdirs();
		}
		
	    try {
	        FileWriter writer = new FileWriter(configFullPath);
	        writer.flush();
	        JsonObject json = settings.asJson();
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        writer.write(gson.toJson(json));
	        writer.close();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	}
}
