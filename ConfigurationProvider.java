package greymerk.roguelike;


import java.util.*;


/**
 *	Provides configuration information.
 */
public abstract class ConfigurationProvider implements Iterable<Configuration> {


	protected Hashtable<String,String> kvp;
	
	
	protected ConfigurationProvider () {
	
		kvp=new Hashtable<String,String>();
	
	}


	/**
	 *	Gets a configuration setting,
	 *	if it exists.
	 *
	 *	\param [in] key
	 *		The key whose associated configuration
	 *		information is to be retrieved.
	 *
	 *	\return
	 *		The data associated with \em key,
	 *		if it exists, \em null otherwise.
	 */
	public String Get (String key) {
	
		//	Guard against null keys
		if (key==null) return null;
		
		//	Fetch
		return kvp.get(key);
	
	}
	
	
	/**
	 *	Gets a configuration setting, returning
	 *	some default value if no default is
	 *	specified.
	 *
	 *	\param [in] key
	 *		The key whose associated configuration
	 *		information is to be retrieved.
	 *	\param [in] fallback
	 *		A value which shall be retrieved if
	 *		no configuration is associated with
	 *		\em key.
	 *
	 *	\return
	 *		The data associated with \em key, if
	 *		it exists, otherwise \em fallback.
	 */
	public String Get (String key, String fallback) {

		String value=Get(key);
		
		return (value==null) ? fallback : value;
	
	}	
	/**
	 *	Gets a configuration setting as a double.
	 *
	 *	\param [in] key
	 *		The key whose associated configuration
	 *		information is to be retrieved.
	 *	\param [in] fallback
	 *		The value which will be retrieved if
	 *		\em key is not present, or if \em key
	 *		could not be parsed to a double.
	 *
	 *	\return
	 *		Either the configuration associated
	 *		with \em key, or \em fallback.
	 */
	public double GetDouble (String key, double fallback) {
	
		String value=Get(key);
		
		if (value==null) return fallback;
		
		try {
		
			return Double.parseDouble(value);
		
		} catch (NumberFormatException e) {	}
		
		return fallback;
	
	}
	/**
	 *	Gets a configuration setting as an integer.
	 *
	 *	\param [in] key
	 *		The key whose associated configuration
	 *		information is to be retrieved.
	 *	\param [in] fallback
	 *		The value which will be retrieved if
	 *		\em key is not present, or if \em key
	 *		could not be parsed to an integer.
	 *
	 *	\return
	 *		Either the configuration associated with
	 *		\em key, or \em fallback.
	 */
	public int GetInteger (String key, int fallback) {
	
		String value=Get(key);
		
		if (value==null) return fallback;
		
		try {
		
			return Integer.parseInt(value);
		
		} catch (NumberFormatException e) {	}
		
		return fallback;
	
	}
	
	
	/**
	 *	Sets a configuration, creating it if it
	 *	does not exist, updating it otherwise.
	 *
	 *	\param [in] key
	 *		The key associated with the configuration
	 *		to update or create.
	 *	\param [in] value
	 *		The value to associate with \em key.
	 */
	public void Set (String key, String value) {
	
		//	If the key is null, pass, that's
		//	meaningless
		if (key==null) return;
		
		//	If the value is null, that's actually
		//	an attempt to remove a configuration
		if (value==null) {
		
			kvp.remove(key);
		
			return;
		
		}
		
		//	Otherwise we update/insert the
		//	configuration
		kvp.put(key,value);
	
	}
	/**
	 *	Sets a configuration, creating it if it
	 *	does not exist, updating it otherwise.
	 *
	 *	\param [in] key
	 *		The key associated with the configuration
	 *		to update or create.
	 *	\param [in] value
	 *		The value to associate with \em key.
	 */
	public void Set (String key, double value) {
	
		Set(
			key,
			Double.toString(value)
		);
	
	}
	/**
	 *	Sets a configuration, creating it if it
	 *	does not exist, updating it otherwise.
	 *
	 *	\param [in] key
	 *		The key associated with the configuration
	 *		to update or create.
	 *	\param [in] value
	 *		The value to associate with \em key.
	 */
	public void Set (String key, int value) {
	
		Set(
			key,
			Integer.toString(value)
		);
	
	}
	
	
	/**
	 *	Unsets a configuration, removing it.
	 *
	 *	If the requested configuration didn't
	 *	exist, nothing happens.
	 *
	 *	\param [in] key
	 *		The key associated with the
	 *		configuration to remote.
	 */
	public void Unset (String key) {
	
		//	If the key is null, pass, that's
		//	meaningless
		if (key==null) return;
		
		//	Remove
		kvp.remove(key);
	
	}
	
	
	/**
	 *	Fetches an iterator which may be used
	 *	to traverse the configurations in this
	 *	provider in an unspecified order.
	 *
	 *	\return
	 *		An iterator.
	 */
	public Iterator<Configuration> iterator () {
	
		return new ConfigurationProviderIterator(kvp.entrySet().iterator());
	
	}


}