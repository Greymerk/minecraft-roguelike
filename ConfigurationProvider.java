package greymerk.roguelike;


/**
 *	Provides configuration information.
 */
public abstract class ConfigurationProvider {


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
	public abstract String Get (String key);
	
	
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
		
		try {
		
			return Integer.parseInt(value);
		
		} catch (NumberFormatException e) {	}
		
		return fallback;
	
	}


}