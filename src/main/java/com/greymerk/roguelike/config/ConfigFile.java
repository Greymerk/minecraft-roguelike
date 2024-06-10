package com.greymerk.roguelike.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConfigFile {

	private static final String configDirPath = "config" + File.separatorChar + "roguelike_dungeons";
	private static final String configFilePath = "roguelike.json";
	private static final String configFullPath = configDirPath + File.separatorChar + configFilePath;
	
	private ConfigSettings settings;
	
	public ConfigFile(ConfigSettings settings) {
		this.settings = settings;
	}
	
	public void read() {
		JsonObject json = JsonParser.parseString(getFileContents()).getAsJsonObject();
		settings.parse(json);
	}
	
	public String getFileContents() {
		File file = this.getFile();
		try {
			return IOUtils.toString(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "{}";
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
