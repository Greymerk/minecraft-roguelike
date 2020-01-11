package greymerk.roguelike.config;


import java.io.BufferedReader;
import java.io.Writer;


/**
 * Specifies the interface to which configuration parsers must conform.
 *
 * Configuration parsers process raw strings from a configuration source, and transform it into a
 * set of key/value pairs.
 *
 * Configuration parsers process configuration data, and transform it back into the raw strings from
 * which it may be reparsed.
 */
public interface ConfigurationParser {


  /**
   * Parses one configuration from an input stream.
   *
   * \param [in] reader A reader which reads from a configuration source.
   *
   * \return A configuration key/value pair from \em reader.
   */
  Configuration Parse(BufferedReader reader) throws Exception;


  /**
   * Writes one configurations to an output stream.
   *
   * \param [in] writer A writer which writes to the destination. \param [in] config The
   * configuration to serialize.
   */
  void Write(Writer writer, Configuration config) throws Exception;


}