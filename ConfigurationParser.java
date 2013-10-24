package greymerk.roguelike;


import java.io.BufferedReader;


/**
 *	Specifies the interface to which configuration
 *	parsers must conform.
 *
 *	Configuration parsers process raw strings from
 *	a configuration source, and transform it into
 *	a set of key/value pairs.
 */
public interface ConfigurationParser {


	/**
	 *	Parses one configuration from an input
	 *	stream.
	 *
	 *	\param [in] reader
	 *		A reader which reads from a configuration
	 *		source.
	 *
	 *	\return
	 *		A configuration key/value pair from
	 *		\em reader.
	 */
	public Configuration Parse (BufferedReader reader) throws Exception;


}