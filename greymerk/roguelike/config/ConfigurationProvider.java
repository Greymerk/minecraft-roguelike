package greymerk.roguelike.config;


import java.util.*;
import java.util.regex.*;


/**
 *	Provides configuration information.
 */
public abstract class ConfigurationProvider implements Iterable<Configuration> {


	private static final String true_regex="^\\s*(?:t(?:rue)?|y(?:es)?)\\s*$";
	private static final String false_regex="^\\s*(?:f(?:alse)?|no?)\\s*$";
	private static final String true_string="true";
	private static final String false_string="false";
	
	
	private Pattern true_pattern;
	private Pattern false_pattern;


	protected Hashtable<String,String> kvp;
	
	
	protected ConfigurationProvider () {
	
		kvp=new Hashtable<String,String>();
		true_pattern=Pattern.compile(true_regex);
		false_pattern=Pattern.compile(false_regex);
	
	}
	
	
	/**
	 *	Checks to see whether a configuration
	 *	setting exists.
	 *
	 *	\param [in] key
	 *		The key to check.
	 *
	 *	\return
	 *		\em true if \em key has associated
	 *		data in the configuration store,
	 *		\em false otherwise.
	 */
	public boolean ContainsKey (String key) {
	
		//	Null keys never exist
		if (key==null) return false;
		
		return kvp.get(key)!=null;
	
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
	 *	Gets a configuration setting as a boolean.
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
	public boolean GetBoolean (String key, boolean fallback) {
	
		String value=Get(key);
		
		if (value==null) return fallback;
		
		if (true_pattern.matcher(value).find()) return true;
		
		if (false_pattern.matcher(value).find()) return false;
		
		return fallback;
	
	}
	
	public List<Integer> GetListInteger (String key, List<Integer> fallback) {

		String value = Get(key);
		
		if(value == null) return fallback;
		
		String[] arrValues = value.split(",");
		
		List<String> values = Arrays.asList(arrValues);
		
		List<Integer> ints = new ArrayList<Integer>();
		
		for (String s : values) {
			try {
				ints.add(Integer.parseInt(s));
			} catch (NumberFormatException e) {	}
		}
			
		return ints;
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
	 *	Sets a configuration, creating it if it
	 *	does not exist, updating it otherwise.
	 *
	 *	\param [in] key
	 *		The key associated with the configuration
	 *		to update or create.
	 *	\param [in] value
	 *		The value to associate with \em key.
	 */
	public void Set (String key, boolean value) {
	
		Set(
			key,
			value ? true_string : false_string
		);
	
	}
	
	
	public void Set (String key, List<Integer> intList){
		
		String intString = "";
		
		for (Iterator it = intList.iterator() ; it.hasNext(); ) {
			
			if(!it.hasNext()) { // last
				intString += it.next().toString();
			}

			intString += it.next().toString() + ',';
		}
		
		Set(key, intString);
		
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