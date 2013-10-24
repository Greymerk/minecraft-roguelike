package greymerk.roguelike;


import java.io.*;
import java.util.*;


/**
 *	Provides configuration information from
 *	a file.
 */
public class ConfigFile extends ConfigurationProvider {


	private Hashtable<String,String> kvp;


	/**
	 *	Creates a configuration file by
	 *	parsing a certain file.
	 *
	 *	\param [in] filename
	 *		The name of the file to read.
	 *	\param [in] parser
	 *		A ConfigurationParser which will
	 *		be used to parse the data in
	 *		the given file.
	 */
	public ConfigFile (String filename, ConfigurationParser parser) throws Exception {
	
		//	Create an input stream reader
		//	to read from the specified file
		BufferedReader reader=new BufferedReader(
			new InputStreamReader(
				new FileInputStream(
					filename
				)
			)
		);
		
		kvp=new Hashtable<String,String>();
		
		//	Loop until all settings have
		//	been extracted
		for (;;) {
		
			//	Attempt to extract setting
			Configuration config=parser.Parse(reader);
			
			//	If setting could not be extracted,
			//	the file is done, break
			if (config==null) break;
			
			//	We extracted a setting, insert it
			//	into the hash map
			kvp.put(
				config.Key,
				config.Value
			);
		
		}
	
	}
	
	
	public String Get (String key) {
	
		//	Guard against nulls
		if (key==null) return null;
		
		//	Fetch
		return kvp.get(key);
	
	}


}