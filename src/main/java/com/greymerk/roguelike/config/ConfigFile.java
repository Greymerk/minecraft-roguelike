package com.greymerk.roguelike.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.IOUtils;

import com.greymerk.roguelike.Roguelike;

public class ConfigFile implements IConfigStore {

	private static final String configDirPath = "config";
	private static final String configFilePath = "roguelike-dungeons.json";
	private static final String configFullPath = configDirPath + File.separatorChar + configFilePath;
	
	public ConfigFile() {
	}

	@Override
	public Optional<String> getFileContents() {
		File file = new File(configFullPath);
		if(!file.exists()) {
			return Optional.empty();
		}
		try {
			return Optional.of(IOUtils.toString(new FileReader(file)));
		} catch (FileNotFoundException e) {
			return Optional.empty();
		} catch (IOException e) {
			return Optional.empty();
		}
	}

	@Override
	public void write(String content) {
		File directory = new File(configDirPath);
		
		if(!directory.exists()) {
			directory.mkdirs();
		}

		try {
			FileWriter writer = new FileWriter(configFullPath);
			writer.flush();
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			Roguelike.LOGGER.error(e.getMessage());
		}
	}

	@Override
	public boolean exists() {
		File f = new File(configFullPath);
		return f.exists();
	}
}
